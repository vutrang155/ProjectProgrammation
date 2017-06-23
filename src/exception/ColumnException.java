package database.exception; 

/**
 * ColumnException will be called when there's errors in creation of columns.
 * constraint or check is violated, existed, ...
 * @author TRANG Hoang Phong Vu
 */
public class ColumnException extends Exception {

    public ColumnException() {

	super();

    }

    public ColumnException(String msg) {

	super(msg);

    }

}
