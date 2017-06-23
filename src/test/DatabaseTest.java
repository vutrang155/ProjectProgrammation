package database.test;

import database.exception.*;
import database.model.*;

import java.util.*;

import org.junit.runner.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;
import org.junit.*;

public class DatabaseTest {

    private Database database;

    @Before()    
    public void  setUp() {

	try {
	    database = new Database("The Giant Database");

	    Table t1 = new Table("Student");
	    t1.addAttribute("no",DataType.INTEGER, 2);
	    t1.addAttribute("name",DataType.CHAR,50);
	    t1.addAttribute("note",DataType.DOUBLE,6);
	    t1.addAttribute("birthday",DataType.DATE,11);

	    t1.addConstraint(0,Constraint.PRIMARYKEY);
	    t1.addCheck(2,">=","0");
	    t1.addCheck(2,"<=","20");

	    ArrayList<Attribute> tuple = t1.tuple();

	    tuple.get(0).addValue(0);
	    tuple.get(1).addValue("Vu");
	    tuple.get(2).addValue(18.4);
	    tuple.get(3).addValueString("15/05/1998");

	    t1.insertTUple(tuple);
	    
	    tuple = t1.tuple();

	    tuple.get(0).addValue(1);
	    tuple.get(1).addValue("Xuan");
	    tuple.get(2).addValue(18.4);
	    tuple.get(3).addValueString("28/01/1997");

	    t1.insertTUple(tuple);
	
	    tuple = t1.tuple();

	    tuple.get(0).addValue(2);
	    tuple.get(1).addValue("Dr who");
	    tuple.get(2).addValue(20);
	    tuple.get(3).addValueString("01/01/1961");

	    t1.insertTUple(tuple);
	
	    
	    Table t2 = new Table("Works");
	    
	    t2.addAttribute("id",DataType.INTEGER,2);
	    t2.addAttribute("name",DataType.CHAR,100);
	    t2.addAttribute("dev",DataType.INTEGER,2);

	    t2.addConstraint(0,Constraint.PRIMARYKEY);
	    t2.addConstraint(2,Constraint.REFERENCEKEY,t1.getAttribute(0));

	    ArrayList<Attribute> c2 = t2.tuple();
	    
	    c2.get(0).addValue(0);
	    c2.get(1).addValue("Cahier d'analyse");
	    c2.get(2).addValue(1);

	    t2.insertTUple(c2);

	    c2 = t2.tuple();
	    
	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Codage");
	    c2.get(2).addValue(2);

	    t2.insertTUple(c2);
	    
	    c2 = t2.tuple();
	      
	    c2.get(0).addValue(2);
	    c2.get(1).addValue("Finalisation");
	    c2.get(2).addValue(2);

	    t2.insertTUple(c2);

	    database.addTable(t1);
	    database.addTable(t2);

	     
	    assertTrue(true);
	}
	catch (Exception e) { assertTrue(false); }

    }

    @Test()
    public void  testGetTables() {

	ArrayList<Table> tables = database.getTables();

	assertTrue( tables.size() == 2 );
	
	assertTrue( tables.get(0).getName().equals("Student") );
	assertTrue( tables.get(1).getName().equals("Works") );
		    
    }

    @Test()
    public void  testGetTableNameAndID() {

	try {
	assertTrue( database.getTableName(0).equals("Student") );
	}
	catch (SizeException e) { assertTrue(true); }
	
	try {
	    assertFalse( database.getTableName(-1).equals("") );
	}
	catch (SizeException e) { assertTrue(true); }

	try {
	    assertTrue( database.getTableName(1).equals("Works") );
	}
	catch (SizeException e) { assertTrue(true); }

	assertTrue( database.getTableId("   student") == 0 );
	assertTrue( database.getTableId("aaasdsadas") == -1 );
	assertTrue( database.getTableId("WORKS") == 1);

    }

    @Test()
    public void testAddTableDelTable() {

	try {
	    Table t3 = new Table("");
	    database.addTable(t3);

	    assertTrue( database.getTables().size() == 3);

	    database.delTable(0);
	    assertTrue( database.getTables().size() == 2);

	     assertTrue( database.getTable(0).getName().equals("Works") );

	}
	catch (Exception e) { assertTrue(false); }
		
    }
  
}
	
	
	
