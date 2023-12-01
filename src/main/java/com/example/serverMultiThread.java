package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.*;

public class serverMultiThread extends Thread{
    
    Socket client;
    ArrayList<Socket> listaSocket = new ArrayList<>();
    static ArrayList<String> listaNomi = new ArrayList<>();
    String input;
    BufferedReader in;
    DataOutputStream out;
            

    
    public serverMultiThread(Socket c, ArrayList<Socket> s,ArrayList<String> no){
        try{
            this.client = c;
            this.listaSocket = s;
            this.listaNomi = no;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new DataOutputStream(client.getOutputStream());
        }catch(IOException e){

            System.out.println(e.getMessage());

        }
        
            
        
    }


    public synchronized void aggiungimento(){

        try{
            listaSocket.add(client);
            String nome = in.readLine();
            listaNomi.add(nome);
        }catch(IOException e){

            System.out.println(e.getMessage());

        }
        

    }


    public void run(){

        try{

            

            
            for(Socket s : listaSocket){
                DataOutputStream outBroadcast = new DataOutputStream(s.getOutputStream());
                outBroadcast.writeBytes("* ||"+"\n");
            }


            aggiungimento();


            while(true){

                input = in.readLine();

                if(input.equalsIgnoreCase("#")){
                    break;
                }

                String[] receive = input.split("||");

                String destinatario = receive[0];
                String messaggio = receive[1];
                String scelta = "";

                if(destinatario.equals("broadcast")){
                    scelta = "t";
                } else if (listaNomi.contains(destinatario)){
                    scelta = "s";
                } else {
                    scelta = "x";
                }
                
                
                switch(scelta){
                    
                    case "s":
                        
                        destinatario = in.readLine();
                        if(listaNomi.contains(destinatario)){
                            messaggio = in.readLine();
                            int i = listaNomi.indexOf(destinatario);
                            Socket S = listaSocket.get(i);
                            DataOutputStream outPut = new DataOutputStream(S.getOutputStream());
                            outPut.writeBytes(destinatario + "||" + messaggio + "\n");
                        } else {
                            out.writeBytes("pizza marmellata,metti un nomne decente");
                        }
                        
                        break;

                    case "t" :
                        messaggio = in.readLine();
                        for(Socket s : listaSocket){
                            DataOutputStream outBroadcast = new DataOutputStream(s.getOutputStream());
                            outBroadcast.writeBytes(destinatario + "||" + messaggio + "\n");
                        }
                        break;

                    default:
                        System.out.println("Wtf");
                        break;
                }   

            }
            client.close();
            

        }catch (Exception e) {
            System.out.println("errore " + e.getMessage());
        }
        



    }










}