package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailTest {
	
	
private static final String[] VALID_EMAILS =
{
   "waseem04748@gmail.com", // a sample for regular email address
   "john.doe@domain.com", // firstname.secondname@domain.com
   "student-number@university.edu.au" // a sample for official university email address.you can write your own email address here
	            
};

private static final String[] NOT_VALID_EMAILS =
{
	// this must be empty to verify the condition            
};


private SimpleEmail email;

@Before
public void setUp() {
		
	 email = new SimpleEmail();
}

@After
public void tearDown() {
	email = new SimpleEmail();
}

private static int Port = 2500;
private String MailServer = "localhost";
	
@Test
public void testGetMailSession() throws EmailException 
{
		
 final Properties properties = new Properties(System.getProperties());
 properties.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP); // here it sets SMTP as protocol
 properties.setProperty(EmailConstants.MAIL_PORT, String.valueOf(Port)); // here it sets value of port (which we set above 2500)
 properties.setProperty(EmailConstants.MAIL_HOST, MailServer); // here it sets hostname as 'localhost'
 properties.setProperty(EmailConstants.MAIL_DEBUG, String.valueOf(false));

 final Session emailSession = Session.getInstance(properties, null);
 email.setMailSession(emailSession);
 //System.out.print(email.getMailSession()); //javax.mail.Session@42d8062c
  assertEquals(emailSession, email.getMailSession());
}


	@Test
	public void testSetFrom() throws AddressException, EmailException {
		
		final List<InternetAddress> testData = new ArrayList<>(); // here i have made a list to store some data for test case. 

		testData.add(new InternetAddress("waseem04748@gmail.com"));
		testData.add(new InternetAddress("john.doe@domain.com"));
		testData.add(new InternetAddress("student-number@university.edu.au"));


		for (int i = 0; i < VALID_EMAILS.length; i++) { //this for loop sets email address from (VALID EMAILS function) one by one to our setFrom() to verify its functionality.
		email.setFrom(VALID_EMAILS[i]);
		assertNotNull(email.getFromAddress());// this will verify that the email addresses are being set and getFromAddress() is not null.
	     		
		}	
	}

	


@Test(expected = EmailException.class) // this expected condition means the test should return such exception.
public void testAddBcc_1() throws AddressException, EmailException {
			 
email.addBcc(NOT_VALID_EMAILS); // here NOT_VALID_EMAILS is empty, so it will raise EmailException.

	}	

@Test
public void testAddBcc_2() throws AddressException, EmailException {
		 
final List<InternetAddress> testData = new ArrayList<>(); // here i have made a list to store some data for test case. 

testData.add(new InternetAddress("waseem04748@gmail.com"));
testData.add(new InternetAddress("john.doe@domain.com"));
testData.add(new InternetAddress("student-number@university.edu.au"));


for (final String Email_address : VALID_EMAILS) { //this for loop adds email address from (VALID EMAILS function) one by one to our AddBcc() to verify its functionality.
email.addBcc(Email_address);
}
//Now we have added email addresses in addBcc(), we will verify them 

assertEquals(testData.size(), email.getBccAddresses().size()); // this assertion verifies if the number of email addresses we have written in above List  is same to that are added to addBcc() by fetching them using getBccAddresses() method.

assertEquals(testData.toString(),email.getBccAddresses().toString()); // this assertion verifies if given emails are same as are in getBccAddresses() function.
}




@Test
public void testAddCcString() throws AddressException, EmailException {
		 
final List<InternetAddress> testData = new ArrayList<>(); // here i have made a list to store some data for test case. 

testData.add(new InternetAddress("waseem04748@gmail.com"));
testData.add(new InternetAddress("john.doe@domain.com"));
testData.add(new InternetAddress("student-number@university.edu.au"));


for (final String Email_address : VALID_EMAILS) { //this for loop adds email address from (VALID EMAILS function) one by one to our addCc() to verify its functionality. Bcc and Cc should not be same but its just for testing purpose!
email.addCc(Email_address);
}
//Now we have added email addresses in addBcc(), we will verify them 

assertEquals(testData.size(), email.getCcAddresses().size()); // this assertion verifies if the number of email addresses we have written in above List  is same to that are added to addCc() by fetching them using getCcAddresses() method.

assertEquals(testData.toString(),email.getCcAddresses().toString()); // this assertion verifies if given emails are same as are in getCcAddresses() function.
}



