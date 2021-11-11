package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

public class ClearCommand implements Command
{
    private RequestSender requestSender;
    private String argument;

    public ClearCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "clear";
    }

    @Override
    public String getDescription()
    {
        return "очистить коллекцию";
    }

    @Override
    public void execute(String input) throws Exception
    {
        this.argument = input;

        try{
            if(!argument.equals("")){
                throw new WrongAmountOfArgumentsException();
            }
            Request<?> request = new Request<String>(this.getName(), null);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка:" + result.message);
            }
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("У команды не должно быть аргументов");
        }
    }
}
