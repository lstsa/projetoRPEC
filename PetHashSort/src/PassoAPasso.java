/**
 * Versões narradas dos três algoritmos, usadas no modo passo a passo (até 10 pets).
 * A lógica é idêntica à da classe Ordenacao; ficam separadas porque imprimir na tela
 * é lento e distorceria os tempos medidos na Grande Corrida.
 */

public class PassoAPasso {

    /**
     * Texto curto do pet para a narração, no formato "Luna (60218841)".
     */

    private static String rotulo(Pet p) {
        return p.getNome() + " (" + p.getMicrochip() + ")";
    }

    /**
     * Mostra o estado atual do vetor em uma linha, para ver o efeito de cada passo.
     */

    private static void mostrarFila(Pet[] v) {
        StringBuilder sb = new StringBuilder("   Fila: ");
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i].getNome()).append(" ").append(v[i].getMicrochip());
            if (i < v.length - 1) sb.append(" | ");
        }
        System.out.println(sb);
    }

    /**
     * Troca dois elementos de posição (swap).
     */

    private static void trocar(Pet[] v, int i, int j) {
        Pet temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

    /**
     * Bubble Sort narrado: cada comparação mostra SIM (os pets trocam de lugar) ou NÃO,
     * e o fim de cada passagem mostra os contadores e a fila. Para quando uma passagem
     * não faz nenhuma troca.
     */

    public static void bubbleSort(Pet[] v, Contador c) {
        int n = v.length;
        for (int passagem = 0; passagem < n - 1; passagem++) {
            System.out.println();
            System.out.println("Bubble Sort · passagem " + (passagem + 1));
            boolean houveTroca = false;

            for (int i = 0; i < n - 1 - passagem; i++) {
                c.comparacoes++;
                Pet a = v[i];
                Pet b = v[i + 1];

                if (a.getMicrochip() > b.getMicrochip()) {
                    System.out.println(rotulo(a) + " > " + rotulo(b) + "? SIM → trocam de cadeira!");
                    trocar(v, i, i + 1);
                    c.trocas++;
                    houveTroca = true;
                } else {
                    System.out.println(rotulo(a) + " > " + rotulo(b) + "? NÃO → " + a.getNome() + " Não quis trocar.");
                }
            }

            System.out.println("fim da passagem " + (passagem + 1)
                    + " → comparações: " + c.comparacoes + " | trocas: " + c.trocas);
            mostrarFila(v);

            if (!houveTroca) {
                System.out.println("Nenhuma troca nesta passagem: a sala de espera já está organizada!");
                break;
            }
        }
    }

    /**
     * Insertion Sort narrado: cada pet "chega" e é encaixado na parte já ordenada,
     * deslocando os maiores. O caso do menor elemento (que vai para a primeira
     * posição) tem frase própria. A fila só é mostrada depois do encaixe, quando o vetor
     * está consistente (durante o deslocamento há um elemento duplicado por instantes).
     */

    public static void insertionSort(Pet[] v, Contador c) {
        for (int i = 1; i < v.length; i++) {
            Pet atual = v[i];
            System.out.println();
            System.out.println("Insertion Sort · " + rotulo(atual) + " chega para ser encaixado na fila");
            int j = i - 1;

            while (j >= 0) {
                c.comparacoes++;
                if (v[j].getMicrochip() > atual.getMicrochip()) {
                    System.out.println(rotulo(v[j]) + " > " + rotulo(atual)
                            + "? SIM → " + v[j].getNome() + " anda uma cadeira para a direita");
                    v[j + 1] = v[j];
                    c.trocas++;
                    j--;
                } else {
                    System.out.println(rotulo(v[j]) + " > " + rotulo(atual)
                            + "? NÃO → " + atual.getNome() + " senta aqui.");
                    break;
                }
            }
            v[j + 1] = atual;

            if (j < 0) {
                System.out.println(atual.getNome() + " é o menor até agora e senta na primeira cadeira.");
            }
            System.out.println("fim do encaixe → comparações: " + c.comparacoes + " | deslocamentos: " + c.trocas);
            mostrarFila(v);
        }
    }

    /**
     * Inicia o Quick Sort narrado sobre o vetor inteiro.
     */

    public static void quickSort(Pet[] v, Contador c) {
        quickSort(v, 0, v.length - 1, 0, c);
    }

    /**
     * Quick Sort narrado para a faixa [inicio..fim]. O recuo da narração cresce com o
     * nível da recursão, mostrando a faixa sendo dividida. A troca de um elemento
     * consigo mesmo tem frase própria, e o pivô é anunciado em cada faixa. Depois da
     * partição, ordena a faixa dos menores [inicio..i] e a dos maiores [i+2..fim].
     */

    private static void quickSort(Pet[] v, int inicio, int fim, int nivel, Contador c) {
        if (inicio >= fim) return;

        String recuo = "  ".repeat(nivel);
        Pet pivo = v[fim];
        System.out.println();
        System.out.println(recuo + "Quick Sort · faixa [" + inicio + ".." + fim + "] · pivô: " + rotulo(pivo));

        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            c.comparacoes++;
            if (v[j].getMicrochip() < pivo.getMicrochip()) {
                i++;
                if (i == j) {
                    System.out.println(recuo + rotulo(v[j]) + " < pivô? SIM → já está no lado certo (troca consigo mesmo)");
                } else {
                    System.out.println(recuo + rotulo(v[j]) + " < pivô? SIM → troca de cadeira com " + v[i].getNome());
                }
                trocar(v, i, j);
                c.trocas++;
            } else {
                System.out.println(recuo + rotulo(v[j]) + " < pivô? NÃO → fica do lado dos maiores.");
            }
        }

        trocar(v, i + 1, fim);
        c.trocas++;
        System.out.println(recuo + rotulo(pivo) + " senta na cadeira " + (i + 1) + ", seu lugar definitivo.");
        System.out.println(recuo + "comparações: " + c.comparacoes + " | trocas: " + c.trocas);
        mostrarFila(v);

        quickSort(v, inicio, i, nivel + 1, c);
        quickSort(v, i + 2, fim, nivel + 1, c);
    }
}