/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.api;

import org.alfresco.service.namespace.QName;

import eu.cec.digit.circabc.business.api.content.AttachementBusinessSrv;
import eu.cec.digit.circabc.business.api.content.CociContentBusinessSrv;
import eu.cec.digit.circabc.business.api.content.ContentBusinessSrv;
import eu.cec.digit.circabc.business.api.link.LinksBusinessSrv;
import eu.cec.digit.circabc.business.api.mail.MailMeContentBusinessSrv;
import eu.cec.digit.circabc.business.api.nav.NavigationBusinessSrv;
import eu.cec.digit.circabc.business.api.props.PropertiesBusinessSrv;
import eu.cec.digit.circabc.business.api.security.PermissionsBusinessSrv;
import eu.cec.digit.circabc.business.api.security.ProfileBusinessSrv;
import eu.cec.digit.circabc.business.api.space.DossierBusinessSrv;
import eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv;
import eu.cec.digit.circabc.business.api.user.RemoteUserBusinessSrv;
import eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv;
import eu.cec.digit.circabc.service.namespace.CircabcNameSpaceService;


/**
 * This interface represents the registry of public Business Services.
 *
 * @author yanick Pignot
 */
public interface BusinessRegistry
{
	public static final String BUSINESS_REGISTRY = "businessRegistry";

	/** @see eu.cec.digit.circabc.business.api.mail.MailMeContentBusinessSrv identifier */
	public static final QName MAIL_ME_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "MailMeContentBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.content.ContentBusinessSrv identifier */
	public static final QName CONTENT_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ContentBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.content.CociContentBusinessSrv identifier */
	public static final QName COCI_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "CociContentBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.nav.NavigationBusinessSrv identifier */
	public static final QName NAVIGATION_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "NavigationBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.space.SpaceBusinessSrv identifier */
	public static final QName SPACE_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "SpaceBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.props.PropertiesBusinessSrv identifier */
	public static final QName PROPERTIES_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "PropertiesBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.space.DossierBusinessSrv identifier */
	public static final QName DOSSIER_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "DossierBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.link.LinksBusinessSrv identifier */
	public static final QName LINKS_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "LinksBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.permission.ProfileBusinessSrv identifier */
	public static final QName PROFILE_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "ProfileBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.permission.PermissionsBusinessSrv identifier */
	public static final QName PERMISSIONS_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "PermissionsBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.user.UserDetailsBusinessSrv identifier */
	public static final QName USER_DETAILS_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "UserDetailsBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.user.RemoteUserBusinessSrv identifier */
	public static final QName REMOTE_USER_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "RemoteUserBusinessSrv");
	/** @see eu.cec.digit.circabc.business.api.content.AttachementBusinessSrv identifier */
	public static final QName ATTACHEMENT_BUSINESS_SERVICE = QName.createQName(CircabcNameSpaceService.CEC_DIGIT_URI, "AttachementBusinessSrv");


    /**
     * @return Mail Me Content Business Srv
     */
    public MailMeContentBusinessSrv getMailMeContentBusinessSrv();

    /**
     * @return Content Business Srv
     */
    public ContentBusinessSrv getContentBusinessSrv();

    /**
     * @return Coci Content Business Srv
     */
    public CociContentBusinessSrv getCociContentBusinessSrv();

    /**
     * @return Navigation Business Srv
     */
    public NavigationBusinessSrv getNavigationBusinessSrv();

    /**
     * @return Space Business Srv
     */
    public SpaceBusinessSrv getSpaceBusinessSrv();

    /**
     * @return Properties Business Srv
     */
    public PropertiesBusinessSrv getPropertiesBusinessSrv();

    /**
     * @return Dossier Business Srv
     */
    public DossierBusinessSrv getDossierBusinessSrv();

    /**
     * @return Links Business Srv
     */
    public LinksBusinessSrv getLinksBusinessSrv();

    /**
     * @return Permissions Business Srv
     */
    public PermissionsBusinessSrv getPermissionsBusinessSrv();

    /**
     * @return Profile Business Srv
     */
    public ProfileBusinessSrv getProfileBusinessSrv();

    /**
     * @return	User Details Business Srv
     */
    public UserDetailsBusinessSrv getUserDetailsBusinessSrv();

    /**
     * @return	Remote User Business Srv
     */
    public RemoteUserBusinessSrv getRemoteUserBusinessSrv();

    /**
     * @return	Attachement Business Srv
     */
    public AttachementBusinessSrv getAttachementBusinessSrv();
}
