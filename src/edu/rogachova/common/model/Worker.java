package edu.rogachova.common.model;

import edu.rogachova.common.xml.ZonedDateTimeXmlAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.*;

@XmlRootElement(name="Worker")
@XmlAccessorType(XmlAccessType.FIELD)
public class Worker implements Comparable<Worker>, Serializable
{

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int salary; //Значение поля должно быть больше 0
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    private java.time.ZonedDateTime endDate; //Поле может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле может быть null
    private Organization organization; //Поле может быть null

    private static final Integer MIN_ID_VALUE = 0;
    private static final int MIN_SALARY = 1;


    public Worker(){

    }

    public Worker(Integer id, String name, Coordinates coordinates,ZonedDateTime creationDate, int salary, ZonedDateTime endDate, Position position, Status status, Organization organization){
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(creationDate);
        setSalary(salary);
        setEndDate(endDate);
        setPosition(position);
        setStatus(status);
        setOrganization(organization);
    }

    public Worker(Integer id, String name, Coordinates coordinates, int salary, ZonedDateTime endDate, Position position, Status status, Organization organization){
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setAutoCreationDate();
        setSalary(salary);
        setEndDate(endDate);
        setPosition(position);
        setStatus(status);
        setOrganization(organization);
    }

    public void setAutoCreationDate(){
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        ZoneId zoneId = ZoneId.of("GMT+03:00");

        creationDate = ZonedDateTime.of( localDate, localTime, zoneId );
    }

    public void setId(Integer id){
        if(id < MIN_ID_VALUE){
            throw new IllegalArgumentException("Значение id должно быть больше" + MIN_ID_VALUE);
        }
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        if(name == null){
            throw new IllegalArgumentException("Имя не может быть null");
        }
        if(name.length() == 0){
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        this.name = name;
    }

    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates)
    {
        if(coordinates == null){
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate){
        if(creationDate == null){
            throw new IllegalArgumentException("Дата создания не может быть null");
        }
        this.creationDate = creationDate;
    }

    public ZonedDateTime getCreationDate()
    {
        return creationDate;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        if(salary < MIN_SALARY){
            throw new IllegalArgumentException("Зарплата может быть не меньше" + MIN_SALARY);
        }
        this.salary = salary;
    }

    public ZonedDateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate)
    {
        this.endDate = endDate;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Organization getOrganization()
    {
        return organization;
    }

    public void setOrganization(Organization organization)
    {
        this.organization = organization;
    }

    @Override
    public String toString()
    {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", endDate=" + endDate +
                ", position=" + position +
                ", status=" + status +
                ", organization=" + organization +
                '}';
    }

    @Override
    public int compareTo(Worker o) {

        if(this.name.compareTo(o.name) != 0){
            return salary - o.salary;
        }
        return 0;
    }

}
