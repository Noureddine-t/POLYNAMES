import webserver.WebServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.listen(8080);
    }
}