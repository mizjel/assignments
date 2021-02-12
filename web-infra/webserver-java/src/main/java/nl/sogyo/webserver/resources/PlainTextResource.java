package nl.sogyo.webserver.resources;

import java.util.Map;

import nl.sogyo.webserver.implementation.HttpRequest;

public class PlainTextResource extends BaseResource {
    public PlainTextResource(HttpRequest requestForParameters) {
        super(SupportedMimeType.TEXT ,SupportedSubType.PLAIN, requestForParameters);
    }

    // HttpRequest request;
    // public TextResource(HttpRequest requestForParameters) {
    //     request = requestForParameters;
    // }
    @Override
    public String getContent(){
        return "You did an " + request.getProtocol() + " " 
        + request.getHTTPMethod() + " and you requested the following resource: " + request.getResourcePath()
        + "\r\n\r\nThe following header parameters were passed:\r\n" + parameterMapToString(request.getHeaderParameters())
        + "\r\n\r\nThe following parameters were passed:\r\n" + parameterMapToString(request.getParameters());
    }
    private String parameterMapToString(Map<String, String> mapToConvert){
        String result = "";
        for(Map.Entry<String,String> entry : mapToConvert.entrySet()){
            result += entry.getKey() + ": " + entry.getValue() + "\r\n";
        }
        return result;
    }
}
