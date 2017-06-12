package database.model;

import java.sql.*;

/**
 * This class hold the connection of the database. It allows to connect,
 * login, create user, log out, and execute the codes that and transfer
 * the databse on the server into server
 * @author TRANG Hoang Phong Vu
 * @author 
 */ 
public class Server {

    private Connect connect;
    private Statement stmt;
    private String username;
    private String password;
    private Database database;

    /**
     * Initialise the user and the password, then connecting to the
     * server. If the user want to create a user, It can be done by
     * loging in as administrator.
     * @param username user's name
     * @param password password
     */ 
    public Server(String username, String password) {
    }

    /**
     * Log in into the server
     */
    private void login() {}

    /**
     * Add a user into the server. This method can only be used by
     * administrator of the database
     * @param username  username
     * @param password password
     */
    public createUser(String username, String password) {
    }

    /**
     * Log out the server. This method can be used while creating user,
     * the administrator will log in into the server to add user, and
     * then loggin out.
     */ 
    private void logout() {
    }

    /**
     * Execute the code SQL passed in param
     * @param code code SQL
     */
    public void executeCode(String code) {
    }

    /**
     * Export the datas on the server into class Database
     */
    public void exportToDatabase() {
    }

    /**
     * Input the data from class Database into the server
     */
     public void imporFromDatabase() {
    }

    /**
     * Return the database exported into Dabase
     * @return The database
     */
    public Database getDatabase() {
    }
