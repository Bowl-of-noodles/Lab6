package edu.rogachova.client.commands;


public class ExitCommand implements Command
{

    @Override
    public String getName()
    {
        return "exit";
    }

    @Override
    public String getDescription()
    {
        return "завершить программу (без сохранения в файл)";
    }

    @Override
    public void execute(String input) throws Exception
    {
        System.out.println("Завершение программы");
    }
}
