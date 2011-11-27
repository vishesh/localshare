/**
 * LocalShare.java
 * Vishesh Yadav - http://www.vishesh-yadav.com
 *
 * License: Public Domain
 *
 * Starts a new HTTP Server at particular port to share files in a particular
 * directory.
 *
 */

package vy.utils.localshare;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import vy.utils.localshare.Server;

public class LocalShare
{
    public static void main(String[] args) throws IOException
    {
        if (args.length == 0) {
            System.out.println("LocalShare: usage: LocalShare <port> <directory>");
            System.out.println("               <port> Port to bind with.");
            System.out.println("               <directory> Directory to serve.");
            return;
        }
        else if (args.length < 2) {
            System.out.println("LocalShare: Invalid number of arguments");
            return;
        }

        String port = args[0];
        String directory = args[1];

        System.out.println("Starting server...");
        Server server = new Server(Integer.parseInt(port), directory);
        server.startServer();
    }
}

