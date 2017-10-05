package com.axonivy.connectivity.ws.secure;

import java.net.MalformedURLException;
import java.net.URI;

import person.service.soap.client.PersonWsSoap;
import person.service.soap.client.PersonWsSoapService;

public class PersonSoapCxf {

	public static PersonWsSoap service(URI uri) throws MalformedURLException {
		PersonWsSoapService quoteService = new PersonWsSoapService(uri.toURL());
		return quoteService.getPersonWsSoapPort();
	}
	
}
