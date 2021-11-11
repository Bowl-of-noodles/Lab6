package edu.rogachova.client.managers;

import edu.rogachova.common.exceptions.NotInBoundsException;
import edu.rogachova.common.model.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Asker
{
    private Scanner scanner;
    private static final int Y_MAX_VALUE = 628;

    public Asker(Scanner scanner){
        this.scanner = scanner;
    }


    public void askName(Worker worker){
        System.out.println("Введите имя работника");
        String name;
        boolean isSet = false;
        while(!isSet){
            try{
                name = scanner.nextLine().trim();
                if(name.equals("")){
                    throw new IllegalArgumentException();
                }
                worker.setName(name);
                isSet = true;
            }catch(IllegalArgumentException e){
                System.out.println("Имя не может быть пустым. Введите значение еще раз");
            }catch(NoSuchElementException e){
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
    }

    public void askCoordinates(Worker worker){
        System.out.println("Введите координту x (дробое число)");
        Coordinates coordinates = new Coordinates();
        boolean isSet = false;
        while(!isSet)
        {
            try
            {
                Float x = Float.parseFloat(scanner.nextLine());
                coordinates.setX(x);
                isSet = true;
            } catch (NumberFormatException e)
            {
                System.out.println("Координата x - дробное число. Введите значение еще раз");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Введите координату у (целое число)");
        while(!isSet){
            try{
            int y = Integer.parseInt(scanner.nextLine());
            if(y > Y_MAX_VALUE){
                throw new IllegalArgumentException();
            }
            coordinates.setY(y);
            worker.setCoordinates(coordinates);
            isSet = true;
            } catch(NumberFormatException e){
                System.out.println("Координата y - целое число. Введите значение еще раз");
            }catch(IllegalArgumentException e){
                System.out.println("Значение y не может превышать 628. Введите значение еще раз");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
    }

    public void askSalary(Worker worker){
        System.out.println("Введите зарплату работника(целое значение)");
        boolean isSet = false;
        int salary;
        while(!isSet){
            try{
                salary = Integer.parseInt(scanner.nextLine().trim());
                if(salary <= 0){
                    throw new IllegalArgumentException();
                }
                worker.setSalary(salary);
                isSet = true;
            }catch(NumberFormatException e){
                System.out.println("Введите в качестве зарплаты целое число");
            }catch(IllegalArgumentException e){
                System.out.println("Зарплата не может быть неположительной. Введите еще раз целое значение");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
    }

    public void askEndDate(Worker worker){
        System.out.println("Введите конечную дату в формате dd-MM-yyyy HH:mm:ss. К примеру, 13-10-2021 22:10:10. Поле может быть не заполнено, в таком случае нажмите enter");
        boolean isSet = false;
        while(!isSet){
            try
            {
                String input = scanner.nextLine().trim();
                if(input.equals("-")){
                    worker.setEndDate(null);
                    isSet = true;
                }
                else{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    ZonedDateTime endDate = ZonedDateTime.parse(input, formatter.withZone(ZoneId.of("Europe/Moscow")));
                    worker.setEndDate(endDate);
                    isSet = true;
                }
            }catch(DateTimeParseException e){
                System.out.println("Введите еще раз конечную дату в формате yyyy-MM-dd HH:mm:ss a z К примеру, 13-10-2021 10:10:10");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
    }

    public void askPosition(Worker  worker){
        System.out.println("Укажите должность сотрудника. Существуют такие: ");
        System.out.println(Position.nameList()+ "\n" +"Если не хотите заполнять поле, введите -");
        boolean isSet = false;
        Position position;
        while(!isSet){
            try{
                String input = scanner.nextLine().trim();
                if(input.equals("-")){
                    worker.setPosition(null);
                    isSet = true;
                }
                else{
                    position = Position.valueOf(input.toUpperCase());
                    worker.setPosition(position);
                    isSet = true;
                }
            }catch (IllegalArgumentException exception) {
                System.out.println("Должности нет в списке");
                System.out.println(Position.nameList());
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
    }

    public void askStatus(Worker worker){
        System.out.println("Укажите статус сотрудника. Существуют такие: ");
        System.out.println(Status.nameList()+ "\n" +"Если не хотите заполнять поле, введите -");
        boolean isSet = false;
        Status status;
        while(!isSet){
            try{
                String input = scanner.nextLine().trim();
                if(input.equals("-")){
                    worker.setStatus(null);
                    isSet = true;
                }
                else{
                    status = Status.valueOf(input.toUpperCase());
                    worker.setStatus(status);
                    isSet = true;
                }
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }catch (IllegalArgumentException exception) {
                System.out.println("Статуса нет в списке");
                System.out.println(Status.nameList());
            }
        }
    }

    public void askOrganization(Worker worker){
        System.out.println("Введите название организации.Если не хотите заполнять поле организации, введите -");
        try
        {
            String name = scanner.nextLine().trim();
            if (name.equals("-"))
            {
                worker.setOrganization(null);
            } else
            {
                Organization organization = askOrganizationDetails(name);
                worker.setOrganization(organization);
            }
        }catch(Exception e){
            System.out.println("Произошла ошибка: " + e.getMessage());
        }

    }

    private Organization askOrganizationDetails(String name){
        Organization organization = new Organization();
        boolean isSet = false;
        while(!isSet)
        {
            try
            {
                if(name.length() > 1643 ){
                    throw new IllegalArgumentException();
                }
                else{
                    organization.setFullName(name);
                    isSet = true;
                }
            } catch (IllegalArgumentException e)
            {
                System.out.println("Имя не может быть пустым. Длина строки не должна превышать 1643. Введите значение еще раз");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Введите тип компании. Если не хотите заполнять поле организации, введите - . Существуют такие типы:");
        System.out.println(OrganizationType.nameList());
        while (!isSet)
        {
            try
            {
                String input = scanner.nextLine().trim();
                if (input.equals("-"))
                {
                    organization.setType(null);
                    isSet = true;
                } else
                {
                    organization.setType(OrganizationType.valueOf(input.toUpperCase()));
                    isSet = true;
                }
            } catch (IllegalArgumentException exception)
            {
                System.out.println("Типа нет в списке");
                System.out.println(OrganizationType.nameList());
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Вы хотите заполнить поле адреса? если да, то поставьте +, если нет -");
        while(!isSet)
        {
            try
            {
                String input = scanner.nextLine().trim();
                if (input.equals("+"))
                {
                    Address address = askAddress();
                    organization.setPostalAddress(address);
                    isSet = true;
                } else if (input.equals("-"))
                {
                    organization.setPostalAddress(null);
                    isSet = true;
                } else
                {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e)
            {
                System.out.println("если хотите заполнить адрес, то поставьте +, если нет -");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return organization;
    }

    private Address askAddress(){
        Address address = new Address();
        Location location = new Location();
        boolean isSet = false;
        System.out.println("Введите название улицы");
        while(!isSet){
            try
            {
                String input = scanner.nextLine().trim();
                if (input.equals("")){
                    throw new IllegalArgumentException();
                }
                else{
                    address.setStreet(input);
                    isSet = true;
                }
            }catch(IllegalArgumentException e){
                System.out.println("Строка не может быть пустой");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Вы хотите заполнить поле город? если да, то поставьте +, если нет -");
        while(!isSet){
            try
            {
                String input = scanner.nextLine().trim();
                if (input.equals("+")) {
                    Location town = askTown();
                    address.setTown(town);
                    isSet = true;
                } else if (input.equals("-")) {
                    address.setTown(null);
                    isSet = true;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e){
                System.out.println("если хотите заполнить город, то поставьте +, если нет -");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return address;
    }

    private Location askTown(){
        Location town = new Location();
        boolean isSet = false;
        String input;
        System.out.println("Введите координату x (целое число)");
        while(!isSet){
            try
            {
                input = scanner.nextLine().trim();
                town.setX(Long.parseLong(input));
                isSet = true;
            }catch(NumberFormatException e){
                System.out.println("Координата x - целое число");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Введите координату y (целое число)");
        while(!isSet){
            try
            {
                input = scanner.nextLine().trim();
                town.setY(Integer.parseInt(input));
                isSet = true;
            }catch(NumberFormatException e){
                System.out.println("Координата y - целое число");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Введите координату z (целое число)");
        while(!isSet){
            try
            {
                input = scanner.nextLine().trim();
                town.setZ(Long.parseLong(input));
                isSet = true;
            }catch(NumberFormatException e){
                System.out.println("Координата z - целое число");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        isSet = false;
        System.out.println("Введите название города");
        while(!isSet){
            try
            {
                input = scanner.nextLine().trim();
                if(input.equals("")){
                    throw new IllegalArgumentException();
                }
                else{
                    town.setName(input);
                    isSet = true;
                }
            }catch(IllegalArgumentException e){
                System.out.println("Название города не может быть null");
            }catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            }

        }

        return town;
    }

    public Worker createWorker(){
        Worker usersWorker = new Worker();
        //usersWorker.setId(id);
        askName(usersWorker);
        askCoordinates(usersWorker);
        usersWorker.setAutoCreationDate();
        askSalary(usersWorker);
        askEndDate(usersWorker);
        askPosition(usersWorker);
        askStatus(usersWorker);
        askOrganization(usersWorker);
        System.out.println("Все данные собраны успешно");
        return usersWorker;
    }

    public Worker askUpdateWorker(Integer id){
        Worker worker = new Worker();
        worker.setId(id);
        if(askQuestion("Хотите изменить имя работника")) askName(worker);
        if(askQuestion("Хотите изменить коопдинаты?")) askCoordinates(worker);
        if(askQuestion("Хотите изменить зарплату?")) askSalary(worker);
        if(askQuestion("Хотите изменить end дату?")) askEndDate(worker);
        if(askQuestion("Хотите изменить должность?")) askPosition(worker);
        if(askQuestion("Хотите изменить статус?")) askStatus(worker);
        if(askQuestion("Хотите изменить организацию?")) askOrganization(worker);
        return worker;
    }

    public boolean askQuestion(String question){
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                System.out.println(finalQuestion);
                answer = scanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                System.out.println("Непредвиденная ошибка");
                System.exit(0);
            } catch (NotInBoundsException exception) {
                System.out.println("Ответ должен быть представлен как '+' или '-'");
            }
        }
        return answer.equals("+");
    }

}
