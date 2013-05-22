/**
 * EntityService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public interface EntityService_PortType extends java.rmi.Remote {

    /**
     * This operation performs a search for internal entities in the
     * current base (maximum 50 entities).<br/>
     * 
     *             One can search for internal entities by using either an
     * explicit criteria (by person, by organization or by ID) or a more
     * generic search expression.<br/>
     * 
     *             <p>The fields that can be used inside a search expression
     * are:
     *             <ul>
     *                 <li> isOrganisation - whether the entities are organizations
     * or persons </li>
     *                 <li> personId - the person internal ID </li>
     *                 <li> personUserId - the person's ecas user name </li>
     * <li> personLastName - the person's last name </li>
     *                 <li> personFirstName - the first name(s) of the person
     * </li>
     *                 <li> personFullName - the person's full name </li>
     * <li> personEmail - the person's email </li>
     *                 <li> personAlias - the alias of the person </li>
     *                 <li> personBuildingCode - the code of the building
     * where the person is located </li>
     *                 <li> personFloorCode - the code of the floor where
     * the person is located </li>
     *                 <li> personRoomCode - the code of the room where the
     * person is located </li>
     *                 <li> organisationId - the organization internal ID
     * </li>
     *                 <li> organisationName - the code of the internal organization
     * (e.g. DIGIT.B.1) </li>
     *                 <li> organisationDg - the DG of the internal organization
     * </li>
     *             </ul>
     *             </p>
     * 
     *             <p>Sample search expressions are: </p>
     *             <ul style="list-style-type:none">
     *               <li>isOrganisation=true</li>
     *               <li>personId = 90309813</li>
     *               <li>isOrganisation=false and personLastName startswith
     * 'Nico'</li>
     *               <li>isOrganisation=true and organisationDg='DIGIT'</li>
     * </ul>
     * 
     *             <p>The fields that can be used for sorting with a search
     * expression are:
     *             <ul>
     *                 <li> personFullName - usefull when searching for internal
     * persons </li>
     *                 <li> organisationName - usefull when searching for
     * internal organisations </li>
     *             </ul>
     *             </p>
     */
    public eu.cec.digit.circabc.repo.hrs.ws.CurrentInternalEntity[] findCurrentInternalEntity(eu.cec.digit.circabc.repo.hrs.ws.Header header, eu.cec.digit.circabc.repo.hrs.ws.FindCurrentInternalEntityRequest criteria, eu.cec.digit.circabc.repo.hrs.ws.EntitySearchByExpressionRequest searchByExpressionRequest) throws java.rmi.RemoteException;

    /**
     * This operation performs a search for external entities in the
     * current base (maximum 50 entities).
     * 
     *             One can search for internal entities by using either an
     * explicit criteria (by person, by organization or by ID) or a more
     * generic search expression.<br/>
     * 
     *             <p>The fields that can be used inside a search expression
     * are:
     *             <ul>
     *                 <li> isOrganisation - whether the entities are organizations
     * or not </li>
     *                 <li> personId - the person external ID </li>
     *                 <li> personLastName - the last name of the person
     * </li>
     *                 <li> personFirstName - the first name(s) of the person
     * </li>
     *                 <li> personFullName - the full name of the person
     * </li>
     *                 <li> personEmail - the email of the person </li>
     *                 <li> personAlias - the alias of the person </li>
     *                 <li> personCity - the city of the person </li>
     *                 <li> personCountry - the country the person </li>
     *                 <li> personValidationLevel - search for external persons
     * that have been validated and at which level; e.g. 1 for CREATED DG,
     * 2 for VALIDATED DG or 3 for VALIDATED EC </li>
     *                 <li> personCreatorDg - the DG of the user that created
     * the external person </li>
     *                 <li> personCreationDate - the creation date of external
     * person </li>
     *                 <li> organisationId - the organization internal ID
     * </li>
     *                 <li> organisationName - the name of the organization
     * </li>
     *                 <li> organisationAcronym - the acronym of the organization
     * </li>
     *                 <li> organisationEmail - the email of the external
     * organization </li>
     *                 <li> organisationCity - the city of the organization
     * </li>
     *                 <li> organisationCountry - the country of the organization
     * </li>
     *                 <li> organisationCreatorDg - the DG of the user that
     * created the external organization </li>
     *                 <li> organisationValidationLevel - search for external
     * organizations that have been validated and at which level, e.g. 1
     * for CREATED DG, 2 for VALIDATED DG or 3 for VALIDATED EC </li>
     *                 <li> organisationCreationDate - the creation date
     * of external organization</li>
     *                 <li> organisationModificationDate - the date when
     * the external organization was last modified </li>
     *             </ul>
     *             </p>
     * 
     *             <p>Sample search expressions are: </p>
     *             <ul style="list-style-type:none">
     *               <li>isOrganisation=true</li>
     *               <li>personId = 90309813</li>
     *               <li>isOrganisation=false and personFullName startswith
     * 'Nico'</li>
     *               <li>isOrganisation=true and organisationCreationDate
     * BETWEEN (2009-10-23, 2009-11-14)</li>
     *               <li>organisationCreatorDg = 'DIGIT'</li>
     *             </ul>
     * 
     *             <p>The fields that can be used for sorting with a search
     * expression are:
     *             <ul>
     *                 <li> personFullName - usefull when searching for external
     * persons  </li>
     *                 <li> organisationName - usefull when searching for
     * external organisations </li>
     *             </ul>
     *             </p>
     */
    public eu.cec.digit.circabc.repo.hrs.ws.CurrentExternalEntity[] findCurrentExternalEntity(eu.cec.digit.circabc.repo.hrs.ws.Header header, eu.cec.digit.circabc.repo.hrs.ws.FindCurrentExternalEntityRequest criteria, eu.cec.digit.circabc.repo.hrs.ws.EntitySearchByExpressionRequest searchByExpressionRequest, eu.cec.digit.circabc.repo.hrs.ws.CurrentExternalEntityRetrievalOptions retrievalOptions) throws java.rmi.RemoteException;

    /**
     * This operation finds all virtual entities in the current base.
     */
    public eu.cec.digit.circabc.repo.hrs.ws.CurrentInternalEntity[] findAllVirtualEntities(eu.cec.digit.circabc.repo.hrs.ws.Header header) throws java.rmi.RemoteException;
}
