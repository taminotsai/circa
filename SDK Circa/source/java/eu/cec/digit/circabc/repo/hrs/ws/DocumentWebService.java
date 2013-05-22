/**
 * DocumentWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public interface DocumentWebService extends javax.xml.rpc.Service {

/**
 * The Document web service exposes basic operations on documents:
 * create,
 *             update, register, file/unfile and search
 */
    public java.lang.String getDocumentServiceAddress();

    public eu.cec.digit.circabc.repo.hrs.ws.DocumentService_PortType getDocumentService() throws javax.xml.rpc.ServiceException;

    public eu.cec.digit.circabc.repo.hrs.ws.DocumentService_PortType getDocumentService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
