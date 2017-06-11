package model;

/**
 * Simulates the Column of a Database, with some basic function that is balanced with constraints
 * and check
 * @author Trang Vu
 * @author Le-Floch Thomas
 */
public class Attribute<V> {
    
    private ArrayList<Constraint> constraints;
    private String name;
    private ArrayList<V> values;
    private int length;
    
    /**
     * Initialise the name of the column and the list
     * @param name name of the column
     */
    public Attribute(String name, int length) {
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
    public String getLength() {
        
    }

    /**
     * This method allow to change the length maximum that user can add. If length is 
     * smaller than zero, Throw an Exception
     * @param length length maximum that user can add
     */
    public void setLength(int length) {
        
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
    protected void addValue(V v) {
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
    private void verifValues() {}

    /**
     * The method will be called at addConstraint, to make sure that the constraint has
     * already been added or not. return true if it's not existed yet
     * @param constraint constraint to be tested
     * @return true if it's not existed yet
     */
    private boolean verifExistConstraints(Constraint constraint) {}
}
