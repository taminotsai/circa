/**
 * UserService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public interface UserService_PortType extends java.rmi.Remote {

    /**
     * Returns the profile of the current user, such as Basic User,
     * Advanced Secretary,
     *                 DMO etc.
     */
    public eu.cec.digit.circabc.repo.hrs.ws.UserProfile getUserProfile(eu.cec.digit.circabc.repo.hrs.ws.Header header) throws java.rmi.RemoteException;
}
