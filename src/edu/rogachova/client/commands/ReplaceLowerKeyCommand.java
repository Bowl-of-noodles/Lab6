package edu.rogachova.client.commands;

import edu.rogachova.client.managers.Asker;
import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

import java.util.Scanner;

public class ReplaceLowerKeyCommand implements Command
{
    private RequestSender requestSender;
    private String argument;
    private Scanner scanner;

    public ReplaceLowerKeyCommand(RequestSender requestSender, Scanner scanner){
        this.requestSender = requestSender;
        this.scanner = scanner;
    }

    @Override
    public String getName()
    {
        return "replace_if_lower";
    }

    @Override
    public String getDescription()
    {
        return "заменить значение по ключу, если новое значение меньше старого";
    }

    @Override
    public void execute(String input) throws Exception
    {
        try
        {
            if(input.equals(""))throw new WrongAmountOfArgumentsException();
            Integer argument = Integer.parseInt(input);
            Asker asker = new Asker(scanner);
            Worker worker = asker.createWorker();
            worker.setId(argument);
            Request<?> request = new Request<>(this.getName(), worker);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("У команды один аргумент - ключ работника");
        }
    }
}
