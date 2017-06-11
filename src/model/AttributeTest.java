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

	for ( int i = 0 ; i < values.lenght ; i++ ) {
	    attributeInt.addValue(v);
	}

	attributeInt.setValue(-1,4);
	

    }


}
