package com.vinne.webservice.mail;

public enum UserOutput {
    OPEN(1), CLOSE(2), BEZET(3);

    private int code;

    private UserOutput(int c) {
	code = c;
    }

    public int getCode() {

	return code;
    }

}
