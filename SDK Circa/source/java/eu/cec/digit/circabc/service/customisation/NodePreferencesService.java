/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.alfresco.service.Auditable;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.repo.customisation.CustomizationException;

/**
 * Interface for the customization of a node. Under any node, we can store any
 * email templates, column display, the icon ... or other configuration stuff.
 * At a given node, you can get its preferences by a recursive search. The first
 * encountered node that define a specific preference decide of the preferences
 * to use.
 * 
 * 
 * <blockquote> <b> For instance, <i>Circabc 1.1</i>, only the circabc root will
 * be configurable and only the mail templates will be used. 1. Later, we will
 * add templates for any kind of display, we can imagine use an ig specific
 * freemarker template to display the IG home page, the document details, the
 * forums, ... 2. We can store in such a process, the columns of the library to
 * display. 3. We can store a specific image for each Interest Group... * <b>
 * </blockquote>
 * 
 * The scope is very large... We can image that an Interest Group override only
 * the image and not the mails, the service must manage it.
 * 
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * Commented the key parameter of the @Auditable annotation.
 * Commented the deprecated @PublicService annotation. 
 */
//@PublicService
public interface NodePreferencesService
{

	/**
	 * Add or update customization file with a given mandatory name and an
	 * mandatory content.
	 * 
	 * @param nodeRef
	 *            The node to customize
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * @param fileName
	 *            The file name of the configuration file
	 * @param content
	 *            The content to add in the file.
	 * 
	 * @return The created file
	 * @throws CustomizationException
	 *             If the noderef is not customizable.
	 */
	public abstract NodeRef addCustomizationFile(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName, final File content) throws CustomizationException;

	/**
	 * Add or update customization file with a given mandatory name and an
	 * mandatory content.
	 * 
	 * @param nodeRef
	 *            The node to customize
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * @param fileName
	 *            The file name of the configuration file
	 * @param content
	 *            The content to add in the file.
	 * 
	 * @return The created file
	 * @throws CustomizationException
	 *             If the noderef is not customizable.
	 */
	public abstract NodeRef addCustomizationFile(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName, final InputStream content) throws CustomizationException;

	/**
	 * Add or update customization file with a given mandatory name and an
	 * optional content.
	 * 
	 * @param nodeRef
	 *            The node to customize
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * @param fileName
	 *            The file name of the configuration file
	 * @param content
	 *            The content to add in the file.
	 * 
	 * @return The created file
	 * @throws CustomizationException
	 *             If the noderef is not customizable.
	 */
	public abstract NodeRef addCustomizationFile(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName, final Properties content) throws CustomizationException;

	/**
	 * Add or update customization file with a given mandatory name and an
	 * mandatory content.
	 * 
	 * @param nodeRef
	 *            The node to customize
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * @param fileName
	 *            The file name of the configuration file
	 * @param content
	 *            The content to add in the file.
	 * 
	 * @return The created file
	 * @throws CustomizationException
	 *             If the noderef is not customizable.
	 */
	public abstract NodeRef addCustomizationFile(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName, final String content) throws CustomizationException;

	/**
	 * Return if the target customization file exist.
	 * 
	 * @param nodeRef
	 *            The node to customize
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * @param fileName
	 *            The file name of the configuration file
	 * @return
	 */
	public abstract boolean customizationFileExists(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName);

	/**
	 * Get all configuration elements in a given context.
	 * 
	 * @param nodeRef
	 *            The node where to start search
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * 
	 * @return All configured files
	 * @throws CustomizationException
	 *             If no configuration is found util top folder
	 */
	public abstract List<NodeRef> getConfigurationFiles(final NodeRef nodeRef, final String configTypeRoot,
			final String configSubType, final String configElement) throws CustomizationException;

	/**
	 * Return the target customization file.
	 * 
	 * @param nodeRef
	 *            The node to customize
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * @param fileName
	 *            The file name of the configuration file
	 * @return
	 * 
	 * @throws CustomizationException
	 *             If the noderef is not customizable or if the file doen'st
	 *             exists
	 */
	public abstract NodeRef getCustomization(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName) throws CustomizationException;

	/**
	 * Return the node on which a customization file / folder container is
	 * defined. Null if not under a customization root.
	 * 
	 * @param customizationNode
	 * @return
	 */
	public abstract NodeRef getCustomizationFromNode(final NodeRef customizationNode);

	/**
	 * Get default configuration elements in a given context.
	 * 
	 * <blockquote> The default element is either the only one element found,
	 * either the first one found with name 'default'.* or the oldest one.
	 * <blockquote>
	 * 
	 * @param nodeRef
	 *            The node where to start search
	 * @param configTypeRoot
	 *            The configuration type root
	 * @param configSubType
	 *            The configuration subtype
	 * @param configElement
	 *            The final configuration element definition
	 * 
	 * @return The default configuration file
	 * @throws CustomizationException
	 *             If no default configuration is found util top folder
	 */
	public abstract NodeRef getDefaultConfigurationFile(final NodeRef nodeRef, final String configTypeRoot,
			final String configSubType, final String configElement) throws CustomizationException;

	/**
	 * Test if a given node is setted being configurable.
	 * 
	 * @param ref
	 *            the node to test
	 * @return if the node is configurable.
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "ref" })
	public abstract boolean isNodeConfigurable(final NodeRef ref);

	/**
	 * Make a node configurable but don't add any configuration element on it.
	 * 
	 * @param ref
	 *            The node to make configurable.
	 * @return The configuration container node ref
	 * @throws CustomizationException
	 *             if the noderef is already configurable
	 */
	@Auditable(/*key = Auditable.Key.ARG_0, */parameters = { "ref" })
	public abstract NodeRef makeConfigurable(final NodeRef ref) throws CustomizationException;

	/**
	 * Perform a clean customization file delete. It means that all
	 * customization folders / containers that will be not longer used will be
	 * deleted too.
	 * 
	 * @param nodeRef
	 * @param configTypeRoot
	 * @param configSubType
	 * @param configElement
	 * @param fileName
	 * @throws CustomizationException
	 *             If the noderef is not customizable, if the file doen'st
	 *             exists of if the nodeRef is the circabc root
	 */
	public void removeCustomization(final NodeRef nodeRef, final String configTypeRoot, final String configSubType,
			final String configElement, final String fileName) throws CustomizationException;

	/**
	 * Update toot preference folder
	 * 
	 */
	@Auditable(/*key = Auditable.Key.NO_KEY*/)
	public abstract void updateRootReference();

}
