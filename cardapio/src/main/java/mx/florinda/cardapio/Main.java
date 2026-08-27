package mx.florinda.cardapio;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        DataBase database = new DataBase();
        HistoricoVisualizacao historicoVisualizacao = new HistoricoVisualizacao(database);
        historicoVisualizacao.registrarVisualizacao(1L);
        historicoVisualizacao.registrarVisualizacao(2L);
        historicoVisualizacao.registrarVisualizacao(4L);
        historicoVisualizacao.registrarVisualizacao(6L);

        historicoVisualizacao.mostrarTotalVisualizados();
        historicoVisualizacao.listarVisualizacoes();

    }
}
