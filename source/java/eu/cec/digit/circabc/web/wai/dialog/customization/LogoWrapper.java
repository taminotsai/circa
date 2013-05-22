/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.customization;

import static eu.cec.digit.circabc.web.wai.dialog.customization.NavigationPrefWrapper.MSG_CURRENT_NODE;
import static eu.cec.digit.circabc.web.wai.dialog.customization.NavigationPrefWrapper.MSG_UNDEFINED;

import java.io.Serializable;

import javax.jcr.PathNotFoundException;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.util.ParameterCheck;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.repo.struct.SimplePath;
import eu.cec.digit.circabc.service.customisation.logo.LogoDefinition;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.WebClientHelper.ExtendedURLMode;

/**
 * @author Yanick Pignot
 *
 */
public class LogoWrapper implements Serializable
{

	/** */
	private static final long serialVersionUID = 8468335099265140975L;

	private final LogoDefinition definition;
	private final NodeService nodeService;
	private final Node currentNode;
	private final String selectedLogid;

	/*package*/ LogoWrapper(final LogoDefinition definition, final Node currentNode, final NodeService nodeService, final String selectedLogid)
	{
		super();

		ParameterCheck.mandatory("A logo definition", definition);
		ParameterCheck.mandatory("The current node", currentNode);
		ParameterCheck.mandatory("The node service", nodeService);

		this.definition = definition;
		this.currentNode = currentNode;
		this.nodeService = nodeService;
		this.selectedLogid = selectedLogid;
	}

	public NodeRef getDefinedOn()
	{
		return definition.getDefinedOn();
	}

	public String getDefinedOnPath()
	{
		final NodeRef customizedOn = getDefinedOn();

		if(customizedOn.equals(currentNode.getNodeRef()))
		{
			return translate(MSG_CURRENT_NODE);
		}
		else
		{
			SimplePath path;
			try
			{
				path = new SimplePath(nodeService, customizedOn);
				return path.toString();
			}
			catch (PathNotFoundException e)
			{
				return "<i>" + translate(MSG_UNDEFINED) + "</i>";
			}
		}
	}

	public String getDownloadPath()
	{
		return WebClientHelper.getGeneratedWaiUrl(getReference(), ExtendedURLMode.HTTP_DOWNLOAD, true);
	}

	public String getDescription()
	{
		return definition.getDescription();
	}

	public String getName()
	{
		return definition.getName();
	}

	public NodeRef getReference()
	{
		return definition.getReference();
	}

	public String getTitle()
	{
		return definition.getTitle();
	}

	public boolean isSelected()
	{
		return selectedLogid != null && selectedLogid.equals(getReference().getId());
	}

	public boolean isSelectable()
	{
		return selectedLogid == null || selectedLogid.equals(getReference().getId()) == false;
	}

	public boolean isRemovable()
	{
		return isSelected() == false && getDefinedOn().equals(currentNode.getNodeRef());
	}

	private String translate(final String message)
	{
		return WebClientHelper.translate(message);
	}
}
