package database.test;

import database.model.*;
import database.exception.*;
import org.junit.*;

import org.junit.runner.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AttributeTest {
    private int[] values = {0, 9, 3, 2, 8, 12, 9};
    private String name;
    private Attribute<Integer> attributeInt;

    public AttributeTest(String name) {

	//	super(name);

    }

    @Before()
    protected void setUp() {
	try {
	this.name = "AttributeTest";
	this.attributeInt = new Attribute<Integer>(name,10);
	}
	catch (SizeException se) {
	    System.out.println(se.getMessage());
	}
    }

    @Test()
    public void testSetGetName() {
	
	attributeInt.setName(null);
	Assert.assertFalse(attributeInt.getName() == null);
	
	attributeInt.setName("Hi");
	Assert.assertTrue(attributeInt.getName() == "Hi");

	attributeInt.setName("");
	Assert.assertTrue(attributeInt.getName() == "");
	
    }

    
    @Test()
    public void testSetGetLength() {

	try {
	attributeInt.setLength(3);
	}
	catch (SizeException se) {
	    System.out.println(se.getMessage());
	}
	finally {
	    Assert.assertFalse(attributeInt.getLength() == 3);
	}
	
	try {
	attributeInt.setLength(-3);
	}
	catch (SizeException se) {
	    System.out.println(se.getMessage());
	}
	finally {
	    Assert.assertFalse(attributeInt.getLength() == -3);
	}
	
	try {
	attributeInt.setLength(0);
	}
	catch (SizeException se) {
	    System.out.println(se.getMessage());
	}
	finally {
	    Assert.assertTrue(attributeInt.getLength() == 0);
	}
	    
    }

    @Test()
    public void testAddValue() {

	try{
	    this.attributeInt.addValue(null);
	}
	catch  (ConstraintCheckViolationException e) {System.out.println(e.getMessage()); }
	catch (NullPointerException e) { System.out.println(e.getMessage()); }
	finally {
	    try {Assert.assertFalse(attributeInt.getValue(0) == null);}
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}
		
	for(int i = 0; i < values.length; i++) {
	    try { this.attributeInt.addValue(values[i]); }
	    catch (ConstraintCheckViolationException e) { System.out.println(e.getMessage()); }
	    catch (NullPointerException e) { System.out.println(e.getMessage()); }
	}

	for(int i = 0; i < values.length; i++) {
	    try {Assert.assertFalse(attributeInt.getValue(i) == values[i]);}
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}
    
    }
    
    public void testGetValue() {

	try {
	    this.attributeInt = new Attribute<Integer>(this.name,10);
	}
	catch (SizeException se) {}
	
	for ( int i = 0 ; i < values.length ; i++ ) {
	    try { attributeInt.addValue(values[i]); }
	    catch (NullPointerException npe) {}
	    catch (ConstraintCheckViolationException ccve) {}
	}

	try {
	    Assert.assertTrue(attributeInt.getValue(-1) == null);
	}
	catch (SizeException se) {
	    System.out.println(se.getMessage());
	}
	finally {
	    try {
		Assert.assertTrue(attributeInt.getValue(values.length) == null);
	    }
	    catch (SizeException se) {
	    }
	}
	    
      
	try {
	    Assert.assertTrue(attributeInt.getValue(0).intValue() == 0);
	}
	catch (SizeException se) {
	}
    
	try {
	    Assert.assertTrue(attributeInt.getValue(1).intValue() == 10);
	}
	catch (SizeException se) {
	}

    }
    
    @Test()
    public void testSetValue() {
	try {
	this.attributeInt = new Attribute<Integer>(this.name,10);
	}
	catch (SizeException se) {}
    
        for ( int i = 0 ; i < values.length ; i++ ) {
	    try {
		attributeInt.addValue(values[i]);
	    }
	    catch (NullPointerException npe) {}
	    catch (ConstraintCheckViolationException ccve) {}
	}

	try {
	    attributeInt.setValue(999,4);
	}
	catch (SizeException se) { System.out.println( se.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println( ccve.getMessage()); }
	finally {
	    try {
		Assert.assertFalse(attributeInt.getValue(999).intValue() == 4);
	    }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}

	try {
	    attributeInt.setValue(-1,4);
	}
	catch (SizeException se) { System.out.println( se.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println( ccve.getMessage()); }
	finally {
	    try {
		Assert.assertFalse(attributeInt.getValue(-1).intValue() == 4);
	    }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}
	
	try {
	    attributeInt.setValue(0,4);
	}
	catch (SizeException se) { System.out.println( se.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println( ccve.getMessage()); }
	finally {
	    try {
		Assert.assertTrue(attributeInt.getValue(0).intValue() == 4);
	    }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}
	
	try {
	    attributeInt.setValue(values.length-1,2);
	}
	catch (SizeException se) { System.out.println( se.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println( ccve.getMessage()); }
	finally {
	    try {
		Assert.assertTrue(attributeInt.getValue(values.length-1).intValue() == 2);
	    }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}


	
    }

    
    @Test()
    public void testOutOfLength() {

	Attribute<String> attributeString = null;
	try {
	    attributeString = new Attribute<String>("Test String", 3);
	}
	catch (SizeException se) {}
        

	try {
	    attributeString.addValue("HI");
	}
	catch (NullPointerException npe) { System.out.println(npe.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println(ccve.getMessage()); }
	finally {
	    try {
		Assert.assertTrue( attributeString.getValue(0).equals("HI") );
	    }
	    catch ( SizeException se ) { System.out.println(se.getMessage()); }
	}


	try {
	    attributeString.setValue(0, "");
	}
	catch (SizeException se) { System.out.println(se.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println(ccve.getMessage()); }
	finally {
	    try {
		Assert.assertTrue( attributeString.getValue(0).equals("") ); 
	    }
	    catch ( SizeException se ) { System.out.println(se.getMessage()); }
	}

	try {
	    attributeString.setValue(0, "aaaaaa");
	}
	catch (SizeException se) { System.out.println(se.getMessage()); }
	catch (ConstraintCheckViolationException ccve) { System.out.println(ccve.getMessage()); }
	finally {
	    try {
		Assert.assertFalse( attributeString.getValue(0).equals("aaaaaa") ); 
	    }
	    catch ( SizeException se ) { System.out.println(se.getMessage()); }
	}



    }

			    
			   

    @Test()
    public void testAddConstraint() {

	try {
	    this.attributeInt = new Attribute<Integer>(this.name,10);
	}
	catch (SizeException se) {}
    
        for ( int i = 0 ; i < values.length ; i++ ) {
	    try {
		attributeInt.addValue(values[i]);
	    }
	    catch (NullPointerException npe) {}
	    catch (ConstraintCheckViolationException ccve) {}
	}

	try {
	    this.attributeInt.addConstraint( Constraint.PRIMARYKEY );
	    Assert.assertTrue( attributeInt.getConstraint(0) == Constraint.PRIMARYKEY );
	}
	catch (ConstraintCheckViolationException ccve ) { System.out.println(ccve.getMessage()) ; }
	catch (SizeException se ) { System.out.println(se.getMessage()) ; }
	
	try {
	    this.attributeInt.addConstraint( Constraint.NOTHING );
	    Assert.assertTrue( attributeInt.getConstraint(1) == Constraint.NOTHING );
	}
	catch (ConstraintCheckViolationException ccve ) { System.out.println(ccve.getMessage()); }
	catch (SizeException se ) { System.out.println(se.getMessage()) ; }

	// Add existed constraint, for testing verifExistConstraints()
	try {
	    this.attributeInt.setConstraint(1, Constraint.PRIMARYKEY );

	}
	catch (ConstraintCheckViolationException ccve ) { System.out.println(ccve.getMessage()); }
	catch (SizeException se )  { System.out.println(se.getMessage()); }
	finally {
	    try {
	        Assert.assertTrue( attributeInt.getConstraint(1) == Constraint.PRIMARYKEY );
	    }
	    catch (SizeException se ) { System.out.println(se.getMessage()); }
	}
					 
    }

    @Test()
    public void testAddCheck() {

	try {
	    this.attributeInt = new Attribute<Integer>(this.name,10);
	}
	catch (SizeException se) {}
	
	this.attributeInt.addCheck(">=", 10);

	// Also Test verifValues()

	try { this.attributeInt.addValue(5); }
	catch (ConstraintCheckViolationException e) { System.out.println(e.getMessage()); }
	catch (NullPointerException e) { System.out.println(e.getMessage()); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 5); }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}

	try { this.attributeInt.setValue(0,0); }
	catch (SizeException e)  { System.out.println(e.getMessage()); }
	catch (ConstraintCheckViolationException e) { System.out.println(e.getMessage()); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 0); }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}

	try { this.attributeInt.setValue(0,10); }
	catch (SizeException e) { System.out.println(e.getMessage()); }
	catch (ConstraintCheckViolationException e) { System.out.println(e.getMessage()); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 10); }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}
	
	try { this.attributeInt.setValue(0,15); }
	catch (SizeException e) { System.out.println(e.getMessage()); }
	catch (ConstraintCheckViolationException e) { System.out.println(e.getMessage()); }
	finally {
	    try { Assert.assertFalse(attributeInt.getValue(0) == 15); }
	    catch (SizeException se) { System.out.println(se.getMessage()); }
	}
	
    }

    public static junit.framework.Test suite() {
	return new junit.framework.JUnit4TestAdapter(AttributeTest.class);
    }
    /*public static void main(String[] args) {
	Result result = JUnitCore.runClasses(AttributeTest.class);

	for (Failure failure : result.getFailures()) {
	    System.out.println(failure.toString());
	}

	System.out.println(result.wasSuccessful());
	}*/

}
