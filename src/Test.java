import java.util.*;
import java.lang.reflect.Field;
import database.model.Attribute;
import database.model.*;
import database.exception.*;

public class Test {
    public static void main(String[] args) {
		try {
	    Server server = new Server("test1","zczccczczzzcc");
	    server.exportDatabaseTables();
	    server.exportDatabaseConstraints();

	    	    
  
	   
	}catch ( Exception e) {
	    System.out.println(e.getMessage());
		}

	/*try {
	    Server server = new Server("test1","zczccczczzzcc");

	    Database db = server.getDatabase();
	    
	    Table tab = new Table("Student");
	    tab.addAttribute("idStudent",DataType.INTEGER, 2);
	    tab.addAttribute("name",DataType.CHAR,50);
	    tab.addAttribute("note",DataType.DOUBLE,6);
	    tab.addAttribute("birthday",DataType.DATE,11);
	    
	    tab.addConstraint(0,Constraint.PRIMARYKEY);
	    tab.addCheck(2,">=","0");
	    tab.addCheck(2,"<=","20");


	    Table tab2 = new Table("Works");

	    tab2.addAttribute("id",DataType.INTEGER,2);
	    tab2.addAttribute("name",DataType.CHAR,100);
	    tab2.addAttribute("dev",DataType.INTEGER,2);

	    tab2.addConstraint(0,Constraint.PRIMARYKEY);
	    tab2.addConstraint(2,Constraint.REFERENCEKEY,tab.getAttribute(0));

	    ArrayList<Attribute> tuple = tab.tuple();

	    tuple.get(0).addValue(0);
	    tuple.get(1).addValue("Vu");
	    tuple.get(2).addValue(18.4);
	    tuple.get(3).addValueString("15/05/1998");

	    tab.insertTUple(tuple);
	    
	    tuple = tab.tuple();

	    tuple.get(0).addValue(1);
	    tuple.get(1).addValue("Xuan");
	    tuple.get(2).addValue(18.4);
	    tuple.get(3).addValueString("28/01/1997");

	    tab.insertTUple(tuple);
	
	    tuple = tab.tuple();

	    tuple.get(0).addValue(2);
	    tuple.get(1).addValue("Dr who");
	    tuple.get(2).addValue(20);
	    tuple.get(3).addValueString("01/01/1961");

	    tab.insertTUple(tuple);
	
	    ArrayList<Attribute> c2 = tab2.tuple();
	    
	    c2.get(0).addValue(0);
	    c2.get(1).addValue("Cahier d analyse");
	    c2.get(2).addValue(1);

	    tab2.insertTUple(c2);

	    c2 = tab2.tuple();
	    
	    c2.get(0).addValue(1);
	    c2.get(1).addValue("Codage");
	    c2.get(2).addValue(2);

	    tab2.insertTUple(c2);
	    
	    c2 = tab2.tuple();
	      
	    c2.get(0).addValue(2);
	    c2.get(1).addValue("Finalisation");
	    c2.get(2).addValue(2);

	    tab2.insertTUple(c2);

	    db.addTable(tab);
	    db.addTable(tab2);
	    System.out.println("OK");
	    server.importFromDatabase();
	}
	catch ( Exception e ) { System.out.println(e.getMessage()); }*/
    }
	
}
