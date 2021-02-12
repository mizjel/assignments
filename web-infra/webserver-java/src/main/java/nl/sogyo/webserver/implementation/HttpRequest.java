package nl.sogyo.webserver.implementation;

import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import nl.sogyo.webserver.HttpMethod;
import nl.sogyo.webserver.Protocol;
import nl.sogyo.webserver.Request;

public class HttpRequest implements Request {

    private HttpMethod httpMethod;
    private String path;
    private Protocol protocol;
    private String protocolVersion;
    private Map<String, String> headerParameters;
    private Map<String, String> parameters;
    
    public HttpRequest(ArrayList<String> requestData, String requestBody) {
        //Get the first item, split that by spaces, first is httpmethod, second is path with optional parameters(with a GET request)
        //third is protocol and version
        setInitialHeaderValues(requestData.get(0));

        //If httpmethod is get, check if requestData.get(1) contains a question mark, set parameters before removing if it does
        if(httpMethod == HttpMethod.GET && requestData.get(0).split(" ")[1].contains("?")){
            setParameters(requestData.get(0).split(" ")[1].split("\\?")[1]);
        }
        else if(httpMethod == HttpMethod.POST) setParameters(requestBody.replace("\r\n", ""));
       
        requestData.remove(0);

        setHeaderParameters(requestData);
    }

    private void setParameters(String string) {
        parameters = new HashMap<>();
        String[] splitParameters = string.split("&");
        for(String parameter : splitParameters){
            String[] keyValue = parameter.split("=");
            parameters.put(keyValue[0], keyValue[1]);
        }
    }

    private void setInitialHeaderValues(String headerData) {
        String[] splitLine = headerData.split(" ");

        httpMethod = HttpMethod.valueOf(splitLine[0]);

        if(splitLine[1].contains("?")) 
            path = splitLine[1].split("\\?")[0];
        else{
            path = splitLine[1];
        }

        protocol = Protocol.valueOf(splitLine[2].split("/")[0]);
        protocolVersion = splitLine[2].split("/")[1];
    }

    private void setHeaderParameters(ArrayList<String> parameterList) {
        headerParameters = new HashMap<>();
        for(String string : parameterList){
            String[] splitLines = string.replace(" ", "").split(":", 2);
            headerParameters.put(splitLines[0], splitLines[1]);
        }
    }

    public Map<String, String> getHeaderParameters(){
        return headerParameters;
    }
    public String getProtocolVersion(){
        return protocolVersion;
    }
    public Protocol getProtocol(){
        return protocol;
    }
    @Override
    public HttpMethod getHTTPMethod() {
        return httpMethod;
    }

    @Override
    public String getResourcePath() {
        return path;
    }

    @Override
    public List<String> getHeaderParameterNames() {
        return new ArrayList<>(headerParameters.keySet());
    }

    @Override
    public String getHeaderParameterValue(String name) {
        return headerParameters.get(name);
    }
    public Map<String, String> getParameters(){
        return parameters;
    }
    /// Get parameter(not header parameters) names for a POST or GET request
    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>(parameters.keySet());
    }

    /// Get parameter(not header parameter) value for a POST or GET request
    @Override
    public String getParameterValue(String name) {
        return parameters.get(name);
    }
    
}
