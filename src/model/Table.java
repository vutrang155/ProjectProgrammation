package database.model;

import java.util.*;
import database.exception.*;
import java.text.*;

/**
 * The class simulates the Table of the database, in which columns are added and managed 
 * as an Arraylist. Also, the class allows user to insert t-uples and basic constraint
 * to column.
 * The checks can only be added while the creation of the column.
 * @author TRANG Hoang Phong Vu
 * @author 
 */
public class Table {

    private String name;
    private ArrayList<Attribute> columns;
    private int numTUple;

    /**
     * Initialise the table
     * @param name name of the table
     */
    public Table(String name) {
	this.name = name;
	this.columns = new ArrayList<Attribute>();
	numTUple = 0; 
    }

    /**
     * Change the table's name
     * @param name name that user want to change to
     */ 
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Return table's name
     * @return name
     */
    public String getName() {
	return this.name;
    }
    

    /**
     * Allows user to add a column (attribute) to the table, DataType can be CHAR, 
     * NUMBER, or DATE. for Date type, the length will be automatically 1, the  length
     * passed in parameters will be canceled
     * @param nameAttribute name of the column
     * @param type type of the column, CHAR, NUMBER, or DATE
     * @param length length maximum that a value can be added
     */
    public void addAttribute( String nameAttribute, DataType type, int length) throws SizeException, TypeException {

	if (type == DataType.CHAR)
	    columns.add(new Attribute<String>(this,nameAttribute, length, DataType.CHAR));

	else if (type == DataType.INTEGER)
	    columns.add(new Attribute<Integer>(this,nameAttribute, length, DataType.INTEGER));

	else if (type == DataType.DOUBLE)
	    columns.add(new Attribute<Double>(this,nameAttribute, length, DataType.DOUBLE));
 
	else if (type == DataType.DATE)
	    columns.add(new Attribute<Date>(this,nameAttribute, length, DataType.DATE));

	else
	    throw new TypeException("The type is not exist ! ( try CHAR, INTEGER, DOUBLE, DATE )");
	
    }

    /**
     * The method will first verify that the attributes' member is the size 1 or not.
     * If yes, a t-uple will be initialised to the table. If not, initialisation of 
     * tuple will be cancelled
     * @param attributes list of attributes whose elements have just 1 value
     */
    public void insertTUple(ArrayList<Attribute> attributes ) throws ConstraintCheckViolationException, NullPointerException, ColumnException, SizeException{

	if ( !verifyTUple(attributes) ) {

	    throw new ColumnException("T-uples are added wrongly !");
	}
	else {

	    for ( int i = 0; i < attributes.size(); i++ ) {
		for ( int j = 0 ; j < attributes.get(i).getValues().size() ; j ++) {
		    columns.get(i).addValue( attributes.get(i).getValue(j) );
		  
		}
	    }
	    numTUple++;
	}
    }

    /**
     * Delete a line of the table
     * @param line line that user want to delete
     */
    public void delTUple( int line ) throws ConstraintCheckViolationException, SizeException {
	
       	if (line < 0 || line >= this.columns.get(0).getValues().size() ) {
	    throw new SizeException("line is not correct !");
	}
	else {
	    for ( Attribute att : this.columns ) {
		att.delValue(line);
	   }
	    numTUple--;
	}
	
    }

    /**
     * Delete a column
     * @param column column's number
     */
    public void delColumn( int column ) throws SizeException{

	if (column < 0 || column >= this.columns.size() ) {
	    throw new SizeException("column not correct !");
	}
	else {
	    this.columns.remove(column);
	}

    }
    
