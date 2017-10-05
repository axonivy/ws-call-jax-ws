package com.axonivy.connectivity.ws;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.junit.Test;

import samples.quickstart.StockQuoteServicePortType;

public class IntegrationTestCxfStockQuote {

	@Test
	public void lowLewelLog() throws MalformedURLException
	{
		StockQuoteServicePortType service = StockQuoteCxf.service();
		installSoapLogger(service);
		
		Double price = service.getPrice("$");
		assertThat(price).isNotNull();
		
		String hello = service.getHelloWorld();
		assertThat(hello).isNotNull();
		
		XMLGregorianCalendar today = service.getToday();
		assertThat(today).isNotNull();
	}

	public static void installSoapLogger(Object service) {
		Client client = ClientProxy.getClient(service);
		client.getInInterceptors().add(new LoggingInInterceptor(new PrintWriter(System.err)));
		client.getOutInterceptors().add(new LoggingOutInterceptor(new PrintWriter(System.out)));
	}
	
}
