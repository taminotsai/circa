/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.link;

import java.io.Serializable;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.business.acl.AclAwareWrapper;


/**
 * @author Slobodan Filipovic
 */
public final class ShareSpaceItem implements Serializable, AclAwareWrapper
{
	/** */
	private static final long serialVersionUID = 4555015582693128592L;

	private NodeRef id;
	private String path;


	public NodeRef getNodeRef()
	{
		return id;
	}

	public String getPath()
	{
		return path;
	}


	public ShareSpaceItem(NodeRef id, String path)
	{
		this.id= id;
		this.path= path;
	}
}
