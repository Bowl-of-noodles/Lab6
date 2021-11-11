package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;


public class ShowCommand implements Command
{
    private RequestSender requestSender;
    private String argument;

    public ShowCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "show";
    }

    @Override
    public String getDescription()
    {
        return "выводит все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(String input) throws Exception
    {
        this.argument = input;
        try{
            if(!argument.equals("")){
                throw new WrongAmountOfArgumentsException();
            }
            Request<?> request = new Request<>(this.getName(), null);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("У команды не должно быть аргументов");
        }
    }
}
