package com.axonivy.connectivity.ws.saml;

import java.util.Arrays;

import javax.xml.namespace.QName;

import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;

/**
 * list of ignored SAP policies...
 */
public class SAPEnabledPolicyProvider extends AbstractPolicyInterceptorProvider {

    private static final long serialVersionUID = 5823249813035449881L;
    static final QName ATTACHMENT = 
    		new QName("http://www.sap.com/710/features/attachment/", "Enabled");
    static final QName OPTIMIZED = 
    		new QName("http://www.sap.com/webas/710/soap/features/transportbinding/", "OptimizedXMLTransfer");
    static final QName SESSION = 
    		new QName("http://www.sap.com/webas/630/soap/features/session/", "Session");
    static final QName CENTRAL_ADMIN = 
    		new QName("http://www.sap.com/webas/700/soap/features/CentralAdministration/", "CentralAdministration");
    static final QName TRANSACTION = 
    		new QName("http://www.sap.com/NW05/soap/features/transaction/", "required");
    static final QName COMMIT = 
    		new QName("http://www.sap.com/NW05/soap/features/commit/", "enableCommit");
    static final QName BLOCKING = 
    		new QName("http://www.sap.com/NW05/soap/features/blocking/", "enableBlocking");
    static final QName WSRM = 
    		new QName("http://www.sap.com/NW05/soap/features/wsrm/", "enableWSRM");
    
    /*
     * 	avoid nasty SAP assertion errors!
    	WARNING: No assertion builder for type {http://www.sap.com/NW05/soap/features/transaction/}required registered.
    	Jul 27, 2018 12:03:57 PM org.apache.cxf.ws.policy.AssertionBuilderRegistryImpl handleNoRegisteredBuilder
    	WARNING: No assertion builder for type {http://www.sap.com/NW05/soap/features/commit/}enableCommit registered.
    	Jul 27, 2018 12:03:57 PM org.apache.cxf.ws.policy.AssertionBuilderRegistryImpl handleNoRegisteredBuilder
    	WARNING: No assertion builder for type {http://www.sap.com/NW05/soap/features/blocking/}enableBlocking registered.
    	Jul 27, 2018 12:03:57 PM org.apache.cxf.ws.policy.AssertionBuilderRegistryImpl handleNoRegisteredBuilder
    	WARNING: No assertion builder for type {http://www.sap.com/NW05/soap/features/wsrm/}enableWSRM registered.
     */
    
    public SAPEnabledPolicyProvider() {
        super(Arrays.asList(new QName[] { 
        		ATTACHMENT, SESSION, 
        		CENTRAL_ADMIN, 
        		TRANSACTION, 
        		COMMIT, 
        		BLOCKING, 
        		WSRM, 
        		OPTIMIZED 
        }));
    }
}
