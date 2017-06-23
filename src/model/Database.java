package database.model;

import java.util.*;
import database.exception.*;

/**
 * Database holds the function of management the tables of the database. It
 * allows users to initialize, modify the Tables.
 * @author Trang Hoang Phong Vu
 * @author Le-Floch Thomas
 */
public class Database {

    private String name;
    private ArrayList<Table> tables;

    /**
     * Initialise database. the tables passed by param will be changed into a 
     * Hashmap with identity, which allows managing 
     * @param name name of t.0he database
     * @param tables tables for the database
     */
    public Database(String name) {
	this.name = name;

	tables = new ArrayList<Table>();
    }

    /**
     * Return an ArrayList of tables of the database 
     * @return List of the Tables without identities
     */
    public ArrayList<Table> getTables() {
	return this.tables;
    }

    /**
     * Return a table of which id is passed in param. If id meet the violation
     * the method will be canceled
     * @param id identity of the table
     * @return table
     */
    public Table getTable(int id) throws SizeException{

	Table ret = null;
	
	if (id < 0 || id >= this.tables.size() ) {
	    throw new SizeException("id not correct !");
	}
	else {
	    ret = this.tables.get(id);
	}

	return ret; 
	    	
    }

    /**
     * Return the name of the table whose id is passed in param. If id meet 
     * the violation , the method will be canceled
     * @param id identity of the table
     * @return table's name
     */  
    public String getTableName(int id) throws SizeException {

	String ret = null;
	if (id < 0 || id >= this.tables.size() ) {
	    throw new SizeException("id not correct !");
	}
	else {
	    ret = this.tables.get(id).getName();
	}

	return ret;
	
    }

    /**
     * Return the table's id by passing his name in the param
     * @param name name of the table to be searched
     * @return table's id
     */
    public int getTableId (String name) {

	int ret = -1;
	for ( int i = 0 ; i < this.tables.size() ; i++ ) {
	    if (name.trim().toUpperCase().equals( this.tables.get(i).getName().trim().toUpperCase()) )
		ret = i;
	}

	return ret;
    }

    /**
     * Return the database's name
     * @return database's name
     */
    public String getName() {
	return this.name;
    }

    /**
     * Change the database's name 
     * @param name to be changed
     */
    public void setName(String name) {

	this.name = name;
	
    }

    /**
     * Change the table's name 
     * @param id id of which the table want to be changed
     * @param name new table's name
     */
    public void setTableName(int id, String name) throws SizeException{

	if (id < 0 || id >= this.tables.size() ) {
	    throw new SizeException("id not correct !");
	}
	else {
	    this.tables.get(id).setName(name);
	}
	
    }

    /**
     * Add a table to the database. Its id will be automatically generated
     * @param Table
     */
    public void addTable(Table table) throws NullPointerException {

	if (table == null)  {
	    throw new NullPointerException("Table cannot be null !");
	}
	else {

	    this.tables.add(table);

	}

    }

    /**
     * Delete a table of the database by passing the table's id in param
     * @param id identity of the table
     */
    public void delTable(int id) throws SizeException {

	if (id < 0 || id >= this.tables.size() ) {
	    throw new SizeException("id not correct !");
	}
	else {
	    this.tables.remove(id);
	}

    }

    public int sizeTable() {
	return this.tables.size();
    }

}
