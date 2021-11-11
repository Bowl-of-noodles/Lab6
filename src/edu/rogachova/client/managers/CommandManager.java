package edu.rogachova.client.managers;

import edu.rogachova.common.exceptions.NoCommandFoundException;
import edu.rogachova.client.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager
{
    protected HashMap<String, Command> commands = new HashMap<String, Command>();

    public CommandManager(Scanner scanner, RequestSender sender){
        addCommand(new HelpCommand(this));
        addCommand(new InfoCommand(sender));
        addCommand(new ShowCommand(sender));
        addCommand(new InsertElementCommand(sender, scanner));
        addCommand(new UpdateCommand(sender, scanner));
        addCommand(new RemoveKeyCommand(sender));
        addCommand(new ClearCommand(sender));
        addCommand(new ExecuteScriptCommand(sender, this));
        addCommand(new ExitCommand());
        addCommand(new RemoveGreaterCommand(sender, scanner));
        addCommand(new ReplaceLowerKeyCommand(sender, scanner));
        addCommand(new RemoveGreaterKeyCommand(sender));
        addCommand(new CountLessSalaryCommand(sender));
        addCommand(new FilterStartWithCommand(sender));
        addCommand(new PrintDSCEndDateCommand(sender));
    }

    void addCommand(Command command){
        if (commands.containsKey(command.getName()))
        {
            throw new IllegalArgumentException("Данная команда уже существует");
        }
        commands.put(command.getName(), command);
    }

    public void executeParsed(String income){
        String[] userCommand = {"",""};
        try{
            userCommand = (income.trim()+" ").split(" ", 2);
            userCommand[1] = userCommand[1].trim();
            if(!commands.containsKey(userCommand[0])){
                throw new NoCommandFoundException("Команды "+ userCommand[0] + " не существует. Список команд можно вывести с помощью команды \"help\" ");
            }
            commands.get(userCommand[0]).execute(userCommand[1]);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getDescriptions(){
        String descriptions = "";
        for (Map.Entry<String, Command> com : commands.entrySet()) {
            Command command = com.getValue();
            descriptions +=  command.getName() + ": " + command.getDescription() + "\n";
        }
        return descriptions;
    }

}
