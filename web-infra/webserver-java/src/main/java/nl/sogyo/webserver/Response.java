package nl.sogyo.webserver;

import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Map;

import nl.sogyo.webserver.resources.BaseResource;

/// Representation of the server HTTP response
public interface Response {

    /// HTTP status code that informs the client of the
    /// processing of the request by the server.
    HttpStatusCode getStatus();

    /// The header parameters and values that are unique for
    /// this response. A response defines a set of headers, some
    /// are unique, others are always present. The date header
    /// is always present and, if the response has content,
    /// so are the Content-Type and Content-Length.
    Map<String, String> getCustomHeaders();

    /// The exact date and time at which this response was
    /// generated. This is used for the date header that is
    /// always added.
    ZonedDateTime getDate();

    /// Optionally, a response contains content. If we want
    /// to transfer for example a web page, we add the HTML contents
    /// in the body of the response.
    String getContent();

    /// This method will enable the implementation of multiple protocols, each protocol should have it's own
    /// type of response and implementation of writing it's own response to the socket output
    void writeResponseToSocket(Socket socket, BaseResource resource);
}
