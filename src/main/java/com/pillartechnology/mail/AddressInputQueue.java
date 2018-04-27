package com.pillartechnology.mail;

import com.vinne.webservice.mail.MailNotFoundException;
import com.vinne.webservice.mail.UserAlreadyExistsException;
import com.vinne.webservice.mail.UserNotExistsException;
import com.vinne.webservice.mail.UserOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection met Mails, is verantwoordelijk voor het ontvangen en afhandelen
 * (schoonmaken afsluiten) van Mails en Accounts.
 * 
 * @author Eigenaar
 * 
 */
public class AddressInputQueue {

    private Map<String, List<Email>> gegevens;

    public AddressInputQueue() {

	this.gegevens = new HashMap<String, List<Email>>();
    }

    public String next() {
	return null;
    }

    /**
     * Het ontvangen van een Mail en deze afhandelen a.d.v een username. Hierbij
     * is de username de key en Mail zorgt voor de email adressen en inhoud.
     * Account wordt ook eerst aangemaakt in de hoger gelegen lagen wordt die
     * check aangeroepen..
     * 
     * @param key
     * @param email
     * @throws MailNotFoundException
     */
    public void verwerkMail(String ontvangerAdres, Email email) throws UserNotExistsException {

	if (checkAccount(ontvangerAdres)) {
	    List<Email> foundMails;
	    try {
		foundMails = returnMails(ontvangerAdres);
		foundMails.add(email);
	    } catch (MailNotFoundException e) {
		throw new UserNotExistsException("verwerkMail kan geen mails vinden voor deze account "
			+ ontvangerAdres);
	    }
	}

    }

    /**
     * Maakt een nieuwe account aan.
     * 
     * @return
     */
    public void maakAccount(String user) throws UserAlreadyExistsException {

	boolean account = gegevens.containsKey(user);
	if (!account) {
	    ArrayList<Email> mails = new ArrayList<Email>();
	    mails.add(createWelkomsMail(user));
	    gegevens.put(user, mails);
	} else
	    throw new UserAlreadyExistsException(" Deze gebruiker bestaat reeds in het systeem " + user);

    }

    /**
     * Maakt een welkoms mail aan!
     * 
     * @param user
     * @return
     */
    private Email createWelkomsMail(String user) {

	Email welkomMail = new Email();
	String body = "Welkom " + user;
	welkomMail.setStatus(UserOutput.OPEN.name());
	welkomMail.setBody(body);
	welkomMail.setUser(user);
	welkomMail.setAfZender("Admin");
	return welkomMail;
    }

    /**
     * Checkt of een account reeds bestaat.
     * 
     * @param user
     * @return
     */
    private boolean checkAccount(String user) throws UserNotExistsException {

	boolean account = gegevens.containsKey(user);
	if (account) {
	    return gegevens.containsKey(user);
	} else
	    throw new UserNotExistsException(" Het Systeem kan deze gebruiker niet vinden " + user);

    }

    /**
     * Haalt de aantal mails op a.d.v een username in collection.
     * 
     * @param user
     * @return
     * @throws MailNotFoundException
     */
    public int returnAantalMails(final String user) throws MailNotFoundException {

	List<Email> mails = gegevens.get(user);
	if (mails != null) {
	    return mails.size();
	} else
	    throw new MailNotFoundException("returnMail heeft geen mail kunnen vinden voor user =" + user);
    }

    /**
     * Geef de Totale aantal mails weer in het systeem.
     * 
     * @param user
     * @return
     * @throws MailNotFoundException
     */
    public int totaalSystemUsers() {

	return gegevens.size();

    }

    /**
     * Haalt een mail op a.d.v een username en datum in collection.
     * 
     * @param user
     * @return
     * @throws MailNotFoundException
     */
    public Email returnMail(final String user, final String datum) throws MailNotFoundException {

	List<Email> mails = gegevens.get(user);
	if (mails != null) {
	    for (Email email : mails) {
		if (email.getTijdStip().equals(datum)) {
		    return email;
		}
	    }
	    throw new MailNotFoundException("returnMail heeft geen mail kunnen vinden voor user =" + user
		    + " met datum " + datum);
	} else
	    throw new MailNotFoundException("returnMail heeft geen mail kunnen vinden voor user =" + user);
    }

