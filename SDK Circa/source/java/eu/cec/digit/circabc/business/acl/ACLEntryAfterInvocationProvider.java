/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.acl;

import net.sf.acegisecurity.AccessDeniedException;
import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.ConfigAttributeDefinition;

/**
 * Acl entry voter (after invocation) in charge to check permissions on business wrappers that implements AclAwareWrapper
 *
 * @author Yanick Pignot
 */
public class ACLEntryAfterInvocationProvider extends org.alfresco.repo.security.permissions.impl.acegi.ACLEntryAfterInvocationProvider
{

	@Override
	public Object decide(final Authentication authentication, final Object object, final ConfigAttributeDefinition config, final Object returnedObject) throws AccessDeniedException
    {
		// TODO to implements
		return returnedObject;
    }
}
