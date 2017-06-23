package database.model;

import database.exception.*;

import java.util.*;
import java.text.*;

import java.lang.reflect.Field;
import java.lang.reflect.*;

/**
 * Simulates the Column of a Database, with some basic function that is balanced with constraints
 * and check.
 * The checks can only be added while the creation of the column.
 * @author Trang Vu
 * @author Le-Floch Thomas
 */
public class Attribute<V> {
    
    private ArrayList<Constraint> constraints;
    private HashMap<String,V> checks;
    private String name;
    private ArrayList<V> values;
    private int length;
    private Attribute<V> referenceKey;
    public DataType type;
    private Table referenceTable;
    private Table tab;
    
    /**
     * Initialise the name of the column and the list
     * @param name name of the column
     */
    public Attribute(Table tab, String name, int length, DataType type) throws SizeException {

	this.name = name;
	this.referenceKey = null;
	this.values = new ArrayList<V>();
	this.checks = new HashMap<String,V>();
	this.constraints = new ArrayList<Constraint>();
	this.type = type;
	this.referenceTable = null;
	this.tab = tab;
	
	if (length <= 0) {
	    throw new SizeException("Length of the column must be greater than 0");
	}
	else {
	    this.length = length;
	}
	    
    }
    
    /**
     * This method will return the name of the Column
     * @return name of the class
     */
    public String getName() {
	return this.name;
    }

    /**
     * This method allow to change the name of the column
     * @param name name that user want to change
     */
    public void setName(String name) throws NullPointerException {
	if(name != null ) 
	    this.name = name;
	else throw new NullPointerException("Name must not be null ");
    }
    
    /**
     * This method will return the length maximum that user can add 
     * @return length maximum that user can add
     */
    public int getLength() {
        return this.length;
    }

    /**
     * This method allow to change the length maximum that user can add. If length is 
     * smaller than zero, Throw an Exception
     * @param length length maximum that user can add
     */
    public void setLength(int length) throws SizeException {

	if ( length <= 0 )
	    throw new SizeException("Length must be greater than 0");
	else this.length = length;
    }

    /**
     * This method will return the values of the column
     * @return the value
     */
    public ArrayList<V> getValues() {

	return this.values ;
	
    }
    
    /**
     * This method will return the value of index i of the Column. It will return null if 
     * index is out of bounds
     * @param index index
     * @return the value
     */
    public V getValue(int index) throws SizeException {

	V ret = null;

	if (index < 0 || index >= values.size() ) throw new SizeException("Index out of bounds !");
	else ret = values.get(index);

	return ret;
	
    }
    
    /**
     * This method will first check if the value that is going to be added fit the constraint
     * . If yes, value will be added to Column. If not, the method will be cancelled
     * @param v value to be added
     */
    public void addValue(V v) throws ConstraintCheckViolationException {
	    
	    if( this.verifValue(v) ) {
		values.add(v);
	    }
	    
    }

    /**
     * Like addValue, but parameters passed is String
     */
    public void addValueString(String strValue) throws ConstraintCheckViolationException, NumberFormatException, ParseException {
	if (strValue == null ) this.addValue(null);
	else if ( this.getType() == DataType.INTEGER ) {
	   Integer v;
	    try {	
		v = Integer.valueOf(strValue.replaceAll(" ",""));
		this.addValue((V)v);
	    }
	    catch (NumberFormatException e) {
			
		throw new NumberFormatException("Invalid number !");
	    }
	}

	else if ( this.getType() == DataType.DOUBLE ) {
	    Double v;
					    
	    try {
		v = Double.valueOf(strValue.replaceAll(" ","").replaceAll(",","."));
		this.addValue((V)v);
	    }
	    catch (NumberFormatException e) {

		throw new NumberFormatException("Invalid number !");
	    }
	}

	else if ( this.getType() == DataType.CHAR ) {
	    this.addValue((V)strValue);
	}

	else if ( this.getType() == DataType.DATE ) {
	    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    Date v;
		    
	    try {
		
		v = df.parse(strValue.replaceAll(" ",""));
		this.addValue((V)v);

	    }
	    catch ( ParseException e) {
		throw new ParseException("Invalid value!",e.getErrorOffset());
	    }
	}
    }

