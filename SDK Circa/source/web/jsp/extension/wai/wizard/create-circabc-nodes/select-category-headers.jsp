<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/alfresco.tld" prefix="a" %>
<%@ taglib uri="/WEB-INF/repo.tld" prefix="r" %>
<%@ taglib uri="/WEB-INF/circabc.tld" prefix="circabc"%>

<%@ page isELIgnored="false"%>

<f:loadBundle basename="alfresco.extension.webclient" var="cmsg" />

<h:panelGrid columns="1">

	<h:dataTable value="#{WizardManager.bean.categoryListDataModel}" var="row"
                rowClasses="selectedItemsRow,selectedItemsRowAlt"
                styleClass="selectedItems" headerClass="selectedItemsHeader"
                cellspacing="0" cellpadding="4">

      <h:column>
         <f:facet name="header">
			<h:outputText value=" " />
	     </f:facet>
		<h:selectOneRadio value="#{WizardManager.bean.categoryHeaderId}"  onclick="dataTableSelectOneRadio(this);">
			<f:selectItem itemValue="#{row.id}" itemLabel=""/>
		</h:selectOneRadio>
	  </h:column>

      <h:column>
         <f:facet name="header">
            <h:outputText value="#{msg.name}" />
         </f:facet>
         <h:outputText value="#{row.name}" />
      </h:column>

      <h:column>
         <f:facet name="header">
            <h:outputText value="#{msg.description}" />
         </f:facet>
         <h:outputText value="#{row.description}" />
      </h:column>

   </h:dataTable>
</h:panelGrid>


<script type="text/javascript">

	if(!isSomethingChecked())
	{
		checkedFirst();
	}

	function isSomethingChecked()
	{
		var el = document.all;
        for (var i = 0; i < el.length; i++) 
        {
        	if (el[i].type == 'radio') {
        		
                if (el[i].checked )
                {
                	return true;
                }
            }
        }
        return false;
	}

	function checkedFirst()
	{
		var el = document.all;
        for (var i = 0; i < el.length; i++) 
        {
        	if (el[i].type == 'radio') 
        	{
                el[i].checked = true;
                return;
            }
        }
	}
    function dataTableSelectOneRadio(radio)
    {
        var id = radio.name.substring(radio.name.lastIndexOf(':'));
        var el = radio.form.elements;
        for (var i = 0; i < el.length; i++) {
            if (el[i].name.substring(el[i].name.lastIndexOf(':')) == id) {
                el[i].checked = false;
            }
        }
        radio.checked = true;
    }
</script>
