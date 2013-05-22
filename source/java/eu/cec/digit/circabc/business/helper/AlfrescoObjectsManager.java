/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.helper;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

/**
 * Manage alfresco object type convertion to Circabc business object wrapper
 * 
 * @author Yanick Pignot
 */
public class AlfrescoObjectsManager
{
	private NamespaceService namespaceService;
	
	//	--------------
	//-- public methods

	
	/**
	 * Convert an Alfresco QName to a String using prefix (ie: cm:name) 
	 * 
	 * @return				qname.toPrefixString
	 */
	public String asString(final QName qname)
	{
		return qname.toPrefixString(getNamespaceService());
	}
	
	/**
	 * Convert an Alfresco NodeRef to a String using nodeRef id only (ie: 16589965656565565)
	 * 
	 * @return			nodeRef.getId
	 */
	public String asString(final NodeRef nodeRef)
	{
		return nodeRef.getId();
	}

	/**
	 * Convert an Alfresco QName to a String using prefix (ie: {http://www.alfresco.org/model/content/1.0}name) 
	 * 
	 * @return				qname.toPrefixString
	 */
	public String asFullString(final QName qname)
	{
		return qname.toString();
	}
	
	/**
	 * Convert an Alfresco NodeRef to a String using nodeRef id only (ie: workspace://SpacesStore/16589965656565565)
	 * 
	 * @return			nodeRef.getId
	 */
	public String asFullString(final NodeRef nodeRef)
	{
		return nodeRef.toString();
	}

	
	/**
	 * Convert a name space string to an Alfresco qname (accept prefixed namespace or long namespace)
	 * 
	 * @return
	 */
	public QName asQName(final String string)
	{	
		return QName.resolveToQName(getNamespaceService(), string);
	}
	
	/**
	 * Convert a nodeRef string to an Alfresco NodeRef (accpet nodeId and nodeRef.toString)
	 * 
	 * @param string
	 * @return
	 */
	public NodeRef asNodeRef(final String string)
	{
		if(NodeRef.isNodeRef(string))
		{
			return new NodeRef(string);
		}
		else
		{
			return new NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, string);
		}
	}

	//--------------
	//-- private helpers


	//	--------------
	//-- IOC
	
	/**
	 * @return the namespaceService
	 */
	protected final NamespaceService getNamespaceService()
	{
		return namespaceService;
	}

	/**
	 * @param namespaceService the namespaceService to set
	 */
	public final void setNamespaceService(NamespaceService namespaceService)
	{
		this.namespaceService = namespaceService;
	}	
}
