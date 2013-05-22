/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.profile;

/**
 * Enumeration of the service for Circa
 * Only 4 services are present so far:
 * CIRCABC   - Administration Root level
 * CATEGORY  - Administration of Category Level
 * LIBRARY   - Space and Content Access
 * DIRECTORY - CircaUser and Profile Access to Administration
 * VISIBILITY - Visibility to a CIRCABC
 * NEWSGROUP - NewsGroup Access
 * SURVEY - Survey Access
 * INFORMATION - Information Access
 * EVENT - Event Access
 *
 * @author Philippe Dubois
 * @author Stephane Clinckart
 */
public enum CircabcServices {
   CIRCABC, CATEGORY_HEADER, CATEGORY, INTEREST_GROUP, LIBRARY, DIRECTORY, APPLICANT, VISIBILITY, NEWSGROUP, SURVEY, EVENT, INFORMATION
}

