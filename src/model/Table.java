package database.model;

import java.util.*;

/**
 * The class simulates the Table of the database, in which columns are added and managed 
 * as an Arraylist. Also, the class allows user to insert t-uples and basic constraint
 * to column.
 * @author TRANG Hoang Phong Vu
 * @author 
 */
public class Table {

    private String name;
    private Attribute attributes;

    /**
     * Initialise the table
     * @param name name of the table
     */
    public Table( String name ) {}

    /**
     * Allows user to add a column (attribute) to the table, DataType can be CHAR, 
     * NUMBER, or DATE. for Date type, the length will be automatically 1, the  length
     * passed in parameters will be canceled
     * @param nameAttribute name of the column
     * @param type type of the column, CHAR, NUMBER, or DATE
     * @param length length maximum that a value can be added
     */
    protected void addAttribute( String nameAttribute, DataType type, int length) {}

    /**
     * The method will first verify that the attributes' member is the size 1 or not.
     * If yes, a t-uple will be initialised to the table. If not, initialisation of 
     * tuple will be cancelled
     * @param attributes list of attributes whose elements have just 1 value
     */
    public void insertTUple( Attribute[] attributes ) {}

    /**
     * Allows users to edit the value of a t-uple by enter the place and the value
     * as a String. If the index or column meet the violation (smaller than 0, out
     * of bounds), the editValue will be canceled 
     * @param index coordinate of the tuple
     * @param column coordinate of the column in the tuple
     * @param strValue value as string
     */
    public void editValue(int index, int column, String strValue ) {}

    /**
     * Add constraint to the column. If column meet the violation (smaller than 
     * 0, out of bounds), The method will be canceled
     * @param order the place of the column
     * @param constraint constraint that user want to add
     */
    public void addConstraint (int column, Constraint constraint ) {}

    /**
     * Return the column by entering the column of the column. If number of the 
     * column meet the violation (smaller than 0, out of bounds), the method 
     * will be canceled
     * @param column the order of the column
     */
    public Attribute getAttribute(int column) {}

    /**
     * Return the column's order by passing in param the name of the column
     * @param strColumnName name of the column
     * @return the column's order
     */
    public int getColumn( String strColumnName ) {}

}
