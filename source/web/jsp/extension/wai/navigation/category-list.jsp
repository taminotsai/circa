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

<%@ page isELIgnored="false"%>
<circabc:panel id="contentMainCatList" styleClass="contentMain">
	<circabc:panel id="categoryList" styleClass="panelCatListLabel">
		<circabc:categoryList value="#{CategoryHeadersBean.categoryHeaders}" chooseHeader="#{cmsg.browse_circabc_categories}" displayCategories="true"></circabc:categoryList>
	</circabc:panel>
</circabc:panel>