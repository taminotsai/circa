package eu.cec.digit.circabc.business.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.filestore.FileContentReader;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.aspect.ContentNotifyAspect;
import eu.cec.digit.circabc.business.impl.ValidationUtils;
import eu.cec.digit.circabc.web.ui.common.UtilsCircabc;

/**
 * @author Yanick Pignot
 */
public class ContentManager
{

	private final Log logger = LogFactory.getLog(ContentManager.class);

	private NodeService nodeService;
	private ContentService contentService;

	private ValidationManager validationManager;
	private ApplicationConfigManager configManager;
	private MetadataManager metadataManager;

	public NodeRef createContent(final NodeRef parent, final String name, final QName associationQname, final QName childTypeQName, final File file, final boolean applyNotification)
	{
		final QName validAssocQName = associationQname == null ? ContentModel.ASSOC_CONTAINS : associationQname;
    	final QName validTypeQName = childTypeQName == null ? ContentModel.TYPE_CONTENT : childTypeQName;

    	// generate a valid and unique name
		final String validName = metadataManager.getValidUniqueName(parent, validAssocQName, name);

		// comput mimetype and encoding
		final String mimetype = computeMimeType(validName);
	    final String encoding = computeEncoding(file, mimetype);

		// create a content
		final NodeRef contentRef = createContent(parent, validAssocQName, validTypeQName, validName, mimetype);

	    // update node content
		updateContent(contentRef, file, null, mimetype, encoding);

		if(applyNotification)
		{
			// Mandatory workaround to ensure that the notification trigger will be not called in another transaction
			nodeService.addAspect(contentRef, ContentNotifyAspect.ASPECT_CONTENT_NOTIFY, null);
		}

		// extract and set common properties
		final Map<QName, Serializable> extractedProps = extractedProperties(file, mimetype, encoding);
		updateProperties(contentRef, mimetype, extractedProps);

		return contentRef;

	}


	/**
	 * Return the target nodeRef if the given node is a link or the node itself.
	 *
	 * <p>
	 * 		Usefull to ensure to have a valid node without testing if it is a link or not.
	 * </p>
	 *  <p>
	 *  	<b>Can throw BusinessStackError if the user can't read the target node ref</b>
	 *  </p>
	 *
	 * @param nodeRef
	 * @return
	 */
	public NodeRef getTargetRef(final NodeRef nodeRef)
	{
		if(nodeRef == null)
		{
			return null;
		}
		else
		{
			final NodeRef target = (NodeRef) nodeService.getProperty(nodeRef, ContentModel.PROP_LINK_DESTINATION);

			if(target == null)
			{
				return nodeRef;
			}
			else
			{
				// check if the user can read the target element
				ValidationUtils.assertNodeRef(nodeRef, validationManager, logger);

				return target;
			}

		}
	}


	//--------------
	//-- private helpers

   /**
    * @param mimetype
 * @param fileContent      File content to save
    * @param strContent       String content to save (if fileContent is null)
    */
   private NodeRef createContent(final NodeRef parent, final QName associationQname, final QName typeQname, final String name, String mimetype)
   {
	   //	 guess a mimetype based on the filename
		QName propContent = UtilsCircabc.getPropContent(typeQname);

       final ContentData contentData = new ContentData(null, mimetype, 0L, "UTF-8");
       final Map<QName, Serializable> properties = new HashMap<QName, Serializable>(2);
       properties.put(ContentModel.PROP_NAME, name);
       properties.put(propContent, contentData);

       final ChildAssociationRef assocRef = nodeService.createNode(
               parent,
               associationQname,
               associationQname,
               typeQname,
               properties);

       final NodeRef fileNodeRef = assocRef.getChildRef();

       if (logger.isDebugEnabled())
       {
    	   logger.debug("Created file node for file: " + name);
       }

       return fileNodeRef;
   }

   private void updateContent(final NodeRef contentRef, final File fileContent, final String stringContent, final String mimetype, final String encoding)
   {
      // get a writer for the content and put the file
	QName propContent = UtilsCircabc.getPropContent(nodeService.getType(contentRef));

	final ContentWriter writer = contentService.getWriter(contentRef, propContent, true);

      // set the mimetype and encoding
      writer.setMimetype(mimetype);
      writer.setEncoding(encoding);
      if (fileContent != null)
      {
         writer.putContent(fileContent);
      }
      else
      {
         writer.putContent(stringContent == null ? "" : stringContent);
      }
   }

   private Map<QName, Serializable> extractedProperties(final File file, final String mimeType, final String encoding)
   {
	   Map<QName, Serializable> props = null;

	   try
	   {
		   //	Try and extract metadata from the file
		   final ContentReader cr = new FileContentReader(file);
		   cr.setMimetype(mimeType);
		   cr.setEncoding(encoding);

		   props = metadataManager.extractContentMetadata(cr);

	   }
	   catch(Throwable ignore)
	   {
		   logger.error("Error extracting metadata for file '" + file.getPath() + "'.", ignore);

		   props = null;
	   }

	   if(props == null)
	   {
		   return Collections.<QName, Serializable>emptyMap();
	   }
	   else
	   {
		   return props;
	   }

   }

