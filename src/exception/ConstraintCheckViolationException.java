package database.exception; 

/**
 * ConstraintCheckViolation, which is an extended class of Exception, is called when the 
 * constraint or check is violated, existed, ...
 * @author TRANG Hoang Phong Vu
 */
public class ConstraintCheckViolationException extends Exception {

    public ConstraintCheckViolationException() {

	super();

    }

    public ConstraintCheckViolationException(String msg) {

	super(msg);

    }

}
