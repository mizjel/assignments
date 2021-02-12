package nl.sogyo.webserver.resources;

public enum SupportedMimeType {
    TEXT("text");

    private String typeName;
    private SupportedMimeType(String type) {
        typeName = type;
    }
    public String getName(){
        return typeName;
    }
    public static SupportedMimeType getByName(String name){
        for(SupportedMimeType mime : values()){
            if(mime.getName().equals(name)) return mime;
        }
        return null;
    }
}
