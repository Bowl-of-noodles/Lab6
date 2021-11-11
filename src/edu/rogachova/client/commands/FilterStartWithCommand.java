package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

public class FilterStartWithCommand implements Command
{
    private RequestSender requestSender;

    public FilterStartWithCommand(RequestSender sender){
        this.requestSender = sender;
    }
    @Override
    public String getName()
    {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription()
    {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    @Override
    public void execute(String input) throws Exception
    {
        try
        {
            if (input.isEmpty()) throw new WrongAmountOfArgumentsException();
            String name = input;

            Request<?> request = new Request<>(this.getName(), name);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Ошибка: " + result.message);
            }
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("введите подстроку для поиска");
        }
    }
}
