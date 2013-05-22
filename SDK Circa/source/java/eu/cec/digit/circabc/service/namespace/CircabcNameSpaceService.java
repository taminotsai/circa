/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.namespace;

import org.alfresco.service.PublicService;
import org.alfresco.service.namespace.NamespacePrefixResolver;

/**
 * Namespace Service.
 *
 * The Namespace Service provides access to and definition of namespace
 * URIs and Prefixes.
 *
 * @author Clinckart Stephane
 */
@PublicService
public interface CircabcNameSpaceService extends NamespacePrefixResolver
{
    /** Default CEC DIGIT URI */
    static final String CEC_DIGIT_URI = "http://eu.cec.digit";
}