    /**
     * This method will first check if index is > length or < 0 and then it will call
     * verifValues() to check the value is fit the constraint. If yes, the value will
     * be changed at the index
     * @param index index
     * @param v value
     */
    public void setValue(int index, V v) throws SizeException, ConstraintCheckViolationException {
	    
	if (index < 0 || index >= values.size()) throw new SizeException("Index is out of bounds !");
	else {
	    if ( this.verifValue(v) )
		this.values.set(index,v);
	}
	    
    }
    /**
     * addConstraint will allow the user to add Constraint into the column. The constraint
     * must not be already initialized. will be checked while entering value to Attribute.
     * If the constraint is REFRENCE KEY, the method will be canceled
     * @param constraint contraint to be added
     */
    public void addConstraint(Constraint constraint) throws ConstraintCheckViolationException {

	if (constraint != Constraint.REFERENCEKEY) {
	    if (this.verifExistConstraints(constraint)) {
		this.constraints.add(constraint);
	    }
	    else throw new ConstraintCheckViolationException("The constraint is existed !");
	}	
	else throw new ConstraintCheckViolationException("Add constraint failed !");
    }

    /**
     * This method allows to add a REFERENCE KEY Constraint by passing an attribute from 
     * another table in param. If the constriant is not REFERENCE key, the method will be
     * canceled and user will be called to use addConstraint(constraint)
     * @param constraint contraint to be added
     */
    public void addConstraint(Constraint constraint, Attribute<V> column) throws ConstraintCheckViolationException {
	
	if (constraint == Constraint.REFERENCEKEY) {
	    if (this.verifExistConstraints(constraint)) {
		boolean isPRIMARYKEY = false;
		for ( Constraint c : column.getAllConstraints() ) {
		    if (c == Constraint.PRIMARYKEY ) {
			isPRIMARYKEY = true;
		    }
		}

	        if (isPRIMARYKEY) {
		    this.constraints.add(constraint);
		    this.referenceKey = column;
		}
		else throw new ConstraintCheckViolationException("The reference key must be a primary key !!!");
		
	    }
	    else throw new ConstraintCheckViolationException("The constraint is existed !");
	}
	else throw new ConstraintCheckViolationException("Add constraint failed !");
		    
    }

    /**
     * This method will return the value of index i of the Constraint. It will return null if 
     * index is out of bounds
     * @param index index
     * @return the value
     */
    public Constraint getConstraint(int index) throws SizeException {
	Constraint ret = null;


	if (index < 0 || index > values.size() ) throw new SizeException("Index out of bounds !");
	else ret = constraints.get(index);

	return ret;
    }

    /**
     * Return all of the constraints
     * @return All of the constraints of Attribute
     */
    public ArrayList<Constraint> getAllConstraints() {

	return this.constraints;

    }

    /**
     * Return list of checks
     * @return list of the checks
     */
    public HashMap<String, V> getAllChecks() {

	return this.checks;

    }
    
    /**
     * This method will first check if index is > length or < 0 and then it will call
     * verifExistConstraint() to check  if the constraint is existed. If ok, the value will
     * be changed at the index
     * @param index index
     * @param c constraint
     */
    public void setConstraint(int index, Constraint c) throws SizeException, ConstraintCheckViolationException {
	    
	if (index < 0 || index >= values.size()) throw new SizeException("Index is out of bounds !");
	else {
	    if ( this.verifExistConstraints(c) )
		this.constraints.set(index,c);
	}
	    
    }
    
    /**
     * Add a Check to Attribute. An operator must be passed as a string and it's specified
     * ">" ">=" "<", "<=" "=". After check added, the check will be checked while entering 
     * a value of Attribute. Example "value >= 10"
     * @param operator Specified operator
     * @param value conditional value
     */
    public void addCheck(String operator, V value) {

	checks.put(operator,value);

    }

