package com.pillartechnology.mail;

import static java.util.Arrays.asList;

import com.vinne.webservice.mail.AdresNotFoundException;
import com.vinne.webservice.mail.MailNotFoundException;
import com.vinne.webservice.mail.UserAlreadyExistsException;
import com.vinne.webservice.mail.UserNotExistsException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Is verantwoordelijk voor het ontvangen van Mails met een email adres en dit
 * adres op te splitsen en door te sturen naar de backend.
 * 
 * @author Eigenaar
 * 
 */
public class AddressSplitter {

    @Autowired
    private AddressInputQueue addressInputQueue;

    public List<String> split() {
	return asList(addressInputQueue.next().split(","));
    }

    /**
     * Eerst een check doen of user al bestaat of is geblokkeerd.
     * 
     * @param ontvangerAdres
     * @param email
     * @throws MailNotFoundException
     */
    public void verstuurMail(String ontvangerAdres, Email email) throws UserNotExistsException, AdresNotFoundException {
	String[] componenten = returnAddressComponents(ontvangerAdres);
	email.setUser(componenten[0]);
	email.setDomain(componenten[1]);
	addressInputQueue.verwerkMail(componenten[0], email);
    }

    /**
     * A.d.v ontvangen Email object mail vertsuren.
     * 
     * @param ontvangerAdres
     * @param email
     * @throws MailNotFoundException
     */
    public void verstuurMail(Email email) throws UserNotExistsException, AdresNotFoundException {
	String[] componenten = returnAddressComponents(email.getAdres());
	email.setUser(componenten[0]);
	email.setDomain(componenten[1]);
	addressInputQueue.verwerkMail(componenten[0], email);
    }

    /**
     * zoek email adres van Gebruiker.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public String zoekEmailAdres(String adres) throws MailNotFoundException, AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	return addressInputQueue.searchMailAdres(componenten[0]);
    }

    /**
     * Zoekt mails van de gebruiker met een bepaalde afzender.
     * 
     * @param key
     * @return
     */
    public List<Email> zoekEmailUserMetAfzender(String adres, String afzender) throws MailNotFoundException,
	    AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	return addressInputQueue.searchMailMetAfzender(componenten[0], afzender);
    }

    /**
     * Vraagt alle mails van een gebruiker op.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public List<Email> zoekEmails(String adres) throws MailNotFoundException, AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	return addressInputQueue.returnMails(componenten[0]);
    }

    /**
     * Zoekt een bepaalde mail van een gebruiker a.d.v een datum.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public Email zoekEmail(String adres, String datum) throws MailNotFoundException, AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	return addressInputQueue.returnMail(componenten[0], datum);
    }

    /**
     * Vraagt aantal mails op van een gebruiker.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public int haalAantalEmails(String adres) throws MailNotFoundException, AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	return addressInputQueue.returnAantalMails(componenten[0]);
    }

    /**
     * Mails op Close zetten van een bepaalde gebruiker;
     */
    public void mailsVanDezeGebruikerAfsluiten(String adres) throws AdresNotFoundException, UserNotExistsException {

	String[] componenten = returnAddressComponents(adres);
	addressInputQueue.afsluitenMails(componenten[0]);
    }

    /**
     * vERWIJDEREN van Mail die horen bij een bepaalde gebruiker met datum.
     * 
     * @return
     */
    public boolean verwijderenMails(String adres, String datum) throws UserNotExistsException, AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	return addressInputQueue.removeMail(componenten[0], datum);

    }

    /**
     * Afsluiten v.d mails van een bepaalde gebruiker.
     * 
     * @return
     */
    public void afsluitenMails(String adres) throws AdresNotFoundException, UserNotExistsException {

	String[] componenten = returnAddressComponents(adres);
	addressInputQueue.afsluitenMails(componenten[0]);
    }

    /**
     * Maakt een nieuwe gebruiker aan.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public void voegGebruikerToe(String adres) throws UserAlreadyExistsException, AdresNotFoundException {

	String[] componenten = returnAddressComponents(adres);
	addressInputQueue.maakAccount(componenten[0]);
    }

    private String[] returnAddressComponents(String address) throws AdresNotFoundException {
	String[] addressComponents = address.split("@");
	if (addressComponents.length > 1) {
	    return addressComponents;
	} else
	    throw new AdresNotFoundException("returnAddressComponents heeft geen adres gevonden " + address);

    }

}
