package edu.rogachova.common.model;

import java.io.Serializable;

public class Coordinates implements Serializable
{
    private Float x; //Поле не может быть null
    private int y; //Максимальное значение поля: 628

    private static final int Y_MAX_VALUE = 628;

    public Coordinates(){

    }

    public Coordinates(Float x, int y){
        setX(x);
        setY(y);
    }

    public Float getX()
    {
        return x;
    }

    public void setX(Float x)
    {
        if(x == null){
            throw new IllegalArgumentException("Значение х не может быть null");
        }
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        if(y > Y_MAX_VALUE){
            throw new IllegalArgumentException("Значение у не может превышать 628");
        }
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