@Test(expected = IllegalArgumentException.class)
public void testAddHeader_1() {
	email.addHeader("", "waseem04748@gmail.com"); // name can not be null or empty exception here!
	
}

@Test(expected = IllegalArgumentException.class)
public void testAddHeader_2() {
	email.addHeader(null, "waseem04748@gmail.com"); // name can not be null or empty exception here!
}

@Test(expected = IllegalArgumentException.class)
public void testAddHeader_3() {
	email.addHeader("MyName", ""); // value can not be null or empty exception here!
}

@Test(expected = IllegalArgumentException.class)
public void testAddHeader_4() {
	email.addHeader("MyName", null); // value can not be null or empty exception here!
}
//
@Test
public void testAddHeader_5()
{
   
    final Map<String, String> my_header = new HashMap<>();
    my_header.put("Myname", "60");
    

    for (final Map.Entry<String, String> email_header : my_header.entrySet()) {
        final String name = email_header.getKey();
        final String value = email_header.getValue();
        email.addHeader(name, value);
    }

    assertEquals(my_header.size(), email.headers.size()); // this assertion verifies that we have added 1 header in our addHeader() and the same quantity is present in email headers (Map).  
    assertEquals(my_header.toString(), email.headers.toString()); //this will match header data which we added   
}

	@Test
	public void testAddReplyTo() throws AddressException, EmailException, UnsupportedEncodingException{

		final String charset = EmailConstants.ISO_8859_1; //here we set an encoding standard...
//since our function returns an encoding value so we will set this encoding and verify if our function actually returns it or not.
		
		final String name = "Name1";

		
		final List<InternetAddress> testData = new ArrayList<>(); // here i have made a list to store some data for test case. 

		testData.add(new InternetAddress("waseem04748@gmail.com",name ,charset));
		testData.add(new InternetAddress("john.doe@domain.com", name,charset));
		testData.add(new InternetAddress("student-number@university.edu.au",name ,charset));

		
		
		for (final String Email_address : VALID_EMAILS) { 
//this for loop adds email address from (VALID EMAILS function), name and encoding value one by one to our addReplyTo() to verify its functionality.
		email.addReplyTo(Email_address,name, charset);
		}
		//Now we have added email addresses in addReplyTo(), we will verify them 

		assertEquals(testData.size(), email.getReplyToAddresses().size()); // this assertion verifies if the number of email entries we have written in above List  is same to that are added to addReplyTo() by fetching them using getReplyToAddresses() method.

		assertEquals(testData.toString(),email.getReplyToAddresses().toString()); // this assertion verifies if given input data are same as are in getReplyToAddresses() function returns.
		}
	

	@Test
	public void testBuildMimeMessage_1() throws EmailException, MessagingException {

        email.setHostName(MailServer);
        email.setSmtpPort(Port);
        email.setFrom("waseem04748@gmail.com");
        email.addTo("john.doe@domain.com");
        email.setSubject("Email Test");

        final String headerValue = "1234567890 1234567890 123456789 01234567890 123456789 0123456789 01234567890 01234567890";
        email.addHeader("MyName", headerValue);

        assertTrue(email.headers.size() == 1);
        assertFalse(email.headers.get("MyName").contains("\r\n"));

        email.buildMimeMessage();

        final MimeMessage message = email.getMimeMessage();
        message.saveChanges();
        final String[] values = message.getHeader("MyName");
        assertEquals(email.headers.size(), values.length);
	}
	
	@Test(expected = EmailException.class)
    public void testBuildMimeMessageTwice_2() throws EmailException {
        // build the message for the first time, as no session created so it will raise an exception!
		email.buildMimeMessage();
        
    }

