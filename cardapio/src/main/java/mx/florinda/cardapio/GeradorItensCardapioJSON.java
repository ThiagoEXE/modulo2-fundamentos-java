package mx.florinda.cardapio;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeradorItensCardapioJSON {

    static void main() throws IOException {

        DataBase dataBase = new DataBase();
        List<ItemCardapio> listaItensCardapio = dataBase.listaDeItensCardapio();

        Gson gson = new Gson();
        String json = gson.toJson(listaItensCardapio);

        Path path = Path.of("itensCardapio.json");
        Files.writeString(path, json);
    }
}
