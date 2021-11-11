package edu.rogachova.client.commands;




import edu.rogachova.client.Console;
import edu.rogachova.client.managers.CommandManager;
import edu.rogachova.client.managers.RequestSender;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScriptCommand implements Command
{
    private RequestSender requestSender;
    private CommandManager commandManager;
    static Set<String> usedScripts = new HashSet<>();

    public ExecuteScriptCommand(RequestSender requestSender, CommandManager commandManager){
        this.requestSender = requestSender;
        this.commandManager = commandManager;
    }


    @Override
    public String getName()
    {
        return "execute_script";
    }

    @Override
    public String getDescription()
    {
        return "считать и исполнить скрипт из указанного файла.";
    }

    @Override
    public void execute(String input) throws Exception
    {
        String path = input;

        File script = new File(path);

        if(!script.exists()){
            throw new Exception("Такого файла не существует");
        }
        if(!script.canRead()){
            throw new Exception("Нет прав на чтение файла");
        }
        if(usedScripts.contains(path)){
            throw new Exception("Этот скрипт уже выполняется, в целях избежания рекурсии его выполнение запрещено.");
        }

        Scanner fileScanner = new Scanner(new BufferedInputStream(new FileInputStream(script)));

        System.out.println("Началось выполнение скрипта");
        usedScripts.add(path);

        CommandManager cm = new CommandManager(fileScanner, requestSender);
        Console.liveMode(cm, fileScanner);

        usedScripts.remove(path);
        System.out.println("Выполение скрипта завершено");
    }
}
