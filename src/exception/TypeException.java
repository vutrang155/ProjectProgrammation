package database.exception; 

/**
 * The Type Exception is called when user add a wrong
 * data type column
 * @author TRANG Hoang Phong Vu
 */
public class TypeException extends Exception {

    public TypeException() {

	super();

    }

    public TypeException(String msg) {

	super(msg);

    }

}
