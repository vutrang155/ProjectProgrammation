public class AttributeTest {
    private int[] values = {0, 9, 3, 2, 8, 12, 9};
    private String name;
    private Attribute<int> attributeInt;

    public AttributeTest(String name) {

	super(name);

    }

    @Before()
    protected void setUp() {

	this.name = "AttributeTest";
	this.attributeInt = new Attribute(name);

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
    public void testAddValue() {

	this.attributeInt.addValue(null)
	    Assert.assertFalse(attributeInt.getValue(0) == null);
	for(int i = 0; i < values.length; i++) {
	    attributeInt.addValue(v);
	}

	for(int i = 0; i < values.length; i++) {
	    Assert.assertTrue(attributeInt.getValue(i) == value[i]);
	}
    
    }
    
    public void testGetValue() {

	this.attributeInt = new Attribute(this.name);
	for ( int i = 0 ; i < values.length ; i++ ) {
	    attributeInt.addValue(v);
	}

	Assert.assertTrue(attributeInt.getValue(-1) == null);
	Assert.assertTrue(attributeInt.getValue(values.length) == null);

	Assert.assertTrue(attributeInt.getValue(0) == 0);
	Assert.assertFalse(attributeInt.getValue(1) == 10);

    }
    
    @Test()
    public void testSetValue() {
	
	this.attributeInt = new Attribute(this.name);
	for ( int i = 0 ; i < values.length ; i++ ) {
	    attributeInt.addValue(v);
	}

	attributeInt.setValue(-1,4);
	Assert.assertFalse(attribute.getValue(-1) == 4);

	attributeInt.setValue(999,4);
	Assert.assertFalse(attribute.getValue(999) == 4);
	    
	attributeInt.setValue(0,4);
	Assert.assertTrue(atribute.getValue(0) == 4);

	attributeInt.setValue( values.length-1, 2);
	Assert.assertTrue( attribute.getValue( values.length - 1 ) == 2 )
	
    }

    @Test()
    public void testAddConstraint() {

	this.attributeInt = new Attribute(this.name);
	for ( int i = 0 ; i < values.length ; i++ ) {
	    attributeInt.addValue(v);
	}

	this.attributeInt.addConstraint( Constraint.PRIMARYKEY );
	Assert.assertTrue( attributeInt.getConstraint(0) == Constraint.PRIMARYKEY );

	// Add existed constraint, for testing verifExistConstraints()
	this.attributeInt.addConstraint( Constraint.PRIMARYKEY );
	Assert.assertFalse( attributeInt.getConstraint(1) == Constraint.PRIMARYKEY );
					 
    }

    @Test()
    public void testAddCheck() {

	this.attributeInt = new Attribute(this.name);
	
	this.attributeInt.addCheck(">=", 10);

	// Also Test verifValues()
	
	this.attributeInt.addValue(5);
	Assert.assertTrue( attributeInt.getValue(0) == 5 );

	this.attributeInt.setValue(0,0);
	Assert.assertTrue( attributeInt.getValue(0) == 0 );

	this.attributeInt.setValue(0,10);
	Assert.assertTrue( attributeInt.getValue(0) == 10 );

	this.attributeInt.setValue(0,15);
	Assert.assertFalse( attributeInt.getValue(0) == 15 );

    }

}
