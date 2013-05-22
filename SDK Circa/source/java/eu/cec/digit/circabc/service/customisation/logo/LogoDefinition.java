/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/


package eu.cec.digit.circabc.service.customisation.logo;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Represent a logo
 *
 * @author Yanick Pignot
 */
public interface LogoDefinition
{

	/**
	 * @return the logo
	 */
	public abstract NodeRef getReference();

	/**
	 * @return where the logo is defined
	 */
	public abstract NodeRef getDefinedOn();
	
	/**
	 * @return the logoDescription
	 */
	public abstract String getDescription();

	/**
	 * @return the logoName
	 */
	public abstract String getName();

	/**
	 * @return the logoTitle
	 */
	public abstract String getTitle();
	
}