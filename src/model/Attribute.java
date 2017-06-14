package database.model;

import database.exception.*;

import java.util.*;

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
    
    /**
     * Initialise the name of the column and the list
     * @param name name of the column
     */
    public Attribute(String name, int length) throws SizeException {

	this.name = name;
	this.referenceKey = null;
	this.values = new ArrayList<V>();
	this.checks = new HashMap<String,V>();
	this.constraints = new ArrayList<Constraint>();

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
    public void setName(String name) {
	try {
	    this.name = name;
	}
	catch (NullPointerException npe) {
	}
	finally {
	    if (name != null ) {
		this.name = name;
	    }
	}
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

	if (index < 0 || index > values.size() ) throw new SizeException("Index out of bounds !");
	else ret = values.get(index);

	return ret;
	
    }
    
    /**
     * This method will first check if the value that is going to be added fit the constraint
     * . If yes, value will be added to Column. If not, the method will be cancelled
     * @param v value to be added
     */
    public void addValue(V v) throws ConstraintCheckViolationException, NullPointerException {
	    
	if ( v == null ) throw new NullPointerException("Added value must be initialized !");
	else {
	    if( this.verifValue(v) ) {
		values.add(v);
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
		this.constraints.add(constraint);
		this.referenceKey = column;
	    }
	    else throw new ConstraintCheckViolationException("The constraint is existed !");
	}
		    
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
    private boolean verifValue(V v) throws ConstraintCheckViolationException {

	boolean ret = true;

	if (v instanceof String || v instanceof Integer || v instanceof Double || v instanceof Date) {

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
		for ( V value :  values ) {
		    if ( value.equals(v) ) {
			ret = false;
			throw new ConstraintCheckViolationException("Value must be unique !");
		    }
		}
		break;

	    case REFERENCEKEY :
		// Convert object ArrayList to V List 
	        ArrayList<V> valuesV = this.referenceKey.getValues();

		
		for ( V frValue : valuesV ) {
		    if (!frValue.equals(v)) {
			ret = false;
			throw new ConstraintCheckViolationException("The value must be contained in "+referenceKey.getName());
		    }
		    break;
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
 	    if (((String)v).length() >= this.length) {
		ret = false;
		throw new ConstraintCheckViolationException("The value meet the length violation !");
	    }
	}
	else if (v instanceof Integer || v instanceof Double) {
	    if (((Number)v).toString().length() >= this.length) {
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

    
}
