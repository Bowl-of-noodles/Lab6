package edu.rogachova.client.commands;

import edu.rogachova.client.managers.Asker;
import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.CollectionIsEmptyException;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.model.Worker;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

import java.util.Scanner;

public class RemoveGreaterCommand implements Command
{
    private RequestSender requestSender;
    private String argument;
    private Scanner scanner;

    public RemoveGreaterCommand(RequestSender requestSender, Scanner scanner){
        this.requestSender  = requestSender;
        this.scanner = scanner;
    }

    @Override
    public String getName()
    {
        return "remove_greater";
    }

    @Override
    public String getDescription()
    {
        return "удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public void execute(String input) throws Exception
    {
        this.argument = input;
        try{
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            Asker asker = new Asker(scanner);
            Worker workerToCompareWith = asker.createWorker();
            Request<?> request = new Request<>(this.getName(), workerToCompareWith);
            CommandResult result = requestSender.sendRequest(request);
            if (result.status == ResultStatus.OK){
                System.out.println(result.message);
            }else{
                System.out.println("Произошла ошибка: " + result.message);
            }
        } catch (WrongAmountOfArgumentsException exception) {
            System.out.println("Введите команду без аргументов. Затем следуйте указаниям для создания сравнительного элемента");
        }
    }
}
