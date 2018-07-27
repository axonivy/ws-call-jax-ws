package com.axonivy.connectivity.ws.saml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants.SPVersion;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;

public class SAPNoOpAssertion extends AbstractSecurityAssertion {

	public SAPNoOpAssertion(SPVersion spVersion) {
		super(spVersion);
	}

	@Override
	public QName getName() {
		return SAPEnabledPolicyProvider.ATTACHMENT;
	}

	@Override
	public void serialize(XMLStreamWriter writer) throws XMLStreamException {
	}

	@Override
	protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
		return new SAPNoOpAssertion(getVersion());
	}
}