    /**
     * Verify when user do addValue that the value is fit with with the constraints and check
     * or not
     */
    protected boolean verifValue(V v) throws ConstraintCheckViolationException {

	boolean ret = true;

	if (v instanceof String || v instanceof Integer || v instanceof Double || v instanceof Date || v == null) {
	   
	    //Verify the constraints
	    for ( Constraint c : constraints ) {
	      
		switch (c) {

		case NOTNULL :
		    if (v==null) {
			ret = false;
			throw new ConstraintCheckViolationException("Value added must not be null !");
		    }
		    break;

		case PRIMARYKEY :

		    for ( V value : values ) {
			if ( value.equals(v) ) {
			    ret = false;
			    throw new ConstraintCheckViolationException("Value must be unique !");
			}
		    }
		    break;

		case REFERENCEKEY :
		    // Convert object ArrayList to V List 
		    ArrayList<V> valuesV = this.referenceKey.getValues();
		    boolean  m = false;
		
		    for ( V frValue : valuesV ) {
			if (frValue.equals(v)) {
			    m = true;
			}
		    }
		    if (m == false) {
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be contained in " +referenceKey.getName());
		    }
		    break;

		default :
		    break;
		    
		}

	    }

	    //Verify the checks
	    for ( Map.Entry<String,V> entry : checks.entrySet() ) {
		String ope = entry.getKey();
		V value = entry.getValue();

		if (v instanceof Integer || v instanceof Double) {
		    if ( ope.equals(">=") ) {
			if (((Number)v).doubleValue() < ((Number)value).doubleValue() ) {
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be greater than or equal to " + ((Number)value).doubleValue() );
			
			}
		    }
		    else if ( ope.equals("<=") ) {
			if (((Number)v).doubleValue() > ((Number)value).doubleValue() ) {
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be smaller than or equal to " +((Number)value).doubleValue() );
			
			}
		    }
		    else if ( ope.equals("=") ) {
			if (((Number)v).doubleValue() != ((Number)value).doubleValue() ) {
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be equal to " +((Number)value).doubleValue() );
			
			}
		    }
		    else if ( ope.equals("!=") ) {
			if (((Number)v).doubleValue() == ((Number)value).doubleValue() ) {
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be different from " +((Number)value).doubleValue() );
			
			}
		    }
		    else if ( ope.equals(">") ) {
			ret = false;
			if (((Number)v).doubleValue() <= ((Number)value).doubleValue() ) {
			    throw new ConstraintCheckViolationException("The value must be greater than " +((Number)value).doubleValue() );

			}
		    }
		    else if ( ope.equals("<") ) {
			ret = false;
			if (((Number)v).doubleValue() >= ((Number)value).doubleValue() ) {
			    throw new ConstraintCheckViolationException("The value must be smaller than " +((Number)value).doubleValue() );

			}
		    }
		    else {
			ret = false;
			throw new ConstraintCheckViolationException("Check must be >= <= > < != or =");
		    }
		}
	    
		else if (v instanceof String) {
		    if ( ope.equals("=") ) {
			if ( !((String)v).equals(value) ) {
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be equal to " + value );

			}
		    }
		    else if ( ope.equals("!=") ) {
			if ( ((String)v).equals(value) ) throw new ConstraintCheckViolationException("The value must be different from " + value );
		    }
		    else {
			ret = false;
			throw new ConstraintCheckViolationException("Check must be = or !=");
		    
		    }
		}

		else if (v instanceof Date) {

		    Calendar calV = Calendar.getInstance();
		    calV.setTime((Date)v);
		
		    if ( ope.equals(">=") ) {
			if ( ((Date)v).compareTo( (Date)value) < 0 ){
			    ret = false;
			    throw new ConstraintCheckViolationException("The value must be before" + calV.get(Calendar.DAY_OF_MONTH) + "/" + calV.get(Calendar.MONTH) + "/" + calV.get(Calendar.YEAR) );

			}
		    
			if ( ope.equals("<=") ) {
			    if ( ((Date)v).compareTo( (Date)value) > 0 ) {
				ret = false;
				throw new ConstraintCheckViolationException("The value must be after" + calV.get(Calendar.DAY_OF_MONTH) + "/" + calV.get(Calendar.MONTH) + "/" + calV.get(Calendar.YEAR) );

			    }
			}
			if ( ope.equals("=") ) {
			    if ( ((Date)v).compareTo( (Date)value) != 0 ) {
				ret = false;
				throw new ConstraintCheckViolationException("The value must be " + calV.get(Calendar.DAY_OF_MONTH) + "/" + calV.get(Calendar.MONTH) + "/" + calV.get(Calendar.YEAR) );

			    }
			}
			if ( ope.equals("!=") ) {
			    if ( ((Date)v).compareTo( (Date)value) == 0 ) {
				ret = false;
				throw new ConstraintCheckViolationException("The value must be before" + calV.get(Calendar.DAY_OF_MONTH) + "/" + calV.get(Calendar.MONTH) + "/" + calV.get(Calendar.YEAR) );

			    }
			}
			else {
			    ret = false;
			    throw new ConstraintCheckViolationException("Check must be >= <= = or !=");

			}
		
		    }

		}
	    }

	    //Check the length's violation for String, Integer, Double
	    if (v instanceof String) {
		if (((String)v).length() > this.length) {
		    ret = false;
		    throw new ConstraintCheckViolationException("The value meet the length violation !");
		}
	    }
	    else if (v instanceof Integer || v instanceof Double) {
		if (((Number)v).toString().length() > this.length) {
		    ret = false;
		    throw new ConstraintCheckViolationException("The value meet the length violation !");
		}
	    }
	}
	else {
	    ret = false;
	    throw new ConstraintCheckViolationException("The value must be Integer, Double, String or Date");
	}
	return ret;
    }
    