   /**
    * Save the specified content using the currently set wizard attributes
    *
    * @param fileContent      File content to save
    * @param strContent       String content to save (if fileContent is null)
    */
   private void updateProperties(final NodeRef fileNodeRef, final String mimetype, final Map<QName, Serializable> extractedProps)
   {
	   final Serializable author = extractedProps.get(ContentModel.PROP_AUTHOR);
	   final Serializable title = extractedProps.get(ContentModel.PROP_TITLE);
	   final Serializable description = extractedProps.get(ContentModel.PROP_DESCRIPTION);

	   // set the author aspect
	   final Map<QName, Serializable> authorProps = Collections.singletonMap(ContentModel.PROP_AUTHOR, author);
	   nodeService.addAspect(fileNodeRef, ContentModel.ASPECT_AUTHOR, authorProps);

	   if (logger.isDebugEnabled())
	   {
		   logger.debug("Author aspect setted with author: " + author);
	   }
	   
	   // take ownership (added for Improvement DIGIT-CIRCABC-1841)
	   getOwnableService().takeOwnership(fileNodeRef);

	   // apply the titled aspect - title and description
	   final Map<QName, Serializable> titledProps = new HashMap<QName, Serializable>(3, 1.0f);
	   titledProps.put(ContentModel.PROP_TITLE, title);
	   titledProps.put(ContentModel.PROP_DESCRIPTION, description);
	   nodeService.addAspect(fileNodeRef, ContentModel.ASPECT_TITLED, titledProps);

	   if (logger.isDebugEnabled())
	   {
		   logger.debug("Titled aspect setted with title: " + title + " and description: " + description);
	   }

	   final boolean inlineEdit = isInlineEditable(mimetype);

	   // apply the inlineeditable aspect
	   if (inlineEdit == true)
	   {
		   final Map<QName, Serializable> editProps = Collections.singletonMap(ApplicationModel.PROP_EDITINLINE, (Serializable) Boolean.TRUE);
		   nodeService.addAspect(fileNodeRef, ApplicationModel.ASPECT_INLINEEDITABLE, editProps);

		   if (logger.isDebugEnabled())
		   {
			   logger.debug("The content is inline editable.");
		   }
	   }
   }

   private final boolean isInlineEditable(final String mimetype)
   {
	   try
	   {
		   return metadataManager.isInlineEditable(mimetype);
	   }
	   catch(Throwable ignore)
	   {
		   logger.error("Error resolving if mimetype '" + mimetype + "' is inline editable.", ignore);

		   return false;
	   }
   }

   private final String computeMimeType(final String fileName)
   {
	   try
	   {
		   return metadataManager.guessMimetype(fileName);
	   }
	   catch(Throwable ignore)
	   {
		   logger.error("Error resolving mimetype for content filename: '" + fileName + "'.", ignore);

		   return MimetypeMap.MIMETYPE_BINARY;
	   }
   }

   private final String computeEncoding(final File fileContent,  final String mimetype)
   {
	   try
	   {
		   if(fileContent != null)
		   {
			   InputStream is = null;
			   try
			   {
				   if (fileContent != null)
				   {
					   is = new BufferedInputStream(new FileInputStream(fileContent));
					   return metadataManager.guessEncoding(is, mimetype);
				   }
			   }
			   catch (final Throwable e)
			   {
				   logger.error("Failed to get encoding from file: " + fileContent.getPath(), e);
			   }
			   finally
			   {
				   try
				   {
					   is.close();
				   }
				   catch (Throwable ignore) {}
			   }
		   }
	   }
	   catch(Throwable ignore)
	   {
		   logger.error("Error resolving encoding for mimetype '" + mimetype + "'.", ignore);
	   }

	   // return default encoding.
	   return configManager.getEncoding();
   }

	//--------------
	//-- IOC

	/**
	 * @param configManager the configManager to set
	 */
	public final void setConfigManager(ApplicationConfigManager configManager)
	{
		this.configManager = configManager;
	}

	/**
	 * @param contentService the contentService to set
	 */
	public final void setContentService(ContentService contentService)
	{
		this.contentService = contentService;
	}


	/**
	 * @param metadataManager the metadataManager to set
	 */
	public final void setMetadataManager(MetadataManager metadataManager)
	{
		this.metadataManager = metadataManager;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}
	
	
	private OwnableService ownableService;
	public OwnableService getOwnableService() {
		return ownableService;
	}
	public void setOwnableService(OwnableService ownableService) {
		this.ownableService = ownableService;
	}


	/**
	 * @param validationManager the validationManager to set
	 */
	public final void setValidationManager(ValidationManager validationManager)
	{
		this.validationManager = validationManager;
	}
}
