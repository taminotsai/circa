/**
 * DocumentSignedBy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class DocumentSignedBy  implements java.io.Serializable {
    /* The user that signed */
    private eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser user;

    /* The date when the user signed */
    private java.util.Date on;

    public DocumentSignedBy() {
    }

    public DocumentSignedBy(
           eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser user,
           java.util.Date on) {
           this.user = user;
           this.on = on;
    }


    /**
     * Gets the user value for this DocumentSignedBy.
     * 
     * @return user   * The user that signed
     */
    public eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser getUser() {
        return user;
    }


    /**
     * Sets the user value for this DocumentSignedBy.
     * 
     * @param user   * The user that signed
     */
    public void setUser(eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser user) {
        this.user = user;
    }


    /**
     * Gets the on value for this DocumentSignedBy.
     * 
     * @return on   * The date when the user signed
     */
    public java.util.Date getOn() {
        return on;
    }


    /**
     * Sets the on value for this DocumentSignedBy.
     * 
     * @param on   * The date when the user signed
     */
    public void setOn(java.util.Date on) {
        this.on = on;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentSignedBy)) return false;
        DocumentSignedBy other = (DocumentSignedBy) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser()))) &&
            ((this.on==null && other.getOn()==null) || 
             (this.on!=null &&
              this.on.equals(other.getOn())));
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
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        if (getOn() != null) {
            _hashCode += getOn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentSignedBy.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", ">Document>signedBy"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "user"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "WorkflowUser"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("on");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "on"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
