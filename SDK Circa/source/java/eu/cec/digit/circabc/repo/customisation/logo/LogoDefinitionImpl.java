/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation.logo;

import java.io.Serializable;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.customisation.logo.LogoDefinition;

/**
 * The base single logo wrapper
 *
 * @author yanick pignot
 */
public class LogoDefinitionImpl implements Serializable, LogoDefinition
{

	/** */
	private static final long serialVersionUID = -4254888522583804060L;

	private NodeRef logo = null;
	private NodeRef definedOn = null;
	private String title;
	private String description;
	private String name;

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.customisation.logo.LogoDefinition#getLogo()
	 */
	public final NodeRef getReference()
	{
		return logo;
	}

	public final NodeRef getDefinedOn()
	{
		return definedOn;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.customisation.logo.LogoDefinition#getLogoDescription()
	 */
	public final String getDescription()
	{
		return description;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.customisation.logo.LogoDefinition#getLogoName()
	 */
	public final String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.customisation.logo.LogoDefinition#getLogoTitle()
	 */
	public final String getTitle()
	{
		return title;
	}

	/**
	 * @param logo the logo to set
	 */
	/*package*/ final void setReference(NodeRef logo)
	{
		this.logo = logo;

	}

	/**
	 * @param logoDescription the logoDescription to set
	 */
	/*package*/ final void setDescription(String logoDescription)
	{
		this.description = logoDescription;
	}

	/**
	 * @param logoName the logoName to set
	 */
	/*package*/ final void setName(String logoName)
	{
		this.name = logoName;
	}

	/**
	 * @param logoTitle the logoTitle to set
	 */
	/*package*/ final void setTitle(String logoTitle)
	{
		this.title = logoTitle;
	}

	/**
	 * @param definedOn the definedOn to set
	 */
	/*package*/ void setDefinedOn(NodeRef definedOn)
	{
		this.definedOn = definedOn;
	}
}
