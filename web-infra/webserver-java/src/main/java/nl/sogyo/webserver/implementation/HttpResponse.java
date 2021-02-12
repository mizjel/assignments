package nl.sogyo.webserver.implementation;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import nl.sogyo.webserver.HttpStatusCode;
import nl.sogyo.webserver.Protocol;
import nl.sogyo.webserver.Response;
import nl.sogyo.webserver.resources.BaseResource;
import nl.sogyo.webserver.resources.IResource;

public class HttpResponse implements Response {
    private Protocol protocol;
    private String protocolVersion;
    private String serverOS;
    private HttpStatusCode status;
    private Map<String, String> customHeaders;
    private String content;
    private BufferedWriter responseWriter;
    private HttpRequest coupledRequest;
    private BaseResource resourceToUse;

    public HttpResponse(HttpRequest request, HttpStatusCode code, Map<String, String> headers) {
        coupledRequest = request;
        serverOS = "Java: " + System.getProperty("java.version");
        status = code;
        customHeaders = headers;
        protocol = coupledRequest.getProtocol();
        protocolVersion = coupledRequest.getProtocolVersion();
    }

    @Override
    public HttpStatusCode getStatus() {
        return status;
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    @Override
    public ZonedDateTime getDate() {
        return ZonedDateTime.now(ZoneId.of("GMT"));
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void writeResponseToSocket(Socket socket, BaseResource resource) {
        content = resource.getContent();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            String responseHeaderValues = setResponseHeaderValues();
            String responseHeaderParameters = setResponseHeaderParameters(content.length(), resource.getMimeType().getName(), resource.getSubType().getName());
            String responseContent = content;
            writer.write(responseHeaderValues + responseHeaderParameters + responseContent);

            writer.flush();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            //TODO: handle exception
        }
    }

    private String setResponseHeaderParameters(int contentLength, String mimeType, String subType) {
        String result = "";
        // Date: Tue, 21 Jul 2015 11:02:13 GMT
        result += "Date: " + getDate() + "\r\n";
        // Server: Apache/2.4.16 (Unix) OpenSSL/1.0.2d PHP/5.4.45
        result += "Server: " + serverOS + "\r\n";
        // Connection: close
        result += "Connection: ";
        if(coupledRequest.getHeaderParameterValue("Connection") != null) result += coupledRequest.getHeaderParameterValue("Connection");
        else result += "keep-alive";
        result += "\r\n"; 
        // Content-Type: text/html; charset=UTF-8
        // needs to be calculated from the request, example of request: 
        // Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
        // q=0.8 means that the preceding return formats infront of the previous comma(,) have a preference of 0.8
        // all the described formats between the previous comma and the next ; have a weight of the number that comes after the ;
        //result += "Content-Type: text/html; charset=UTF-8\r\n";
        result += "Content-Type: " + mimeType + "/" + subType + "\r\n";
        // Content-Length: 90
        result += "Content-Length: " + contentLength + "\r\n";
        // Rest of the customHeaders
        if(customHeaders != null){
            for (Map.Entry<String,String> entry : customHeaders.entrySet()){
                result += entry.getKey() + ": " + entry.getValue() + "\r\n";
            }
        }
        result += "\r\n"; 
        return result;
    }

    private String setResponseHeaderValues() {
        String result = "";
        // HTTP/1.1 200 OK
        result += this.protocol + "/" + this.protocolVersion + " " + status.getCode() + " " + status.getDescription() + "\r\n";
        return result;
    }
    
}