    /**
     * The method will be called at addConstraint, to make sure that the constraint has
     * already been added or not. return true if it's not existed yet
     * @param constraint constraint to be tested
     * @return true if it's not existed yet
     */
    private boolean verifExistConstraints(Constraint constraint) {
	
	boolean ret = true;
	
	for (Constraint addedConstraint : this.constraints) {
	    if ( addedConstraint == constraint ) ret = false;
	}

	return ret;
    }

   
    /**
     * Return the type of Attribute. 
     * @return attribute's datatype
     */
    public DataType getType() {
	return this.type;
    }

    public void delConstraint(int index) throws SizeException{

	if ( index < 0 || index >= constraints.size() )
	    throw new SizeException("index out of bound");
	else
	    constraints.remove(index);

    }

    public void delCheck(String ope) throws ConstraintCheckViolationException {
	
	V available = checks.remove(ope);
	
	if (available == null)
	    throw new ConstraintCheckViolationException("Failed to delete check");

    }

    protected void delValue(int index) throws SizeException {

	if ( index < 0 || index >= values.size() )
	    throw new SizeException("index out of bound");
	else
	    values.remove(index);
		
    }

    public Attribute<V> cloneAttribute() throws SizeException{
	return new Attribute(this.tab, this.name, this.length, this.type);
    }
   
    public String toStringValue(int i) throws SizeException {

	String ret = null;

	if ( i < 0 || i >= values.size() )
	    throw new SizeException("index out of bound");
	else {
	
	    if( this.getType() == DataType.DATE ) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ret = sdf.format(values.get(i));

	    }
	    else {
		ret = values.get(i).toString();
	    }
	}

	return ret;
    }

    public ArrayList<String> toStringValues() {
 	
	ArrayList<String> ret = new ArrayList<String>();

	for ( V v : values ) {
     
	    if( this.getType() == DataType.DATE ) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ret.add(sdf.format(v));

	    }
	    else {
		ret.add(v.toString());
	    }
	    
	}

	return ret;
					    
    }

    public void setReferenceTable(Table tab) { this.referenceTable = tab; }
    public Table getReferenceTable() { return this.referenceTable; }
    public Attribute<V> getReferenceKey() { return this.referenceKey; }
    public Table getTable() { return this.tab; }

}
