package edu.rogachova.client.commands;

import edu.rogachova.client.managers.Asker;
import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.CollectionIsEmptyException;
import edu.rogachova.common.exceptions.WorkerNotFoundException;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

import java.util.Scanner;

public class UpdateCommand implements Command
{
    private RequestSender requestSender;
    private String argument;
    private Scanner scanner;

    public UpdateCommand(RequestSender requestSender, Scanner scanner){
        this.requestSender = requestSender;
        this.scanner = scanner;
    }

    @Override
    public String getName()
    {
        return "update";
    }

    @Override
    public String getDescription()
    {
        return "обновляет значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(String input) throws Exception
    {
        this.argument = input;
        if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();

        Integer id = Integer.parseInt(argument);
        Asker asker = new Asker(scanner);
        Worker worker = asker.askUpdateWorker(id);

        Request<?> request = new Request<>(this.getName(), worker);
        CommandResult result = requestSender.sendRequest(request);

        if(result.status == ResultStatus.OK){
            System.out.println(result.message);
        }
        else{
            System.out.println("Произошла ошибка: " + result.message);
        }
    }
}
