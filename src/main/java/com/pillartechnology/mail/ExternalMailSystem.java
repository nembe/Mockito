package com.pillartechnology.mail;

import com.vinne.webservice.mail.AdresNotFoundException;
import com.vinne.webservice.mail.MailNotFoundException;
import com.vinne.webservice.mail.UserAlreadyExistsException;
import com.vinne.webservice.mail.UserNotExistsException;

import java.util.List;

public interface ExternalMailSystem {

    /**
     * Systeem ontvangt losse stukjes en verzend Email.
     * 
     * @param adresAdres
     * @param ontvangerAdres
     * @param body
     * @throws MailNotFoundException
     * @throws UserNotExistsException
     */
    public void send(String adresAdres, String ontvangerAdres, String body) throws UserNotExistsException,
	    AdresNotFoundException;

    /**
     * Systeem handelt mail af.
     * 
     * @param email
     */
    public void send(Email email) throws UserNotExistsException, AdresNotFoundException;

    public void search(String adres);

    /**
     * geef de aantal mails weer van een gebruiker.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public int getTotalMails(String adres) throws MailNotFoundException, AdresNotFoundException;

    /**
     * verwijdert alle mails van een gebruiker met een bepaalde datum.
     * 
     * @param adres
     * @param datum
     * @throws UserNotExistsException
     */
    public boolean deleteMail(String adres, String datum) throws MailNotFoundException, UserNotExistsException,
	    AdresNotFoundException;

    /**
     * Het systeem voegt een gebruiker toe.
     * 
     * @param adres
     * @throws UserAlreadyExistsException
     */
    public void addUser(String adres) throws UserAlreadyExistsException, AdresNotFoundException;

    /**
     * het systeem zoekt naar de mails van een gebruiker met een speciefieke
     * datum.
     * 
     * @param adres
     * @param datum
     * @return
     * @throws MailNotFoundException
     */
    public Email findEmail(String adres, String datum) throws MailNotFoundException, AdresNotFoundException;

    /**
     * Email adres van een gebruiker opvragen.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public String findEmailAdres(String adres) throws MailNotFoundException, AdresNotFoundException;

    /**
     * Zoek alle mails van een bepaalde gebruiker.
     * 
     * @param adres
     * @return
     * @throws MailNotFoundException
     */
    public List<Email> findMails(String adres) throws MailNotFoundException, AdresNotFoundException;

    /**
     * Zoek alle mails van een gebruiker met een bepaalde afzender.
     * 
     * @param adres
     * @param afzender
     * @return
     * @throws MailNotFoundException
     */
    public List<Email> findMailsWithDeliverer(String adres, String afzender) throws MailNotFoundException,
	    AdresNotFoundException;

    /**
     * Systeem geeft alle mails weer.
     * 
     * @return
     */
    public int getTotalUsersInSystem();

    /**
     * Systeem verwijdert alle mails die op Close staan.. er kunnen in totaal 5
     * gebruikers tegelijk worden verwijdert anders ArrayOutofBoundException
     * 
     */
    public void cleanAllMails();

    /**
     * Systeem sluit alle mails af die bij een gebruiker horen.
     * 
     * @param adres
     */
    public void closeMails(String adres) throws AdresNotFoundException, UserNotExistsException;

}
