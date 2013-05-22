/**
 * Procedure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;


/**
 * A procedure
 */
public class Procedure  implements java.io.Serializable {
    /* Internal ID of the procedure */
    private int id;

    /* English name */
    private java.lang.String englishName;

    /* French name */
    private java.lang.String frenchName;

    public Procedure() {
    }

    public Procedure(
           int id,
           java.lang.String englishName,
           java.lang.String frenchName) {
           this.id = id;
           this.englishName = englishName;
           this.frenchName = frenchName;
    }


    /**
     * Gets the id value for this Procedure.
     * 
     * @return id   * Internal ID of the procedure
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this Procedure.
     * 
     * @param id   * Internal ID of the procedure
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the englishName value for this Procedure.
     * 
     * @return englishName   * English name
     */
    public java.lang.String getEnglishName() {
        return englishName;
    }


    /**
     * Sets the englishName value for this Procedure.
     * 
     * @param englishName   * English name
     */
    public void setEnglishName(java.lang.String englishName) {
        this.englishName = englishName;
    }


    /**
     * Gets the frenchName value for this Procedure.
     * 
     * @return frenchName   * French name
     */
    public java.lang.String getFrenchName() {
        return frenchName;
    }


    /**
     * Sets the frenchName value for this Procedure.
     * 
     * @param frenchName   * French name
     */
    public void setFrenchName(java.lang.String frenchName) {
        this.frenchName = frenchName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Procedure)) return false;
        Procedure other = (Procedure) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.id == other.getId() &&
            ((this.englishName==null && other.getEnglishName()==null) || 
             (this.englishName!=null &&
              this.englishName.equals(other.getEnglishName()))) &&
            ((this.frenchName==null && other.getFrenchName()==null) || 
             (this.frenchName!=null &&
              this.frenchName.equals(other.getFrenchName())));
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
        _hashCode += getId();
        if (getEnglishName() != null) {
            _hashCode += getEnglishName().hashCode();
        }
        if (getFrenchName() != null) {
            _hashCode += getFrenchName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Procedure.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "Procedure"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("englishName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "englishName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frenchName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "frenchName"));
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
