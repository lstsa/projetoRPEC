/**
 * Acumula as medidas de um algoritmo de ordenação: comparações entre microchips
 * (não conta as condições dos laços), trocas (no Insertion Sort, os deslocamentos)
 * e a profundidade máxima da recursão do Quick Sort. Os contadores são long porque o
 * Bubble Sort com 10.000 elementos faz dezenas de milhões de comparações. Os
 * campos são públicos de propósito, para simplificar o código dos algoritmos.
 */

public class Contador {
    public long comparacoes = 0;
    public long trocas = 0;
    public int profundidadeMax = 0;

    /**
     * Zera todas as medidas para reaproveitar o contador em outra execução.
     */

    public void zerar() {
        comparacoes = 0;
        trocas = 0;
        profundidadeMax = 0;
    }
}