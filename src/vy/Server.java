/**
 * Server.java
 * Vishesh Yadav - http://www.vishesh-yadav.com
 *
 * License: Public Domain
 *
 * Starts a new HTTP Server at particular port to share files in a particular
 * directory.
 *
 */

package vy.utils.localshare;

import java.net.InetSocketAddress;
import java.io.*;
import com.sun.net.httpserver.*;
import vy.utils.localshare.Handler;

public class Server
{
    public Server(int port, String directory)
    {
        m_port = port;
        m_directory = directory;
    }

    public void startServer() throws IOException
    {
        m_server = HttpServer.create(new InetSocketAddress(m_port), 0);
        m_server.createContext("/", new Handler(m_directory));
        m_server.setExecutor(null);
        m_server.start();
    }

    private int m_port;
    private String m_directory;
    private HttpServer m_server;
}

