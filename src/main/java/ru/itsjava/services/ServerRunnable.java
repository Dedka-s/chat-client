package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
@RequiredArgsConstructor

public class ServerRunnable implements Runnable{
    private final Socket socket;
    private boolean checkMyAuthorization = false;
    @SneakyThrows
    @Override
    public void run() {
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String serverMessage;
                if (!checkMyAuthorization){
                    checkMyAuthorization = checkAuthorization(serverReader);
            while (!((serverMessage = serverReader.readLine()) == null)) {
                System.out.println(serverMessage);
            }
        } else  {
            System.out.println("Ошибка авторизации");

        }

    }
    @SneakyThrows
    private boolean checkAuthorization (BufferedReader serverReader){

        String serverMessage = serverReader.readLine();
        if (serverMessage.equals("Вы успешно авторизовались")){
            System.out.println("Вы успешно авторизовались");
            return true;
        }
        return false;
    }



}
