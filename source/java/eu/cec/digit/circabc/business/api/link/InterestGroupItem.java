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
public final class InterestGroupItem implements Serializable, AclAwareWrapper
{
	 /** */
	 private static final long serialVersionUID = 2898619656671170057L;

	 private final NodeRef id;
	 private final String name;
	 private final String title;
	 private final String permission;

	 public NodeRef getNodeRef()
	 {
		 return id;
	 }
	 public String getName()
	 {
		return name;
	 }

	 public String getPermission()
	 {
		return permission;
	 }

	 public InterestGroupItem(final NodeRef id, final String name, final String permission, final String title)
	 {
		 this.id= id;
		 this.name= name;
		 this.permission= permission;
		 this.title = title;
	 }

	 public final String getTitle()
	 {
		 return title;
	 }

}
