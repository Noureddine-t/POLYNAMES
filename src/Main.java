import webserver.WebServer;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebServer server = new WebServer();
        server.listen(8080);
        server.getRouter().get("/game/:id", (context) -> {
            context.getResponse().ok("Game ID: " + context.getRequest().getParam("id"));
// TODO Afficher la page HTML du jeu: grille pour SEE
            
        });


    }
}