/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.action;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.web.app.Application;
import org.alfresco.web.bean.actions.BaseActionWizard.RecipientWrapper;
import org.alfresco.web.bean.actions.RunActionWizard;
import org.alfresco.web.bean.actions.handlers.BaseActionHandler;
import org.alfresco.web.bean.wizard.IWizardBean;

/**
 * The handler of the action
 *
 * @author atadian
 */
public class MainWithAttachActionHandler extends BaseActionHandler
{
    private static final long serialVersionUID = -8669657013575840329L;

	/** Where the JSP resides * */
    public static final String JSP_PATH = "/jsp/extension/action/mailwithattach.jsp";

    public String getJSPPath()
    {
        return JSP_PATH;
    }

    public void prepareForSave(	final Map<String, Serializable> actionProps,
            					final Map<String, Serializable> repoProps)
    {

        repoProps.put(MailActionExecuterWithAttach.PARAM_TO, actionProps
                .get(MailActionExecuterWithAttach.PARAM_TO));
        repoProps.put(MailActionExecuterWithAttach.PARAM_TO_MANY, actionProps
                .get(MailActionExecuterWithAttach.PARAM_TO_MANY));
        repoProps.put(MailActionExecuterWithAttach.PARAM_SUBJECT, actionProps
                .get(MailActionExecuterWithAttach.PARAM_SUBJECT));
        repoProps.put(MailActionExecuterWithAttach.PARAM_TEXT, actionProps
                .get(MailActionExecuterWithAttach.PARAM_TEXT));
        repoProps.put(MailActionExecuterWithAttach.PARAM_FROM, actionProps
                .get(MailActionExecuterWithAttach.PARAM_FROM));
        repoProps.put(MailActionExecuterWithAttach.PARAM_TEMPLATE, actionProps
                .get(MailActionExecuterWithAttach.PARAM_TEMPLATE));
        repoProps.put(MailActionExecuterWithAttach.PARAM_SENDDOCUMENT,
                actionProps
                        .get(MailActionExecuterWithAttach.PARAM_SENDDOCUMENT));
    }

    public void prepareForEdit(	final Map<String, Serializable> actionProps,
            					final Map<String, Serializable> repoProps)
    {
    }

    @SuppressWarnings("unchecked")
    public String generateSummary(final FacesContext context, final IWizardBean wizard,
            final Map<String, Serializable> actionProps)
    {
        // Retrieving the list of person to send the email
        // This is the only place where I can set the personList
        ArrayList<String> personList = null;
        final RunActionWizard wiz = (RunActionWizard) wizard;

        final List<RecipientWrapper> dataList = (List<RecipientWrapper>) wiz
                .getEmailRecipientsDataModel().getWrappedData();
        personList = new ArrayList<String>(dataList.size());
        for (final RecipientWrapper r : dataList)
        {
            personList.add(r.getAuthority());

        }
        actionProps.put(MailActionExecuterWithAttach.PARAM_TO_MANY, personList);

        // Now returning the Summary String
        return getFormattedMessage(context, actionProps);
    }

    /**
     * Generate the right message to show in the summary page
     *
     * @param context
     *            The FaceContext being used
     * @param actionProps
     *            properties of the action
     * @return the right message to show in the summary page
     */
    private String getFormattedMessage(final FacesContext context,
            final Map<String, Serializable> actionProps)
    {
        // The subject of the mail
        final String subject = String.valueOf(actionProps
                .get(MailActionExecuterWithAttach.PARAM_SUBJECT));

        // The message to indicate if the file is attached or not
        final Boolean isFileAttached = (Boolean) actionProps
                .get(MailActionExecuterWithAttach.PARAM_SENDDOCUMENT);

        String msg_id = "send_email_attach_without_attachment";
        if (isFileAttached.booleanValue())
        {
            msg_id = "send_email_attach_with_attachment";
        }

        return MessageFormat.format(Application.getMessage(context, msg_id),
                new Object[] { subject, });
    }
}