/**
 * DocumentCreationSummary.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class DocumentCreationSummary  implements java.io.Serializable {
    /* Repository id of the document */
    private java.lang.String documentId;

    /* Date at which the document has been encoded in the repository */
    private java.util.Date encodingDate;

    /* Internal document number */
    private java.lang.String saveNumber;

    public DocumentCreationSummary() {
    }

    public DocumentCreationSummary(
           java.lang.String documentId,
           java.util.Date encodingDate,
           java.lang.String saveNumber) {
           this.documentId = documentId;
           this.encodingDate = encodingDate;
           this.saveNumber = saveNumber;
    }


    /**
     * Gets the documentId value for this DocumentCreationSummary.
     * 
     * @return documentId   * Repository id of the document
     */
    public java.lang.String getDocumentId() {
        return documentId;
    }


    /**
     * Sets the documentId value for this DocumentCreationSummary.
     * 
     * @param documentId   * Repository id of the document
     */
    public void setDocumentId(java.lang.String documentId) {
        this.documentId = documentId;
    }


    /**
     * Gets the encodingDate value for this DocumentCreationSummary.
     * 
     * @return encodingDate   * Date at which the document has been encoded in the repository
     */
    public java.util.Date getEncodingDate() {
        return encodingDate;
    }


    /**
     * Sets the encodingDate value for this DocumentCreationSummary.
     * 
     * @param encodingDate   * Date at which the document has been encoded in the repository
     */
    public void setEncodingDate(java.util.Date encodingDate) {
        this.encodingDate = encodingDate;
    }


    /**
     * Gets the saveNumber value for this DocumentCreationSummary.
     * 
     * @return saveNumber   * Internal document number
     */
    public java.lang.String getSaveNumber() {
        return saveNumber;
    }


    /**
     * Sets the saveNumber value for this DocumentCreationSummary.
     * 
     * @param saveNumber   * Internal document number
     */
    public void setSaveNumber(java.lang.String saveNumber) {
        this.saveNumber = saveNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocumentCreationSummary)) return false;
        DocumentCreationSummary other = (DocumentCreationSummary) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documentId==null && other.getDocumentId()==null) || 
             (this.documentId!=null &&
              this.documentId.equals(other.getDocumentId()))) &&
            ((this.encodingDate==null && other.getEncodingDate()==null) || 
             (this.encodingDate!=null &&
              this.encodingDate.equals(other.getEncodingDate()))) &&
            ((this.saveNumber==null && other.getSaveNumber()==null) || 
             (this.saveNumber!=null &&
              this.saveNumber.equals(other.getSaveNumber())));
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
        if (getDocumentId() != null) {
            _hashCode += getDocumentId().hashCode();
        }
        if (getEncodingDate() != null) {
            _hashCode += getEncodingDate().hashCode();
        }
        if (getSaveNumber() != null) {
            _hashCode += getSaveNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocumentCreationSummary.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "DocumentCreationSummary"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "documentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encodingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "encodingDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saveNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "saveNumber"));
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
