package com.axonivy.connectivity.ws.saml;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceFeature;

import sap.saml.echo.ZCAPUGECHO_Service;

public class SapSamlProtectedEchoCxf {

	/**
	 * PATCHED WSDL:
	 * - from > http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200512/IncludeToken/AlwaysToRecipient
	 * - to   > http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702/IncludeToken/AlwaysToRecipient
	 * 
	 * 
	 * @param features
	 * @return
	 * @throws MalformedURLException
	 */
	public static ZCAPUGECHO_Service service(WebServiceFeature... features) throws MalformedURLException {
		URL patchedWsdl = SapSamlProtectedEchoCxf.class.getResource("sap_saml.wsdl");
		return new ZCAPUGECHO_Service(patchedWsdl, features);
	}
	
}
