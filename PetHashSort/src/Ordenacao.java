/**
 * Algoritmos de ordenação escritos pelo grupo (Bubble, Insertion e Quick Sort),
 * que ordenam os pets por microchip em ordem crescente e contam comparações e
 * trocas. Regra de contagem: só entram comparações entre microchips, e no Insertion
 * Sort os deslocamentos são contados como trocas.
 */

public class Ordenacao {

    /**
     * Devolve uma cópia do vetor. A cópia é rasa (copia as referências), o que basta,
     * pois os algoritmos só mudam a ordem das referências. Assim cada algoritmo trabalha
     * numa cópia dos mesmos dados e a comparação é justa.
     */

    public static Pet[] copiar(Pet[] original) {
        Pet[] copia = new Pet[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    /**
     * Troca dois elementos de posição (swap). Reaproveitado pelos algoritmos.
     */

    private static void trocar(Pet[] v, int i, int j) {
        Pet temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

    /**
     * Teste de correção: confere se o vetor está em ordem crescente de microchip.
     */

    public static boolean estaOrdenado(Pet[] v) {
        for (int i = 0; i < v.length - 1; i++) {
            if (v[i].getMicrochip() > v[i + 1].getMicrochip()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Bubble Sort com flag: a cada passagem o maior elemento restante vai para o fim, então
     * a parte final já ordenada não é comparada de novo. Se uma passagem termina sem
     * trocas, o vetor já está ordenado e o algoritmo para; em dados já ordenados isso
     * resulta em apenas n-1 comparações.
     */

    public static void bubbleSort(Pet[] v, Contador c) {
        int n = v.length;
        for (int passagem = 0; passagem < n - 1; passagem++) {
            boolean houveTroca = false;

            for (int i = 0; i < n - 1 - passagem; i++) {
                c.comparacoes++;
                if (v[i].getMicrochip() > v[i + 1].getMicrochip()) {
                    trocar(v, i, i + 1);
                    c.trocas++;
                    houveTroca = true;
                }
            }

            if (!houveTroca) {
                break;
            }
        }
    }

    /**
     * Insertion Sort: encaixa cada elemento na parte da esquerda, que já está ordenada,
     * deslocando para a direita os elementos maiores (cada deslocamento conta como
     * troca). Quando o anterior é menor ou igual, o laço para; é isso que faz o algoritmo
     * ser O(n) em dados já ordenados (uma comparação por elemento e nenhum deslocamento).
     */

    public static void insertionSort(Pet[] v, Contador c) {
        for (int i = 1; i < v.length; i++) {
            Pet atual = v[i];
            int j = i - 1;

            while (j >= 0) {
                c.comparacoes++;
                if (v[j].getMicrochip() > atual.getMicrochip()) {
                    v[j + 1] = v[j];
                    c.trocas++;
                    j--;
                } else {
                    break;
                }
            }
            v[j + 1] = atual;
        }
    }

    /**
     * Quick Sort com pivô no último elemento (versão ingênua, de propósito). Em dados
     * já ordenados ou em ordem inversa o pivô é sempre um extremo da faixa, a divisão
     * fica em 0 e n-1 elementos e o custo passa a ser O(n²), com recursão de
     * profundidade próxima de n (pergunta 9).
     */

    public static void quickSort(Pet[] v, Contador c) {
        quickSort(v, 0, v.length - 1, 0, c);
    }

    /**
     * Versão recursiva: ordena apenas a faixa [inicio..fim]. O parâmetro nivel mede a
     * profundidade da recursão. Faixas com 0 ou 1 elemento já estão ordenadas
     * (condição de parada).
     */

    private static void quickSort(Pet[] v, int inicio, int fim, int nivel, Contador c) {
        if (inicio < fim) {
            if (nivel > c.profundidadeMax) c.profundidadeMax = nivel;
            int posPivo = particionar(v, inicio, fim, c);
            quickSort(v, inicio, posPivo - 1, nivel + 1, c);
            quickSort(v, posPivo + 1, fim, nivel + 1, c);
        }
    }

    /**
     * Partição de Lomuto: move para a esquerda os elementos menores que o pivô e coloca o
     * pivô entre as duas regiões. Devolve a posição definitiva do pivô. Trocas de um
     * elemento consigo mesmo (i == j) também são contadas.
     */

    private static int particionar(Pet[] v, int inicio, int fim, Contador c) {
        int pivo = v[fim].getMicrochip();
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            c.comparacoes++;
            if (v[j].getMicrochip() < pivo) {
                i++;
                trocar(v, i, j);
                c.trocas++;
            }
        }

        trocar(v, i + 1, fim);
        c.trocas++;
        return i + 1;
    }
}