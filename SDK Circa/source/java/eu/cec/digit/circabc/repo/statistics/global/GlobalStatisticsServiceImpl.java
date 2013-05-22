/**
 * 
 */
package eu.cec.digit.circabc.repo.statistics.global;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.TempFileProvider;
import org.alfresco.web.bean.repository.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import eu.cec.digit.circabc.error.CircabcRuntimeException;
import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.repo.log.LogCountResultDAO;
import eu.cec.digit.circabc.repo.statistics.ig.IgDescriptor;
import eu.cec.digit.circabc.service.compress.ZipService;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.profile.IGRootProfileManagerService;
import eu.cec.digit.circabc.service.profile.permissions.CategoryPermissions;
import eu.cec.digit.circabc.service.report.ReportDaoService;
import eu.cec.digit.circabc.service.statistics.global.GlobalStatisticsService;
import eu.cec.digit.circabc.service.struct.ManagementService;

/**
 * @author beaurpi
 *
 */
public class GlobalStatisticsServiceImpl implements GlobalStatisticsService {
	
	private ManagementService managementService;
	private NodeService nodeService;
	private SearchService searchService;
	private FileFolderService fileFolderService;
	private PersonService personService;
	private ReportDaoService reportDaoService;
	private TransactionService transactionService;
	private ContentService contentService;
	private IGRootProfileManagerService igRootProfileManagerService;
	private LogService logService;
	private ZipService zipService;
		
	private static String circabcDictionaryFolderName = "CircaBC";
	private static String statisticsDictionaryFolderName = "statistics";
	private static String reportsDictionaryFolderName = "reports";
	
	private static String jobConfigFileName = "statsJobConfig.properties";
	
	final static Log logger = LogFactory.getLog(GlobalStatisticsServiceImpl.class);
	
	private Integer getNumberOfCircabcHeaders() {
		return getListOfCircabcHeaders().size();
	}


	private List<NodeRef> getListOfCircabcHeaders() {
		
		NodeRef rootHeader = getManagementService().getRootCategoryHeader();
		List<ChildAssociationRef> lHeadersAssoc = nodeService.getChildAssocs(rootHeader);
		List<NodeRef> lHeadersNoderef = new ArrayList<NodeRef>();
		for(ChildAssociationRef c: lHeadersAssoc)
		{
			lHeadersNoderef.add(c.getChildRef());
		}
		
		return lHeadersNoderef;
	}
	
	private Integer getNumberOfCircabcCategories() {
		
		return getListOfCircabcCategories().size();
	}

	private List<NodeRef> getListOfCircabcCategories() {
		
		return getManagementService().getCategories();
	}
	
	private Integer getNumberOfCircabcInterestGroups() {
		
		return getListOfCircabcInterestGroups().size();
	}

	private List<NodeRef> getListOfCircabcInterestGroups() {
		
		List<NodeRef> lIg = new ArrayList<NodeRef>();
		for(NodeRef categ: getListOfCircabcCategories())
		{
			for(ChildAssociationRef c: nodeService.getChildAssocs(categ))
			{
				if(nodeService.hasAspect(c.getChildRef(), CircabcModel.ASPECT_IGROOT))
	        	{
					lIg.add(c.getChildRef());
	        	}
			}
			
		}
		return lIg;
	}
	
	private Integer getNumberOfUsers() {
		
		return personService.getAllPeople().size();
	}
		
	private  Map<NodeRef,Integer> getNumberOfCircabcInterestGroupsPerCategory() {

		Map<NodeRef,Integer> nbIgPerCateg = new HashMap<NodeRef,Integer>();
		Map<NodeRef,List<NodeRef>> lIgPerCateg = getListOfCircabcInterestGroupsPerCategory();
		for(Entry<NodeRef, List<NodeRef>> entry: lIgPerCateg.entrySet())
		{
			nbIgPerCateg.put(entry.getKey(), entry.getValue().size());
		}
		
		return nbIgPerCateg;
	}

