package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DataBase {
    List<ItemCardapio> listaDeItensCardapio();

    Optional<ItemCardapio> itemCardapioPorId(Long itemId);

    boolean removerItemCardapio(Long idParaRemover);

    boolean alterarPrecoItemCardapio(Long itemId, BigDecimal novoPreco);

    void adicionaItemCardapio(ItemCardapio itemCardapio);
}
