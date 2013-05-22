/**
 * WorkflowWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public interface WorkflowWebService extends javax.xml.rpc.Service {

/**
 * This web service exposes operations to manage workflows.
 */
    public java.lang.String getWorkflowServiceAddress();

    public eu.cec.digit.circabc.repo.hrs.ws.WorkflowService_PortType getWorkflowService() throws javax.xml.rpc.ServiceException;

    public eu.cec.digit.circabc.repo.hrs.ws.WorkflowService_PortType getWorkflowService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
