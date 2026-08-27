package mx.florinda.cardapio;

import java.time.LocalDateTime;
import java.util.*;

public class HistoricoVisualizacao {

    private final DataBase dataBase;
    final Map<ItemCardapio, LocalDateTime> visualizacoes = new HashMap<>();

    public HistoricoVisualizacao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void registrarVisualizacao(Long itemId) {
        Optional<ItemCardapio> optionalItemCardapio = dataBase.itemCardapioPorId(itemId);
        if(optionalItemCardapio.isEmpty()) {
            System.out.println("Item não encontrado: " + itemId);
            return;
        }
        ItemCardapio itemCardapio = optionalItemCardapio.get();
        LocalDateTime agora = LocalDateTime.now();
        visualizacoes.put(itemCardapio, agora);
        System.out.printf("'%s' visualizado em '%s'\n", itemCardapio.nome(), agora);
    }

    public void mostrarTotalVisualizados() {
        System.out.println("\nTotal de itens visualizados: " + visualizacoes.size());
    }

    public void listarVisualizacoes() {
        if(visualizacoes.isEmpty()) {
            System.out.println("Nenhum item foi visualizado ainda.");
            return;
        }

        System.out.println("\nHistórico de visualizações");
        visualizacoes.forEach((item, hora) ->
                System.out.printf("- %s em %s\n", item.nome(), hora));
        System.out.println();
    }
}
