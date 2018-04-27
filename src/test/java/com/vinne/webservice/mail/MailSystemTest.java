package com.vinne.webservice.mail;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.pillartechnology.mail.Email;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(JUnit4.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "applicationContext-test.xml" })
// @RunWith(MockitoJUnitRunner.class)
public class MailSystemTest {

    @Autowired
    MailSystem system;

    @Test
    public void tesMail() throws UserAlreadyExistsException, AdresNotFoundException, MailNotFoundException,
	    UserNotExistsException {

	String adres = "vinne@doenda.nl";
	String ontvangerAdres = "blaka@doenda.nl";
	String body = "Fawaka blaka";

	system.addUser(adres);
	system.addUser(ontvangerAdres);

	system.send(adres, ontvangerAdres, body);
	List<Email> mails = system.findMails(ontvangerAdres);

	assertThat("Welkom blaka", is(mails.get(0).getBody()));
	assertThat("Admin", is(mails.get(0).getAfZender()));
	// aantal mails in systeem
	assertTrue(2 == system.getTotalUsersInSystem());

	// Aantal mails van betreffende gebruiker
	assertTrue(2 == system.getTotalMails(ontvangerAdres));
    }

    @Test
    public void tesMailsVanAfzenderEnDatum() throws AdresNotFoundException, MailNotFoundException,
	    UserNotExistsException {

	String adres = "vinne@doenda.nl";
	String ontvangerAdres = "blaka@doenda.nl";
	String body = "Derde Mail blaka";

	// zoeken met afzender
	system.send(adres, ontvangerAdres, body);
	List<Email> mails = system.findMailsWithDeliverer(ontvangerAdres, adres);

	assertThat(3, is(system.getTotalMails(ontvangerAdres)));
	assertEquals("Derde Mail blaka", mails.get(0).getBody());
	assertThat("vinne@doenda.nl", is(mails.get(0).getAfZender()));

	// Zoeken met datum
	String tijd = mails.get(0).getTijdStip();
	Email mail = system.findEmail(ontvangerAdres, tijd);
	assertThat(mail.getTijdStip(), is(tijd));

	// Aantal mails van betreffende gebruiker
	assertTrue(3 == system.getTotalMails(ontvangerAdres));
    }

    @Test
    public void testVerwijderenMails() throws AdresNotFoundException, MailNotFoundException, UserNotExistsException {

	String afzenderadres = "vinne@doenda.nl";
	String ontvangeradres = "blaka@doenda.nl";

	// zoeken met afzender
	List<Email> mails = system.findMailsWithDeliverer(ontvangeradres, afzenderadres);
	// Zoeken met datum
	String tijd = mails.get(0).getTijdStip();

	assertTrue(system.deleteMail(ontvangeradres, tijd));
	// Aantal mails van betreffende gebruiker
	assertTrue(2 == system.getTotalMails(ontvangeradres));

    }

    @Test
    public void testVerwijderenGebruiker() throws AdresNotFoundException, MailNotFoundException, UserNotExistsException {

	// Afsluiten van Mails voor de volgende tests (Close)
	system.closeMails("blaka@doenda.nl");
	system.cleanAllMails();
	// aantal mails in systeem
	assertTrue(1 == system.getTotalUsersInSystem());
    }

    @Test(expected = MailNotFoundException.class)
    public void testMailNotFoundException() throws AdresNotFoundException, MailNotFoundException,
	    UserNotExistsException {

	String adres = "vinne@doenda.nl";
	Email mail = system.findEmail(adres, "21-11-2012");
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testUserAlreadyExistsException() throws UserAlreadyExistsException, AdresNotFoundException {

	String adres = "vinne@doenda.nl";
	system.addUser(adres);
    }

    @Test(expected = AdresNotFoundException.class)
    public void testAdresNotFoundException() throws AdresNotFoundException, UserNotExistsException {

	String adres = "vinne@doenda.nl";
	String ontvangerAdres = "notexistsuserdoenda.nl";
	String body = "Fawaka notexistsuser this is not a valid email adres";

	system.send(adres, ontvangerAdres, body);
    }

    @Test(expected = UserNotExistsException.class)
    public void testUserNotExistsException() throws UserAlreadyExistsException, AdresNotFoundException,
	    UserNotExistsException {

	String adres = "vinne@doenda.nl";
	String ontvangerAdres = "notexistsuser@doenda.nl";
	String body = "Fawaka notexistsuser";

	system.send(adres, ontvangerAdres, body);
    }

}
