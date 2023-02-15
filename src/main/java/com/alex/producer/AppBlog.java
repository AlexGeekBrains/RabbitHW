package com.alex.producer;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class AppBlog {
    public static void main(String[] args) {
        ItBlog itBlog = new ItBlog();
        try {
            itBlog.connect();
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("Start a blog by specifying a topic");
                String in = sc.nextLine();
                String[] msg = in.split(" ", 2);
                if (msg[0].trim().equals("exit")) {
                    itBlog.disconnect();
                    sc.close();
                    break;
                }
                itBlog.sendMessage(msg[0], msg[1]);
            }
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                itBlog.disconnect();
            } catch (IOException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
