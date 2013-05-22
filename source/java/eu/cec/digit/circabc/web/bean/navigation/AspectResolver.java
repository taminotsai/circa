/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.bean.navigation;

import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.CATEGORY;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.CATEGORY_HEADER;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.CIRCABC_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.CIRCABC_ROOT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.DIRECTORY;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.EVENT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.EVENT_APPOINTMENT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.EVENT_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.IG_ROOT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.INFORMATION;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.INFORMATION_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.INFORMATION_CONTENT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.INFORMATION_FILE_LINK;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.INFORMATION_SPACE;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_CONTENT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_DOSSIER;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_EMPTY_TRANSLATION;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_FILE_LINK;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_FORUM;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_ML_CONTENT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_ML_CONTENT_FORUM;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_ML_CONTENT_POST;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_ML_CONTENT_TOPIC;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_POST;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_SPACE;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.LIBRARY_TOPIC;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.NEWSGROUP;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.NEWSGROUP_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.NEWSGROUP_FORUM;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.NEWSGROUP_POST;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.NEWSGROUP_TOPIC;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.SURVEY;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.SURVEY_CHILD;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.SURVEY_ELEMENT;
import static eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType.SURVEY_SPACE;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.web.bean.repository.MapNode;
import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Services;

/**
 * Util class that resolve the cicabc node type of a given node.
 * 
 * @see eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType
 * @author yanick pignot
 */
public abstract class AspectResolver {
	private static final Log LOGGER = LogFactory.getLog(AspectResolver.class);
	private static final StoreRef SPACE_STORE = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore");

	public static boolean isNodeManagedByCircabc(final Node node) {
		if (!SPACE_STORE.equals(node.getNodeRef().getStoreRef())) {
			return false;
		} else if (CIRCABC_CHILD.isNodeFromType(node) || CATEGORY_HEADER.isNodeFromType(node)) {
			return true;
		} else if (LIBRARY_ML_CONTENT.isNodeFromType(node)) {
			final MultilingualContentService mlService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getMultilingualContentService();

			return isNodeManagedByCircabc(new Node(mlService.getPivotTranslation(node.getNodeRef())));
		} else if (LIBRARY_ML_CONTENT_FORUM.isNodeFromType(node) || LIBRARY_ML_CONTENT_POST.isNodeFromType(node) || LIBRARY_ML_CONTENT_TOPIC.isNodeFromType(node)) {
			final NodeService nodeService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getNodeService();
			return isNodeManagedByCircabc(new Node(nodeService.getPrimaryParent(node.getNodeRef()).getParentRef()));
		} else if (LIBRARY_EMPTY_TRANSLATION.isNodeFromType(node)) {
			final MultilingualContentService mlService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getMultilingualContentService();
			return isNodeManagedByCircabc(new Node(mlService.getPivotTranslation(node.getNodeRef())));
		} else {
			return false;
		}
	}

	public static NavigableNodeType resolveType(final Node node) {

		long startTime = 0;
		if (LOGGER.isDebugEnabled()) {
			startTime = System.currentTimeMillis();
		}

		final NavigableNodeType nodeType = resolveTypeImpl(node);

		if (LOGGER.isDebugEnabled()) {
			final long endTime = System.currentTimeMillis();
			LOGGER.debug("Time to resolve a circabc node type: " + (endTime - startTime) + "ms. ");
			LOGGER.debug("Type returned : " + nodeType + " for the node : " + node.getName() + "\n\tAspects: " + node.getAspects());
		}

		return nodeType;
	}

	public static NavigableNodeType resolveType(final NodeRef noderef) {
		return resolveType(new MapNode(noderef));
	}