	private  Map<NodeRef,List<NodeRef>> getListOfCircabcInterestGroupsPerCategory() {
		
		 Map<NodeRef,List<NodeRef>> lIgPerCateg = new HashMap<NodeRef,List<NodeRef>>();
		for(NodeRef categ: getListOfCircabcCategories())
		{
			lIgPerCateg.put(categ, getListOfCircabcInterestGroupsForCategory(categ));
			
		}
		return lIgPerCateg;
	}

	private List<NodeRef> getListOfCircabcInterestGroupsForCategory(NodeRef categNodeRef) {
		
		List<NodeRef> lIg = new ArrayList<NodeRef>();
		for(ChildAssociationRef c: nodeService.getChildAssocs(categNodeRef))
		{
			if(nodeService.hasAspect(c.getChildRef(), CircabcModel.ASPECT_IGROOT))
        	{
				lIg.add(c.getChildRef());
        	}
		}
		return lIg;
	}

	private Integer getNumberOfDocumentsInCircabc() {
		
        Integer nbDocs = 0 ;
                
        try {
			nbDocs = reportDaoService.queryDbForNumberOfDocuments();
		} catch (NumberFormatException e) {

			if(logger.isErrorEnabled())
			{
				logger.error("Error during getting number of documents in CIRCABC:", e);
			}

		} catch (SQLException e) {

			if(logger.isErrorEnabled())
			{
				logger.error("Error during SQL query for number of documents in CIRCABC:", e);
			}
		}
       
		return nbDocs;
	}
	
	public void prepareFolderRecipient() {

		NodeRef dicoNodeRef = getDicoNodeRef(); 

		NodeRef circabcFolderNodeRef = nodeService.getChildByName(dicoNodeRef,  ContentModel.ASSOC_CONTAINS, circabcDictionaryFolderName);
		
		FileInfo statisticsFolder =null;

		if(nodeService.getChildByName(circabcFolderNodeRef, ContentModel.ASSOC_CONTAINS, statisticsDictionaryFolderName) == null)
		{
			statisticsFolder = fileFolderService.create(circabcFolderNodeRef, statisticsDictionaryFolderName, ContentModel.TYPE_FOLDER);
			fileFolderService.create(statisticsFolder.getNodeRef(), reportsDictionaryFolderName , ContentModel.TYPE_FOLDER );
			fileFolderService.create(statisticsFolder.getNodeRef(), jobConfigFileName , ContentModel.TYPE_CONTENT );
		}
	}


	/**
	 * @return
	 */
	private NodeRef getDicoNodeRef() {
		String dicoPath = managementService.getAlfrescoDictionaryLucenePath();
		
		NodeRef dicoNodeRef;
				
		SearchParameters sp = new SearchParameters();
        sp.addStore(Repository.getStoreRef());
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        ResultSet results = null;
        sp.setQuery(dicoPath);
        
        try
        {
            results = searchService.query(sp);
            if(results.length()>0)
            {
            	 dicoNodeRef=results.getNodeRef(0);
            }
            else
            {
            	throw new CircabcRuntimeException("Impossible to retrieve Data dictionary space noderef");
			}
        }
        finally
        {
            if(results != null)
            {
                results.close();
            }
        }
		return dicoNodeRef;
	}

