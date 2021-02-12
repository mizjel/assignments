package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.sogyo.webserver.implementation.HttpRequest;
import nl.sogyo.webserver.implementation.HttpResponse;
import nl.sogyo.webserver.resources.BaseResource;
import nl.sogyo.webserver.resources.IResource;
import nl.sogyo.webserver.resources.PlainTextResource;

public class ConnectionHandler implements Runnable {
    private Socket socket;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    /// Handle the incoming connection. This method is called by the JVM when passing an
    /// instance of the connection handler class to a Thread.
    public void run() {
        try {
            // No use reader, only use inputstream
            InputStream stream = socket.getInputStream();
            
            System.out.println("Creating new lines array");

            ArrayList<String> headers = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            boolean headersFinished = false;
            int contentLength = -1;

            while (!headersFinished) {
                String line = br.readLine();
                
                // One empty line means the headers are finished so the handler can start listening
                // for the body if the content length has been set
                headersFinished = line.isEmpty();
                if(!headersFinished) headers.add(line);

                if (line.startsWith("Content-Length:")) {
                    String cl = line.substring("Content-Length:".length()).trim();
                    contentLength = Integer.parseInt(cl);
                }
            }
            String body = "";
            if(contentLength != -1){
                // Contains the body after reading
                char[] buf = new char[contentLength];
                // This method will wait for input again after waiting for the first empty line after the headers in the code above
                // ensuring that the body will be read
                // this will only be done when the content length has been set
                br.read(buf);
                body = String.copyValueOf(buf);
            }
            System.out.println("End of request");

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if(headers.size() > 0){
                HttpRequest request = new HttpRequest(headers, body);
                BaseResource resourceToUse = BaseResource.createFirstSupportedResource(request);
                HttpResponse response = new HttpResponse(request, HttpStatusCode.OK, null);
                // BaseResource textResource = new TextResource(request);
                IResource resource = resourceToUse;
                
                response.writeResponseToSocket(socket, resourceToUse);
            }
            else{
                writer.write("Something went wrong\r\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // After handling the request, we can close our socket.
            try {
                System.out.println("Closing socket connection");
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            } catch(IndexOutOfBoundsException iex) {
                iex.printStackTrace();
            }
        }
    }

    // private String getBodyFromRequest(ArrayList<String> lines, int lastHeaderIndex) {
    //     //Loop through lines
    //     //If line is whitespace and contains carriage return and line feed(\r\n) indicate start of body, set lastHeaderIndex on element before whitespace
    //     //From start of body until end of list, add the body as one whole string
    //     String result = "";
    //     int index = 0;
    //     for(int i = 0; i < lines.size(); i++){
    //         if(lines.get(i).contains("\r\n") && lines.get(i).trim().length() == 0){
    //             index = i;
    //             lastHeaderIndex = i - 1;
    //             break;
    //         }
    //     }
    //     for(int j = index; j < lines.size(); j++){
    //         result += lines.get(j);
    //     }
    //     return result;
    // }

    public static void main(String... args) {
        try {
            // A server socket opens a port on the local computer (in this program port 9090).
            // The computer now listens to connections that are made using the TCP/IP protocol.
            ServerSocket socket = new ServerSocket(9090);
            System.out.println("Application started. Listening at localhost:9090");
            // We are going to use threading. Plain threads (i.e. new Thread(...)) are very expensive -
            // it requires a low-level call to the operating system (kernel) for every thread. By using
            // a thread pool, we can reuse a single operating system thread for multiple requests. This
            // is also known als multiplexing.
            ExecutorService threadPool = Executors.newCachedThreadPool();

            // Start an infinite loop. This pattern is common for applications that run indefinitely
            // and react on system events (e.g. connection established). Inside the loop, we handle
            // the connection with our application logic. 
            while(true) {
                // Wait for someone to connect. This call is blocking; i.e. our program is halted
                // until someone connects to localhost:9090. A socker is a connection (a virtual
                // telephone line) between two endpoints - the client (browser) and the server (this).
                Socket newConnection = socket.accept();
                // We want to process our incoming call. Furthermore, we want to support multiple
                // connections. Therefore, we handle the processing on a background thread. Java
                // takes care of finding an available thread for us. We submit a new task (implementing
                // the Runnable interface) by passing it into the submit function.
                // When a Runnable is submitted, the thread is started by calling the run() method of
                // the runnable (which is the ConnectionHandler).
                // As our handling is in a background thread, we can accept new connections on the
                // main thread (in the next iteration of the loop).
                // Starting the thread is so-called fire and forget. The main thread starts a second
                // thread and forgets about its existence. We recieve no feedback on whether the
                // connection was handled gracefully.
                threadPool.submit(new ConnectionHandler(newConnection));
                System.out.println("Creating new socket connection!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
