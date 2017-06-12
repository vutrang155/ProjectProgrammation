package Database.model;

import java.util.*;

/**
 * Database holds the function of management the tables of the database. It
 * allows users to initialize, modify the Tables.
 * @author Trang Hoang Phong Vu
 * @author Le-Floch Thomas
 */
public class Database {

    private String name;
    private HashMap<int,Tables> tables;

    /**
     * Initialise database. the tables passed by param will be changed into a 
     * Hashmap with identity, which allows managing 
     * @param name name of the database
     * @param tables tables for the database
     */
    public Database(String name, ArrayList<Table> tables) {}

    /**
     * Return a hashmap that contains Tables and their identities
     * @return Tables and their identities
     */
    public HashMap<int,Table> getTablesMap() {}

    /**
     * Return an ArrayList of tables of the database 
     * @return List of the Tables without identities
     */
    public ArrayList<Table> getTablesList() {}

    /**
     * Return a table of which id is passed in param. If id meet the violation
     * the method will be canceled
     * @param id identity of the table
     * @return table
     */
    public Table getTable(int id) {}

    /**
     * Return the name of the table whose id is passed in param. If id meet 
     * the violation , the method will be canceled
     * @param id identity of the table
     * @return table's name
     */  
    public String getTableName(int id) {}

    /**
     * Return the table's id by passing his name in the param
     * @param name name of the table to be searched
     * @return table's id
     */
    public int getTableId (String name) {}

    /**
     * Return the database's name
     * @return database's name
     */
    public String getName() {}

    /**
     * Change the database's name 
     * @param name to be changed
     */
    public void setName(String name) {}

    /**
     * Change the table's name 
     * @param id id of which the table want to be changed
     * @param name new table's name
     */
    public void setTableName(int id, String name) {}

    /**
     * Add a table to the database. Its id will be automatically generated
     * @param Table
     */
    public void addTable(Table table) {}

    /**
     * Delete a table of the database by passing the table's id in param
     * @param id identity of the table
     */
    public void delTable(int id) {}

}
