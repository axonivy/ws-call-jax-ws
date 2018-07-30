package com.axonivy.connectivity.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyInterceptorProviderRegistry;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JOutInterceptor;
import org.junit.Test;

import com.axonivy.connectivity.ws.saml.SAPEnabledAssertionBuilder;
import com.axonivy.connectivity.ws.saml.SAPEnabledPolicyProvider;
import com.axonivy.connectivity.ws.saml.mex.SapSamlProtectedMexEchoCxf;

import person.service.soap.client.WebServiceProcessTechnicalException;
import sap.saml.mex.echo.ZCAPUGECHO;

public class IntegrationTestCxfSamlMex {

	@Test
	public void callWsSecuredService() throws MalformedURLException,
			WebServiceProcessTechnicalException, URISyntaxException {
		ZCAPUGECHO service = SapSamlProtectedMexEchoCxf.service().getZCAPUGECHO();
		
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
		Interceptor<?>  wssOut = new PolicyBasedWSS4JOutInterceptor();
		endpoint.getOutInterceptors().add(wssOut);
		
		endpoint.getBus().getExtension(PolicyInterceptorProviderRegistry.class)
			.register(new SAPEnabledPolicyProvider());
		endpoint.getBus().getExtension(AssertionBuilderRegistry.class)
			.registerBuilder(new SAPEnabledAssertionBuilder());
	}

}
