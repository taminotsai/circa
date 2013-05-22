/**
 * TaskStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class TaskStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TaskStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _DRAFT = "DRAFT";
    public static final java.lang.String _LAUNCHED = "LAUNCHED";
    public static final java.lang.String _ACTIVE = "ACTIVE";
    public static final java.lang.String _CLOSED = "CLOSED";
    public static final java.lang.String _BYPASSED = "BYPASSED";
    public static final java.lang.String _MANUAL = "MANUAL";
    public static final java.lang.String _DECLINED = "DECLINED";
    public static final java.lang.String _DELEGATED = "DELEGATED";
    public static final java.lang.String _RETURNED_TO_SENDER = "RETURNED_TO_SENDER";
    public static final TaskStatus DRAFT = new TaskStatus(_DRAFT);
    public static final TaskStatus LAUNCHED = new TaskStatus(_LAUNCHED);
    public static final TaskStatus ACTIVE = new TaskStatus(_ACTIVE);
    public static final TaskStatus CLOSED = new TaskStatus(_CLOSED);
    public static final TaskStatus BYPASSED = new TaskStatus(_BYPASSED);
    public static final TaskStatus MANUAL = new TaskStatus(_MANUAL);
    public static final TaskStatus DECLINED = new TaskStatus(_DECLINED);
    public static final TaskStatus DELEGATED = new TaskStatus(_DELEGATED);
    public static final TaskStatus RETURNED_TO_SENDER = new TaskStatus(_RETURNED_TO_SENDER);
    public java.lang.String getValue() { return _value_;}
    public static TaskStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TaskStatus enumeration = (TaskStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TaskStatus fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TaskStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", ">Task>status"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
