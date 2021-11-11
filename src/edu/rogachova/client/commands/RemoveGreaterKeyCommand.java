package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

public class RemoveGreaterKeyCommand implements Command
{
    private RequestSender requestSender;

    public RemoveGreaterKeyCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "remove_greater_key";
    }

    @Override
    public String getDescription()
    {
        return "удалить из коллекции все элементы, ключ которых превышает заданный";
    }

    @Override
    public void execute(String input)
    {
        long key;
        try{
            if(input.isEmpty()) throw new WrongAmountOfArgumentsException();
            key = Long.parseLong(input);
            Request<?> request = new Request<Long>(this.getName(), key);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        } catch (NumberFormatException e){
            System.out.println("Аргумент команды - key - целое число");
        } catch(WrongAmountOfArgumentsException e){
            System.out.println("У команды должен быть один аргумент - key");
        }
    }
}