    /**
     * Haalt een mail op a.d.v een username in collection.
     * 
     * @param user
     * @return
     * @throws MailNotFoundException
     */
    public List<Email> returnMails(final String user) throws MailNotFoundException {

	List<Email> mails = gegevens.get(user);
	if (mails != null) {
	    return mails;
	} else
	    throw new MailNotFoundException("returnMails heeft geen mails kunnen vinden voor user =" + user);
    }

    /**
     * Zoekt een Email adres van een gebruiker a.d.v een username.
     * 
     * @param user
     * @return
     * @throws MailNotFoundException
     */
    public String searchMailAdres(String user) throws MailNotFoundException {

	List<Email> foundMails = gegevens.get(user);

	if (foundMails != null) {
	    return foundMails.get(0).getAdres();
	} else
	    throw new MailNotFoundException("searchMailAdres heeft geen mailAdres kunnen vinden voor user =" + user);

    }

    /**
     * Zoekt naar een Mails met een bepaalde afzender.
     * 
     * @param afzender
     * @return
     * @throws MailNotFoundException
     */
    public List<Email> searchMailMetAfzender(String user, String afzender) throws MailNotFoundException {

	List<Email> mails = gegevens.get(user);
	if (mails != null) {
	    List<Email> foundMails = null;
	    for (Email email : mails) {
		if (email.getAfZender().equals(afzender)) {
		    foundMails = new ArrayList<Email>();
		    foundMails.add(email);
		}
	    }
	    if (foundMails == null) {
		throw new MailNotFoundException("searchMailMetAfzender heeft geen mails kunnen vinden voor user ="
			+ user + " Met afzender =" + afzender);
	    }
	    return foundMails;
	} else
	    throw new MailNotFoundException("searchMailMetAfzender heeft geen mail kunnen vinden voor user =" + user);

    }

    /**
     * De mails afsluiten van een bepaalde gebruiker.
     * 
     * @param afzender
     * @return
     * @throws UserNotExistsException
     * @throws MailNotFoundException
     */

    public void afsluitenMails(String user) throws UserNotExistsException {

	if (checkAccount(user)) {
	    List<Email> foundMails = gegevens.get(user);
	    for (Email mail : foundMails) {
		if (mail.getStatus().equals(UserOutput.OPEN.name())) {
		    mail.setStatus(UserOutput.CLOSE.name());
		}

	    }
	}

    }

    /**
     * Verwijderen van mail van een bepaalde gebruiker met datum.
     * 
     * @param afzender
     * @return
     * @throws MailNotFoundException
     */
    public boolean removeMail(String user, String datum) throws UserNotExistsException {

	if (checkAccount(user)) {

	    List<Email> foundMails = gegevens.get(user);
	    for (Email mail : foundMails) {
		if (mail.getTijdStip().equals(datum)) {
		    foundMails.remove(mail);
		    return true;
		}
	    }
	    return false;
	} else
	    throw new UserNotExistsException("removeMail kan geen mails vinden voor deze gebruiker " + user);

    }

    /**
     * opruimen van afgesloten mails.
     * 
     * @param afzender
     * @return
     * @throws MailNotFoundException
     */
    public void schoonMakenMails() {
	// Aantal gebruikers die tegelijk afgeloten kunnen worden is.
	String[] user = new String[5];
	int i = 0;

	for (List<Email> mails : gegevens.values()) {
	    for (Email email : mails) {
		if (email.getStatus().equals(UserOutput.CLOSE.name())) {
		    user[i] = email.getUser();
		    i++;
		}
	    }
	}

	for (int j = 0; j < user.length; j++) {
	    gegevens.remove(user[j]);
	}

    }
}
