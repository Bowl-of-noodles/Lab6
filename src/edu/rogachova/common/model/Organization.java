package edu.rogachova.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="Organization")
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable
{
    private String fullName; //Длина строки не должна быть больше 1643, Поле не может быть null
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле может быть null

    private static final int MAX_LENGTH = 1643;

    public Organization(){

    }

    public Organization(String name, OrganizationType type, Address postalAddress){
        setFullName(name);
        setType(type);
        setPostalAddress(postalAddress);
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        if(fullName == null || fullName.length() > MAX_LENGTH ){
            throw new IllegalArgumentException("Длина строки должна быть ненулевой и не больше 1643");
        }
        this.fullName = fullName;
    }

    public OrganizationType getType()
    {
        return type;
    }

    public void setType(OrganizationType type)
    {
        this.type = type;
    }

    public Address getPostalAddress()
    {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress)
    {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString()
    {
        return "Organization{" +
                "fullName='" + fullName + '\'' +
                ", type=" + type +
                ", postalAddress=" + postalAddress +
                '}';
    }
}
