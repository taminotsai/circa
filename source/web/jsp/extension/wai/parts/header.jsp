<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ page import="javax.faces.context.FacesContext" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page isELIgnored="false"%>

<c:set var="currentLocale" value="<%=FacesContext.getCurrentInstance().getViewRoot().getLocale()%>" />
<c:set var="currentContextPath" value="<%=request.getContextPath()%>" />

<f:loadBundle basename="alfresco.extension.messages.circabc-version" var="rev" />

<!-- Banner START -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="${currentLocale}" lang="${currentLocale}">
	<head>
		<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
		<meta name="Reference" content="COMM/SITE_NAME" />
		<meta name="Title" content="${currentTitle}" />
		<meta name="Creator" content="CREATOR" />
		<meta name="Language" content="<c:out value="${currentLocale}" />" />
		<meta name="Type" content="Numeric code given in the list of document types" />
		<meta name="Classification" content="Numeric code from the alphabetical classification list common to all the institutions" />
		<meta name="Keywords" content="one or more of the commission specific keywords + europa-kommissionen, europ�iske union, eu" />
		<meta name="Description" content="Europa-Kommissionen - Content should be a sentence or two that describes the content of the page" />
		<title>CIRCABC - <c:out value="${currentTitle}" /></title>
		<!-- <link rel="stylesheet" href="${currentContextPath}/css/extension/d-commission.css" type="text/css" />  -->
		<link rel="stylesheet" href="${currentContextPath}/css/extension/circabc.css?rev=<h:outputText value="#{rev['version.revision']}" escape="false" />" type="text/css" />
		<link rel="stylesheet" href="${currentContextPath}/css/extension/commission.css" type="text/css" />
		<link rel="icon" type="image/gif" href="${currentContextPath}/images/favicon.gif"/>
		<link rel="shortcut icon" type="image/x-icon" href="${currentContextPath}/images/favicon.ico" />
		<script src="${currentContextPath}/scripts/extension/language.js" type="text/javascript"></script>
		<%
			String browser = request.getHeader("User-Agent");
			if(browser.indexOf("MSIE") > 0)
			{

				%>
					<link rel="stylesheet" href="${currentContextPath}/css/extension/circabc-ie.css" type="text/css" />
				<%

			}
		%>
	</head>
<body>
<a name="top"></a>
