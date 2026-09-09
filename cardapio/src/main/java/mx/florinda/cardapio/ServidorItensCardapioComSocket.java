package mx.florinda.cardapio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorItensCardapioComSocket {

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
            String[] partesReqsuisicao = request.split(" ");

            String metodoHTTP = partesReqsuisicao[0];
            String endpoint = partesReqsuisicao[1];
            System.out.println("Método usado: " + metodoHTTP);
            Thread.sleep(250);
            if(metodoHTTP.equals("GET")) {

                if (endpoint.equals("/itens-cardapio")) {
                    getItensCardapio(clientSocket);
                } else if(endpoint.equals("/itens-cardapio/total")) {
                    getQtdItensCardapio(clientSocket);
                } else{
                    System.out.println("Rota não existe");
                }
            } else if(metodoHTTP.equals("POST") && endpoint.equals("/itens-cardapio")) {
                String[] partesReq = request.split("\r\n\r\n", 2);


                if (partesReq.length > 1) {
                    String json = partesReq[1].trim();
                    adicionaUmItemNoCardapio(clientSocket, json);
                }

            } else{
                System.out.println("Método não permitido");

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void getItensCardapio(Socket clientSocket) throws IOException {

        Path path = Path.of("itensCardapio.json");
        String json = Files.readString(path);

        OutputStream clientOS = clientSocket.getOutputStream();
        PrintStream clientOut = new PrintStream(clientOS);
        clientOut.println("HTTP/1.1 200 OK");
        clientOut.println("Content-type: application/json; charset=UTF-8");
        clientOut.println();
        clientOut.println(json);
    }

    private static void getQtdItensCardapio(Socket clientSocket) throws IOException {
        DataBase dataBase = new DataBase();
        int qtdItens = dataBase.listaDeItensCardapio().size();

        OutputStream clientOS = clientSocket.getOutputStream();
        PrintStream clientOut = new PrintStream(clientOS);
        clientOut.println("HTTP/1.1 200 OK");
        clientOut.println("Content-type: application/json; charset=UTF-8");
        clientOut.println();
        clientOut.println("Total de itens no cardápio: " + qtdItens);
    }

    private static void adicionaUmItemNoCardapio(Socket clientSocket, String dadosJson) throws IOException {
        try {
            DataBase dataBase = new DataBase();
            String nomeNovoItem = dataBase.adicionaItemCardapio(dadosJson);
            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS);
            clientOut.println("HTTP/1.1 200 OK");
            clientOut.println("Content-type: application/json; charset=UTF-8");
            clientOut.println();
            clientOut.println("Nome do novo item adicionado: " + nomeNovoItem);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
