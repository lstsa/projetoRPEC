public class PassoAPasso {

    private static String rotulo(Pet p) {
        return p.getNome() + " (" + p.getMicrochip() + ")";
    }

    private static void mostrarFila(Pet[] v) {
        StringBuilder sb = new StringBuilder("   Fila: ");
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i].getNome()).append(" ").append(v[i].getMicrochip());
            if (i < v.length - 1) sb.append(" | ");
        }
        System.out.println(sb);
    }

    private static void trocar(Pet[] v, int i, int j) {
        Pet temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

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

    public static void quickSort(Pet[] v, Contador c) {
        quickSort(v, 0, v.length - 1, 0, c);
    }

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