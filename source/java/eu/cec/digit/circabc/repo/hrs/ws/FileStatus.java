/**
 * FileStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class FileStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FileStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _INACTIVE = "INACTIVE";
    public static final java.lang.String _ACTIVE = "ACTIVE";
    public static final java.lang.String _CLOSED = "CLOSED";
    public static final java.lang.String _TRANSFERRED = "TRANSFERRED";
    public static final java.lang.String _DESTROYED = "DESTROYED";
    public static final FileStatus INACTIVE = new FileStatus(_INACTIVE);
    public static final FileStatus ACTIVE = new FileStatus(_ACTIVE);
    public static final FileStatus CLOSED = new FileStatus(_CLOSED);
    public static final FileStatus TRANSFERRED = new FileStatus(_TRANSFERRED);
    public static final FileStatus DESTROYED = new FileStatus(_DESTROYED);
    public java.lang.String getValue() { return _value_;}
    public static FileStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FileStatus enumeration = (FileStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FileStatus fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FileStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "FileStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
