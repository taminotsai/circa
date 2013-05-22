/**
 * SignatoryTaskToAddCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class SignatoryTaskToAddCode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SignatoryTaskToAddCode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _RED = "RED";
    public static final java.lang.String _CONTRIB = "CONTRIB";
    public static final java.lang.String _VISA = "VISA";
    public static final java.lang.String _SIGN = "SIGN";
    public static final java.lang.String _EXP = "EXP";
    public static final SignatoryTaskToAddCode RED = new SignatoryTaskToAddCode(_RED);
    public static final SignatoryTaskToAddCode CONTRIB = new SignatoryTaskToAddCode(_CONTRIB);
    public static final SignatoryTaskToAddCode VISA = new SignatoryTaskToAddCode(_VISA);
    public static final SignatoryTaskToAddCode SIGN = new SignatoryTaskToAddCode(_SIGN);
    public static final SignatoryTaskToAddCode EXP = new SignatoryTaskToAddCode(_EXP);
    public java.lang.String getValue() { return _value_;}
    public static SignatoryTaskToAddCode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SignatoryTaskToAddCode enumeration = (SignatoryTaskToAddCode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SignatoryTaskToAddCode fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SignatoryTaskToAddCode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", ">SignatoryTaskToAdd>code"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
