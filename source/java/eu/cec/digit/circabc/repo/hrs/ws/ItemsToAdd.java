/**
 * ItemsToAdd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;

public class ItemsToAdd  implements java.io.Serializable {
    /* Scanned attachments */
    private eu.cec.digit.circabc.repo.hrs.ws.ScannedItemToAdd scannedItem;

    /* Uploaded attachments */
    private eu.cec.digit.circabc.repo.hrs.ws.UploadedItemToAdd uploadedItem;

    public ItemsToAdd() {
    }

    public ItemsToAdd(
           eu.cec.digit.circabc.repo.hrs.ws.ScannedItemToAdd scannedItem,
           eu.cec.digit.circabc.repo.hrs.ws.UploadedItemToAdd uploadedItem) {
           this.scannedItem = scannedItem;
           this.uploadedItem = uploadedItem;
    }


    /**
     * Gets the scannedItem value for this ItemsToAdd.
     * 
     * @return scannedItem   * Scanned attachments
     */
    public eu.cec.digit.circabc.repo.hrs.ws.ScannedItemToAdd getScannedItem() {
        return scannedItem;
    }


    /**
     * Sets the scannedItem value for this ItemsToAdd.
     * 
     * @param scannedItem   * Scanned attachments
     */
    public void setScannedItem(eu.cec.digit.circabc.repo.hrs.ws.ScannedItemToAdd scannedItem) {
        this.scannedItem = scannedItem;
    }


    /**
     * Gets the uploadedItem value for this ItemsToAdd.
     * 
     * @return uploadedItem   * Uploaded attachments
     */
    public eu.cec.digit.circabc.repo.hrs.ws.UploadedItemToAdd getUploadedItem() {
        return uploadedItem;
    }


    /**
     * Sets the uploadedItem value for this ItemsToAdd.
     * 
     * @param uploadedItem   * Uploaded attachments
     */
    public void setUploadedItem(eu.cec.digit.circabc.repo.hrs.ws.UploadedItemToAdd uploadedItem) {
        this.uploadedItem = uploadedItem;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemsToAdd)) return false;
        ItemsToAdd other = (ItemsToAdd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.scannedItem==null && other.getScannedItem()==null) || 
             (this.scannedItem!=null &&
              this.scannedItem.equals(other.getScannedItem()))) &&
            ((this.uploadedItem==null && other.getUploadedItem()==null) || 
             (this.uploadedItem!=null &&
              this.uploadedItem.equals(other.getUploadedItem())));
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
        if (getScannedItem() != null) {
            _hashCode += getScannedItem().hashCode();
        }
        if (getUploadedItem() != null) {
            _hashCode += getUploadedItem().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ItemsToAdd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "ItemsToAdd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scannedItem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "scannedItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "ScannedItemToAdd"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uploadedItem");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "uploadedItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "UploadedItemToAdd"));
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
