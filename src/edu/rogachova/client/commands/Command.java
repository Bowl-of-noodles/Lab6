package edu.rogachova.client.commands;

public interface Command
{
    String getName();

    String getDescription();

    void execute(String input) throws Exception;
}
