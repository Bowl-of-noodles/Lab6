package edu.rogachova.common.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="Location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable
{
    private long x;
    private int y;
    private long z;
    private String name; //Поле не может быть null

    public Location(){

    }

    public Location(long x, int y, long z, String name){
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }

    public long getX()
    {
        return x;
    }

    public void setX(long x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public long getZ()
    {
        return z;
    }

    public void setZ(long z)
    {
        this.z = z;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        if(name == null){
            throw new IllegalArgumentException("Название города не может быть null");
        }
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }
}
