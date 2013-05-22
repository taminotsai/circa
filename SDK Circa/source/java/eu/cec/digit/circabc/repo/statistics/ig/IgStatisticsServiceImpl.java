/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.ig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.util.ISO9075;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.repo.log.ActivityCountDAO;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.statistics.ig.IgStatisticsService;
import eu.cec.digit.circabc.web.Services;

/**
 * @author beaurpi
 * 
 */
public class IgStatisticsServiceImpl implements IgStatisticsService {

	private NodeService nodeService;
	private SearchService searchService;
	private NamespaceService  namespaceService;
	private FileFolderService fileFolderService;
	private LogService logService;
	
	private static final String LUCENE_PATH_PREFIX= "(PATH:\"";
	private static final String LUCENE_AND_TYPE_PREFIX= "AND (TYPE:\"";
	private static final String LUCENE_END_PATH_PREFIX = "\")";
	private static final String LUCENE_STAR_PATH_PREFIX = "/*\")";
	private static final String LOG_PARSING_PREFIX = "Parsing Lucene indexes for statistics: ";
	
	/** A logger for the class */
	final static Log logger = LogFactory.getLog(IgStatisticsServiceImpl.class);
	
	public Integer getNumberOfUsers(NodeRef igRoot) {

		final FacesContext fc = FacesContext.getCurrentInstance();
		
		final IGRootProfileManagerService igrootProfileManger = Services.getCircabcServiceRegistry(fc).getIGRootProfileManagerService();
		
		return igrootProfileManger.getInvitedUsers(igRoot).size();
	}

	

	public Serializable getIGCreationDate(NodeRef igRoot) {
	
		
		return nodeService.getProperty(igRoot, ContentModel.PROP_CREATED);
	}

	/**
	 * @return the nodeService
	 */
	public NodeService getNodeService() {
		return nodeService;
	}

	/**
	 * @param nodeService the nodeService to set
	 */
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	/**
	 * @return the searchService
	 */
	public SearchService getSearchService() {
		return searchService;
	}



	/**
	 * @param searchService the searchService to set
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}



	public Integer getNumberOfLibraryDocuments(NodeRef igRoot) {
         
        String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot)+ getLuceneLibraryPathPrefix()+ LUCENE_END_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX+ContentModel.TYPE_CONTENT+LUCENE_END_PATH_PREFIX;
        if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}

	public Integer getNumberOfLibrarySpaces(NodeRef igRoot) {
         
        String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) +getLuceneLibraryPathPrefix()+ LUCENE_END_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX+ContentModel.TYPE_FOLDER+LUCENE_END_PATH_PREFIX;
		query = query + "AND ( ASPECT:\"{http://www.cc.cec/circabc/model/content/1.0}circaLibrary\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}

	private String getLuceneLibraryPathPrefix()
	{
		return "cm:Library//*";
	}
	
	private String getPathFromSpaceRef(NodeRef igRoot) {
		
		final Path path = getNodeService().getPath(igRoot);
		final StringBuilder buf = new StringBuilder(64);
		String elementString;
		Path.Element element;
		ChildAssociationRef elementRef;
		Collection<?> prefixes;
		for (int i = 0; i < path.size(); i++)
		{
			elementString = "";
			element = path.get(i);
			if (element instanceof Path.ChildAssocElement)
			{
				elementRef = ((Path.ChildAssocElement) element).getRef();
				if (elementRef.getParentRef() != null)
				{
					prefixes = getNamespaceService().getPrefixes(elementRef.getQName().getNamespaceURI());
					if (prefixes.size() > 0)
					{
						elementString = '/' + (String) prefixes.iterator().next() + ':' + ISO9075.encode(elementRef.getQName().getLocalName());
					}
				}
			}

			buf.append(elementString);
		}

			buf.append("/");


		return buf.toString();
	}


	public Integer getNumberOfInformationDocuments(NodeRef igRoot) {
         
        String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot)+ getLuceneInformationPathPrefix()+ LUCENE_END_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX+ContentModel.TYPE_CONTENT+LUCENE_END_PATH_PREFIX;
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        } 
        return queryLuceneAndReturnNbResult(query, igRoot);
	}

	public Integer getNumberOfInformationSpaces(NodeRef igRoot) {
         
        String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) +getLuceneInformationPathPrefix()+ LUCENE_END_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX+ContentModel.TYPE_FOLDER+LUCENE_END_PATH_PREFIX;
		query = query + "AND ( ASPECT:\"{http://www.cc.cec/circabc/model/content/1.0}circabcInformation\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
        return queryLuceneAndReturnNbResult(query, igRoot);
	}

	private String getLuceneInformationPathPrefix() {
		return "cm:Information//*";
	}


	public String getIGTitle(NodeRef igRoot) {
		return nodeService.getProperty(igRoot, ContentModel.PROP_TITLE).toString();
	}



	/**
	 * @return the namespaceService
	 */
	public NamespaceService getNamespaceService() {
		return namespaceService;
	}



