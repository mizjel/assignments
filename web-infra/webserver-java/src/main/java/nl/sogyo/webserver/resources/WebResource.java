package nl.sogyo.webserver.resources;

import nl.sogyo.webserver.implementation.HttpRequest;

public class WebResource extends BaseResource {
    // Maar dan de aangepaste response(zie TextResource)
    ////String content = "<html><body>You did an HTTP GET request.<br/>Requested resource: "+ request.getResourcePath() +"</body></html>";
    public WebResource(HttpRequest requestForParameters) {
        super(SupportedMimeType.TEXT, SupportedSubType.HTML, requestForParameters);
    }
    @Override
    public String getContent(){
        return "<html><body>Blah</body></html>";
    }
}
