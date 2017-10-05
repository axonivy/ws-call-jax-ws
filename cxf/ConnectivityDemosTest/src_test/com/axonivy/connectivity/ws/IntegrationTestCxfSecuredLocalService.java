package com.axonivy.connectivity.ws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.junit.Test;

import person.service.soap.client.Person;
import person.service.soap.client.PersonWsSoap;
import person.service.soap.client.WebServiceProcessTechnicalException;

import com.axonivy.connectivity.rest.EngineUrl;
import com.axonivy.connectivity.ws.secure.PersonSoapCxf;

public class IntegrationTestCxfSecuredLocalService {

	@Test
	public void callWsSecuredService() throws MalformedURLException,
			WebServiceProcessTechnicalException, URISyntaxException {
		
		URI wsdl = new URI(EngineUrl.soap()+"/ConnectivityDemos/15EEB6E4567F1390?WSDL");
		PersonWsSoap service = PersonSoapCxf.service(wsdl);
		IntegrationTestCxfStockQuote.installSoapLogger(service);
		installWsSecurityHeader(service);

		Person rew = new Person();
		rew.setFirstname("Reguel");
		rew.setLastname("Wermelinger");
		
		service.hello(rew);
	}

	/**
	 * Tutorial: http://cxf.apache.org/docs/ws-security.html
	 * @param service
	 */
	private void installWsSecurityHeader(PersonWsSoap service) {
		Endpoint endpoint = ClientProxy.getClient(service).getEndpoint();
		WSS4JOutInterceptor wssOut = getWsSecurityInterceptor();
		endpoint.getOutInterceptors().add(wssOut);
	}

	private WSS4JOutInterceptor getWsSecurityInterceptor() {
		Map<String, Object> outProps = new HashMap<>();
		outProps.put(WSHandlerConstants.ACTION,
				WSHandlerConstants.USERNAME_TOKEN);
		// Specify our username
		outProps.put(WSHandlerConstants.USER, "Developer");
		// Password type : plain text
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		// for hashed password use:
		// properties.put(WSHandlerConstants.PASSWORD_TYPE,
		// WSConstants.PW_DIGEST);
		// Callback used to retrieve password for given user.
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
				ClientPasswordHandler.class.getName());

		return new WSS4JOutInterceptor(outProps);
	}

	public static class ClientPasswordHandler implements CallbackHandler {

		public void handle(Callback[] callbacks) throws IOException,
				UnsupportedCallbackException {

			WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

			// set the password for our message.
			pc.setPassword("Developer");
		}
	}

}
