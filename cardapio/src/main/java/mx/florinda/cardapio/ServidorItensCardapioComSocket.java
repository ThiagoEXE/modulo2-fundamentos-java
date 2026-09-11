package mx.florinda.cardapio;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

public class ServidorItensCardapioComSocket {

    private static final DataBase database = new DataBase();

    public static void main(String[] args) throws Exception {

        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {

            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                System.out.println("Subiu o Servidor!");

                while (true) {

                    Socket clientSocket = serverSocket.accept();
                    executorService.execute(() -> trataRequisicao(clientSocket));

                }
            }
        }
    }

    private static void trataRequisicao(Socket clientSocket) {
        try {
            InputStream clientIS = clientSocket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();

            int data;
            do {
                data = clientIS.read();
                requestBuilder.append((char) data);
            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            System.out.println(request);
            System.out.println("--------------------------");
            System.out.println("\n\nChegou um novo request");

            String[] requestChunks = request.split("\r\n\r\n");

            String requestLineAndHeaders = requestChunks[0];
            String[] requestLineAndHeadersChunks = requestLineAndHeaders.split("\r\n");
            String requestLine = requestLineAndHeadersChunks[0];
            String[] requestLineChunks = requestLine.split(" ");

            // method (GET/POST)
            String method = requestLineChunks[0];
            // uri 
            String requestURI = requestLineChunks[1];
            System.out.println(method);
            System.out.println(requestURI);

            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS);
            if (method.equals("GET") && requestURI.equals("/itensCardapio.json")) {
                System.out.println("Chamou arquivo Json");

                Path path = Path.of("itensCardapio.json");
                String json = Files.readString(path);

                clientOut.println("HTTP/1.1 200 OK");
                clientOut.println("Content-type: application/json; charset=UTF-8");
                clientOut.println();
                clientOut.println(json);
            } else if (method.equals("GET") && requestURI.equals("/itens-cardapio")) {
                System.out.println("Chamou Listagem de Itens de Cardápio");

                List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();
                Gson gson = new Gson();
                String json = gson.toJson(listaItensCardapio);

                clientOut.println("HTTP/1.1 200 OK");
                clientOut.println("Content-type: application/json; charset=UTF-8");
                clientOut.println();
                clientOut.println(json);

            } else if (method.equals("GET") && requestURI.equals("/itens-cardapio/total")) {
                System.out.println("Chamou Total de Itens de Cardápio");

                List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();
                int total = listaItensCardapio.size();
                clientOut.println("HTTP/1.1 200 OK");
                clientOut.println("Content-type: application/json; charset=UTF-8");
                clientOut.println();
                clientOut.println("Total de Itens de Cardápio: " + total);

            } else if (method.equals("POST") && requestURI.equals("/itens-cardapio")) {
                System.out.println("Chamou adição de Item de Cardápio");

                if (requestChunks.length == 1) {
                    clientOut.println("HTTP/1.1 400 Bad Request");
                    return;
                }
                String body = requestChunks[1];

                Gson gson = new Gson();
                ItemCardapio novoItemCardapio = gson.fromJson(body, ItemCardapio.class);
                database.adicionaItemCardapio(novoItemCardapio);

                clientOut.println("HTTP/1.1 201 Created");
            } else {
                System.out.println("URI não encontrada: " + requestURI);
                clientOut.println("HTTP/1.1 404 Not Found");
            }
            Thread.sleep(250);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
