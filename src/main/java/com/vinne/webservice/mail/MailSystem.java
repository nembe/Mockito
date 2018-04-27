package com.vinne.webservice.mail;

import com.pillartechnology.mail.AddressInputQueue;
import com.pillartechnology.mail.AddressSplitter;
import com.pillartechnology.mail.Email;
import com.pillartechnology.mail.ExternalMailSystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailSystem implements ExternalMailSystem {

    @Autowired()
    private AddressSplitter addressSplitter;

    @Autowired
    private AddressInputQueue inputQueue;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#send(com.pillartechnology
     * .mail.Email)
     */
    public void send(Email email) throws UserNotExistsException, AdresNotFoundException {

	email.setStatus(UserOutput.OPEN.name());
	addressSplitter.verstuurMail(email);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pillartechnology.mail.ExternalMailSystem#send(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void send(String userAdres, String ontvangerAdres, String body) throws UserNotExistsException,
	    AdresNotFoundException {

	Email email = new Email();
	email.setAfZender(userAdres);
	email.setStatus(UserOutput.OPEN.name());
	email.setBody(body);
	email.setAdres(ontvangerAdres);
	addressSplitter.verstuurMail(ontvangerAdres, email);
    }

    @Override
    public void search(String adres) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#getTotalMails(java.lang.
     * String)
     */
    @Override
    public int getTotalMails(String adres) throws MailNotFoundException, AdresNotFoundException {
	return addressSplitter.haalAantalEmails(adres);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#deleteMail(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean deleteMail(String adres, String datum) throws UserNotExistsException, AdresNotFoundException {

	return addressSplitter.verwijderenMails(adres, datum);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#addUser(java.lang.String)
     */
    @Override
    public void addUser(String adres) throws UserAlreadyExistsException, AdresNotFoundException {
	addressSplitter.voegGebruikerToe(adres);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#findEmail(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Email findEmail(String adres, String datum) throws MailNotFoundException, AdresNotFoundException {

	return addressSplitter.zoekEmail(adres, datum);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#findEmailAdres(java.lang
     * .String)
     */
    @Override
    public String findEmailAdres(String adres) throws MailNotFoundException, AdresNotFoundException {

	return addressSplitter.zoekEmailAdres(adres);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#findMails(java.lang.String)
     */
    @Override
    public List<Email> findMails(String adres) throws MailNotFoundException, AdresNotFoundException {

	return addressSplitter.zoekEmails(adres);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#findMailsWithDeliverer(java
     * .lang.String, java.lang.String)
     */
    @Override
    public List<Email> findMailsWithDeliverer(String adres, String afzender) throws MailNotFoundException,
	    AdresNotFoundException {

	return addressSplitter.zoekEmailUserMetAfzender(adres, afzender);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pillartechnology.mail.ExternalMailSystem#getTotalMailsInSystem()
     */
    @Override
    public int getTotalUsersInSystem() {

	return inputQueue.totaalSystemUsers();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.pillartechnology.mail.ExternalMailSystem#cleanAllMails()
     */
    @Override
    public void cleanAllMails() {
	inputQueue.schoonMakenMails();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pillartechnology.mail.ExternalMailSystem#closeMails(java.lang.String)
     */
    @Override
    public void closeMails(String adres) throws AdresNotFoundException , UserNotExistsException{

	addressSplitter.mailsVanDezeGebruikerAfsluiten(adres);
    }

}
