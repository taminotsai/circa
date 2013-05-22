/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.config.source;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.springframework.extensions.config.ConfigException;
import org.springframework.extensions.config.source.BaseConfigSource;

import eu.cec.digit.circabc.web.ui.common.UtilsCircabc;

/**
 * ConfigSource that looks for a nodeRef <b></b>.
 *
 * @author Yanick Pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * ConfigException was moved to Spring.
 * BaseConfigSource was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class RepoRefConfigSource extends BaseConfigSource
{
	private final ContentService contentService;
	private final NodeService nodeService;

	/**
	 * @param sourceLocations
	 */
	public RepoRefConfigSource(final List<String> sourceLocations, final ContentService contentService ,  final NodeService nodeService)
	{
		super(sourceLocations);
		this.contentService = contentService;
		this.nodeService = nodeService;
	}

	/**
	 * @param sourceLocation
	 */
	public RepoRefConfigSource(String sourceLocation, final ContentService contentService,final NodeService nodeService)
	{
		this(Collections.<String>singletonList(sourceLocation), contentService, nodeService);
	}

	/**
	 * @param sourceRef
	 */
	public RepoRefConfigSource(NodeRef sourceRef, final ContentService contentService,final NodeService nodeService)
	{
		this(sourceRef.toString(), contentService, nodeService);
	}

	@Override
	public InputStream getInputStream(String sourceUrl)
    {
		if(NodeRef.isNodeRef(sourceUrl) == false)
		{
			throw new ConfigException("The service only allow noderef source.");

		}
		else if(this.contentService == null)
		{
			throw new ConfigException("The content Service constructor parameter is mandatory.");

		}
		else
		{
			NodeRef nodeRef = new NodeRef(sourceUrl);
			QName propContent = UtilsCircabc.getPropContent(nodeService.getType(nodeRef));
			final ContentReader cr = contentService.getReader(nodeRef, propContent);
            return cr.getContentInputStream();
        }
    }
}
