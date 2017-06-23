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

    /* public AttributeTest(String name) {

	super(name);

	}*/

    @Before()
    public void setUp() {
	try {
	this.name = "AttributeTest";
	this.attributeInt = new Attribute<Integer>(null,name,10,DataType.INTEGER);
	}
	catch (SizeException se) {
	   Assert.assertFalse(true);
	}
    }

    @Test()
    public void testSetGetName() {
	try {
	    attributeInt.setName(null);
	}
	catch (NullPointerException e) { Assert.assertTrue(true); }
	Assert.assertFalse(attributeInt.getName() == null);


	try {
	    attributeInt.setName("Hi");
	}
	catch (NullPointerException e) {   Assert.assertFalse(true); }
	Assert.assertTrue(attributeInt.getName() == "Hi");


	try {
	    attributeInt.setName("");
	}
	catch (NullPointerException e) {   Assert.assertFalse(true); }
	Assert.assertTrue(attributeInt.getName() == "");

    }

    
    @Test()
    public void testSetGetLength() {

	try {
	    attributeInt.setLength(3);
	    Assert.assertTrue(true);
	}
	catch (SizeException se) {
	    Assert.assertFalse(true);
	}
	finally {
	    Assert.assertTrue(attributeInt.getLength() == 3);
	}
	
	try {
	    attributeInt.setLength(-3);
	    Assert.assertFalse(true);
	}
	catch (SizeException se) {
	    Assert.assertTrue(true);
	}

	try {
	    attributeInt.setLength(0);
	}
	catch (SizeException se) {
	    Assert.assertTrue(true);
	}
	finally {
	    Assert.assertFalse(attributeInt.getLength() == 0);
	}
	    
    }

    @Test()
    public void testAddValue() {
		
	for(int i = 0; i < values.length; i++) {
	    try { this.attributeInt.addValue(values[i]); }
	    catch (ConstraintCheckViolationException e) { Assert.assertFalse(true); }
	    catch (NullPointerException e) { Assert.assertFalse(true); }
	}

	for(int i = 0; i < values.length; i++) {
	    try {Assert.assertTrue(attributeInt.getValue(i) == values[i]);}
	    catch (SizeException se) { Assert.assertFalse(true); }
	}
    
    }
    
    public void testGetValue() {

	try {
	    this.attributeInt = new Attribute<Integer>(null,this.name,10,DataType.INTEGER);
	}
	catch (SizeException se) {Assert.assertFalse(true);}
	
	for ( int i = 0 ; i < values.length ; i++ ) {
	    try { attributeInt.addValue(values[i]); }
	    catch (NullPointerException npe) {Assert.assertFalse(true);}
	    catch (ConstraintCheckViolationException ccve) {Assert.assertFalse(true);}
	}

	try {
	    Assert.assertTrue(attributeInt.getValue(-1) == null);
	}
	catch (SizeException se) {
	    Assert.assertTrue(true);
	}
	finally {
	    try {
		Assert.assertTrue(attributeInt.getValue(values.length) == null);
	    }
	    catch (SizeException se) {Assert.assertFalse(true);}
	}
	    
      
	try {
	    Assert.assertTrue(attributeInt.getValue(0).intValue() == 0);
	}
	catch (SizeException se) {Assert.assertFalse(true);
	}
    
	try {
	    Assert.assertTrue(attributeInt.getValue(1).intValue() == 10);
	}
	catch (SizeException se) {Assert.assertFalse(true);
	}

    }
    
    @Test()
    public void testSetValue() {
	try {
	    this.attributeInt = new Attribute<Integer>(null,this.name,10,DataType.INTEGER);
	}
	catch (SizeException se) {Assert.assertFalse(true);}
    
        for ( int i = 0 ; i < values.length ; i++ ) {
	    try {
		attributeInt.addValue(values[i]);
	    }
	    catch (NullPointerException npe) {Assert.assertFalse(true);}
	    catch (ConstraintCheckViolationException ccve) {Assert.assertFalse(true);}
	}

	try {
	    attributeInt.setValue(999,4);
	}
	catch (SizeException se) {Assert.assertTrue(true);}
	catch (ConstraintCheckViolationException ccve) { Assert.assertFalse(true);}


	try {
	    attributeInt.setValue(-1,4);
	}
	catch (SizeException se) { Assert.assertTrue(true);}
	catch (ConstraintCheckViolationException ccve) { Assert.assertFalse(true); }
	
	try {
	    attributeInt.setValue(0,4);
	}
	catch (SizeException se) {Assert.assertFalse(true); }
	catch (ConstraintCheckViolationException ccve) { Assert.assertFalse(true); }
	finally {
	    try {
		Assert.assertTrue(attributeInt.getValue(0).intValue() == 4);
	    }
	    catch (SizeException se) {Assert.assertFalse(true);}
	}
	
	try {
	    attributeInt.setValue(values.length-1,2);
	}
	catch (SizeException se) {Assert.assertFalse(true); }
	catch (ConstraintCheckViolationException ccve) { Assert.assertFalse(true); }
	finally {
	    try {
		Assert.assertTrue(attributeInt.getValue(values.length-1).intValue() == 2);
	    }
	    catch (SizeException se) {Assert.assertFalse(true); }
	}


	
    }

    
    @Test()
    public void testOutOfLength() {

	Attribute<String> attributeString = null;
	try {
	    attributeString = new Attribute<String>(null,"Test String", 3,DataType.CHAR);
	}
	catch (SizeException se) {Assert.assertFalse(true);}
        

	try {
	    attributeString.addValue("HI");
	}
	catch (NullPointerException npe) { Assert.assertFalse(true); }
	catch (ConstraintCheckViolationException ccve) {Assert.assertFalse(true); }
	finally {
	    try {
		Assert.assertTrue( attributeString.getValue(0).equals("HI") );
	    }
	    catch ( SizeException se ) {Assert.assertFalse(true); }
	}


	try {
	    attributeString.setValue(0, "");
	}
	catch (SizeException se) {Assert.assertFalse(true); }
	catch (ConstraintCheckViolationException ccve) {Assert.assertFalse(true); }
	finally {
	    try {
		Assert.assertTrue( attributeString.getValue(0).equals("") ); 
	    }
	    catch ( SizeException se ) {Assert.assertFalse(true); }
	}

	try {
	    attributeString.setValue(0, "aaaaaa");
	}
	catch (SizeException se) {Assert.assertFalse(true);}
	catch (ConstraintCheckViolationException ccve) { Assert.assertTrue(true);}
	finally {
	    try {
		Assert.assertFalse( attributeString.getValue(0).equals("aaaaaa") ); 
	    }
	    catch ( SizeException se ) {Assert.assertFalse(true);}
	}



    }

			    
			   

    @Test()
    public void testAddConstraint() {

	try {
	    this.attributeInt = new Attribute<Integer>(null,this.name,10, DataType.INTEGER);
	}
	catch (SizeException se) {Assert.assertFalse(true);}
    
        for ( int i = 0 ; i < values.length ; i++ ) {
	    try {
		attributeInt.addValue(values[i]);
	    }
	    catch (NullPointerException npe) {Assert.assertFalse(true);}
	    catch (ConstraintCheckViolationException ccve) {Assert.assertFalse(true);}
	}

	try {
	    this.attributeInt.addConstraint( Constraint.PRIMARYKEY );
	    Assert.assertTrue( attributeInt.getConstraint(0) == Constraint.PRIMARYKEY );
	}
	catch (ConstraintCheckViolationException ccve ) { Assert.assertFalse(true); }
	catch (SizeException se ) {Assert.assertFalse(true); }
	
	try {
	    this.attributeInt.addConstraint( Constraint.NOTHING );
	    Assert.assertTrue( attributeInt.getConstraint(1) == Constraint.NOTHING );
	}
	catch (ConstraintCheckViolationException ccve ) {Assert.assertFalse(true); }
	catch (SizeException se ) { Assert.assertFalse(true); }

	// Add existed constraint, for testing verifExistConstraints()
	try {
	    this.attributeInt.setConstraint(1, Constraint.PRIMARYKEY );

	}
	catch (ConstraintCheckViolationException ccve ) { Assert.assertFalse(true); }
	catch (SizeException se )  { Assert.assertFalse(true); }
	finally {
	    try {
	        Assert.assertFalse( attributeInt.getConstraint(1) == Constraint.PRIMARYKEY );
	    }
	    catch (SizeException se ) {Assert.assertFalse(true); }
	}
					 
    }

    @Test()
    public void testAddCheck() {

	try {
	    this.attributeInt = new Attribute<Integer>(null,this.name,10, DataType.INTEGER);
	}
	catch (SizeException se) { Assert.assertFalse(true);}
	
	this.attributeInt.addCheck(">=", 10);

	// Also Test verifValues()

	try { this.attributeInt.addValue(5); }
	catch (ConstraintCheckViolationException e) {  Assert.assertTrue(true); }
	catch (NullPointerException e) {  Assert.assertFalse(true); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 5); }
	    catch (SizeException se) {  Assert.assertTrue(true); }
	}

	try { this.attributeInt.addValue(0); }
	catch (ConstraintCheckViolationException e) {  Assert.assertTrue(true); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 0); }
	    catch (SizeException se) { Assert.assertTrue(true); }
	}

	try { this.attributeInt.addValue(10); }
	catch (ConstraintCheckViolationException e) {  Assert.assertFalse(true); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 10); }
	    catch (SizeException se) {  Assert.assertFalse(true); }
	}
	
	try { this.attributeInt.setValue(0,15); }
	catch (SizeException e) {  Assert.assertFalse(true); }
	catch (ConstraintCheckViolationException e) {  Assert.assertFalse(true); }
	finally {
	    try { Assert.assertTrue(attributeInt.getValue(0) == 15); }
	    catch (SizeException se) {  Assert.assertFalse(true); }
	}
	
    }

    @Test()
    public void testGetType() {
	try {
	    this.attributeInt = new Attribute<Integer>(null,this.name,10, DataType.INTEGER) {};
	}
	catch (SizeException se) { Assert.assertFalse(true);}

	Assert.assertTrue(this.attributeInt.getType() == DataType.INTEGER);
    }

    public static junit.framework.Test suite() {
	return new junit.framework.JUnit4TestAdapter(AttributeTest.class);
    }
   
}
