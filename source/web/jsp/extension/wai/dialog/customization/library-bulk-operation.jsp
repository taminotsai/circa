<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a"%>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r"%>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>
<%@ taglib uri="/WEB-INF/tomahawk.tld" prefix="t"%>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.9.1.min.js" ></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-ui-1.10.2.custom.min.js" ></script>

<script type="text/javascript">
    
    $(function () {
    	$('.checkall').click(function () {
    		$(this).parents('fieldset:eq(0)').find(':checkbox').attr('checked', this.checked);
    	});
       	
    	$('#FormPrincipal\\:deleteNotice').hide();
    	$('#FormPrincipal\\:selectBulkOperation').change(function() {
    		if ($(this).val() == "delete") {
    			$('#FormPrincipal\\:deleteNotice').show();			
    		} else {
    			$('#FormPrincipal\\:deleteNotice').hide();
    		}
    	});
    });
    
</script>

<circabc:panel id="contentMainFormManageIgBreadcrumbs" styleClass="contentMainForm">

	<f:verbatim><br /></f:verbatim>	
	<h:outputText value="#{cmsg.library_bulk_operation_dialog_bulk_operation}" />	
	<f:verbatim>&nbsp;</f:verbatim>
    <h:selectOneMenu value="#{DialogManager.bean.selectedOperation}" id="selectBulkOperation" >  
        <f:selectItems value="#{DialogManager.bean.operations}" />  
    </h:selectOneMenu>  
	<f:verbatim>&nbsp;&nbsp;</f:verbatim>
	<h:outputText id="deleteNotice" value="#{cmsg.library_bulk_operation_dialog_delete_notice}" />

	<f:verbatim><br /><br /><br /></f:verbatim>

	<circabc:panel id="panelLibraryContainer" label="#{cmsg.library_panel_container_label}" styleClass="panelLibraryContainerGlobal" styleClassLabel="panelLibraryContainerLabel" tooltip="#{cmsg.library_panel_container_tooltip}">
		<f:verbatim>
		<fieldset>
		</f:verbatim>
			<h:selectBooleanCheckbox style="margin-left:7px" id="selectAllContainerCheckBox" styleClass="checkall"/>
			<h:outputText value="#{cmsg.library_bulk_operation_dialog_select_all}" /> 
			<t:dataTable rowIndexVar="row" value="#{DialogManager.bean.containers}" var="entry">
				<f:facet name="header">
					<t:selectManyCheckbox id="containers" layout="spread" value="#{DialogManager.bean.selectedContainers}"  required="false">
						<f:selectItems value="#{DialogManager.bean.allContainers}"/>
					</t:selectManyCheckbox>
				</f:facet>					
				<h:column id="manage-applicants-list-icon">							
					<t:checkbox for="containers" index="#{row}"></t:checkbox>
				</h:column>
				<h:column id="small-icon">
					<circabc:actionLink id="ig-home-wnew-col-name-icon" value="#{entry.bestTitle}" href="#{entry.url}" target="new" image="/images/icons/#{entry.smallIcon}.gif" showLink="false" tooltip="#{cmsg.igroot_home_whats_new_link_icon_tooltip}" />
				</h:column>				
			</t:dataTable>
		<f:verbatim>	
		</fieldset>	
		</f:verbatim>
	</circabc:panel>	
	
	<circabc:panel id="topOfPageAnchorLibHomeContainer" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorLibHomeContainer-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorLibHomeContainer-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>	

	<circabc:panel id="panelLibraryContent" label="#{cmsg.library_panel_content_label}" styleClass="panelLibraryContentGlobal" styleClassLabel="panelLibraryContentLabel" tooltip="#{cmsg.library_panel_content_tooltip}">
		<f:verbatim>
		<fieldset>	
		</f:verbatim>		
			<h:selectBooleanCheckbox style="margin-left:7px" id="selectAllContentCheckBox" styleClass="checkall"/>
			<h:outputText value="#{cmsg.library_bulk_operation_dialog_select_all}" /> 
			<t:dataTable rowIndexVar="row" value="#{DialogManager.bean.contents}" var="entry">
				<f:facet name="header">
					<t:selectManyCheckbox id="contents" layout="spread" value="#{DialogManager.bean.selectedContents}"  required="false">
						<f:selectItems value="#{DialogManager.bean.allContents}"/>
					</t:selectManyCheckbox>
				</f:facet>
				<h:column id="manage-applicants-list-icon">							
					<t:checkbox for="contents" index="#{row}"></t:checkbox>
				</h:column>
				<h:column id="small-icon">					
					<circabc:actionLink id="ig-home-wnew-col-name-icon" value="#{entry.bestTitle}" href="#{entry.url}" target="new" image="#{entry.fileType16}" showLink="false" tooltip="#{cmsg.igroot_home_whats_new_link_icon_tooltip}" />
				</h:column>		
			</t:dataTable>
		<f:verbatim>	
		</fieldset>	
		</f:verbatim>	
	</circabc:panel>
	
	<circabc:panel id="topOfPageAnchorLibHomeContent" styleClass="topOfPageAnchor" rendered="#{NavigationBean.currentIGRoot.library != null}" >
		<%-- Display the "back to top icon first and display the text after." --%>
		<circabc:actionLink id="topOfPageAnchorLibHomeContent-1" value="#{cmsg.top_page}&nbsp;" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="true" />
		<circabc:actionLink id="topOfPageAnchorLibHomeContent-2" value="#{cmsg.top_page}" tooltip="#{cmsg.back_top_page}" href="#top" styleClass="topOfPageAnchor" showLink="false" image="#{currentContextPath}/images/extension/top_ns.gif" />
	</circabc:panel>
	
	
<%-- 	<circabc:panel id="panelLibraryContent" label="#{cmsg.library_panel_content_label}" styleClass="panelLibraryContentGlobal" styleClassLabel="panelLibraryContentLabel" tooltip="#{cmsg.library_panel_content_tooltip}">
		<h:selectManyCheckbox id="contents" layout="pageDirection" value="#{DialogManager.bean.selectedContents}"  required="false">
	       <f:selectItems value="#{DialogManager.bean.allContents}"/>
		</h:selectManyCheckbox>	
	</circabc:panel> --%>

</circabc:panel>