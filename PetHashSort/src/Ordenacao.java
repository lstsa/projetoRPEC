public class Ordenacao {

    public static Pet[] copiar(Pet[] original) {
        Pet[] copia = new Pet[original.length];
        for (int i = 0; i < original.length; i++) {
            copia[i] = original[i];
        }
        return copia;
    }

    private static void trocar(Pet[] v, int i, int j) {
        Pet temp = v[i];
        v[i] = v[j];
        v[j] = temp;
    }

    public static boolean estaOrdenado(Pet[] v) {
        for (int i = 0; i < v.length - 1; i++) {
            if (v[i].getMicrochip() > v[i + 1].getMicrochip()) {
                return false;
            }
        }
        return true;
    }

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

    public static void quickSort(Pet[] v, Contador c) {
        quickSort(v, 0, v.length - 1, c);
    }

    private static void quickSort(Pet[] v, int inicio, int fim, Contador c) {
        if (inicio < fim) {
            int posPivo = particionar(v, inicio, fim, c);
            quickSort(v, inicio, posPivo - 1, c);
            quickSort(v, posPivo + 1, fim, c);
        }
    }

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