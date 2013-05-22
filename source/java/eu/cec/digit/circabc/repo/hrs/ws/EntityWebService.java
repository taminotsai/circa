/**
 * EntityWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public interface EntityWebService extends javax.xml.rpc.Service {

/**
 * This web service exposes operations related to entities that are
 * stored in the common repository.
 */
    public java.lang.String getEntityServiceAddress();

    public eu.cec.digit.circabc.repo.hrs.ws.EntityService_PortType getEntityService() throws javax.xml.rpc.ServiceException;

    public eu.cec.digit.circabc.repo.hrs.ws.EntityService_PortType getEntityService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