	/**
	 * @param namespaceService the namespaceService to set
	 */
	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}



	public Integer getNumbetOfMeetings(NodeRef igRoot) {
		
        String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) + LUCENE_STAR_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX + "{http://www.cc.cec/circabc/model/events/1.0}meetingDefinition\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}



	public Integer getNumbetOfEvents(NodeRef igRoot) {
		
		String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) + LUCENE_STAR_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX + "{http://www.cc.cec/circabc/model/events/1.0}eventDefinition\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}
	
	/***
	 * 
	 *
	 * @param igRoot the ig root noderef
	 * @param query the Lucene query
	 * @return the number of results
	 */
	private Integer queryLuceneAndReturnNbResult(String query, NodeRef igRoot)
	{
		SearchParameters sp = new SearchParameters();
        sp.addStore(igRoot.getStoreRef());
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        
        ResultSet results = null;
        
        sp.setQuery(query);
        
        Integer nb=0;
        
        try
        {
            results = searchService.query(sp);
            nb=results.length();

        }
        finally
        {
            if(results != null)
            {
                results.close();
            }
        } 
        return nb;
	}
	
	/***
	 * 
	 *
	 * @param igRoot the ig root noderef
	 * @param query the Lucene query
	 * @return the number of results
	 */
	private Long computeSize(String query, NodeRef igRoot)
	{
		SearchParameters sp = new SearchParameters();
        sp.addStore(igRoot.getStoreRef());
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        
        ResultSet results = null;
        
        sp.setQuery(query);
        
        Long nb=0L;
        
        try
        {
            results = searchService.query(sp);
            for (ResultSetRow resultSetRow : results) {
            	ContentData cd= (ContentData)nodeService.getProperty(resultSetRow.getNodeRef(),ContentModel.PROP_CONTENT);
            	if(cd != null)
            	{
            		nb+=cd.getSize();
            	}
			}

        }
        finally
        {
            if(results != null)
            {
                results.close();
            }
        } 
        return nb;
	}



	public Integer getNumberOfForums(NodeRef igRoot) {
		String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) + LUCENE_STAR_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX + "{http://www.alfresco.org/model/forum/1.0}forum\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}


	public Integer getNumberOfTopics(NodeRef igRoot) {
		String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) + LUCENE_STAR_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX + "{http://www.alfresco.org/model/forum/1.0}topic\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}


	public Integer getNumberOfPosts(NodeRef igRoot) {
		String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) + LUCENE_STAR_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX + "{http://www.alfresco.org/model/forum/1.0}post\")";
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        }
		return queryLuceneAndReturnNbResult(query, igRoot);
	}



	public Long getContentSizeOfLibrary(NodeRef igRoot) {
		String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot)+ getLuceneLibraryPathPrefix()+ LUCENE_END_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX+ContentModel.TYPE_CONTENT+LUCENE_END_PATH_PREFIX;
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        } 
        return computeSize(query, igRoot);
	}


	public Long getContentSizeOfInformation(NodeRef igRoot) {
		
		String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot)+ getLuceneInformationPathPrefix()+ LUCENE_END_PATH_PREFIX ;
		query = query + LUCENE_AND_TYPE_PREFIX+ContentModel.TYPE_CONTENT+LUCENE_END_PATH_PREFIX;
		if(logger.isDebugEnabled()){
        	logger.debug(LOG_PARSING_PREFIX+query);
        } 
        return computeSize(query, igRoot);
	}

	/***
	 * Walk recursively into the library service and build a tree representation of all folders
	 */
	public ServiceTreeRepresentation getLibraryStructure(NodeRef igRoot) {
		
		ServiceTreeRepresentation libraryTree = new ServiceTreeRepresentation("library");
		Child libraryRoot = new Child();
		libraryRoot.setName("Library");
		
		libraryRoot.setNode(getLibraryNodeRefFromLucene(igRoot));
		
		libraryTree.setChild(libraryRoot);
		
		recursiveWalkAndBuildTree(libraryRoot.getNode(), libraryRoot);
		
		return libraryTree;
	}
	
	/***
	 * Walk recursively into the information service and build a tree representation of all folders
	 */
	public ServiceTreeRepresentation getInformationStructure(NodeRef igRoot) {
		
		ServiceTreeRepresentation informationTree = new ServiceTreeRepresentation("library");
		Child informationRoot = new Child();
		informationRoot.setName("Information");
		
		informationRoot.setNode(getInformationNodeRefFromLucene(igRoot));

		informationTree.setChild(informationRoot);
		
		recursiveWalkAndBuildTree(informationRoot.getNode(), informationRoot);
		
		return informationTree;
	}
	
	/***
	 * Walk recursively into the information service and build a tree representation of all folders
	 */
	public ServiceTreeRepresentation getNewsgroupsStructure(NodeRef igRoot) {
		
		ServiceTreeRepresentation newsgroupTree = new ServiceTreeRepresentation("newsgroup");
		Child newsgroupRoot = new Child();
		newsgroupRoot.setName("Newsgroup");
		
		newsgroupRoot.setNode(getNewsgroupsNodeRefFromLucene(igRoot));

		newsgroupTree.setChild(newsgroupRoot);
		
		recursiveWalkAndBuildTree(newsgroupRoot.getNode(), newsgroupRoot);
		
		return newsgroupTree;
	}

	
	private void recursiveWalkAndBuildTree(NodeRef node, Child child)
	{
		ArrayList<FileInfo> lf = (ArrayList<FileInfo>) fileFolderService.listFolders(node);
		

		if(lf.size()>0)
		{
			for(FileInfo fi: lf)
			{
				Child cTmp = new Child();
				
				cTmp.setName(fi.getName());
				cTmp.setNode(fi.getNodeRef());
				
				child.getChildren().add(cTmp);
				
				recursiveWalkAndBuildTree(fi.getNodeRef(), cTmp);
			}
		}
		
	}



	/**
	 * @return the fileFolderService
	 */
	public FileFolderService getFileFolderService() {
		return fileFolderService;
	}



	/**
	 * @param fileFolderService the fileFolderService to set
	 */
	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	private NodeRef getLibraryNodeRefFromLucene(NodeRef igRoot)
	{
		 String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) +"cm:Library"+ LUCENE_END_PATH_PREFIX ;
			 
		return getNodeRefFomLucene(igRoot, query);
	}



	private NodeRef getNodeRefFomLucene(NodeRef igRoot, String query) {
		NodeRef nodeRef;
		 
		 SearchParameters sp = new SearchParameters();
	        sp.addStore(igRoot.getStoreRef());
	        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
	        
	        ResultSet results = null;
	        
	        sp.setQuery(query);
	        	        
	        try
	        {
	            results = searchService.query(sp);
	            nodeRef=results.getNodeRef(0);

	        }
	        finally
	        {
	            if(results != null)
	            {
	                results.close();
	            }
	        }
		return nodeRef;
	}
	
	private NodeRef getInformationNodeRefFromLucene(NodeRef igRoot)
	{
		 String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) +"cm:Information"+ LUCENE_END_PATH_PREFIX ;
			 
		return getNodeRefFomLucene(igRoot, query);
	}
	
	private NodeRef getNewsgroupsNodeRefFromLucene(NodeRef igRoot)
	{
		 String query = LUCENE_PATH_PREFIX + getPathFromSpaceRef(igRoot) +"cm:Newsgroups"+ LUCENE_END_PATH_PREFIX ;
			 
		return getNodeRefFomLucene(igRoot, query);
	}

	public List<ActivityCountDAO> getListOfActivityCount(NodeRef igRoot) {
		
		Long igDbNode = Long.valueOf(this.getNodeService().getProperty(igRoot, ContentModel.PROP_NODE_DBID).toString());
		
		return logService.getListOfActivityCountForInterestGroup(igDbNode);
	}



	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}



	/**
	 * @param logService the logService to set
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	

}
