package com.axonivy.connectivity.ws;

import static com.axonivy.connectivity.ws.IntegrationTestCxfStockQuote.installSoapLogger;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderRegistry;
import org.junit.Test;

import com.axonivy.connectivity.ws.saml.SAPEnabledAssertionBuilder;
import com.axonivy.connectivity.ws.saml.SAPEnabledPolicyProvider;
import com.axonivy.connectivity.ws.saml.mex.SapSamlProtectedMexEchoCxf;
import com.axonivy.connectivity.ws.saml.ping.PingIdentitySTSClientConfigurer;

import person.service.soap.client.WebServiceProcessTechnicalException;
import sap.saml.mex.echo.ZCAPUGECHO;

public class IntegrationTestCxfSamlPingIdentity {

	@Test
	public void callWsSecuredService() throws MalformedURLException,
			WebServiceProcessTechnicalException, URISyntaxException {
		ZCAPUGECHO service = SapSamlProtectedMexEchoCxf.service().getZCAPUGECHO();
		
		installSoapLogger(service);
		installWsSecurityHeader(service);
		
		String myOutput = service.zCAPUGECHO("myInput");
		assertThat(myOutput).isNotNull();
	}

	private void installWsSecurityHeader(ZCAPUGECHO service) {
		Client client = ClientProxy.getClient(service);
		EndpointImpl endpoint = (EndpointImpl) client.getEndpoint();
		
		endpoint.getBus().getExtension(PolicyInterceptorProviderRegistry.class)
			.register(new SAPEnabledPolicyProvider());
		endpoint.getBus().getExtension(AssertionBuilderRegistry.class)
			.registerBuilder(new SAPEnabledAssertionBuilder());
		
		Configurer defaultConfigurer = endpoint.getBus().getExtension(Configurer.class);
		PingIdentitySTSClientConfigurer pingIdClient = new PingIdentitySTSClientConfigurer(defaultConfigurer);
		pingIdClient.setIssuer("https://sb1.trust4energy.com/idp/sts.wst");
		pingIdClient.setAgentPassword("mySecret"); // set real password here!
		pingIdClient.setTokenSubject("ivySamples");
		pingIdClient.setUsername("ivy-rew");
		
		endpoint.getBus()
			.setExtension(pingIdClient, Configurer.class);
	}

}
