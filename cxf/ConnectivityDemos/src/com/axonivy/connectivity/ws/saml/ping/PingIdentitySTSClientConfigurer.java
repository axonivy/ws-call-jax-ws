package com.axonivy.connectivity.ws.saml.ping;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.ws.security.trust.STSClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.pingidentity.opentoken.Agent;
import com.pingidentity.opentoken.AgentConfiguration;
import com.pingidentity.opentoken.Token;
import com.pingidentity.opentoken.TokenException;
import com.pingidentity.sts.clientapi.STSClientException;
import com.pingidentity.sts.clientapi.SecurityTokenException;
import com.pingidentity.sts.clientapi.protocol.STSMessage;
import com.pingidentity.sts.clientapi.tokens.wsse.BinaryToken;

public class PingIdentitySTSClientConfigurer implements Configurer
{
	private final Configurer original;
	private String agentPassword;
	private String tokenSubject;
	private String username;
	private String issuer;

	public PingIdentitySTSClientConfigurer(Configurer defaultConfigurer) {
		this.original = defaultConfigurer;
	}

	@Override
	public void configureBean(Object beanInstance) {
		original.configureBean(beanInstance);
	}

	@Override
	public void configureBean(String name, Object beanInstance) {
		if (beanInstance instanceof STSClient  && Objects.equals(name, "default.sts-client"))
		{
			configureStsClient((STSClient)beanInstance);
			return;
		}
		original.configureBean(name, beanInstance);
	}
	
	private void configureStsClient(STSClient client) 
	{
		installLoggers(client);

		if (StringUtils.isNotBlank(issuer))
		{
			client.setLocation(issuer); // override issuer from WSDL!
		}
		
		try
		{
			Document pingMessageSoap = createPingIdentityMessage();
			Header pingHeader = getBinaryTokenHeader(pingMessageSoap);
			List<Header> headers = new ArrayList<>(Arrays.asList(pingHeader));
			client.getRequestContext().put(Header.HEADER_LIST, headers);
		}
		catch (Exception ex)
		{
			
		}
		 
		/*
		 * YET WE DO NOT SET the REFERENCE TO the token in the body... but is is an easy fish with setCustomContent()..
		 * 
		Element binaryToken;
		try {
			binaryToken = newOpenToken();
			client.setCustomContent(binaryToken);
		} catch (TokenException | SecurityTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	private void installLoggers(STSClient client) {
		client.getOutInterceptors().add(new LoggingOutInterceptor(new PrintWriter(System.out)));
		client.getOutFaultInterceptors().add(new LoggingOutInterceptor(new PrintWriter(System.err)));
		
		client.getInInterceptors().add(new LoggingInInterceptor(new PrintWriter(System.out)));
		client.getInFaultInterceptors().add(new LoggingInInterceptor(new PrintWriter(System.err)));
	}
	
	private Element newOpenToken() throws TokenException, SecurityTokenException
	{
		String tokenData = createOpenToken();
		BinaryToken openToken = new BinaryToken(tokenData);
		openToken.setValueTypeUri("urn:pingidentity:opentoken");
		return openToken.getRoot();
	}

	private String createOpenToken() throws TokenException {
		AgentConfiguration agentConfiguration = new AgentConfiguration();
		agentConfiguration.setPassword(agentPassword);
		agentConfiguration.setCipherSuite(Token.CIPHER_SUITE_AES128CBC);
		// Instantiate the OpenToken agent
		Agent agent = new Agent(agentConfiguration);
		// Set OpenToken attributes
		MultiMap values = new MultiValueMap();
		values.put(Agent.TOKEN_SUBJECT, tokenSubject);
		values.put("username", username);
		return agent.writeToken(values);
	}
	
	private Header getBinaryTokenHeader(Document pingMessageSoap) {
		Node securityHeader = pingMessageSoap.getFirstChild().getFirstChild().getChildNodes().item(1);
		Header pingHeader = new Header(new QName(securityHeader.getBaseURI(), securityHeader.getLocalName()), 
				securityHeader);
		return pingHeader;
	}

	private Document createPingIdentityMessage() throws STSClientException, TokenException, SecurityTokenException {
		STSMessage pingMessage = new com.pingidentity.sts.clientapi.protocol.
				STSMessage("http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue");
		Element openToken = newOpenToken();
		pingMessage.addSecurityToken(openToken);
		Document document = pingMessage.toDocument();
		return document;
	}

	public void setAgentPassword(String agentPassword) {
		this.agentPassword = agentPassword;
	}

	public void setTokenSubject(String tokenSubject) {
		this.tokenSubject = tokenSubject;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

}