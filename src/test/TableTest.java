package database.test;

import database.model.Table;
import database.model.*;
import java.util.*;
import database.exception.*;
import org.junit.*;
import java.text.*;

import org.junit.runner.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

public class TableTest {

    private Table tab;
    private ArrayList<Attribute> columns;
    private final int NO = 2;
    private final int NAME = 50;
    private final int NOTE = 6;
    private final int BD = 11;

    @Before()
    public void setUp() {
	try {

	    columns = new ArrayList<Attribute>();
	    tab = new Table("Student");
	    tab.addAttribute("no",DataType.INTEGER, 2);
	    tab.addAttribute("name",DataType.CHAR,50);
	    tab.addAttribute("note",DataType.DOUBLE,6);
	    tab.addAttribute("birthday",DataType.DATE,11);

	    //Initialise column
	    columns.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    columns.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    columns.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    columns.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));	

	    columns.get(0).addValueString("0");
	    columns.get(1).addValueString("TRANG VU");
	    columns.get(2).addValueString("19.9");
	    columns.get(3).addValueString("15/05/1998");

	    tab.insertTUple(columns);
	    assertTrue(true);

	}
	catch (Exception e) {
	    assertTrue(false);
	}

    }
     @Test()
    public void testAddAttribute() {

	Table tab2 = new Table("Test Table");

	//Test datatype null
	try {
	    tab2.addAttribute("null",null,30);
	    assertTrue(false);
	}
	catch (TypeException e) {
	    assertTrue(true);
	}
	catch (SizeException e) {
	    assertTrue(false);
	}

	//Test length -1
	try {
	    tab2.addAttribute("null",DataType.DOUBLE,-5);
	    assertTrue(false);
	}
	catch (SizeException e) {
	    assertTrue(true);
	}
	catch (TypeException e) {
	    assertTrue(false);
	}

	//Normal case
	try {
	    tab2.addAttribute("ok",DataType.CHAR,10);
	    assertTrue(true);
	}
	catch (Exception e) {
	    assertTrue(false);
	}

    }

     @Test()
    public void testInsertTUple() {

	// Normal case
	try {
	    ArrayList<Attribute> c2 = new ArrayList<Attribute>();

	    c2.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    c2.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    c2.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    c2.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));

	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");

	    tab.insertTUple(c2);

	    assertTrue(true);
	}
	catch (Exception e) {
	    assertTrue(false);
	}
	
     }

    @Test()
    public void TestInserTUple2() {
	//Error case : tuples don't match, c2 got 2 ligne
	try {
	    ArrayList<Attribute> c2 = new ArrayList<Attribute>();

	    c2.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    c2.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    c2.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    c2.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));

	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");

	    c2.get(0).addValue(2);
	    c2.get(1).addValue("Vu Trang");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("15/05/1998");
	    
	    tab.insertTUple(c2);

	    assertTrue(false);
	}
	catch (Exception e) {

	    assertTrue(true);
	}
    }
    public void TestTUple3() {
	// Error case : tuples don't match, c2 got 5 columns
	try {
	    ArrayList<Attribute> c2 = new ArrayList<Attribute>();

	    c2.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    c2.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    c2.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    c2.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));
	    c2.add( new Attribute<Integer>(null,"age",3,DataType.INTEGER));

	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");
	    c2.get(4).addValue(20);
	    
	    c2.get(0).addValue(2);
	    c2.get(1).addValue("Vu Trang");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("15/05/1998");
	    c2.get(4).addValue(19);
	    
	    tab.insertTUple(c2);

	    assertTrue(false);
	}
	catch (Exception e) {
	    assertTrue(true);
	}
		
    }

    @Test()
    public void testAddConstraint1() {

	//Error case, column violation
	try {
	    tab.addConstraint(-1,Constraint.PRIMARYKEY);
	    assertTrue(false);
	}
	catch (SizeException e ) {
	    assertTrue(true);
	}
	catch (ConstraintCheckViolationException e) {
	    assertTrue(false);
	}
    }

    @Test()
    public void testAddConstraint2() {
	//error case, PRIMARY KEY

	try {
	    tab.addConstraint(0,Constraint.PRIMARYKEY);
	    assertTrue(true);
	    ArrayList<Attribute> c2 = new ArrayList<Attribute>();

	    c2.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    c2.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    c2.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    c2.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));

	    c2.get(0).addValueString("0");
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");
	    
	    tab.insertTUple(c2);
	  
	    assertTrue(false);
	}
	catch (ConstraintCheckViolationException e) {

	    assertTrue(true);
	}
	catch (SizeException e) {
	    assertTrue(false);
	}
	catch (ParseException e) {
	    assertTrue(false);
	}
	catch (ColumnException e) {
	    assertTrue(true);
	}
	try {
	  for (int i = 0 ; i < 3; i++) {
	      if (tab.getAttribute(i+1).getValues().size() != tab.getAttribute(i).getValues().size()) {
		  assertTrue(false);
		  break;
	      }
	  }
	}
	catch(Exception e) {
	    assertTrue(false);
	}

    }


    @Test()
    public void TestConstraint3() {
        //Normal case, REFERENCE key
	Table tab2 = new Table("Tasks");
	try {
	    ArrayList<Attribute> c2 = new ArrayList<Attribute>();

	    c2.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    c2.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    c2.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    c2.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));

	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");
	    
	    tab.insertTUple(c2);	    

	    ArrayList<Attribute> tuple = tab.tuple();

	    tuple.get(0).addValue(2);
	    tuple.get(1).addValue("Vu");
	    tuple.get(2).addValue(19.9);
	    tuple.get(3).addValueString("15/05/1998");

	    tab.insertTUple(tuple);
	    
	    tab2.addAttribute("id",DataType.INTEGER,2);
	    tab2.addAttribute("name",DataType.CHAR,100);
	    tab2.addAttribute("dev",DataType.INTEGER,2);

	    tab2.addConstraint(0,Constraint.PRIMARYKEY);
	    tab2.addConstraint(2,Constraint.REFERENCEKEY,tab.getAttribute(0));

	    c2 = tab2.tuple();

	    c2.get(0).addValue(0);
	    c2.get(1).addValue("Cahier d'analyse");
	    c2.get(2).addValue(1);

	    tab2.insertTUple(c2);

	    c2 = tab2.tuple();
	    
	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Codage");
	    c2.get(2).addValue(2);

	    tab2.insertTUple(c2);
	    
	    c2 = tab2.tuple();
	      
	    c2.get(0).addValue(10);
	    c2.get(1).addValue("Finalisation");
	    c2.get(2).addValue(2);

	    tab2.insertTUple(c2);
	    
	    assertTrue(true);
	}
	catch ( Exception e) {
	    System.out.println(e.getMessage());
	    assertTrue(false);
	}
    }


    @Test()
    public void TestConstraint4() {
        //Error case, REFERENCE key
	Table tab2 = new Table("Tasks");
	try {
	    ArrayList<Attribute> c2 = new ArrayList<Attribute>();

	    c2.add( new Attribute<Integer>(null,"no",2,DataType.INTEGER));
	    c2.add( new Attribute<String>(null,"name",50,DataType.CHAR));
	    c2.add( new Attribute<Double>(null,"note", 6, DataType.DOUBLE));
	    c2.add( new Attribute<Date>(null,"birthday",11,DataType.DATE));

	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");
	    
	    tab.insertTUple(c2);	    

	    ArrayList<Attribute> tuple = tab.tuple();

	    tuple.get(0).addValue(2);
	    tuple.get(1).addValue("Vu");
	    tuple.get(2).addValue(19.9);
	    tuple.get(3).addValueString("15/05/1998");

	    tab.insertTUple(tuple);
	    
	    tab2.addAttribute("id",DataType.INTEGER,2);
	    tab2.addAttribute("name",DataType.CHAR,100);
	    tab2.addAttribute("dev",DataType.INTEGER,2);

	    tab2.addConstraint(0,Constraint.PRIMARYKEY);
	    tab2.addConstraint(2,Constraint.REFERENCEKEY,tab.getAttribute(0));

	    c2 = tab2.tuple();

	    c2.get(0).addValue(0);
	    c2.get(1).addValue("Cahier d'analise");
	    c2.get(2).addValue(1);

	    tab2.insertTUple(c2);

	    c2 = tab2.tuple();
	    
	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Codage");
	    c2.get(2).addValue(2);

	    tab2.insertTUple(c2);
	    
	    c2 = tab2.tuple();
	      
	    c2.get(0).addValue(10);
	    c2.get(1).addValue("Finalisation");
	    c2.get(2).addValue(8);

	    tab2.insertTUple(c2);
	    
	    assertTrue(false);
	}
	catch ( Exception e) {
	    assertTrue(true);
	}
    }

    @Test()
    public void testCheck() {

	try {
	    tab.addCheck(tab.getColumn("note"), ">=", "0");
	    tab.addCheck(tab.getColumn("note"), "<=", "20");
	

	    ArrayList<Attribute> c2 = tab.tuple();

	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Xuan Le");
	    c2.get(2).addValue(19.9);
	    c2.get(3).addValueString("21/01/1997");

	    tab.insertTUple(c2);
	    assertTrue(true);
	}
	catch (Exception e) {
	    assertTrue(false);
	}
    }
    @Test()
    public void testCheck2() {
	// Test limit case, 20 and 0 
	try {
	    tab.addCheck(tab.getColumn("note"), ">=", "0");
	    tab.addCheck(tab.getColumn("note"), "<=", "20");
	
	    
	    ArrayList<Attribute> c2 = tab.tuple();

	    c2.get(0).addValue(2);
	    c2.get(1).addValue("AXuan Le");
	    c2.get(2).addValue(20);
	    c2.get(3).addValueString("21/01/1997");

	    tab.insertTUple(c2);

	    c2 = tab.tuple();

	    c2.get(0).addValue(3);
	    c2.get(1).addValue("AXuan Le");
	    c2.get(2).addValue(0);
	    c2.get(3).addValueString("21/01/1997");

	    tab.insertTUple(c2);
	    
	    assertTrue(true);
	}
	catch (Exception e) {
	    assertTrue(false);
	}
    }

        @Test()
    public void testCheck3() {
	// Test limit case, 20 and 0 
	try {
	    tab.addCheck(tab.getColumn("note"), ">=", "0");
	    tab.addCheck(tab.getColumn("note"), "<=", "20");
	
	    
	    ArrayList<Attribute> c2 = tab.tuple();

	    c2.get(0).addValue(2);
	    c2.get(1).addValue("AXuan Le");
	    c2.get(2).addValue(21);
	    c2.get(3).addValueString("21/01/1997");

	    tab.insertTUple(c2);
	    
	    assertTrue(false);
	}
	catch (Exception e) {
	    assertTrue(true);
	}
    }

    @Test()
    public void testEditValue() {
	// Normal case
	try {
	    tab.editValue(0,1,"HIHI");
	    assertTrue( tab.getAttribute(1).getValue(0).equals("HIHI") );
	}
	catch (Exception e) { assertTrue(false); }
    }

    @Test()
    public void testEditValue2() {
	// Error case, wrong column
	try {
	    tab.editValue(0,0,"HIHI");
	    assertTrue( !tab.getAttribute(1).getValue(0).equals("HIHI") );
	}
	catch (Exception e) { assertTrue(true); }
    }

    @Test()
    public void testEditValue3() {
	// Error case, edit a value that violated a constraint
	try {
	    ArrayList<Attribute> tuple = tab.tuple();
	    tuple.get(0).addValue(1);
	    tuple.get(1).addValue("Vu");
	    tuple.get(2).addValue(19.9);
	    tuple.get(3).addValueString("15/05/1998");

	    tab.insertTUple(tuple);
	}
	catch (Exception e) { assertTrue(false); }

	try {
	    tab.editValue(1,0,"0");
	    //assertTrue( !(tab.getAttribute(0).getValue(1).equals(0)) );
	}
	catch ( Exception e ) {
	    assertTrue(true);
	}
	
    }
			 	    
	

    
    @Test()
    public void testFunctionTuple() {

	for (int i = 0 ; i < 4 ; i++ ) {
	    try {
		assertTrue( tab.getAttribute(i).getValues().size() == 1 );
	    }
	    catch (Exception e ) {
		assertTrue(false);
	    }
	}

	try {
	ArrayList<Attribute> test = tab.tuple();
	}
	catch ( Exception e ) { assertTrue(false); }
	for (int i = 0 ; i < 4 ; i++ ) {
	    try {
		assertTrue( tab.getAttribute(i).getValues().size() == 1 );
	    }
	    catch (Exception e ) {
		assertTrue(true);
	    }
	}
    }
	
}	




