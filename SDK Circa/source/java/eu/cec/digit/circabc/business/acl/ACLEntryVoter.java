/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.acl;

import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.ConfigAttributeDefinition;
import net.sf.acegisecurity.vote.AccessDecisionVoter;

/**
 * Acl entry voter in charge to check permissions on business wrappers that implements AclAwareWrapper
 *
 * @author Yanick Pignot
 */
public class ACLEntryVoter extends org.alfresco.repo.security.permissions.impl.acegi.ACLEntryVoter
{
	@Override
	public int vote(final Authentication authentication, final Object object, final ConfigAttributeDefinition config)
	{
		// TODO to implements
		return AccessDecisionVoter.ACCESS_ABSTAIN;
	}
}
