/**
 * Handler.java
 * Vishesh Yadav - http://www.vishesh-yadav.com
 *
 * License: Public Domain
 *
 */

package vy.utils.localshare;

import java.io.*;
import java.net.URI;
import com.sun.net.httpserver.*;
import javax.activation.MimetypesFileTypeMap;

public class Handler implements HttpHandler
{
    private String currentDir;

    public Handler(String path)
    {
        super();
        currentDir = path;
    }

    public void handle(HttpExchange t) throws IOException
    {
        InputStream is = t.getRequestBody();
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));

        Headers headers = t.getRequestHeaders();
        URI uri = t.getRequestURI();
        String path = uri.getPath();

        File requestedFile = new File(currentDir, path);

        if (!requestedFile.exists()) {
            String response = "Not Found!";
            t.sendResponseHeaders(404, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

            return;
        }

        if (requestedFile.isFile()) { 
            byte[] bytearray  = new byte [(int)requestedFile.length()];
            FileInputStream fis = new FileInputStream(requestedFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytearray, 0, bytearray.length);

            Headers responseHeader = t.getResponseHeaders();
            responseHeader.add("Content-Type",   
                    new MimetypesFileTypeMap().getContentType(requestedFile));
            t.sendResponseHeaders(200, requestedFile.length());

            OutputStream os = t.getResponseBody();
            os.write(bytearray, 0, bytearray.length);
            os.close();
        }

        else if (requestedFile.isDirectory()) {
            String[] fileArray = requestedFile.list();
            StringBuilder response = new StringBuilder();
            response.append("<html><head><title>ls</title>" +
                    "<style type=\"text/css\">" +
                    " body {font-family: Monospace; font-size: 14px;}" +
                    "</style>" +
                    "</head><body><h1>local-share</h1>" +
                    "<h4><a href=\"http://www.vishesh-yadav.com\">" +
                    "Vishesh Yadav</a></h4>");

            for (String f : fileArray) {
                File currentFile = new File(requestedFile, f);

                if (currentFile.isDirectory()) 
                    response.append("D");
                else
                    response.append("&nbsp;");

                response.append("&nbsp;&nbsp;&nbsp");
                response.append("<a href=\"" + new File(path, f) + "\">");
                response.append(f + "</a>");
                response.append("<br>");
            }

            response.append("</body></html>");

            t.sendResponseHeaders(200, response.length());

            OutputStream os = t.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }
    }
}

