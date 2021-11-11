package edu.rogachova.client;

import edu.rogachova.client.managers.CommandManager;
import edu.rogachova.client.managers.RequestSender;
import edu.rogachova.common.Config;

import java.util.Scanner;

public class ClientMain
{
    private static int port = Config.PORT;

    public static void main(String[] args) {

        if(args.length == 1){
            try{
                port = Integer.parseInt(args[0]);
            }
            catch (Exception e){
                System.out.println("Не получилось спарсить порт, переданный в командной строки, используется стандартный");
            }
        }

        RequestSender requestSender = new RequestSender(port);
        CommandManager cm = new CommandManager(new Scanner(System.in), requestSender);
        System.out.println("Клиентское приложение запущено!");
        Console.liveMode(cm, new Scanner(System.in));
    }
}
