package database.model;

import database.exception.*;
import java.text.*;

import java.sql.*;
import java.util.*;
import oracle.jdbc.OracleConnection;

/**
 * This class hold the connection of the database. It allows to connect,
 * login, create user, log out, and execute the codes that and transfer
 * the databse on the server into server
 * @author TRANG Hoang Phong Vu
 * @author 
 */ 
public class Server {

    private Connection connection;
    private Statement stmt;
    private String username;
    private String password;
    private Database database;
    private boolean connected;
    private final String ADMIN = "learntofly1";
    private final String PASSWORD = "zczccczczzzcc";

    /**
     * Initialise the user and the password, then connecting to the
     * server. If the user want to create a user, It can be done by
     * loging in as administrator.
     * @param username user's name
     * @param password password
     */ 
    public Server(String username, String password) throws ClassNotFoundException, SQLException {

	this.username = username;
	this.password = password;
	this.connected = false;
	this.database = new Database("HI");
	
	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	}
	catch(ClassNotFoundException e) {
	    throw new ClassNotFoundException("Oracle JDBC driver not found !");
	}

	this.connect();

    }

    public void connect() throws SQLException {
		
	try {
	    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", this.username, this.password);
	}
	catch (SQLException e) {
	    throw new SQLException("Connection failed!");
	}

	if(connection != null) {
	    connected = true;
	    try {
	    stmt = connection.createStatement();
	    }
	    catch ( SQLException e) { throw new SQLException("Cannot create statement"); }
	    	    
	}
	else {
	    connected = false;
	}

    }

    public void finish() throws SQLException {
	
	try {
	    if (stmt != null ) { stmt.close(); };
	    if (connection != null) { connection.close(); }
	}
	catch (SQLException e) { throw new SQLException("Close failed !"); }
	
    }

    public boolean isConnected() {
	
	return connected;

    }

    public void changeSession(String user, String password) throws SQLException{
	//Stock previous usr and password
	    String usr,pw;
	    usr = this.username;
	    pw = this.password;
	    
	try {	    
	    this.username = user;
	    this.password = password;
	    this.finish();
	    this.connect();
	}

	catch (SQLException e ) {
	    this.username = usr;
	    this.password = pw;
	    this.connect();
	    throw new SQLException("Change session failed !"); }

    }
    /**
     * Add a user into the server. This method can only be used by
     * administrator of the database
     * @param username  username
     * @param password password
     */
    public void createUser(String username, String password) throws SQLException{
        try {
	    executeCode("CREATE USER "+username+" IDENTIFIED BY "+password);
	    executeCode("GRANT ALL privileges TO "+ username);
	    executeCode("GRANT CONNECT TO "+ username);
	}
	catch (SQLException e) {
	    throw new SQLException("You are not allowed to create new user !" );
	}

    }

    /**
     * Execute the code SQL passed in param
     * @param code code SQL
     */
    public ResultSet executeCode(String code) throws SQLException {
	
	ResultSet rs = null;
	
	try {
	    stmt = connection.createStatement();
	    rs = stmt.executeQuery(code);
	}
	catch (SQLException e) { throw new SQLException("Error, cannot execute the code"+ e.getMessage()); }
	
	return rs;
    }

    /**
     * Export the datas on the server into class Database
     */
    public void exportDatabaseTables() throws SQLException, SizeException, ConstraintCheckViolationException , TypeException, ParseException{

	ResultSet rs = this.executeCode("SELECT * FROM tab");
	
	while (rs.next()) {

	    String tableStr = rs.getString("TNAME");
	    String tableType = rs.getString("TABTYPE");

	    if  (tableType.equals("TABLE")) {

		Table tab = new Table(tableStr);
		
		ResultSet rs2 = this.executeCode("SELECT * FROM " + tableStr.toUpperCase());
		ResultSetMetaData rsmd = rs2.getMetaData();

		// Inform columns information ( name, type and length) 
		for( int i = 1 ; i <= rsmd.getColumnCount() ; i++) {

		    String columnName = rsmd.getColumnName(i);
		    DataType columnDataType = null;
		    ArrayList<Constraint> columnConstraint = new ArrayList<Constraint>();
		    int columnLength=0;
		    String type="" ;

		    ResultSet rsType = executeCode("SELECT data_type FROM all_tab_columns WHERE table_name=\'"+tableStr+"\' AND column_name=\'"+columnName+"'");

		    while (rsType.next()) {
			type =rsType.getString("data_type");
		    }
		   
		    if ( type.equals("NUMBER") ) {
		        columnDataType = DataType.INTEGER;

		    }
		    else if ( type.equals("FLOAT") ) {
		        columnDataType = DataType.DOUBLE;
		    }
		    else if ( type.equals("VARCHAR2") ) {
		        columnDataType = DataType.CHAR;

		    }
		    else if ( type.equals("DATE") ) {
		        columnDataType = DataType.DATE;
		    }
		    else {
			throw new SQLException("Failed to transfer !");
		    }
		    ResultSet rs3 = this.executeCode("SELECT DATA_LENGTH FROM USER_TAB_COLUMNS WHERE TABLE_NAME = \'"+tableStr.toUpperCase()+"\' AND COLUMN_NAME=\'"+columnName.toUpperCase()+"\'");
		    
		    while(rs3.next()) {
			columnLength = rs3.getInt("DATA_LENGTH");
		    }
		    //rs3.close();
		    // add Attribute to table 
		    tab.addAttribute( columnName, columnDataType, columnLength );
		   		    
		    ResultSet rs4 = this.executeCode("SELECT "+columnName.toUpperCase()+" FROM "+ tableStr.toUpperCase());
		    		    
		    while(rs4.next()) {
			String valueStr = rs4.getString(columnName);
		        
			tab.getAttribute( tab.getColumn(columnName) ).addValueString(valueStr);
		    }
		   
		    
		}
        
		database.addTable(tab);

	    }
	}
    }

    public void exportDatabaseConstraints() throws SQLException,SizeException,ParseException,TypeException,ConstraintCheckViolationException {

	ResultSet rs = this.executeCode("SELECT * FROM tab");
	
	while (rs.next()) {

	    String tableStr = rs.getString("TNAME");
	    String tableType = rs.getString("TABTYPE");


	    if  (tableType.equals("TABLE")) {

		Table tab = this.getDatabase().getTable( this.database.getTableId(tableStr) );
			
		ResultSet rs2 = this.executeCode("SELECT * FROM " + tableStr.toUpperCase());
	
		ResultSetMetaData rsmd = rs2.getMetaData();

		// Inform columns information ( name, type and length) 
		for( int i = 1 ; i <= rsmd.getColumnCount() ; i++) {

		    String columnName = rsmd.getColumnName(i);
		    DataType columnDataType = null;
		    ArrayList<Constraint> columnConstraint = new ArrayList<Constraint>();
		    int columnLength=-1;
	 
		    //Constraint and check
		    ResultSet rs4 = this.executeCode("SELECT A1.CONSTRAINT_NAME,CONSTRAINT_TYPE, SEARCH_CONDITION FROM ALL_CONS_COLUMNS A1, ALL_CONSTRAINTS A2 WHERE A1.CONSTRAINT_NAME = A2.CONSTRAINT_NAME AND A2.TABLE_NAME = \'"+tableStr.toUpperCase()+"\' AND A1.COLUMN_NAME = \'"+columnName.toUpperCase()+"\'");
		    while ( rs4.next() ) {
			
			String constraintType = rs4.getString("CONSTRAINT_TYPE");
			String searchCondition = rs4.getString("SEARCH_CONDITION");

	 
			if ( constraintType.equals("P") )
			    tab.addConstraint( tab.getColumn(columnName),Constraint.PRIMARYKEY);
			else if ( constraintType.equals("C") ) {

			    //--------------------------------------------
			    if (searchCondition.contains("IS NOT NULL")) {
				tab.addConstraint( tab.getColumn(columnName),Constraint.NOTNULL);
			    }
			    else {
				
				String[] subSearchCondition = searchCondition.split("(AND)|(and)|(OR)|(or)");
				for ( int j = 0 ; j < subSearchCondition.length ; j++ ) {
				
				    int supegal = searchCondition.indexOf(">=");
				    int infegal = searchCondition.indexOf("<=");
				    int egal = searchCondition.indexOf("==");
				    int sup = -1;
				    int inf = -1;

				    if (subSearchCondition[j].indexOf(">") != -1) {
					if ( subSearchCondition[j].charAt(subSearchCondition[j].indexOf(">")+1) != '=' ) sup = subSearchCondition[j].indexOf(">");				    
				    }
				    if (subSearchCondition[j].indexOf("<") != -1) {
				    if ( subSearchCondition[j].charAt(subSearchCondition[j].indexOf("<")+1)!= '=' ) sup = subSearchCondition[j].indexOf("<");				    
				    }

				    if ( supegal != -1 ) {
					String strValue = subSearchCondition[j].substring( subSearchCondition[j].indexOf(">=")+2);
				        tab.addCheck( tab.getColumn(columnName),">=",strValue );
				    }
				    else if ( infegal != -1 ) {
					String strValue = subSearchCondition[j].substring( subSearchCondition[j].indexOf("<=")+2);
				        tab.addCheck( tab.getColumn(columnName),"<=",strValue );
				    }
				    else if ( egal != -1 ) {
					String strValue = subSearchCondition[j].substring( subSearchCondition[j].indexOf("==")+2);
				        tab.addCheck( tab.getColumn(columnName),"==",strValue );
				    }
				    else if ( supegal != -1 ) {
					String strValue = subSearchCondition[j].substring( subSearchCondition[j].indexOf(">")+1);
				        tab.addCheck( tab.getColumn(columnName),">",strValue );
				    }
				    else if ( supegal != -1 ) {
					String strValue = subSearchCondition[j].substring( subSearchCondition[j].indexOf("<")+1);
				        tab.addCheck( tab.getColumn(columnName),"<",strValue );
				    }
				} // end of for 
			    }
			} // end of else if

			else if ( constraintType.equals("R") ) {
			    ResultSet rs5 = this.executeCode("SELECT A3.TABLE_NAME, A3.COLUMN_NAME FROM ALL_CONS_COLUMNS A1, ALL_CONSTRAINTS A2, ALL_CONS_COLUMNS A3 WHERE A1.constraint_name = A2.constraint_name AND constraint_type = 'R' AND a1.column_name = \'"+ columnName.toUpperCase() +"\' AND a1.table_name = \'"+ tableStr.toUpperCase() +"\' AND A3.constraint_name=A2.r_constraint_name");
			    String rTableName = rs5.getString("TABLE_NAME");
			    String rColumnName = rs5.getString("COLUMN_NAME");			

			    tab.addConstraint( tab.getColumn(columnName), Constraint.REFERENCEKEY, database.getTable ( database.getTableId( tableStr ) ).getAttribute( tab.getColumn(rColumnName) ) );
			}

			
		    }
		    //----------------------------------------------------

		} // end of if 
		
	    }// end of while
	    
	}
    }

    public void exporttoDatabase() throws  SQLException,SizeException,ParseException,TypeException,ConstraintCheckViolationException, ParseException{

	this.exportDatabaseTables();
	this.exportDatabaseConstraints();

    }
    /**
     * Input the data from class Database into the server
     */
    public void importFromDatabase() throws SizeException, SQLException, ConstraintCheckViolationException {
		
	ResultSet rs = executeCode("SELECT * FROM tab");
	// Delete all the table on the server
	while (rs.next()) {
	    String tableName = rs.getString("TNAME");
	    String tableType = rs.getString("TABTYPE");
	    if ( tableType.equals("TABLE") ) {
		executeCode("DROP TABLE "+tableName+" CASCADE CONSTRAINTS");
	    }
	}
	// Create
	String code = "";
	for(int i = 0 ; i < database.sizeTable() ; i++ ) {
	    
	    code += "\nCREATE TABLE ";
	    code += database.getTableName(i) + "( ";

	    
	    for ( int j = 0 ; j < database.getTable(i).sizeAttribute(); j++ ) {
		Attribute att = database.getTable(i).getAttribute(j);
	        
	        code += att.getName()+ " ";
		
		String typeAtt = "";
		
		if (att.getType() == DataType.INTEGER ) {
		    typeAtt = "INTEGER ";
		}
		else if(att.getType() == DataType.DOUBLE ) {
		    typeAtt = "DOUBLE PRECISION ";
		}
		else if(att.getType() == DataType.CHAR) {
		    typeAtt = "VARCHAR2";
		    typeAtt += "("+att.getLength()+") ";
		}
		else if(att.getType() == DataType.DATE) {
		    typeAtt = "DATE ";
		}

		code += typeAtt;

		String checkAtt = "";
		HashMap<String, ?> checks = att.getAllChecks();
		for (Map.Entry<String, ?> entry : checks.entrySet() ) {
		    String ope = entry.getKey();
		    String value;
		    if ( att.getType() != DataType.DATE ) {
			value = entry.getValue().toString();
			
		    }
		    else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			
			value = "to_date(\'"+sdf.format(entry.getValue())+"\',\'DD-MM-YYYY\') ";
		    }
		    checkAtt += "CHECK (";
		    checkAtt += att.getName()+" "+ope+" "+ value +") ";
		}

		code += checkAtt;

		
		ArrayList<Constraint> constraints = att.getAllConstraints();
		String constraintAtt = "";
		for ( Constraint constraint : constraints ) {

		    if ( constraint == Constraint.NOTNULL ) {
			constraintAtt += "NOT NULL ";
		    }
		    else if ( constraint == Constraint.PRIMARYKEY) {
			constraintAtt += "PRIMARY KEY ";
		    }
		    else if ( constraint == Constraint.REFERENCEKEY ) {
			constraintAtt += ", FOREIGN KEY ("+ att.getName()+") REFERENCES "+ att.getReferenceTable().getName()+"("+att.getReferenceKey().getName()+") ";
		    }
		}

		code += constraintAtt+", ";

	    }

	    code = code.substring(0,code.lastIndexOf(','))+" )";
	    this.executeCode(code);
	    code = "";
	}
	
	for ( int i = 0 ; i < database.sizeTable() ; i++ ) {
	    for ( int j = 0 ; j < database.getTable(i).getNumTUple(); j++ )  {
		String insert = "\nINSERT INTO "+ database.getTable(i).getName()+" VALUES ( ";
		ArrayList<Attribute> tuple = database.getTable(i).getTuple(j);
		
		for ( Attribute att : tuple ) {
		    if (att.getType() == DataType.CHAR ) { insert += "\'"+att.toStringValue(0)+"\'"; }
		    else if(att.getType() == DataType.INTEGER || att.getType() == DataType.DOUBLE) {
			insert += att.toStringValue(0);
		    }
		    else if (att.getType() == DataType.DATE) { insert += "to_date(\'"+att.toStringValue(0)+"\',\'DD/MM/YYYY\')"; }
		    insert +=",";
		}

		code += insert;
		
		code = code.substring(0,code.lastIndexOf(','))+" )";

		this.executeCode(code);
		code ="";
		
	    }
	}
	//System.out.println(code);
	//this.executeCode(code);
	    
    }

    /**
     * Return the database exported into Dabase
     * @return The database
     */
    public Database getDatabase() {
	return this.database;
    }

}
