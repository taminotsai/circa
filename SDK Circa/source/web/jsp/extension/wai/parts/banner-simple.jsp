<%--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |  		   http://ec.europa.eu/idabc/en/document/6523
    |
    +--%>
<%@ page buffer="32kb" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false"%>

<!-- Banner START -->
<h:form acceptcharset="UTF-8" id="FormBanner">

<div id="bannerBackground">
            <div class="bannerRight">
                <div id="title-en" class="title"></div>
                <!-- Use h1 here for homepage only -->
                <!-- <div id="imageBanner"><h1>European Commission</h1></div> -->
                <div id="linkBox">
                    <div id="linkBoxTools">
														<ul>
																<!-- search -->				<li class="first"><a accesskey="4" href="http://ec.europa.eu/geninfo/query/search_${currentLocale}.html" target="_blank">Search</a></li>
																<!-- contact -->			<li><a accesskey="7" href="mailto:DIGIT-CIRCABC-SUPPORT@ec.europa.eu"  target="_blank">Contact</a></li>
																<!-- legal notice -->		<li><a accesskey="8" id="legalNotice" href="http://ec.europa.eu/geninfo/legal_notices_${currentLocale}.htm"  target="_blank">Legal notice</a></li>
														</ul>
                    </div>
                </div>
            </div>
     	      <div id="langsFormContainer"></div>
     	      <div id="linkBoxArrow"></div>
  
                   <div id="langsSelector">
                      <script type="text/javascript" language="JavaScript">
							document.write('<h:selectOneMenu id="language" style="font-size:9px;" value="#{UserPreferencesBean.language}" onchange="document.forms[0].submit(); return true;"><f:selectItems value="#{UserPreferencesBean.languages}" /></h:selectOneMenu>');
						</script>
						<noscript id="langsel_noscript">
							<c:forEach var="langueJavaDisable" items="${UserPreferencesBean.languages}">
								<c:choose>
									<c:when test="${langueJavaDisable.value == currentLocale}">
										<span class="languagenolink" lang="${langueJavaDisable.value}" title="${langueJavaDisable.label}">${langueJavaDisable.value}</span>
									</c:when>
									<c:otherwise>
										<input type="submit" class="languageBanner" lang="${langueJavaDisable.value}" value="${langueJavaDisable.value}" name="FormPrincipal:language" alt="${langueJavaDisable.label}" />
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</noscript>
                  </div>
					<div id="linkBoxLanguage">
					</div>
					
        </div>
        
        <div id="path">
            <div>
                <ul>
                    <li class="first-child">
                        <a id="firstTab" href="http://europa.eu/index_${currentLocale}.htm">${cmsg.banner_europa}</a>
                    </li>
                    <li>
						<a href="http://ec.europa.eu/index_${currentLocale}.htm">${cmsg.banner_european_commission}</a>
					</li>
                    <li>
						<a href="https://circabc.europa.eu">CIRCABC</a>
					</li>
                </ul>
            </div>
        </div>

</h:form>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/extension/smoothbox.css" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/smoothbox.js" ></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/mootools-1.2.1-core-nc.js" ></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/progress.js" ></script>

<!-- Banner STOP -->