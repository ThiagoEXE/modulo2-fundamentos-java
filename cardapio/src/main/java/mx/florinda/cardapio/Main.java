package mx.florinda.cardapio;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        DataBase database = new DataBase();

        // PRECISO ALTERAR O PRECO DE UM ITEM DO CARDAPIO
        database.alterarPrecoItemCardapio(1L, new BigDecimal("3.99")); // 2.99 => 3.99
        database.alterarPrecoItemCardapio(1L, new BigDecimal("2.99")); // 3.99 => 2.99
        database.alterarPrecoItemCardapio(1L, new BigDecimal("4.99")); // 2.99 => 4.99

        // PRECISO AUDITAR AS MUDANCAS DE PRECO DOS ITENS DO CARDAPIO
        database.imprimirRastroAuditoriaPrecos();

    }
}