    /**
     * Allows users to edit the value of a t-uple by enter the place and the value
     * as a String. If the index or column meet the violation (smaller than 0, out
     * of bounds), the editValue will be canceled 
     * @param index coordinate of the tuple
     * @param column coordinate of the column in the tuple
     * @param strValue value as string
     */
    public void editValue(int index, int column, String strValue ) throws SizeException, NumberFormatException, SizeException,ConstraintCheckViolationException, ParseException {

	if(columns.size() <= column) throw new SizeException("column inputted must be smaller than number of columns");

	else {
	    if ( index >= columns.get(column).getValues().size() )
		throw new SizeException("index inputted must be smaller than number of indexes");
	    else {

		Attribute att = columns.get(column);

		if ( att.getType() == DataType.INTEGER ) {
		    Integer v;
					    
		    try {
			v = Integer.valueOf(strValue.replaceAll(" ","").replaceAll(".","").replaceAll(",","."));
			columns.get(column).setValue(index, v);
		    }
		    catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid number !");
		    }
		}

		else if ( att.getType() == DataType.DOUBLE ) {
		    Double v;
					    
		    try {
			v = Double.valueOf(strValue.replaceAll(" ","").replaceAll(",","."));
			att.setValue(index, v);
		    }
		    catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid number !");
		    }
		}

		else if ( att.getType() == DataType.CHAR ) {
		    att.setValue(index, strValue);
		}

		else if ( att.getType() == DataType.DATE ) {

		    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    Date v;
		    
		    try {
			
			v = df.parse(strValue.replaceAll(" ",""));
			att.setValue(index, v);

		    }
		    catch ( ParseException e) {
			throw new ParseException("Invalid value!",e.getErrorOffset());
		    }
		}						
	    }
	}
    }
    
    /**
     * Add constraint to the column. If column meet the violation (smaller than 
     * 0, out of bounds), The method will be canceled. If the constraint is 
     * REFERENCEKEY, tha method will be canceled. User must call the method
     * addConstraint(int,Constraint,Attribute)
     * @param order the place of the column
     * @param constraint constraint that user want to add
     */
    public void addConstraint (int column, Constraint constraint ) throws SizeException, ConstraintCheckViolationException {
	
	if(columns.size() <= column || column < 0 ) throw new SizeException("column inputted must be smaller than number of columns");

	else {
	    if ( constraint == Constraint.REFERENCEKEY )
		throw new ConstraintCheckViolationException("Add constraint failed !");
	    else {
		columns.get(column).addConstraint(constraint);
	    }
	}
	
    }

    /**
     * Add REFERENCEKEY constraint to the column. If column meet the violation (smaller
     * than 0, out of bounds), The method will be canceled. If the constraint is not
     * a REFERENCEKEY constraint, the method will be canceled and user will be called
     * to user addConstraint(int,Constraint)  
     * @param order the place of the column
     * @param constraint constraint that user want to add
     * @param attribute attribute from another table
     */
    public void addConstraint (int column, Constraint constraint, Attribute<?> attribute) throws ConstraintCheckViolationException, SizeException {
	if(columns.size() <= column || column < 0) throw new SizeException("column inputted must be smaller than number of columns");

	else {
	    if ( constraint != Constraint.REFERENCEKEY )
		throw new ConstraintCheckViolationException("Add constraint failed!");
	    else {
		if (attribute.getType() == columns.get(column).getType()) {
		    columns.get(column).addConstraint(constraint, attribute);
		    columns.get(column).setReferenceTable(attribute.getTable());
		}
		else throw new ConstraintCheckViolationException("The reference key must be the same type ");
	    }
	}
	
    }

    /**
     * Add a check to Column
     */
    public void addCheck(int column, String ope, String strValue) throws SizeException, NumberFormatException, ParseException {

	if(columns.size() <= column || column < 0) throw new SizeException("column inputted must be smaller than number of columns");

	else {
	    
		Attribute att = columns.get(column);

		if ( att.getType() == DataType.INTEGER ) {
		    Integer v;
					    
		    try {
			v = Integer.valueOf(strValue.replaceAll(" ",""));
			att.addCheck(ope,v);
		    }
		    catch (NumberFormatException e) {//
			throw new NumberFormatException("Invalid number !");
		    }
		}

		else if ( att.getType() == DataType.DOUBLE ) {
		    Double v;
					    
		    try {
			v = Double.valueOf(strValue.replaceAll(" ","").replaceAll(",","."));
			att.addCheck(ope,v);
		    }
		    catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid number !");
		    }
		}

		else if ( att.getType() == DataType.CHAR ) {
		    att.addCheck(ope,strValue);
		}

		else if ( att.getType() == DataType.DATE ) {

		    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    Date v;
		    
		    try {
			
			v = df.parse(strValue.replaceAll(" ",""));
			att.addCheck(ope,v);
					
		    }
		    catch ( ParseException e) {
			throw new ParseException("Invalid value!",e.getErrorOffset());
		    }
		}						
	    }
    }

    /**
     * Return the column by entering the column of the column. If number of the 
     * column meet the violation (smaller than 0, out of bounds), the method 
     * will be canceled
     * @param column the order of the column
     */
    public Attribute getAttribute(int column) throws SizeException {

	Attribute ret = null;
	if(columns.size() <= column)
	    throw new SizeException("column inputted must be smaller than number of columns");
	else {
	    ret = columns.get(column);
	}

	return ret;
    }

    public int sizeAttribute() {
	return columns.size();
    }

    /**
     * Return the column's order by passing in param the name of the column
     * @param strColumnName name of the column
     * @return the column's order
     */
    public int getColumn( String strColumnName ) {

	int ret = -1;
	for(int i = 0 ; i < columns.size(); i++ ) {
	    if (strColumnName.toUpperCase().trim().equals(columns.get(i).getName().toUpperCase().trim())) {
		ret = i;
	    }
	}

	return ret;
    }

    /**
     * Verify that the columns added are the same size or not. Return true if yes
     * @param attributes columns
     * @return true if columns added are the same size
     */
    private boolean verifyTUple (ArrayList<Attribute> attributes ) {

	boolean ret = true;

	for ( int i = 0 ; i < attributes.size()-1; i++ ) {

	    if ( attributes.get(i+1).getValues().size() != attributes.get(i).getValues().size() ) {
       
		ret = false;

	    }
	 
	}


	if (ret == true && attributes.size() == columns.size()) {

	    for (int i = 0 ; i < attributes.size() ; i++ ) {
		if (attributes.get(i).getType() != columns.get(i).getType() )  {
		
		    ret = false;
		}
	    }
	}
	else ret = false;

	if ( ret == true ) {
	    for ( Attribute att : attributes ) {
		if (att.getValues().size() > 1) {
		    ret = false;
		}
	    }
	}
	
	return ret;
		
    }

    /**
     * Return the columns with 0 value, served for adding a tuple
     * @return columns with 0 value
     */
    public ArrayList<Attribute> tuple() throws SizeException{

	ArrayList<Attribute> ret = new ArrayList<Attribute>();

	for ( Attribute att : columns) {
	    ret.add( att.cloneAttribute() );
	}

	for ( Attribute att : ret ) {
	    att.getValues().clear();
	    att.getAllConstraints().clear();
	    att.getAllChecks().clear();
	}

	return ret;
    }

    public ArrayList<Attribute> getTuple(int index) throws SizeException, ConstraintCheckViolationException {


	ArrayList<Attribute> ret = this.tuple();
	if ( index < 0 || index >= numTUple ) throw new SizeException("index out of bounds !");
	else {
	    for ( int i = 0; i < ret.size() ; i++  ) {
		ret.get(i).addValue( columns.get(i).getValue(index) );
	    }
	}

	return ret;
    }

    public int getNumTUple() {
	return numTUple;
    }

}
