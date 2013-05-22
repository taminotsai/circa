/**
 * ExternalEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;


/**
 * An entity which is external to the Commission
 */
public class ExternalEntity  extends eu.cec.digit.circabc.repo.hrs.ws.Entity  implements java.io.Serializable {
    /* External organization */
    private eu.cec.digit.circabc.repo.hrs.ws.ExternalOrganization externalOrganization;

    /* External person */
    private eu.cec.digit.circabc.repo.hrs.ws.ExternalPerson externalPerson;

    public ExternalEntity() {
    }

    public ExternalEntity(
           java.lang.String entityId,
           eu.cec.digit.circabc.repo.hrs.ws.ExternalOrganization externalOrganization,
           eu.cec.digit.circabc.repo.hrs.ws.ExternalPerson externalPerson) {
        super(
            entityId);
        this.externalOrganization = externalOrganization;
        this.externalPerson = externalPerson;
    }


    /**
     * Gets the externalOrganization value for this ExternalEntity.
     * 
     * @return externalOrganization   * External organization
     */
    public eu.cec.digit.circabc.repo.hrs.ws.ExternalOrganization getExternalOrganization() {
        return externalOrganization;
    }


    /**
     * Sets the externalOrganization value for this ExternalEntity.
     * 
     * @param externalOrganization   * External organization
     */
    public void setExternalOrganization(eu.cec.digit.circabc.repo.hrs.ws.ExternalOrganization externalOrganization) {
        this.externalOrganization = externalOrganization;
    }


    /**
     * Gets the externalPerson value for this ExternalEntity.
     * 
     * @return externalPerson   * External person
     */
    public eu.cec.digit.circabc.repo.hrs.ws.ExternalPerson getExternalPerson() {
        return externalPerson;
    }


    /**
     * Sets the externalPerson value for this ExternalEntity.
     * 
     * @param externalPerson   * External person
     */
    public void setExternalPerson(eu.cec.digit.circabc.repo.hrs.ws.ExternalPerson externalPerson) {
        this.externalPerson = externalPerson;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExternalEntity)) return false;
        ExternalEntity other = (ExternalEntity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.externalOrganization==null && other.getExternalOrganization()==null) || 
             (this.externalOrganization!=null &&
              this.externalOrganization.equals(other.getExternalOrganization()))) &&
            ((this.externalPerson==null && other.getExternalPerson()==null) || 
             (this.externalPerson!=null &&
              this.externalPerson.equals(other.getExternalPerson())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getExternalOrganization() != null) {
            _hashCode += getExternalOrganization().hashCode();
        }
        if (getExternalPerson() != null) {
            _hashCode += getExternalPerson().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExternalEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "ExternalEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalOrganization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "externalOrganization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "ExternalOrganization"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalPerson");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "externalPerson"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "ExternalPerson"));
        elemField.setMinOccurs(0);
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
