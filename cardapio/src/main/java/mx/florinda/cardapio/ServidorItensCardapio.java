package mx.florinda.cardapio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

public class ServidorItensCardapio {

    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8000);
        HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);

        httpServer.createContext("/itensCardapio.json", exchange -> {

            try {

                Path path = Path.of("itensCardapio.json");
                String json = Files.readString(path);
                byte[] bytes = json.getBytes();

                Headers responseHeaders = exchange.getResponseHeaders();
                responseHeaders.add("Content-type", "application/json; charset=UTF-8");

                exchange.sendResponseHeaders(200, bytes.length);

                try (OutputStream responseBody = exchange.getResponseBody()) {

                    responseBody.write(bytes);
                    responseBody.flush();
                }
            } catch (IOException e) {
                exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(500, 0);
                try (OutputStream responseBody = exchange.getResponseBody()) {
                    responseBody.write(e.getMessage().getBytes());
                }
            }
        });
        System.out.println("Subiu servidor http! URL: http://localhost:8000/itensCardapio.json");
        httpServer.start();

    }
}
