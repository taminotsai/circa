/**
 * UserWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public interface UserWebService extends javax.xml.rpc.Service {

/**
 * This web service exposes user related operations.
 */
    public java.lang.String getUserServiceAddress();

    public eu.cec.digit.circabc.repo.hrs.ws.UserService_PortType getUserService() throws javax.xml.rpc.ServiceException;

    public eu.cec.digit.circabc.repo.hrs.ws.UserService_PortType getUserService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
