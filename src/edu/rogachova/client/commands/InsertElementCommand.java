package edu.rogachova.client.commands;

import edu.rogachova.client.managers.Asker;
import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;


import java.util.Scanner;

public class InsertElementCommand implements Command
{
    private String argument;
    private RequestSender requestSender;
    private Scanner scanner;

    public InsertElementCommand(RequestSender requestSender, Scanner scanner){
        this.requestSender = requestSender;
        this.scanner = scanner;
    }

    @Override
    public String getName()
    {
        return "insert";
    }

    @Override
    public String getDescription()
    {
        return "добавляет новый элемент с заданным ключом";
    }

    @Override
    public void execute(String input) throws Exception
    {
        this.argument = input;
        try{
            if(!argument.equals("")){
                throw new WrongAmountOfArgumentsException();
            }
            Asker asker = new Asker(scanner);
            Worker worker = asker.createWorker();

            Request<?> request = new Request<>(this.getName(), worker);
            CommandResult result = requestSender.sendRequest(request);

            if(result.status == ResultStatus.OK){
                System.out.println(result.message);
            }
            else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("Введите комнаду insert без аргументов, затем следуйте указаниям.");
        }
    }
}
