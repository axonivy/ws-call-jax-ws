package com.axonivy.connectivity.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.policy.WSPolicyFeature;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JOutInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.junit.Test;

import com.axonivy.connectivity.ws.saml.SapSamlProtectedEchoCxf;

import person.service.soap.client.WebServiceProcessTechnicalException;
import sap.saml.echo.ZCAPUGECHO;

public class IntegrationTestCxfSaml {

	@Test
	public void callWsSecuredService() throws MalformedURLException,
			WebServiceProcessTechnicalException, URISyntaxException {
		WSPolicyFeature ignorePolicy = new WSPolicyFeature();
				
		ignorePolicy.setIgnoreUnknownAssertions(true);
		ignorePolicy.setEnabled(false);
		
		ZCAPUGECHO service = SapSamlProtectedEchoCxf.service(ignorePolicy).getZCAPUGECHO();
		
		IntegrationTestCxfStockQuote.installSoapLogger(service);
		installWsSecurityHeader(service);
		
		String myOutput = service.zCAPUGECHO("myInput");
		assertThat(myOutput).isNotNull();
	}

	/**
	 * Tutorial: http://cxf.apache.org/docs/ws-security.html
	 * @param service
	 */
	private void installWsSecurityHeader(ZCAPUGECHO service) {
		Client client = ClientProxy.getClient(service);
		EndpointImpl endpoint = (EndpointImpl) client.getEndpoint();
		AbstractPhaseInterceptor<?>  wssOut = new PolicyBasedWSS4JOutInterceptor();
		endpoint.getOutInterceptors().add(wssOut);
		/*
		endpoint.getBus().getExtension(PolicyInterceptorProviderRegistry.class)
			.register(new SAPEnabledPolicyProvider());
		endpoint.getBus().getExtension(AssertionBuilderRegistry.class)
			.registerBuilder(new SAPEnabledAssertionBuilder());
			*/
	}

	private WSS4JOutInterceptor usernameTokenInterceptor() {
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
