/**
 * CloseTaskRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;


/**
 * Request for closing a task (assignment or e-Signatory).
 */
public class CloseTaskRequest  implements java.io.Serializable {
    /* The task id for the task to be closed. */
    private int taskId;

    /* Optional comment when closing the task. */
    private java.lang.String comment;

    public CloseTaskRequest() {
    }

    public CloseTaskRequest(
           int taskId,
           java.lang.String comment) {
           this.taskId = taskId;
           this.comment = comment;
    }


    /**
     * Gets the taskId value for this CloseTaskRequest.
     * 
     * @return taskId   * The task id for the task to be closed.
     */
    public int getTaskId() {
        return taskId;
    }


    /**
     * Sets the taskId value for this CloseTaskRequest.
     * 
     * @param taskId   * The task id for the task to be closed.
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    /**
     * Gets the comment value for this CloseTaskRequest.
     * 
     * @return comment   * Optional comment when closing the task.
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this CloseTaskRequest.
     * 
     * @param comment   * Optional comment when closing the task.
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CloseTaskRequest)) return false;
        CloseTaskRequest other = (CloseTaskRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.taskId == other.getTaskId() &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment())));
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
        _hashCode += getTaskId();
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CloseTaskRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "CloseTaskRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taskId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "taskId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
