package com.pillartechnology.mail;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vinne.webservice.mail.AdresNotFoundException;
import com.vinne.webservice.mail.MailNotFoundException;
import com.vinne.webservice.mail.UserNotExistsException;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.annotations.BeforeMethod;

//@RunWith(JUnit4.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "applicationContext.xml" })
@RunWith(MockitoJUnitRunner.class)
public class MailDelivererTest {

    @InjectMocks
    private MailDeliverer subject = new MailDeliverer();

    @Mock
    private ExternalMailSystem externalMailSystem;
    @Mock
    private Timestamper timestamper;

    @Captor
    private ArgumentCaptor<Email> emailCaptor;

    @BeforeMethod(alwaysRun = true)
    public void injectDoubles() {
	MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendsEmailByConstructingEmailObject() throws MailNotFoundException, UserNotExistsException,
	    AdresNotFoundException {
	String expectedUser = "tim";
	String expectedDomain = "wingfield.com";
	String expectedBody = "Hi Tim!";

	subject.deliver(expectedUser + "@" + expectedDomain, expectedBody);

	verify(externalMailSystem).send(emailCaptor.capture());
	Email email = emailCaptor.getValue();
	assertThat(email.getUser(), is(expectedUser));
	assertThat(email.getDomain(), is(expectedDomain));
	assertThat(email.getBody(), is(expectedBody));
    }

    @Test
    public void setsTheTimestampOnTheEmail() throws MailNotFoundException, UserNotExistsException,
	    AdresNotFoundException {
	Date expected = new Date(8932l);
	when(timestamper.stamp()).thenReturn(expected);

	subject.deliver("a@b", null);

	verify(externalMailSystem).send(emailCaptor.capture());
	Email email = emailCaptor.getValue();
	assertThat(email.getTimestamp(), is(expected));
    }

    @Test
    public void testTimeStamper() {

	Timestamper timestamper2 = new Timestamper();
	System.out.println("Time :" + timestamper2.getStamp());

    }

}
