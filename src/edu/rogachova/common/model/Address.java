package edu.rogachova.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="Address")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address implements Serializable
{
    private String street; //Строка не может быть пустой, Поле может быть null
    private Location town; //Поле может быть null

    public Address(){

    }

    public Address(String street, Location town){
        setStreet(street);
        setTown(town);
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        if(street.length() == 0){
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        this.street = street;
    }

    public Location getTown()
    {
        return town;
    }

    public void setTown(Location town)
    {
        this.town = town;
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "street='" + street + '\'' +
                ", town=" + town +
                '}';
    }
}
