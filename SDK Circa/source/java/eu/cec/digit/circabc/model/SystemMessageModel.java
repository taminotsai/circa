package eu.cec.digit.circabc.model;

import org.alfresco.service.namespace.QName;

public interface SystemMessageModel extends BaseCircabcModel 
{
	public static final String CIRCABC_SYSTEMMESSAGE_MODEL_1_0_URI = CIRCABC_NAMESPACE + "/model/systemmessage/1.0";
	public static final String CIRCABC_SYSTEMMESSAGE_MODEL_PREFIX = "sm";
	
	public static final QName TYPE_SYSTEMMESSAGE = QName.createQName(CIRCABC_SYSTEMMESSAGE_MODEL_1_0_URI, "systemMessage");
	
	public static final QName PROP_IS_SYSTEMMESSAGE_ENABLED = QName.createQName(CIRCABC_SYSTEMMESSAGE_MODEL_1_0_URI, "isSytemMessageEnabled");
	public static final QName PROP_SYSTEMMESSAGE_TEXT = QName.createQName(CIRCABC_SYSTEMMESSAGE_MODEL_1_0_URI, "systemMessageText");
}
