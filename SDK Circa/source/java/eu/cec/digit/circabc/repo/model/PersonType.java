/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.model;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.service.user.UserService;

/**
 *	Class containing behavior for the person type.
 * 	Inside Circabc, the email of an user <b>MUST</b> be valid and unique.
 *
 * {@link ContentModel#TYPE_PERSON person type}
 *
 * @author Yanick Pignot
 */
public class PersonType implements NodeServicePolicies.OnUpdatePropertiesPolicy
{

   //     Dependencies
   private PolicyComponent policyComponent;
   private UserService userService;
   private boolean validateDuplicateEmail;

   /**
    * Initialise the Person Type

    */
   public void init()
   {
       this.policyComponent.bindClassBehaviour(
               QName.createQName(NamespaceService.ALFRESCO_URI, "onUpdateProperties"),
               ContentModel.TYPE_PERSON,
               new JavaBehaviour(this, "onUpdateProperties"));
   }

   /**
    * @param policyComponent the policy component to register behaviour with
    */
   public void setPolicyComponent(final PolicyComponent policyComponent)
   {
       this.policyComponent = policyComponent;
   }

   /**
    * Check if the email defined is not already in use in circabc.
    */
   public void onUpdateProperties(final NodeRef nodeRef, final Map<QName, Serializable> before, Map<QName, Serializable> after)
   {
	   if (! validateDuplicateEmail)
	   {	   
		   return;
	   }
	   
	   final String oldEmail = (String) before.get(ContentModel.PROP_EMAIL);
	   final String newEmail = (String) after.get(ContentModel.PROP_EMAIL);

	   if(newEmail == null || newEmail.length() < 1)
	   {
		   if(oldEmail != null && oldEmail.length() > 0)
		   {
			   // The exception is throwed only if the properties are in a edit mode. The new
			   // email can be null ONLY if the old email is null too. For example for the Alfresco Admin created
			   // at the startup.
			   throw new IllegalStateException("The email of a person is mandatory.");
		   }

	   }
	   else if(oldEmail == null || oldEmail.length() < 1 || !oldEmail.equalsIgnoreCase(newEmail))
	   {
		   final Boolean exists = (Boolean) AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
		   {
					public Object doWork()
					{
						boolean exists = userService.isEmailExists(newEmail, true);
						return Boolean.valueOf(exists);
					}
		   }, AuthenticationUtil.getSystemUserName());

		   if(exists.booleanValue())
		   {
			   throw new IllegalArgumentException("The email of a person must be unique.");
		   }
	   }
   }

   /**
    * @param UserService the UserService to set
    */
   public void setUserService(final UserService userService)
   {
	   this.userService = userService;
   }

	public boolean isValidateDuplicateEmail() {
		return validateDuplicateEmail;
	}
	
	public void setValidateDuplicateEmail(boolean validateDuplicateEmail) {
		this.validateDuplicateEmail = validateDuplicateEmail;
	}
}
