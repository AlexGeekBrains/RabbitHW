package com.alex.consumer;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class AppSubscriber {
    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber();
        try {
            subscriber.connect();
            subscriber.start();
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("Enter the subscription topic in the following format set_topic 'desired topic''");
                String in = sc.nextLine();
                String[] token = in.split(" ");
                if (token[0].trim().equals("set_topic")) {
                    try {
                        subscriber.subscribe(token[1]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(token[0].trim().equals("unsubscribe")){
                    subscriber.unsubscribe(token[1]);
                }
                if (token[0].trim().equals("exit")){
                    subscriber.disconnect();
                    break;
                }
            }
        }catch(IOException | TimeoutException e){
            throw new RuntimeException(e);
        }finally{
            try {
                subscriber.disconnect();
            } catch (IOException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
