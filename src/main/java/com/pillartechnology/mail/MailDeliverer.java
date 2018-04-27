package com.pillartechnology.mail;

import com.vinne.webservice.mail.AdresNotFoundException;
import com.vinne.webservice.mail.MailNotFoundException;
import com.vinne.webservice.mail.UserNotExistsException;

public class MailDeliverer {

    private ExternalMailSystem externalMailSystem;
    private Timestamper timestamper;

    public void deliver(String address, String body) throws AdresNotFoundException, MailNotFoundException,
	    UserNotExistsException {
	Email email = new Email();
	email.setBody(body);
	applyUserAndDomainUsingProvidedAddress(address, email);
	email.setTimestamp(timestamper.stamp());
	externalMailSystem.send(email);
    }

    private void applyUserAndDomainUsingProvidedAddress(String address, Email email) {
	// TODO - refactor this into the Email class (constructor? smart
	// accessor?)
	String[] addressComponents = address.split("@");
	email.setUser(addressComponents[0]);
	email.setDomain(addressComponents[1]);
    }
}