	private static NavigableNodeType resolveTypeImpl(final Node node) {
		if (node == null) {
			throw new IllegalArgumentException("The node reference is mandatory.");
		} else if (CIRCABC_CHILD.isNodeFromType(node, false)) {
			if (CIRCABC_ROOT.isNodeFromType(node, false)) {
				return CIRCABC_ROOT;
			} else if (CATEGORY.isNodeFromType(node, false)) {
				return CATEGORY;
			} else if (IG_ROOT.isNodeFromType(node, false)) {
				return IG_ROOT;
			} else if (LIBRARY.isNodeFromType(node, false)) {
				return LIBRARY;
			} else if (NEWSGROUP.isNodeFromType(node, false)) {
				return NEWSGROUP;
			} else if (SURVEY.isNodeFromType(node, false)) {
				return SURVEY;
			} else if (DIRECTORY.isNodeFromType(node, false)) {
				return DIRECTORY;
			} else if (INFORMATION.isNodeFromType(node, false)) {
				return INFORMATION;
			} else if (EVENT.isNodeFromType(node, false)) {
				return EVENT;
			} else if (LIBRARY_CHILD.isNodeFromType(node, false)) {
				if (LIBRARY_FORUM.isNodeFromType(node, false)) {
					return LIBRARY_FORUM;
				} else if (LIBRARY_POST.isNodeFromType(node, false)) {
					return LIBRARY_POST;
				} else if (LIBRARY_TOPIC.isNodeFromType(node, false)) {
					return LIBRARY_TOPIC;
				} else if (LIBRARY_DOSSIER.isNodeFromType(node, false)) {
					return LIBRARY_DOSSIER;
				} else if (LIBRARY_SPACE.isNodeFromType(node, false)) {
					return LIBRARY_SPACE;
				} else if (LIBRARY_EMPTY_TRANSLATION.isNodeFromType(node, false)) {
					return LIBRARY_EMPTY_TRANSLATION;
				} else if (LIBRARY_CONTENT.isNodeFromType(node, false)) {
					return LIBRARY_CONTENT;
				} else if (LIBRARY_FILE_LINK.isNodeFromType(node, false)) {
					return LIBRARY_FILE_LINK;
				}
			} else if (NEWSGROUP_CHILD.isNodeFromType(node, false)) {
				if (NEWSGROUP_FORUM.isNodeFromType(node, false)) {
					return NEWSGROUP_FORUM;
				} else if (NEWSGROUP_TOPIC.isNodeFromType(node, false)) {
					return NEWSGROUP_TOPIC;
				} else if (NEWSGROUP_POST.isNodeFromType(node, false)) {
					return NEWSGROUP_POST;
				}
			} else if (SURVEY_CHILD.isNodeFromType(node, false)) {
				if (SURVEY_SPACE.isNodeFromType(node, false)) {
					return SURVEY_SPACE;
				} else if (SURVEY_ELEMENT.isNodeFromType(node, false)) {
					return SURVEY_ELEMENT;
				}
			} else if (EVENT_CHILD.isNodeFromType(node, false)) {
				if (EVENT_APPOINTMENT.isNodeFromType(node, false)) {
					return EVENT_APPOINTMENT;
				} else {
					return EVENT_CHILD;
				}
			} else if (INFORMATION_CHILD.isNodeFromType(node, false)) {
				if (INFORMATION_SPACE.isNodeFromType(node, false)) {
					return INFORMATION_SPACE;
				} else if (INFORMATION_CONTENT.isNodeFromType(node, false)) {
					return INFORMATION_CONTENT;
				} else if (INFORMATION_FILE_LINK.isNodeFromType(node, false)) {
					return INFORMATION_FILE_LINK;
				}
			}
		} else if (CATEGORY_HEADER.isNodeFromType(node, false)) {
			return CATEGORY_HEADER;
		} else if (isNodeManagedByCircabc(node)) {
			if (LIBRARY_ML_CONTENT.isNodeFromType(node, false)) {
				return LIBRARY_ML_CONTENT;
			} else if (LIBRARY_EMPTY_TRANSLATION.isNodeFromType(node, false)) {
				return LIBRARY_EMPTY_TRANSLATION;
			} else if (LIBRARY_ML_CONTENT_FORUM.isNodeFromType(node, false)) {
				return LIBRARY_ML_CONTENT_FORUM;
			} else if (LIBRARY_ML_CONTENT_POST.isNodeFromType(node, false)) {
				return LIBRARY_ML_CONTENT_POST;
			} else if (LIBRARY_ML_CONTENT_TOPIC.isNodeFromType(node, false)) {
				return LIBRARY_ML_CONTENT_TOPIC;
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Circabc attempt to manage an No Circabc node or a not managed node " + node);
		}

		return null;
	}

	private AspectResolver() {

	}
}
