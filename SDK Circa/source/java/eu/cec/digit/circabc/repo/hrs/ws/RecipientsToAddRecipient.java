/**
 * RecipientsToAddRecipient.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class RecipientsToAddRecipient  implements java.io.Serializable {
    /* Internal repository ID of the entity in the current
     *                                     base */
    private java.lang.String currentEntityId;

    /* Recipient code indicating if the recipient was
     *                                     directly addressed or not */
    private eu.cec.digit.circabc.repo.hrs.ws.RecipientCode code;

    public RecipientsToAddRecipient() {
    }

    public RecipientsToAddRecipient(
           java.lang.String currentEntityId,
           eu.cec.digit.circabc.repo.hrs.ws.RecipientCode code) {
           this.currentEntityId = currentEntityId;
           this.code = code;
    }


    /**
     * Gets the currentEntityId value for this RecipientsToAddRecipient.
     * 
     * @return currentEntityId   * Internal repository ID of the entity in the current
     *                                     base
     */
    public java.lang.String getCurrentEntityId() {
        return currentEntityId;
    }


    /**
     * Sets the currentEntityId value for this RecipientsToAddRecipient.
     * 
     * @param currentEntityId   * Internal repository ID of the entity in the current
     *                                     base
     */
    public void setCurrentEntityId(java.lang.String currentEntityId) {
        this.currentEntityId = currentEntityId;
    }


    /**
     * Gets the code value for this RecipientsToAddRecipient.
     * 
     * @return code   * Recipient code indicating if the recipient was
     *                                     directly addressed or not
     */
    public eu.cec.digit.circabc.repo.hrs.ws.RecipientCode getCode() {
        return code;
    }


    /**
     * Sets the code value for this RecipientsToAddRecipient.
     * 
     * @param code   * Recipient code indicating if the recipient was
     *                                     directly addressed or not
     */
    public void setCode(eu.cec.digit.circabc.repo.hrs.ws.RecipientCode code) {
        this.code = code;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecipientsToAddRecipient)) return false;
        RecipientsToAddRecipient other = (RecipientsToAddRecipient) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.currentEntityId==null && other.getCurrentEntityId()==null) || 
             (this.currentEntityId!=null &&
              this.currentEntityId.equals(other.getCurrentEntityId()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCurrentEntityId() != null) {
            _hashCode += getCurrentEntityId().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecipientsToAddRecipient.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", ">RecipientsToAdd>recipient"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentEntityId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "currentEntityId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "RecipientCode"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
