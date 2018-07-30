package com.axonivy.connectivity.ws.saml.mex;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceFeature;

import sap.saml.mex.echo.ZCAPUGECHO_Service;

public class SapSamlProtectedMexEchoCxf {

	public static ZCAPUGECHO_Service service(WebServiceFeature... features) throws MalformedURLException {
		URL patchedWsdl = SapSamlProtectedMexEchoCxf.class.getResource("metadaten_dut_https_z_ca_pug_echo.wsdl");
		return new ZCAPUGECHO_Service(patchedWsdl, features);
	}
	
}
