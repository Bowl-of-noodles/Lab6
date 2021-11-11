package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.CollectionIsEmptyException;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;
import edu.rogachova.server.managers.CollectionManager;

public class PrintDSCEndDateCommand implements Command
{
    private RequestSender requestSender;

    public PrintDSCEndDateCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "print_field_descending_end_date";
    }

    @Override
    public String getDescription()
    {
        return "вывести значения поля endDate всех элементов в порядке убывания";
    }

    @Override
    public void execute(String input) throws Exception
    {
        try{
            if(!input.isEmpty()) throw new WrongAmountOfArgumentsException();
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
