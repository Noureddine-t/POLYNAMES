import webserver.WebServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.listen(8080);
        server.getRouter().get("/game/:id", (context) -> {
            context.getResponse().ok("Game ID: " + context.getRequest().getParam("id"));
            File file = new File("index.html");
            he.sendResponseHeaders(200, file.length());
            try (OutputStream os = he.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        });


    }
}