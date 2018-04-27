package com.pillartechnology.mail;

import java.util.Date;

import org.joda.time.DateTime;

public class Timestamper {

    public Date stamp() {
	return null; // TODO - not yet implemented
    }

    public String getStamp() {
	DateTime datetime = new DateTime();

	// LocalTime localTime = new LocalTime("HH:mm:ss");
	return datetime.toString("dd-MM-yyyy HH:mm:ss");
    }

}
