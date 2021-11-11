package edu.rogachova.client;

import edu.rogachova.client.managers.CommandManager;

import java.util.Scanner;

public class Console
{
    public static void liveMode(CommandManager cm, Scanner scanner){
        String input;

        do{
            if(!scanner.hasNextLine()){
                return;
            }
            input = scanner.nextLine();
            try {
                cm.executeParsed(input);
            }
            catch (Exception e){
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
            System.out.println();

        }while ( !input.equals("exit") );
    }
}
