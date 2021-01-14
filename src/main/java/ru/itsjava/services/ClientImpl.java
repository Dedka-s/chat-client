package ru.itsjava.services;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientImpl {
    private final static String HOST = "localhost";
    private final static int PORT = 8080;

    @SneakyThrows
    public void start () {
        Socket socket = new Socket(HOST,PORT);
        Socket socket2 = new Socket(HOST,PORT);
        System.out.println("I'm connected");

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());


        new Thread(new ServerRunnable(socket)).start();

        authorization(consoleReader,socketWriter);
        checkAuthorization(socket2);

                 while (true){
                     socketWriter.println(consoleReader.readLine());

                     socketWriter.flush();

                 }
             }
    @SneakyThrows
    private void authorization (BufferedReader consoleReader, PrintWriter socketWriter){

        System.out.println("Введите логин: ");
        String login = consoleReader.readLine();
        System.out.println("Введите пароль: ");
        String password = consoleReader.readLine();

        socketWriter.println("!auth!" + login + ":" + password);
        socketWriter.flush();
    }

    @SneakyThrows
    private void checkAuthorization (Socket socket){
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String socketInputMessage = serverReader.readLine();
        while (socketInputMessage != null){
            if (socketInputMessage.equals("Вы успешно авторизовались")){
                System.out.println("Успешно");
                break;
            }
        }
    }

    }