	public Map<String, Object> makeGlobalStats() {

		Map<String, Object> globalStats = new HashMap<String, Object>();
		
		Integer nbHeaders = getNumberOfCircabcHeaders();
		globalStats.put("numberOfCategoryHeaders", nbHeaders);
		
		Integer nbCategories = getNumberOfCircabcCategories();
		globalStats.put("numberOfCategories", nbCategories);
		
		
		Integer nbInterestGroups = getNumberOfCircabcInterestGroups();
		globalStats.put("numberOfIgs", nbInterestGroups);
				
		Map<NodeRef,Integer> nbIgPC =  getNumberOfCircabcInterestGroupsPerCategory();
		Map<String,Integer> lIgPC =  new HashMap<String, Integer>();
		
		for(Entry<NodeRef, Integer> entry: nbIgPC.entrySet())
		{
			lIgPC.put(getNameOrTitleOfNodeRefHelper(entry.getKey()), entry.getValue());
		}
		globalStats.put("numberOfIgsPerCategory", lIgPC);
		
		Integer nbUsers = getNumberOfUsers();
		globalStats.put("numberOfUsers", nbUsers);
		
		Integer nbDocs = getNumberOfDocumentsInCircabc();
		globalStats.put("numberOfDocuments", nbDocs);
		
		globalStats.put("listOfCircabcStructure", getCircabcStructure());
		
		globalStats.put("actionCountForYesterDay", getCountOfActionsForYesterday());
		
		return globalStats;
	}
	
