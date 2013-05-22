package eu.cec.digit.circabc.web.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.ml.MultilingualContentService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.web.app.servlet.BaseServlet;
import org.alfresco.web.app.servlet.BaseServlet.PathRefInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.DocumentModel;
public class DownloadSecureTransportFilter extends BaseSecureTransportFilter {
	private final static Log logger = LogFactory.getLog(DownloadSecureTransportFilter.class);

	@Override
	protected boolean isSecureDocument(HttpServletRequest httpReq) {
		String uri = httpReq.getRequestURI();

		   final String ARG_PATH     = "path";

	      uri = uri.substring(httpReq.getContextPath().length());
	      StringTokenizer t = new StringTokenizer(uri, "/");
	      int tokenCount = t.countTokens();

	      t.nextToken();    // skip servlet name

	      // skip attachment mode (either 'attach' or 'direct')
	      t.nextToken();

	      ServiceRegistry serviceRegistry = getServiceRegistry(getServletContext() );

	      // get or calculate the noderef and filename to download as
	      NodeRef nodeRef;
	      String filename;

	      // do we have a path parameter instead of a NodeRef?
	      String path = httpReq.getParameter(ARG_PATH);
	      if (path != null && path.length() != 0)
	      {
	         // process the name based path to resolve the NodeRef and the Filename element
	         PathRefInfo pathInfo =  BaseServlet.resolveNamePath(getServletContext(), path);

	         nodeRef = pathInfo.NodeRef;
	         filename = pathInfo.Filename;
	      }
	      else
	      {
	         // a NodeRef must have been specified if no path has been found
	         if (tokenCount < 6)
	         {
	        	 return false ;
	         }

	         // assume 'workspace' or other NodeRef based protocol for remaining URL elements
	         StoreRef storeRef = new StoreRef(t.nextToken(), t.nextToken());

	         String id="";

             try {
				id = URLDecoder.decode(t.nextToken(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				logger.error(e1);
			}
	         // build noderef from the appropriate URL elements
	         nodeRef = new NodeRef(storeRef, id);

	         if (tokenCount > 6)
	         {
	            // found additional relative path elements i.e. noderefid/images/file.txt
	            // this allows a url to reference siblings nodes via a cm:name based relative path
	            // solves the issue with opening HTML content containing relative URLs in HREF or IMG tags etc.
	            List<String> paths = new ArrayList<String>(tokenCount - 5);
	            while (t.hasMoreTokens())
	            {
	               paths.add(URLDecoder.decode(t.nextToken()));
	            }
	            filename = paths.get(paths.size() - 1);

	            try
	            {
	               NodeRef parentRef = serviceRegistry.getNodeService().getPrimaryParent(nodeRef).getParentRef();
	               FileInfo fileInfo = serviceRegistry.getFileFolderService().resolveNamePath(parentRef, paths);
	               nodeRef = fileInfo.getNodeRef();
	            }
	            catch (FileNotFoundException e)
	            {
	            	return false ; 	
	               //throw new AlfrescoRuntimeException("Unable to find node reference by relative path:" + uri);
	            }
	         }
	         else
	         {
	            // filename is last remaining token
	            filename = t.nextToken();
	         }


	      }

	    final String currentUser = AuthenticationUtil.getRunAsUser();
	    boolean result = false;
	    try 
	    {
		    
		    AuthenticationUtil.setRunAsUserSystem();
		    serviceRegistry = getServiceRegistry(getServletContext() );
			NodeService nodeService = serviceRegistry.getNodeService();
			if (nodeService.hasAspect(nodeRef, org.alfresco.model.ContentModel.ASPECT_MULTILINGUAL_DOCUMENT))
			{
				MultilingualContentService multilingualContentService = serviceRegistry.getMultilingualContentService();
			    nodeRef = multilingualContentService.getTranslationContainer(nodeRef);
			}
			String securityRanking = (String) nodeService.getProperty(nodeRef, DocumentModel.PROP_SECURITY_RANKING);
			result =   securityRanking != null && !securityRanking.equalsIgnoreCase("PUBLIC") ;
		    if (logger.isDebugEnabled())
		    {
		    	logger.debug("file: " + filename);
		    	logger.debug("noderef: " + nodeRef);
		    	logger.debug("securityRanking: " + securityRanking);
		    }
	    }
	    finally
	    {
	    	AuthenticationUtil.setRunAsUser(currentUser);
	    }
		
		return result;
	}

	@Override
	protected Log getLogger() {
		return logger;
	}

}
