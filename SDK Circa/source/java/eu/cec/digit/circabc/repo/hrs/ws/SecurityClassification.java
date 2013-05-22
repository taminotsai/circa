/**
 * SecurityClassification.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class SecurityClassification implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SecurityClassification(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NORMAL = "NORMAL";
    public static final java.lang.String _LIMITED = "LIMITED";
    public static final java.lang.String _EU_RESTRAINED = "EU_RESTRAINED";
    public static final java.lang.String _EURA_RESTRICTED = "EURA_RESTRICTED";
    public static final SecurityClassification NORMAL = new SecurityClassification(_NORMAL);
    public static final SecurityClassification LIMITED = new SecurityClassification(_LIMITED);
    public static final SecurityClassification EU_RESTRAINED = new SecurityClassification(_EU_RESTRAINED);
    public static final SecurityClassification EURA_RESTRICTED = new SecurityClassification(_EURA_RESTRICTED);
    public java.lang.String getValue() { return _value_;}
    public static SecurityClassification fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SecurityClassification enumeration = (SecurityClassification)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SecurityClassification fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SecurityClassification.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "SecurityClassification"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
