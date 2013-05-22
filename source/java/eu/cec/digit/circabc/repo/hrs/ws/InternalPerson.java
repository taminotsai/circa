/**
 * InternalPerson.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.cec.digit.circabc.repo.hrs.ws;


/**
 * An internal person
 */
public class InternalPerson  implements java.io.Serializable {
    /* The person's last name */
    private java.lang.String lastName;

    /* First name(s) of the person */
    private java.lang.String firstName;

    /* Internal user name. This is user name is also the ECAS user
     * name of the person */
    private java.lang.String userName;

    /* Floor on which the person is located */
    private java.lang.String floor;

    /* Building where the person is located */
    private java.lang.String building;

    /* Room in the building where the person is located */
    private java.lang.String room;

    public InternalPerson() {
    }

    public InternalPerson(
           java.lang.String lastName,
           java.lang.String firstName,
           java.lang.String userName,
           java.lang.String floor,
           java.lang.String building,
           java.lang.String room) {
           this.lastName = lastName;
           this.firstName = firstName;
           this.userName = userName;
           this.floor = floor;
           this.building = building;
           this.room = room;
    }


    /**
     * Gets the lastName value for this InternalPerson.
     * 
     * @return lastName   * The person's last name
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this InternalPerson.
     * 
     * @param lastName   * The person's last name
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the firstName value for this InternalPerson.
     * 
     * @return firstName   * First name(s) of the person
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this InternalPerson.
     * 
     * @param firstName   * First name(s) of the person
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the userName value for this InternalPerson.
     * 
     * @return userName   * Internal user name. This is user name is also the ECAS user
     * name of the person
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this InternalPerson.
     * 
     * @param userName   * Internal user name. This is user name is also the ECAS user
     * name of the person
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the floor value for this InternalPerson.
     * 
     * @return floor   * Floor on which the person is located
     */
    public java.lang.String getFloor() {
        return floor;
    }


    /**
     * Sets the floor value for this InternalPerson.
     * 
     * @param floor   * Floor on which the person is located
     */
    public void setFloor(java.lang.String floor) {
        this.floor = floor;
    }


    /**
     * Gets the building value for this InternalPerson.
     * 
     * @return building   * Building where the person is located
     */
    public java.lang.String getBuilding() {
        return building;
    }


    /**
     * Sets the building value for this InternalPerson.
     * 
     * @param building   * Building where the person is located
     */
    public void setBuilding(java.lang.String building) {
        this.building = building;
    }


    /**
     * Gets the room value for this InternalPerson.
     * 
     * @return room   * Room in the building where the person is located
     */
    public java.lang.String getRoom() {
        return room;
    }


    /**
     * Sets the room value for this InternalPerson.
     * 
     * @param room   * Room in the building where the person is located
     */
    public void setRoom(java.lang.String room) {
        this.room = room;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InternalPerson)) return false;
        InternalPerson other = (InternalPerson) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.floor==null && other.getFloor()==null) || 
             (this.floor!=null &&
              this.floor.equals(other.getFloor()))) &&
            ((this.building==null && other.getBuilding()==null) || 
             (this.building!=null &&
              this.building.equals(other.getBuilding()))) &&
            ((this.room==null && other.getRoom()==null) || 
             (this.room!=null &&
              this.room.equals(other.getRoom())));
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
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getFloor() != null) {
            _hashCode += getFloor().hashCode();
        }
        if (getBuilding() != null) {
            _hashCode += getBuilding().hashCode();
        }
        if (getRoom() != null) {
            _hashCode += getRoom().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InternalPerson.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "InternalPerson"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("floor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "floor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("building");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "building"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("room");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ec.europa.eu/sg/hrs/types", "room"));
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