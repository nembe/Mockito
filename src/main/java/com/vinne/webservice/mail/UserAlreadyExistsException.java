package com.vinne.webservice.mail;

public class UserAlreadyExistsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4123557366013516876L;

    public UserAlreadyExistsException() {
	super();
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
	super(message, cause);
    }

    public UserAlreadyExistsException(String arg0) {
	super(arg0);

    }

}
