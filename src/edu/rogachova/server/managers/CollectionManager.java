package edu.rogachova.server.managers;

import edu.rogachova.common.DataManager;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.AccessException;
import java.time.*;
import java.util.*;

public class CollectionManager extends DataManager
{

    public HashMap<Long, Worker> employees = new HashMap<>();
    private String type;
    private Date initDate;

    protected Integer nextId;
    protected long nextKey;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) throws AccessException{
        this.fileManager = fileManager;
        employees = fileManager.readFile();
        nextKey = getNextKey();
        setNextId();
        initDate = new Date();
        type = employees.getClass().getSimpleName();
    }

    private long getNextKey(){
        return (long)(employees.size()+1);
    }

    private void setNextId(){
        List<Integer> ids = new ArrayList<>();
        for (Map.Entry<Long, Worker> w : employees.entrySet()) {
            ids.add(w.getValue().getId());
        }

        this.nextId = Collections.max(ids) + 1;
    }

    public Integer getNextId(){
        return nextId++;
    }

    public Date getInitDate(){
        return initDate;
    }

    public String getCollType(){
        return type;
    }

    public int getSize(){
        return employees.size();
    }


    @Override
    public void save(){
        fileManager.writeToFile(employees);
    }

    @Override
    public CommandResult insert(Request<?> request){
        Worker worker;
        try{
            worker = (Worker) request.entity;
        }
        catch (Exception exc){
            return new CommandResult(ResultStatus.ERROR, "В контроллер передан аргумент другого типа");
        }
        worker.setId(getNextId());
        employees.put(nextKey, worker);
        nextKey++;
        return new CommandResult(ResultStatus.OK, "Новый элемент добавлен");
    }

    public Worker getById(long id){
        return employees.get(id);
    }

    @Override
    public CommandResult update(Request<?> request){
        Worker worker;
        try{
            worker = (Worker) request.entity;
            Long id = (long)worker.getId();
            worker.setId(getNextId());
            employees.put(id, worker);
            return new CommandResult(ResultStatus.OK, "Изменения добавлены");
        }catch(Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult clear(Request<?> request){
        employees.clear();
        return new CommandResult(ResultStatus.OK, "Все элементы из коллекции удалены");
    }


    @Override
    public CommandResult removeByKey(Request<?> request){
        try{
            Long id = (Long)request.entity;
            employees.remove(id);
            return new CommandResult(ResultStatus.OK, "Элемент удален из коллекции");
        }catch(Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult show(Request<?> request){
        if(employees.size() == 0){
            return new CommandResult(ResultStatus.OK, "В коллекции нет элементов");
        }
        else{
            StringBuffer message = new StringBuffer();
            for(long key : employees.keySet()){
                Worker w = employees.get(key);
                message.append(w.toString() + "\n");
            }
            return new CommandResult(ResultStatus.OK, message.toString());
        }
    }

    @Override
    public CommandResult removeGreater(Request<?> request){
        Worker worker;
        try{
            worker = (Worker)request.entity;
            HashMap <Long, Worker> W = new HashMap<>();
            int count = 0;
            for (Map.Entry<Long, Worker> w : employees.entrySet()) {
                if(w.getValue().compareTo(worker) > 0){
                    employees.remove(w.getKey());
                    count += 1;
                }
            }
            return new CommandResult(ResultStatus.OK, "Удалено "+count+" объектов");
        }catch(Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult removeGreaterKey(Request<?> request){
        try
        {
            Long usersKey = (Long) request.entity;
            ArrayList<Long> keys = new ArrayList<Long>();
            for (long collKey : employees.keySet())
            {
                keys.add(collKey);
            }

            int count = 0;
            for (long key : keys)
            {
                Worker w = employees.get(key);
                if (w.getId() > usersKey)
                {
                    employees.remove(key);
                    count++;
                }
            }

            return new CommandResult(ResultStatus.OK, String.format("Из коллекции успешно удалено %d элементов.", count));
        }catch(Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult info(Request<?> request){
        String type = "HashMap<Long, Route>";

        return new CommandResult(ResultStatus.OK,
                "Информация о коллекции: " + "\n" +
                        "Тип коллекции: " + getCollType() + "\n" +
                        "Дата инициализации: " + getInitDate() + "\n" +
                        "Количество элементов в коллекции: " + employees.size()
        );
    }

    public CommandResult replaceLowerKey(Request<?> request)
    {
        try{
            Worker worker = (Worker)request.entity;
            Long key = (long)worker.getId();
            Worker workerToCompare = employees.get(key);
            worker.setId(getNextId());
            if (workerToCompare.getId() - worker.getId() < 0)
            {
                employees.put(key, worker);
            }
            return new CommandResult(ResultStatus.OK, "");
        } catch (Exception e)
        {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult countLessSalary(Request<?> request){
        try
        {
            int userS = (Integer) request.entity;
            int count = 0;
            for (Map.Entry<Long, Worker> w : employees.entrySet())
            {
                if (w.getValue().getSalary() < userS)
                {
                    count += 1;
                }
            }
            return new CommandResult(ResultStatus.OK, "Работников с меньшей зарплатой - " + count);
        }catch(Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult filterStartWith(Request<?> request){
        try{
            String name = (String)request.entity;
            StringBuffer message = new StringBuffer();
            for(long key : employees.keySet()){
                Worker w = employees.get(key);
                if(w.getName().startsWith(name)){
                    message.append(w.toString() + "\n");
                }
            }
            return new CommandResult(ResultStatus.OK, message.toString());
        }catch(Exception exception) {
            return new CommandResult(ResultStatus.ERROR, "Передан аргумент другого типа");
        }
    }

    @Override
    public CommandResult printDSCEnd(Request<?> request){
        ArrayList<ZonedDateTime> arr = new ArrayList<>();
        for(long key : employees.keySet()){
            if(employees.get(key).getEndDate()!= null){
                ZonedDateTime endDate = employees.get(key).getEndDate();
                arr.add(endDate);
            }
        }

        Collections.sort(arr, Collections.reverseOrder());
        StringBuffer message = new StringBuffer();
        for(ZonedDateTime e :arr){
            message.append(e + "\n");
        }
        return new CommandResult(ResultStatus.OK, message.toString());
    }

}
