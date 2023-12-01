package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ServerSocket Server;
        Socket client;

        try{
            
            Server = new ServerSocket(3000);
            ArrayList<Socket> listaSocket = new ArrayList<>();
            ArrayList<String> listaNomi = new ArrayList<>();

            while (true) {
                
                client = Server.accept();
                Thread t = new serverMultiThread(client,listaSocket,listaNomi);
                
                t.start();

            }

           
        }
        catch(Exception e){

            System.out.println(e.getMessage());
            System.exit(1);


        }
        





    }
}