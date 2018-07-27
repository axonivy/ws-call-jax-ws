package com.axonivy.connectivity.ws.saml;

import javax.xml.namespace.QName;

import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.policy.SPConstants;
import org.w3c.dom.Element;

public class SAPEnabledAssertionBuilder implements AssertionBuilder<Element> {

	@Override
	public Assertion build(Element element, AssertionBuilderFactory factory)
			throws IllegalArgumentException {
        final SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
		return new SAPNoOpAssertion(spVersion);
	}

	@Override
	public QName[] getKnownElements() {
		return new QName[]{
				SAPEnabledPolicyProvider.ATTACHMENT, 
				SAPEnabledPolicyProvider.SESSION, 
				SAPEnabledPolicyProvider.CENTRAL_ADMIN,
				SAPEnabledPolicyProvider.TRANSACTION,
				SAPEnabledPolicyProvider.COMMIT,
				SAPEnabledPolicyProvider.BLOCKING,
				SAPEnabledPolicyProvider.WSRM,
				SAPEnabledPolicyProvider.OPTIMIZED
		};
	}
}
