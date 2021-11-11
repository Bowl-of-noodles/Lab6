package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;


public class CountLessSalaryCommand implements Command
{
    private RequestSender requestSender;

    public CountLessSalaryCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "count_less_than_salary";
    }

    @Override
    public String getDescription()
    {
        return "вывести количество элементов, значение поля salary которых меньше заданного";
    }

    @Override
    public void execute(String input) throws Exception
    {
        try{
            if(input.isEmpty())throw new WrongAmountOfArgumentsException();
            int argument = Integer.parseInt(input);
            Request<?> request = new Request<>(this.getName(), argument);
            CommandResult result = requestSender.sendRequest(request);
            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }else{
                System.out.println("Ошибка: " + result.message);
            }

        }catch(NumberFormatException e){
            System.out.println("начение зарплаты - целое число");
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("У данной команды один аргумент - зарплата");
        }
    }
}
