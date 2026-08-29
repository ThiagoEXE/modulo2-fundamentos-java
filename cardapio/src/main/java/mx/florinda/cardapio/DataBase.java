package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.*;

import static mx.florinda.cardapio.ItemCardapio.CategoriaCardapio.*;

public class DataBase {

    private final Map<Long, ItemCardapio> itensPoId = new HashMap<>();
    private final Map<ItemCardapio, BigDecimal> auditoriaPrecos = new IdentityHashMap<>();

    public DataBase() {
        ItemCardapio refrescoDoChaves = new ItemCardapio(1L, "Refresco do Chaves", """
                Suco de limão, que parece tamarindo, mas tem gosto de groselha""",
                BEBIDAS, new BigDecimal("2.99"), null);
        itensPoId.put(1L, refrescoDoChaves);

        ItemCardapio sanduicheDoChaves = new ItemCardapio(2L, "Sanduíche de Presunto do Chaves",
                "Sanduíche de presunto simples, mas feito com muito amor.",
                PRATOS_PRINCIPAIS, new BigDecimal("3.50"), new BigDecimal("2.99"));
        itensPoId.put(2L, sanduicheDoChaves);

        ItemCardapio tortaDaDonaFlorinda = new ItemCardapio(5L, "Torta de Frango da Dona Florinda",
                "Torta de frango com recheio cremoso e massa crocante.",
                PRATOS_PRINCIPAIS, new BigDecimal("12.99"), new BigDecimal("10.99"));
        itensPoId.put(5L, tortaDaDonaFlorinda);

        ItemCardapio pipocaDoQuico = new ItemCardapio(6L, "Pipoca do Quico",
                "Balde de pipoca preparado com carinho pelo Quico.",
                PRATOS_PRINCIPAIS, new BigDecimal("4.99"), new BigDecimal("3.99"));
        itensPoId.put(6L, pipocaDoQuico);

        ItemCardapio aguaDeJamaica = new ItemCardapio(7L, "Água de Jamaica",
                "Água aromatizada com hibisco e toque de açúcar.",
                BEBIDAS, new BigDecimal("2.50"), new BigDecimal("2.00"));
        itensPoId.put(7L, aguaDeJamaica);

        ItemCardapio churrosDoChaves = new ItemCardapio(9L, "Churros do Chaves",
                "Churros recheados com doce de leite, clássicos e irresistíveis.",
                SOBREMESAS, new BigDecimal("4.99"), new BigDecimal("3.99"));
        itensPoId.put(9L, churrosDoChaves);
    }
    public List<ItemCardapio> listaDeItensCardapio() {

        return new ArrayList<>(itensPoId.values());
    }

    public Optional<ItemCardapio> itemCardapioPorId(Long itemId) {

        ItemCardapio itemCardapio = itensPoId.get(itemId);
        return Optional.ofNullable(itemCardapio);
    }

    public boolean removerItemCardapio(Long idParaRemover) {
        ItemCardapio itemCardapioRemovido = itensPoId.remove(idParaRemover);

        return itemCardapioRemovido != null;
    }

    public boolean alterarPrecoItemCardapio(Long itemId, BigDecimal novoPreco) {
        ItemCardapio itemAntigo = itensPoId.get(itemId);
        if (itemId == null) {
            return false;
        }
        ItemCardapio itemComPrecoAlterado =  itemAntigo.alterarPreco(novoPreco);
        itensPoId.put(itemId, itemComPrecoAlterado);
        auditoriaPrecos.put(itemAntigo, novoPreco);
        return true;
    }

    public void imprimirRastroAuditoriaPrecos() {
        System.out.println("\nAuditoria de preços:");
        auditoriaPrecos.forEach((itemAntigo, novoPreco) ->
                System.out.printf("- %s: %s => %s\n", itemAntigo.nome(), itemAntigo.preco(), novoPreco));
        System.out.println();
    }
}
