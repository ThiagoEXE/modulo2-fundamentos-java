package mx.florinda.cardapio;
import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

       DataBase database = new SQLDatabase();
       List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();
        listaItensCardapio.forEach(System.out::println);

        System.out.println(database.listaDeItensCardapio().size());

        /*ItemCardapio novoItemCardapio = new ItemCardapio(10L, "Tacos de Carnitas", "Tacos recheados com carne tenra", ItemCardapio.CategoriaCardapio.PRATOS_PRINCIPAIS, new BigDecimal("25.9"), null);
        database.adicionaItemCardapio(novoItemCardapio);*/


    }
}
