/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.bean.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.surveys.Survey;
import eu.cec.digit.circabc.web.bean.surveys.SurveysBean;

/**
 * Bean that backs the navigation inside the Survey Service
 *
 * @author yanick pignot
 */
public class SurveyHomeBean extends InterestGroupBean
{
	private static final String TITLE_SURVEYS = "title_surveys";

	private static final String TYPE = ", type = ";

	private static final String FOUND_INVALID_OBJECT_IN_DATABASE_ID = "Found invalid object in database: id = ";

	private static final String MS = "ms";

	private static final String TIME_TO_QUERY_AND_BUILD_MAP_NODES = "Time to query and build map nodes: ";

	private static final String UNEXPECTED_ERROR = "Unexpected error:";

	private static final long serialVersionUID = -6967164595499663893L;

	private static final Log logger = LogFactory.getLog(SurveyHomeBean.class);

	public static final String JSP_NAME  = "survey-home.jsp";
	public static final String BEAN_NAME = "SurveyHomeBean";

	public static final String MSG_PAGE_DESCRIPTION = "surveys_title_desc";

	private List<Node> containers = null;
	private SurveysBean nativeSurveysBean;

	public NavigableNodeType getManagedNodeType()
	{
		return NavigableNodeType.SURVEY;
	}

	public String getRelatedJsp()
	{
		return NAVIGATION_JSP_FOLDER + JSP_NAME;
	}

	public String getPageDescription()
	{
		return translate(MSG_PAGE_DESCRIPTION);
	}

	public String getPageTitle()
	{
		return getCurrentNode().getName();
	}

	public String getPageIcon()
	{
		return IMAGES_ICONS + getCurrentNode().getProperties().get(ICON) + GIF;
	}

	public String getPageIconAltText()
	{
		return getCurrentNode().getName();
	}

	@Override
	public String getBrowserTitle()
	{
		return translate(TITLE_SURVEYS);
	}

	public void init(Map<String, String> parameters)
    {
		containers = null;
		getNativeSurveysBean().setInWAI(true);
    }

	/**
	 * Get list of container nodes for the currentNodeRef
	 *
	 * @return The list of container nodes for the currentNodeRef
	 */
	public List<Node> getContainerNodes()
	{
		if (this.containers == null)
		{
			// We populate the list if not already done
			fillBrowseNodes(getNavigator().getCurrentNode());
		}

		return this.containers;
	}


    /**
     * @return the list of surveys
     */
    public List<Survey> getSurveys()
    {
    	return getNativeSurveysBean().getSurveys();
    }


	/**
	 * Query a list of nodes for the specified parent node Id<br>
	 * Based on BrowseBean.queryBrowseNodes()
	 *
	 * @param currentNode of the parent node or null for the root node
	 */
	private void fillBrowseNodes(final Node currentNode)
	{
		long startTime = 0;
		if (logger.isDebugEnabled())
			startTime = System.currentTimeMillis();

		final FacesContext context = FacesContext.getCurrentInstance();
		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
		{
			public Object execute() throws Throwable
			{
				try
				{
					final List<FileInfo> children = getFileFolderService().list(currentNode.getNodeRef());
					containers = new ArrayList<Node>(children.size());
					TypeDefinition typeDef;
					NodeRef nodeRef;
					QName type;
					NavigableNode node = null;
					for (final FileInfo fileInfo : children)
					{
						// create our Node representation from the NodeRef
						nodeRef = fileInfo.getNodeRef();

						// find it's type so we can see if it's a node we are interested in
						type = getNodeService().getType(nodeRef);

						// make sure the type is defined in the data dictionary
						typeDef = getDictionaryService().getType(type);

						if (typeDef != null)
						{
							if (ContentModel.TYPE_FOLDER.equals(type))
							{
								node = ResolverHelper.createFolderRepresentation(fileInfo, getNodeService(), getBrowseBean());
							}
							else if (ApplicationModel.TYPE_FOLDERLINK.equals(type))
							{
								node = ResolverHelper.createFolderLinkRepresentation(fileInfo, getNodeService(), getBrowseBean());
							}
							else if(getDictionaryService().isSubClass(type, ContentModel.TYPE_FOLDER) == true &&
								      getDictionaryService().isSubClass(type, ContentModel.TYPE_SYSTEM_FOLDER) == false)
							{
								node = ResolverHelper.createFolderRepresentation(fileInfo, getNodeService(), getBrowseBean());
							}

							if(node != null)
							{
								containers.add(node);
							}

						}
						else
						{
							if (logger.isWarnEnabled())
								logger.warn(FOUND_INVALID_OBJECT_IN_DATABASE_ID + nodeRef + TYPE + type);
						}
					}

					// all is OK, mem the node id

				} catch (final InvalidNodeRefException refErr)
				{
					Utils.addErrorMessage(translate(Repository.ERROR_NODEREF, new Object[]
					{ refErr.getNodeRef() }), refErr);
					containers = Collections.<Node> emptyList();

				} catch (final Throwable err)
				{
					Utils.addErrorMessage(translate(Repository.ERROR_GENERIC, err.getMessage()), err);
					containers = Collections.<Node> emptyList();
					if(logger.isErrorEnabled())
						logger.error(UNEXPECTED_ERROR + err.getMessage(), err);
				}
				return null;
			}
		};
		txnHelper.doInTransaction(callback, true);

		if (logger.isDebugEnabled())
		{
			long endTime = System.currentTimeMillis();
			logger.debug(TIME_TO_QUERY_AND_BUILD_MAP_NODES + (endTime - startTime) + MS);
		}
	}

	/**
	 * @return the nativeSurveysBean
	 */
	public SurveysBean getNativeSurveysBean()
	{
		if(nativeSurveysBean == null)
		{
			nativeSurveysBean = (SurveysBean) Beans.getBean(SurveysBean.BEAN_NAME);
		}
		return nativeSurveysBean;
	}

	/**
	 * @param nativeSurveysBean the nativeSurveysBean to set
	 */
	public void setNativeSurveysBean(final SurveysBean nativeSurveysBean)
	{
		this.nativeSurveysBean = nativeSurveysBean;
	}

}
