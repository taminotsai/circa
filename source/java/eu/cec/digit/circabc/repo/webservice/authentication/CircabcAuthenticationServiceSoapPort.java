/**
 * CircabcAuthenticationServiceSoapPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.webservice.authentication;

public interface CircabcAuthenticationServiceSoapPort extends java.rmi.Remote {
    public eu.cec.digit.circabc.repo.webservice.authentication.AuthenticationResult startSession(java.lang.String username, java.lang.String ecasProxyTicket) throws java.rmi.RemoteException, eu.cec.digit.circabc.repo.webservice.authentication.AuthenticationFault;
}
