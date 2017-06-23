package database.test;


import database.model.*;
import java.util.*;
import database.exception.*;
import org.junit.*;

import org.junit.runner.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

import java.sql.*;

public class ServerTest {
    private Server server;

    @Before()
    public void setUp() {

	try {
	    server = new Server("learntofly1","zczccczczzzcc");
	}
	catch (Exception e) {
	    assertTrue(false);
	}

	assertTrue( server.isConnected() );
    }
    
    @Test()
    public void testExecuteCode() {
	try {
	    ResultSet rs = server.executeCode("SELECT * FROM tab");
	    server.finish();
	    assertTrue(true);
	}
	catch ( Exception e ) {
	    System.out.println(e.getMessage());
	    assertTrue(false);
	}
        
    }

    @Test()
    public void testChangeSession() {

	//Error Case
	try {
	    server.changeSession("ASDCASDCASDCASD","ASDCASDCASD");
	}
	catch (SQLException e) {
	    assertTrue(true);
	}

	//NormalCase
	try {
	    server.changeSession("test1","zczccczczzzcc");
	}
	catch (SQLException e) {       
	    assertTrue(false);
	}
    }

    @Test()
    public void testCreateUser() {

	try {
	    server.createUser("test2","zczccczczzzcc");
	}
	catch (SQLException e) {
	    
	    assertTrue(false);
	}

	try {
	    server.changeSession("test2","zczccczczzzcc");
	    
	    server.changeSession("learntofly1","zczccczczzzcc");

	    server.executeCode("DROP USER TEST2");
	}
	catch (SQLException e) {
	    System.out.println(e.getMessage());
	    assertTrue(false);
	}

    }
    @After()
    public void after() {
	try {
	server.finish();
	}
	catch( SQLException e ) { assertTrue(false); }
    }

    

}
