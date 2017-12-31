package db.bigdata.webserver.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class APIResponse {

    private String title;
    private String message;

    public String toString() {
        return String.format("{ \"title\": \"%s\", \"message\": \"%s\" }", title, message);
    }

}
