package eu.cec.digit.circabc.web.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.web.bean.groups.GroupsDialog;

/**
 * Gets all root groups in addition to Alresco's normal get groups behaviour.
 * 
 * @author schwerr
 */
public class CircabcGroupsDialog extends GroupsDialog {
	
	private static final long serialVersionUID = 8400018839652467976L;
	
	transient private AuthorityService authorityService = null;
	
	private static final ThreadLocal<Boolean> rootGroups = new ThreadLocal<Boolean>() {
		/**
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};
	
	public CircabcGroupsDialog() {
		super();
		rootGroups.set(false);
	}
	
	/**
	 * Action handler to show all the root groups
	 * 
	 * @return The outcome
	 */
	public String showRootGroups() {
		
		group = null;
		groupsRichList.setValue(null);
		
		Set<String> results = authorityService.getAllRootAuthoritiesInZone(
						AuthorityService.ZONE_APP_DEFAULT, AuthorityType.GROUP);
		
		List<String> authorities = new ArrayList<String>(results);
		
		groups = new ArrayList<Map<String,String>>(authorities.size());
		
		for (String authority : authorities) {
			
			Map<String, String> authMap = new HashMap<String, String>(8);
			
			String name = authorityService.getAuthorityDisplayName(authority);
			if (name == null) {
				name = authorityService.getShortName(name);
			}
			authMap.put("name", name);
			authMap.put("id", authority);
			authMap.put("group", authority);
			authMap.put("groupName", name);
			
			groups.add(authMap);
		}
		
		rootGroups.set(true);
		
		return null;
	}
	
	/**
	* @return The list of group objects to display. Returns the list of root 
	* groups if set.
	*/
	public List<Map<String,String>> getGroups() {
		
		if (!rootGroups.get()) {
			return super.getGroups();
		}
		
		rootGroups.set(false);
		
		return groups;
	}
	
	/**
	 * @param authorityService the authorityService to set
	 */
	public void setAuthService(AuthorityService authorityService) {
		this.authorityService = authorityService;
		super.setAuthService(authorityService);
	}
}
