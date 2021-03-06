/**
 * CurrentEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;


/**
 * Ancestor for CurrentExternalEntity and CurrentInternalEntity
 */
public abstract class CurrentEntity  implements java.io.Serializable {
    /* Internal repository ID that uniquely identifies this entity */
    private java.lang.String currentEntityId;

    public CurrentEntity() {
    }

    public CurrentEntity(
           java.lang.String currentEntityId) {
           this.currentEntityId = currentEntityId;
    }


    /**
     * Gets the currentEntityId value for this CurrentEntity.
     * 
     * @return currentEntityId   * Internal repository ID that uniquely identifies this entity
     */
    public java.lang.String getCurrentEntityId() {
        return currentEntityId;
    }


    /**
     * Sets the currentEntityId value for this CurrentEntity.
     * 
     * @param currentEntityId   * Internal repository ID that uniquely identifies this entity
     */
    public void setCurrentEntityId(java.lang.String currentEntityId) {
        this.currentEntityId = currentEntityId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurrentEntity)) return false;
        CurrentEntity other = (CurrentEntity) obj;
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
              this.currentEntityId.equals(other.getCurrentEntityId())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurrentEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "CurrentEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentEntityId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "currentEntityId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
