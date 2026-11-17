public class GrandeCorrida {
    private static final int[] TAMANHOS = {50, 1000, 10000};
    private static final String[] ALGORITMOS = {"Bubble Sort", "Insertion Sort", "Quick Sort"};
    private static final String[] SITUACOES = {"Aleatório", "Já ordenado", "Ordem inversa"};
    private static final int REPETICOES = 5;
    private static final long SEMENTE = 42;
    private static final boolean PILHA_GRANDE = true;

    private static double[][][] tempo = new double[3][3][3];
    private static double[][][] comparacoes = new double[3][3][3];
    private static double[][][] trocas = new double[3][3][3];
    private static int[][] profundidade = new int[3][3];

    public static void iniciar() {
        if (!PILHA_GRANDE) {
            executar();
            return;
        }
        Thread t = new Thread(null, GrandeCorrida::executar, "corrida", 256L * 1024 * 1024);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("Corrida interrompida.");
        }
    }

    private static void executar() {
        System.out.println("Preparar, apontar...");
        aquecer();

        for (int t = 0; t < TAMANHOS.length; t++) {
            int n = TAMANHOS[t];
            Pet[] ordenado = GeradorPets.gerarOrdenado(n, SEMENTE);
            Pet[][] situacoes = {
                    GeradorPets.embaralhar(ordenado, SEMENTE + 1),
                    ordenado,
                    GeradorPets.inverter(ordenado)
            };

            System.out.println("Corrida com n = " + n + "...");
            for (int a = 0; a < 3; a++) {
                for (int s = 0; s < 3; s++) {
                    medir(a, s, t, situacoes[s]);
                }
            }
        }

        imprimirTabela("TEMPO (ms)", tempo, "%.3f");
        imprimirTabela("COMPARAÇÕES", comparacoes, "%,.0f");
        imprimirTabela("TROCAS / DESLOCAMENTOS", trocas, "%,.0f");
        imprimirProfundidade();
    }

    private static void ordenar(int alg, Pet[] v, Contador c) {
        if (alg == 0) Ordenacao.bubbleSort(v, c);
        else if (alg == 1) Ordenacao.insertionSort(v, c);
        else Ordenacao.quickSort(v, c);
    }

    private static void aquecer() {
        Pet[] base = GeradorPets.gerarAleatorio(300, SEMENTE);
        Contador c = new Contador();
        for (int rep = 0; rep < 20; rep++) {
            for (int a = 0; a < 3; a++) {
                c.zerar();
                ordenar(a, Ordenacao.copiar(base), c);
            }
        }
    }

    private static void medir(int alg, int sit, int t, Pet[] base) {
        Contador c = new Contador();
        long melhor = Long.MAX_VALUE;

        try {
            for (int rep = 0; rep < REPETICOES; rep++) {
                Pet[] copia = Ordenacao.copiar(base);
                c.zerar();

                long inicio = System.nanoTime();
                ordenar(alg, copia, c);
                long duracao = System.nanoTime() - inicio;

                if (duracao < melhor) melhor = duracao;
                if (!Ordenacao.estaOrdenado(copia)) {
                    System.out.println("ERRO: " + ALGORITMOS[alg] + " não ordenou ("
                            + SITUACOES[sit] + ", n = " + base.length + ")");
                }
            }
            tempo[alg][sit][t] = melhor / 1_000_000.0;
            comparacoes[alg][sit][t] = c.comparacoes;
            trocas[alg][sit][t] = c.trocas;
            if (alg == 2) profundidade[sit][t] = c.profundidadeMax;

        } catch (StackOverflowError e) {
            tempo[alg][sit][t] = -1;
            comparacoes[alg][sit][t] = -1;
            trocas[alg][sit][t] = -1;
            if (alg == 2) profundidade[sit][t] = -1;
        }
    }

    private static void imprimirTabela(String titulo, double[][][] v, String formato) {
        System.out.println();
        System.out.println("=== " + titulo + " ===");
        System.out.printf("%-15s %-14s %14s %14s %14s%n",
                "Algoritmo", "Situação", "n = 50", "n = 1.000", "n = 10.000");
        for (int a = 0; a < 3; a++) {
            for (int s = 0; s < 3; s++) {
                System.out.printf("%-15s %-14s", ALGORITMOS[a], SITUACOES[s]);
                for (int t = 0; t < 3; t++) {
                    double x = v[a][s][t];
                    String texto = x < 0 ? "ESTOURO" : String.format(formato, x);
                    System.out.printf(" %14s", texto);
                }
                System.out.println();
            }
        }
    }

    private static void imprimirProfundidade() {
        System.out.println();
        System.out.println("=== QUICK SORT: PROFUNDIDADE MÁXIMA DA RECURSÃO ===");
        System.out.printf("%-14s %14s %14s %14s%n", "Situação", "n = 50", "n = 1.000", "n = 10.000");
        for (int s = 0; s < 3; s++) {
            System.out.printf("%-14s", SITUACOES[s]);
            for (int t = 0; t < 3; t++) {
                String texto = profundidade[s][t] < 0 ? "ESTOURO" : String.valueOf(profundidade[s][t]);
                System.out.printf(" %14s", texto);
            }
            System.out.println();
        }
    }
}