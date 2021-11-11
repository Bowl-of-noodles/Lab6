package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.CollectionIsEmptyException;
import edu.rogachova.common.exceptions.WorkerNotFoundException;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

public class RemoveKeyCommand implements Command
{
    private RequestSender requestSender;
    private String argument;

    public RemoveKeyCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "remove_key";
    }

    @Override
    public String getDescription()
    {
        return "удаляет элемент из коллекции по его ключу";
    }

    @Override
    public void execute(String input) throws Exception
    {
        this.argument = input;
        try {
            if (argument.isEmpty()) throw new  WrongAmountOfArgumentsException();
            Long id = Long.parseLong(argument);

            Request<?> request = new Request<Long>(this.getName(), id);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }

        }catch(WrongAmountOfArgumentsException e){
            System.out.println("У команды должен быть один аргумент - key работника");
        }
    }
}
