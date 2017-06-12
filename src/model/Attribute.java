package database.model;

import database.exception.*;

/**
 * Simulates the Column of a Database, with some basic function that is balanced with constraints
 * and check.
 * The checks can only be added while the creation of the column.
 * @author Trang Vu
 * @author Le-Floch Thomas
 */
public class Attribute<V> {
    
    private ArrayList<Constraint> constraints;
    private HashMap<String,V> checks
    private String name;
    private ArrayList<V> values;
    private int length;
    private Attribute referenceKey;
    
    /**
     * Initialise the name of the column and the list
     * @param name name of the column
     */
    public Attribute(String name, int length) throws SizeException {

	this.name = name;
	this.referenceKey = null;
	this.values = new ArrayList<V>();
	this.checks = new HashMap<String,V>();
	this.constraint = new ArrayList<Constraint>();

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
	this.name = name;
    }
    
    /**
     * This method will return the length maximum that user can add 
     * @return length maximum that user can add
     */
    public int getLength() {
        return this.length
    }

    /**
     * This method allow to change the length maximum that user can add. If length is 
     * smaller than zero, Throw an Exception
     * @param length length maximum that user can add
     */
    public void setLength(int length) throws SizeException {

	if ( length <= 0 ) throw new SizeException("Length must be greater than 0");
	else this.length = length
    }

     /**
     * This method will return the values of the column
     * @return the value
     */
    public ArrayList<V> getValue() {
	return this.values ;
    }
    
    /**
     * This method will return the value of index i of the Column. It will return null if 
     * index is out of bounds
     * @param index index
     * @return the value
     */
    public V getValue(int index) {
	return values.get(index);
    }

    /**
     * This method will first check if the value that is going to be added fit the constraint
     * . If yes, value will be added to Column. If not, the method will be cancelled
     * @param v value to be added
     */
    protected void addValue(V v) throws NullPointException {
	if ( v == null ) throw new NullPointException("Added value must be initialized !");
	else {
	    
    }

    /**
     * This method will first check if index is > length or < 0 and then it will call
     * verifValues() to check the value is fit the constraint. If yes, the value will
     * be changed at the index
     * @param index index
     * @param v value
     */
    protected void setValue(int index, V v) {}

    /**
     * addConstraint will allow the user to add Constraint into the column. The constraint
     * must not be already initialized. will be checked while entering value to Attribute
     * @param constraint contraint to be added
     */
    protected void addConstraint(Constraint constraint) {}

    /**
     * This method will return the value of index i of the Constraint. It will return null if 
     * index is out of bounds
     * @param index index
     * @return the value
     */
    protected Constraint getConstraint(int index) {}
    
    /**
     * Add a Check to Attribute. An operator must be passed as a string and it's specified
     * ">" ">=" "<", "<=" "=". After check added, the check will be checked while entering 
     * a value of Attribute. Example "value >= 10"
     * @param operator Specified operator
     * @param value conditional value
     */
    protected void addCheck(String operator, V value) {}

    /**
     * Verify when user do addValue that the value is fit with with the constraints and check
     * or not
     */
    private boolean verifValue(V v) throw ConstraintViolationException {

	boolean ret;
	
	//Verify the constraints
	for ( Constraint c : constraints ) {
	      
	    switch (c) {

	    case Constraint.NOTNULL :
		if (v==null) {
		    ret = false;
		    throw new ConstraintViolationException("Value added must not be null !");
		}
		break;

	    case Constraint.PRIMARYKEY :
		for ( V value :  values ) {
		    if ( value.equals(v) ) {
			ret = false;
			throw new ConstraintViolationException("Value must be unique !");
			break;
		    }
		}
		break;

	    case Constraint.REFERENCEKEY :
		for ( V frValue : this.referenceKey ) {
		    if (!frValue.equals(v)) {
			ret = false;
			throw new ConstraintViolationException("The value must be contained in "+referenceKey.getName());
		    }
		    break;
		}
		break;

	    default :
		break;
	    }

	}

	//Verify the checks
		    
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
