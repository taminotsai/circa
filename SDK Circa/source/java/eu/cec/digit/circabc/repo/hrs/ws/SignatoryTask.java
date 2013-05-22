/**
 * SignatoryTask.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;


/**
 * A task used in a Signatory workflow
 */
public class SignatoryTask  extends eu.cec.digit.circabc.repo.hrs.ws.Task  implements java.io.Serializable {
    /* The task's code */
    private eu.cec.digit.circabc.repo.hrs.ws.SignatoryTaskCode code;

    public SignatoryTask() {
    }

    public SignatoryTask(
           int taskId,
           int workflowId,
           eu.cec.digit.circabc.repo.hrs.ws.TaskStatus status,
           eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser assignee,
           eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser sender,
           java.util.Date sentDate,
           eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser closer,
           java.util.Date closeDate,
           eu.cec.digit.circabc.repo.hrs.ws.WorkflowUser reader,
           java.util.Date readDate,
           java.util.Date deadlineDate,
           java.lang.String instructions,
           java.lang.String comments,
           boolean critical,
           java.lang.Boolean deleted,
           eu.cec.digit.circabc.repo.hrs.ws.SignatoryTaskCode code) {
        super(
            taskId,
            workflowId,
            status,
            assignee,
            sender,
            sentDate,
            closer,
            closeDate,
            reader,
            readDate,
            deadlineDate,
            instructions,
            comments,
            critical,
            deleted);
        this.code = code;
    }


    /**
     * Gets the code value for this SignatoryTask.
     * 
     * @return code   * The task's code
     */
    public eu.cec.digit.circabc.repo.hrs.ws.SignatoryTaskCode getCode() {
        return code;
    }


    /**
     * Sets the code value for this SignatoryTask.
     * 
     * @param code   * The task's code
     */
    public void setCode(eu.cec.digit.circabc.repo.hrs.ws.SignatoryTaskCode code) {
        this.code = code;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SignatoryTask)) return false;
        SignatoryTask other = (SignatoryTask) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
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
        int _hashCode = super.hashCode();
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SignatoryTask.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "SignatoryTask"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", ">SignatoryTask>code"));
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