@Test
public void testGetSentDate_1() {
 final Date currentDate = Calendar.getInstance().getTime();
 email.setSentDate(currentDate);
 assertEquals(currentDate, email.getSentDate());
	}
	
@Test
public void testGetSentDate_2() 
{
	
final Date currentDate = Calendar.getInstance().getTime();
email.setSentDate(null); // when we sent null as date, it will return a new Date.
	       
// Here Date works in milliseconds. So if you have a slow processor, time passes between the generation 
// of currentDate and the new Date() in getSentDate() method and this test will fail. 

// So we assert that the difference between currentDate &  new Date() is less than a second...
assertTrue((currentDate.getTime() - email.getSentDate().getTime() )< 1000);
}

	
	@Test
	public void testGetHostName_1() { // in this test case the session is created & not null.
					
		final Properties properties = new Properties(System.getProperties());
		 properties.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP); // here it sets SMTP as protocol
		 properties.setProperty(EmailConstants.MAIL_PORT, String.valueOf(Port)); // here it sets value of port (which we set above 2500)
		 properties.setProperty(EmailConstants.MAIL_HOST, MailServer); // here it sets hostname as 'localhost'
		 properties.setProperty(EmailConstants.MAIL_DEBUG, String.valueOf(false));

		 final Session emailSession = Session.getInstance(properties, null);
		 email.setMailSession(emailSession);
		 assertEquals(MailServer, email.getHostName());
	}
	
	@Test
	public void testGetHostName_2() { //in this test case the session is not created & is null. 
						
		final Properties properties = new Properties(System.getProperties());
		 properties.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP); // here it sets SMTP as protocol
		 properties.setProperty(EmailConstants.MAIL_PORT, String.valueOf(Port)); // here it sets value of port (which we set above 2500)
		 properties.setProperty(EmailConstants.MAIL_HOST, MailServer); // here it sets hostname as 'localhost'
		 properties.setProperty(EmailConstants.MAIL_DEBUG, String.valueOf(false));

//		 
		 email.setHostName(MailServer);
		 assertEquals(MailServer, email.getHostName());
	}
	
	@Test
	public void testGetHostName_3() { //in this test case the session is not created & hostname is also null.
						
		 email.setHostName("");
		 assertEquals(null, email.getHostName());
	}

	@Test
	public void testGetSocketConnectionTimeout_01() {
		
		int expectedTimeout = 60000; // default timeout value is 60 seconds which is 60000 in miliseconds
	    email.setSocketConnectionTimeout(expectedTimeout);

	    int actualTimeout = email.getSocketConnectionTimeout();
	    assertEquals(expectedTimeout, actualTimeout);
	   
	}

	@Test(expected = IllegalStateException.class)
	public void testGetSocketConnectionTimeout_02() {
		
		final Properties properties = new Properties(System.getProperties());
		 properties.setProperty(EmailConstants.MAIL_TRANSPORT_PROTOCOL, EmailConstants.SMTP); // here it sets SMTP as protocol
		 properties.setProperty(EmailConstants.MAIL_PORT, String.valueOf(Port)); // here it sets value of port (which we set above 2500)
		 properties.setProperty(EmailConstants.MAIL_HOST, MailServer); // here it sets hostname as 'localhost'
		 properties.setProperty(EmailConstants.MAIL_DEBUG, String.valueOf(false));

		 final Session emailSession = Session.getInstance(properties, null);
		 email.setMailSession(emailSession);
		 
		int expectedTimeout = 60000; // default timeout value is 60 seconds which is 60000 in miliseconds
	    email.setSocketConnectionTimeout(expectedTimeout);// here it will raise 'The mail session is already initialized' exception because we already made a session & e don't need to wait.

//  when we run getSocketConnectionTimeout() and it then calls setSocketConnectionTimeout() which raises exception	    
	    email.getSocketConnectionTimeout();  
	   
	}
}