	public NodeRef saveStatsToExcel(final NodeRef destinationFolder, Map<String, Object> lData)
	{

		Calendar cFile = new GregorianCalendar();
		cFile.setTime(new Date());
		final String fileName = "StatisticReport"+ cFile.get(Calendar.DAY_OF_MONTH)+"-"+(cFile.get(Calendar.MONTH)+1)+"-"+cFile.get(Calendar.YEAR)+"-"+cFile.get(Calendar.HOUR_OF_DAY)+"-"+cFile.get(Calendar.MINUTE)+"-"+cFile.get(Calendar.MILLISECOND)+".xls";
		
		if(nodeService.getType(destinationFolder).equals(ContentModel.TYPE_FOLDER))
		{
			 final HSSFWorkbook wb = builtExcelFromData(lData);
			 
			 RetryingTransactionHelper helper = transactionService.getRetryingTransactionHelper();
		     return helper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<NodeRef>()
	        {
	            public NodeRef execute() throws Throwable
	            {
	            	NodeRef node = nodeService.getChildByName(destinationFolder, ContentModel.ASSOC_CONTAINS, fileName);
	                	 
	            	 if(node == null)
	                 {
	            		 final FileInfo fileInfo = fileFolderService.create(destinationFolder, fileName, ContentModel.TYPE_CONTENT);
	                     final NodeRef createdNodeRef = fileInfo.getNodeRef();
	                     nodeService.setProperty(createdNodeRef, ContentModel.PROP_TITLE, "Stats report");
	                     File tempFile = TempFileProvider.createTempFile(fileName, "tmp");
	                     FileOutputStream fileWriter =null;
	                     try 
	                     {
	                    	 fileWriter = new FileOutputStream(tempFile) ;
	                    	 wb.write(fileWriter);
	                     }
	                     finally
	                     {
	                    	 if (fileWriter != null)
	                    	 {
	                    		 fileWriter.close();
	                    	 }
	                     }
	             		 
	                     // get a writer for the content and put the file
	                     final ContentWriter writer = contentService.getWriter(createdNodeRef, ContentModel.PROP_CONTENT, true);
	
	                     writer.setMimetype(MimetypeMap.MIMETYPE_EXCEL);
	                  
	                     writer.putContent(tempFile);
	                     
	                     boolean isDeleted = tempFile.delete();
	                     if (!isDeleted && logger.isWarnEnabled())
                         {
                      	   try {
								logger.warn ("Unable to delete file : " + tempFile.getCanonicalPath() ) ;
							} catch (IOException e) {
								logger.warn ("Unable to get getCanonicalPath for ." + tempFile.getPath() ,e ) ;
							}
                         }
	
	                     node=createdNodeRef;
	                 }

	                
	                return node; 
	            }
	        }, false, true);
	                
			 
		}
		else
		{
		  throw new CircabcRuntimeException("destination noderef:"+destinationFolder.toString()+" is not a valid folder");
		}
	}


	/**
	 * @param lData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook builtExcelFromData(Map<String, Object> lData) {
		final HSSFWorkbook wb = new HSSFWorkbook();
		 HSSFSheet sheetNumbers = wb.createSheet("numbers");
		 
		 Integer iRow = 0;
		 
		 for(Entry<String, Object> entry: lData.entrySet())
		 {
			 final String key = entry.getKey();
			 if(!key.matches("list.*"))
			 {
				 if(key.equals("numberOfIgsPerCategory"))
				 {
					 
					 HSSFRow row = sheetNumbers.createRow(iRow);
					 HSSFCell cell = row.createCell((short) 0);
					 cell.setCellValue(new HSSFRichTextString(key));
					 
					 iRow++; 
					 
					 Map<String,Integer> lnbs = (Map<String,Integer>) entry.getValue();
					 
					 for(Entry<String,Integer> item : lnbs.entrySet())
					 {
						 HSSFRow row2 = sheetNumbers.createRow(iRow);
						 HSSFCell cell2 = row2.createCell((short) 0);
						 cell2.setCellValue(new HSSFRichTextString(item.getKey()));
						 
						 HSSFCell cell3 = row2.createCell((short) 1);
						 cell3.setCellValue(item.getValue());
						 
						 iRow++; 
					 }
					 
				 }
				 else if (key.equals("actionCountForYesterDay")) {
					 
					 HSSFRow row = sheetNumbers.createRow(iRow);
					 HSSFCell cell = row.createCell((short) 0);
					 cell.setCellValue(new HSSFRichTextString(key));
					 
					 HSSFCell cell2 = row.createCell((short) 1);
					 
					 GregorianCalendar gc = new GregorianCalendar();
					 gc.setTime(new Date());
					 
					 cell2.setCellValue(new HSSFRichTextString(""+(gc.get(Calendar.DAY_OF_MONTH)-1)+"-"+(gc.get(Calendar.MONTH)+1)+"-"+gc.get(Calendar.YEAR)));
					 
					 iRow++; 
					 
					 for(LogCountResultDAO logEntry : (List<LogCountResultDAO>) entry.getValue())
					 {
						 HSSFRow row2 = sheetNumbers.createRow(iRow);
						 
						 HSSFCell cellDate = row2.createCell((short) 0);
						 cellDate.setCellValue(new HSSFRichTextString(logEntry.getHourPeriod()+"h-"+(logEntry.getHourPeriod()+1)+"h"));
						 
						 HSSFCell cellNumber = row2.createCell((short) 1);
						 cellNumber.setCellValue(logEntry.getNumberOfActions());
						 
						 iRow++;
					 }
					 
				 } 
				 else
				 {
					 HSSFRow row = sheetNumbers.createRow(iRow);
					 HSSFCell cell = row.createCell((short) 0);
					 cell.setCellValue(new HSSFRichTextString(key));
					 
					 HSSFCell cell2 = row.createCell((short) 1);
					 cell2.setCellValue((Integer) entry.getValue() );
				 }
				 
				 iRow++; 
			 } 
		 }
		 
		 iRow = 0;
		 short iCol = 0;
		 
		 HSSFSheet sheetLists = wb.createSheet("lists");
		 
		HSSFRow row = sheetLists.createRow(iRow);
		HSSFCell cell = row.createCell( iCol);
		cell.setCellValue(new HSSFRichTextString("category"));

		HSSFCell cell2 = row.createCell((short) (iCol +1));
		cell2.setCellValue(new HSSFRichTextString("interest group"));
		
		HSSFCell cell3 = row.createCell((short) (iCol +2));
		cell3.setCellValue(new HSSFRichTextString("leaders"));
		 
		iRow++; 
		 
		 for(CategoryDescriptor c: (List<CategoryDescriptor>) lData.get("listOfCircabcStructure") )
		 {
			 HSSFRow rowCateg = sheetLists.createRow(iRow);
				HSSFCell cCateg1 = rowCateg.createCell( iCol);
				cCateg1.setCellValue(new HSSFRichTextString( (c.getTitle().equals("") ? c.getName() : c.getTitle()) ));

				HSSFCell cCateg2 = rowCateg.createCell((short) (iCol +1));
				cCateg2.setCellValue(new HSSFRichTextString("-----"));
				
				HSSFCell cCateg3 = rowCateg.createCell((short) (iCol +2));
				cCateg3.setCellValue(new HSSFRichTextString( c.getListOfAdmins().toString() ));
				
				iRow++;
				
			 for(IgDescriptor ig: c.getListOfIgs())
			 {
				 HSSFRow row2 = sheetLists.createRow(iRow);
				HSSFCell c1 = row2.createCell( iCol);
				c1.setCellValue(new HSSFRichTextString( (c.getTitle().equals("") ? c.getName() : c.getTitle()) ));

				HSSFCell c2 = row2.createCell((short) (iCol +1));
				c2.setCellValue(new HSSFRichTextString( (ig.getTitle().equals("") ? ig.getName() : ig.getTitle()) ));
				
				HSSFCell c3 = row2.createCell((short) (iCol +2));
				c3.setCellValue(new HSSFRichTextString( ig.getSetOfLeaders().toString() ));
				
				iRow++;
			 }
			

			
		 }
		return wb;
	}
	
	/**
	 * GETTERS & SETTERS
	 */

	/**
	 * @return the managementService
	 */
	public ManagementService getManagementService() {
		return managementService;
	}


	/**
	 * @param managementService the managementService to set
	 */
	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
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


	/**
	 * @return the personService
	 */
	public PersonService getPersonService() {
		return personService;
	}


	/**
	 * @param personService the personService to set
	 */
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}


	/**
	 * @return the reportDaoService
	 */
	public ReportDaoService getReportDaoService() {
		return reportDaoService;
	}


	/**
	 * @param reportDaoService the reportDaoService to set
	 */
	public void setReportDaoService(ReportDaoService reportDaoService) {
		this.reportDaoService = reportDaoService;
	}
	
	/**
	 * title if exists, name if not
	 * @param node
	 * @return
	 */
	private String getNameOrTitleOfNodeRefHelper(NodeRef node)
	{
		String title = "";
		if(nodeService.getProperties(node).containsKey(ContentModel.PROP_TITLE)){
			title = nodeService.getProperty(node, ContentModel.PROP_TITLE).toString();
		}
		return (title.length() > 0 ? title : nodeService.getProperty(node, ContentModel.PROP_NAME).toString()); 
	}
	
	/**
	 * title if exists, name if not 
	 * @param list of nodes
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<String> getListOfNameFromListOfNodeRefHelper(List<NodeRef> nodes)
	{
		List<String> lNames = new ArrayList<String>();
		
		for(NodeRef n: nodes)
		{
			lNames.add(getNameOrTitleOfNodeRefHelper(n));			
		}
		
		return lNames;
	}


	/**
	 * @return the transactionService
	 */
	public TransactionService getTransactionService() {
		return transactionService;
	}


	/**
	 * @param transactionService the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}


	/**
	 * @return the contentService
	 */
	public ContentService getContentService() {
		return contentService;
	}


	/**
	 * @param contentService the contentService to set
	 */
	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}
	
	public NodeRef getReportSaveFolder()
	{
		NodeRef dicoNodeRef = getDicoNodeRef(); 

		NodeRef circabcFolderNodeRef = nodeService.getChildByName(dicoNodeRef,  ContentModel.ASSOC_CONTAINS, circabcDictionaryFolderName);
		
		NodeRef statFolderNodeRef = nodeService.getChildByName(circabcFolderNodeRef, ContentModel.ASSOC_CONTAINS, statisticsDictionaryFolderName);
		
		NodeRef reportNode =null;
		
		if(statFolderNodeRef != null)
		{
			reportNode = nodeService.getChildByName(statFolderNodeRef,  ContentModel.ASSOC_CONTAINS, reportsDictionaryFolderName);
		}
		
		return reportNode;
	}


	private Set<String> getListOfLeadersForInterestGroup(NodeRef igNodeRef) {
			
		Set<String> usernames = new HashSet<String>();
		/*
		 * Old name of leader group
		 */
		usernames.addAll(igRootProfileManagerService.getPersonInProfile(igNodeRef, IGRootProfileManagerService.Profiles.IGLEADER));
		/*
		 * potential names of leaders
		 */
		usernames.addAll(igRootProfileManagerService.getPersonInProfile(igNodeRef, IGRootProfileManagerService.Profiles.LEADER));
		usernames.addAll(igRootProfileManagerService.getPersonInProfile(igNodeRef, IGRootProfileManagerService.Profiles.LEADER000));
		
		Set<String> usernamesAndEmails = new HashSet<String>();
		Iterator<String> i = usernames.iterator();
		while(i.hasNext())
		{
			String uname = i.next();
			usernamesAndEmails.add(uname+":"+nodeService.getProperty(personService.getPerson(uname), ContentModel.PROP_EMAIL).toString());
		}
		
		return usernamesAndEmails;
	}


	/**
	 * @return the igRootProfileManagerService
	 */
	public IGRootProfileManagerService getIgRootProfileManagerService() {
		return igRootProfileManagerService;
	}


	/**
	 * @param igRootProfileManagerService the igRootProfileManagerService to set
	 */
	public void setIgRootProfileManagerService(
			IGRootProfileManagerService igRootProfileManagerService) {
		this.igRootProfileManagerService = igRootProfileManagerService;
	}


	private List<CategoryDescriptor> getCircabcStructure() {
		
		List<CategoryDescriptor> lCategs =  new ArrayList<CategoryDescriptor>();
		for(NodeRef nCateg : getListOfCircabcCategories())
		{
			CategoryDescriptor c = new CategoryDescriptor();
			c.setRef(nCateg);
			c.setName(nodeService.getProperty(nCateg, ContentModel.PROP_NAME).toString());
			c.setTitle((nodeService.getProperties(nCateg).containsKey(ContentModel.PROP_TITLE) ? nodeService.getProperty(nCateg, ContentModel.PROP_TITLE).toString() : ""));
			c.setListOfAdmins(getListOfAdminsForCategory(nCateg));
			
			List<IgDescriptor> lIgDesc = new ArrayList<IgDescriptor>();
			
			for(NodeRef nIg: getListOfCircabcInterestGroupsForCategory(nCateg))
			{
				IgDescriptor ig = new IgDescriptor();
				ig.setRef(nIg);
				ig.setName(nodeService.getProperty(nIg, ContentModel.PROP_NAME).toString());
				ig.setTitle((nodeService.getProperties(nIg).containsKey(ContentModel.PROP_TITLE) ? nodeService.getProperty(nIg, ContentModel.PROP_TITLE).toString() : ""));
				ig.setSetOfLeaders(getListOfLeadersForInterestGroup(nIg));
				
				lIgDesc.add(ig);
			}
			
			c.setListOfIgs(lIgDesc);
			lCategs.add(c);
		}
		
		return lCategs;
	}

	private Set<String> getListOfAdminsForCategory(NodeRef categoryNodeRef) {
		
		Set<String> usernames = new HashSet<String>();

		usernames.addAll(igRootProfileManagerService.getPersonInProfile(categoryNodeRef, CategoryPermissions.CIRCACATEGORYADMIN.toString()));

		Set<String> usernamesAndEmails = new HashSet<String>();
		Iterator<String> i = usernames.iterator();
		while(i.hasNext())
		{
			String uname = i.next();
			usernamesAndEmails.add(uname+":"+nodeService.getProperty(personService.getPerson(uname), ContentModel.PROP_EMAIL).toString());
		}
		
		return usernamesAndEmails;
	}

	public Date getLastLoginDateOfUser(String username) {
		

			return logService.getLastLoginDateOfUser(username);
	
	}

	public List<FileInfo> getListOfReportFiles()
	{
		NodeRef folderNodeRef = getReportSaveFolder();
		
		return fileFolderService.listFiles(folderNodeRef);
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

	private List<LogCountResultDAO> getCountOfActionsForYesterday() {
		
		
		List<LogCountResultDAO> listOfActionsCompleted =  new ArrayList<LogCountResultDAO>();
		List<LogCountResultDAO> listOfActionsFromDB = logService.getNumberOfActionsYesterdayPerHour();
		
		/*
		 * Fill new list to have all hours in a day
		 */
		for(int i=0; i<24; i++)
		{
			LogCountResultDAO currentCount = new LogCountResultDAO();
			currentCount.setHourPeriod(i);
			currentCount.setNumberOfActions(0);
			
			for(LogCountResultDAO lCount : listOfActionsFromDB)
			{
				if(lCount.getHourPeriod() == i)
				{
					currentCount.setNumberOfActions(lCount.getNumberOfActions());
				}
			}
			
			listOfActionsCompleted.add(currentCount);
		}
		
		return listOfActionsCompleted;
	}

	public void cleanAndZipPreviousReportFiles() {
		
		 GregorianCalendar gc = new GregorianCalendar();
		 gc.setTime(new Date());
		 final String archiveName = "archive-"+gc.get(Calendar.DAY_OF_MONTH)+"-"+(gc.get(Calendar.MONTH)+1)+"-"+gc.get(Calendar.YEAR)+"-"+gc.get(Calendar.MILLISECOND)+".zip";
		 final NodeRef reportFolder = getReportSaveFolder();
		 final File tempZipFile = TempFileProvider.createTempFile(archiveName,".tmp", TempFileProvider.getTempDir());
		 
		 List<FileInfo> listOfReports = fileFolderService.search(reportFolder, "*.xls", true, false, false);
		 List<NodeRef> listOfReportsNodeRefs = new ArrayList<NodeRef>();
		 
		 
		 for(FileInfo file: listOfReports)
		 {
			 listOfReportsNodeRefs.add(file.getNodeRef());

		 }
		 
		 zipService.addingFileIntoArchive(listOfReportsNodeRefs, tempZipFile);
		 
		 for(FileInfo file: listOfReports)
		 {

			 
			 fileFolderService.delete(file.getNodeRef());
			 
			 
		 }
		 
		 RetryingTransactionHelper helper = transactionService.getRetryingTransactionHelper();
	     helper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<NodeRef>()
        {
            public NodeRef execute() throws Throwable
            {
            	
            	 NodeRef node = nodeService.getChildByName( reportFolder , ContentModel.ASSOC_CONTAINS, archiveName);
                	
            	 if(node == null)
                 {
            		 final FileInfo fileInfo = fileFolderService.create(reportFolder, archiveName, ContentModel.TYPE_CONTENT);
                     final NodeRef createdNodeRef = fileInfo.getNodeRef();
                     
                     node=createdNodeRef;
                                                            		 
                     // get a writer for the content and put the file
                     final ContentWriter writer = contentService.getWriter(createdNodeRef, ContentModel.PROP_CONTENT, true);

                     writer.setMimetype(MimetypeMap.MIMETYPE_ZIP);
                  
                     writer.putContent(tempZipFile);
                 }
                
                return node; 
            }
        }, false, true);
	     
	     
	     boolean isDeleted = tempZipFile.delete();
	     
	     if (!isDeleted && logger.isWarnEnabled())
         {
      	   try {
				logger.warn ("Unable to delete file : " + tempZipFile.getCanonicalPath() ) ;
			} catch (IOException e) {
				logger.warn ("Unable to get getCanonicalPath for : " + tempZipFile.getPath() ,e ) ;
			}
         }

	}


	/**
	 * @return the zipService
	 */
	public ZipService getZipService() {
		return zipService;
	}


	/**
	 * @param zipService the zipService to set
	 */
	public void setZipService(ZipService zipService) {
		this.zipService = zipService;
	}


	public Boolean isReportSaveFolderExisting() {
		Boolean result = false;
		if(getReportSaveFolder() != null)
		{
			result = true;
		}
		return result;
	}
	
	
}
