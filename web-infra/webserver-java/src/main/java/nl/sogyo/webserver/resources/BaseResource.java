package nl.sogyo.webserver.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import nl.sogyo.webserver.implementation.HttpRequest;

public class BaseResource implements IResource {
    SupportedSubType subType;
    SupportedMimeType mainType;
    HttpRequest request;

    public BaseResource(SupportedMimeType mimeType, SupportedSubType resourceType, HttpRequest requestForParameters) {
        mainType = mimeType;
        subType = resourceType;
        request = requestForParameters;
    }
    // public TextResource(HttpRequest requestForParameters) {
    // request = requestForParameters;
    // }
    // public abstract String getContent();
    public SupportedMimeType getMimeType(){
        return mainType;
    }
    public SupportedSubType getSubType(){
        return subType;
    }

    @Override
    public String getContent() {
        return "Error: could not get content";
    }

    public static BaseResource createFirstSupportedResource(HttpRequest clientRequest) {
        BaseResource result = null;
        String clientAccepts = clientRequest.getHeaderParameterValue("Accept");
        if (clientAccepts != null && !clientAccepts.isEmpty()) {
            // Get the accepted types/subtypes
            String[] splitAccepts = clientAccepts.split(",");
            Map<Double, String> weightedAccepts = getWeightedAccepts(splitAccepts);
            Map<Double, String> supportedAccepts = getSupportedAccepts(weightedAccepts);
            if (supportedAccepts.size() > 0) {
                // Don't know how to get a class based on enum properties without instantiating
                // it so just gonna do a bunch of if statements
                result = getBaseResourceByMimeType(supportedAccepts.entrySet().iterator().next(), clientRequest);
            }
        }
        return result;
    }

    private static BaseResource getBaseResourceByMimeType(Entry<Double, String> clientAccept, HttpRequest clientRequest) {
        String mimeType = clientAccept.getValue();
        if(mimeType.equals("*/*")) return new PlainTextResource(clientRequest);
        else if(mimeType.equals("text/plain")) return new PlainTextResource(clientRequest);
        else if (mimeType.equals("text/html")) return new WebResource(clientRequest);
        else return null;
    }

    public static Map<Double, String> getSupportedAccepts(Map<Double, String> accepts) {
        Map<Double, String> result = new TreeMap<>();
        for(Map.Entry<Double,String> entry : accepts.entrySet()){
            String mimeType = entry.getValue().split("/")[0];
            String subType = entry.getValue().split("/")[1];
            try{
                SupportedMimeType mime = SupportedMimeType.getByName(mimeType);
                SupportedSubType sub = SupportedSubType.getByName(subType);
                if(mimeType.equals("*") && subType.equals("*")) result.put(entry.getKey(), entry.getValue());
                if(mime != null && sub != null) result.put(entry.getKey(), entry.getValue());
            } catch(Exception e){

            }
        }
        return result;
    }
    public static Map<Double, String> getWeightedAccepts(String[] acceptStrings){
        Map<Double, String> result = new HashMap<>();
        for(String string : acceptStrings){
            if(string.contains(";")){
                String weight = string.split(";")[1].split("=")[1];
                result.put(Double.parseDouble(weight), string.split(";")[0]);
            }
            else result.put(1.0, string);
        }
        return result;
    }
}
