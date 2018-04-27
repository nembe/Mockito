package com.vinne.webservice.mail;

public class UserNotExistsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8534106733895430298L;

    public UserNotExistsException() {
	super();
    }

    public UserNotExistsException(String message, Throwable cause) {
	super(message, cause);
    }

    public UserNotExistsException(String arg0) {
	super(arg0);

    }

}
