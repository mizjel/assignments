package nl.sogyo.webserver.resources;

public enum SupportedSubType {
    PLAIN("plain"), HTML("html");

    private String subTypeName;

    private SupportedSubType(String subType) {
        subTypeName = subType;
    }
    public String getName(){
        return subTypeName;
    }
    public static SupportedSubType getByName(String name){
        for(SupportedSubType sub : values()){
            if(sub.getName().equals(name)) return sub;
        }
        return null;
    }
}
