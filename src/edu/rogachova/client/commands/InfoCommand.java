package edu.rogachova.client.commands;

import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.exceptions.WrongAmountOfArgumentsException;
import edu.rogachova.common.net.CommandResult;
import edu.rogachova.common.net.Request;
import edu.rogachova.common.net.ResultStatus;

public class InfoCommand implements Command
{
    private RequestSender requestSender;
    String argument;

    public InfoCommand(RequestSender requestSender){
        this.requestSender = requestSender;
    }

    @Override
    public String getName()
    {
        return "info";
    }

    @Override
    public String getDescription()
    {
        return "выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
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
            /*System.out.println(
                    "Информация о коллекции: "+"\n" +
                    "Тип коллекции: "+ collectionManager.getCollType() +"\n" +
                    "Дата инициализации: "+ collectionManager.getInitDate() + "\n" +
                    "Количество элементов в коллекции: " + collectionManager.getSize()
            );*/
        }catch(WrongAmountOfArgumentsException e){
            System.out.println("У команды не должно быть аргументов");
        }
    }
}
