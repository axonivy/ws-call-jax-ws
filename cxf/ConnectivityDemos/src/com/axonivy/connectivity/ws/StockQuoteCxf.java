package com.axonivy.connectivity.ws;

import java.net.MalformedURLException;
import java.net.URL;

import samples.quickstart.StockQuoteService;
import samples.quickstart.StockQuoteServicePortType;

public class StockQuoteCxf {

	public static void hello() throws MalformedURLException
	{
		service().getHelloWorld();
	}

	public static StockQuoteServicePortType service() throws MalformedURLException {
		StockQuoteService quoteService = new StockQuoteService(new URL("http://zugtstweb:8080/axis2/services/StockQuoteService?wsdl"));
		return quoteService.getStockQuoteServiceHttpSoap12Endpoint();
	}
	
}
