import java.util.ArrayList;

public class TabelaHash {
    private No[] baldes;
    private int m;
    private int quantidade;
    private int tipoHash;

    public TabelaHash(int m, int tipoHash) {
        this.m = m;
        this.tipoHash = tipoHash;
        this.baldes = new No[m];
        this.quantidade = 0;
    }

    private int hash(String chave) {
        if (tipoHash == 2) {
            return hash2(chave);
        }
        return hash1(chave);
    }

    private int hash1(String chave) {
        int soma = 0;
         for (int i = 0; i < chave.length(); i++) {
             soma += chave.charAt(i);
         }

         return soma % m;
    }

    private int hash2(String chave) {
        int h = 0;
        for (int i = 0; i < chave.length(); i++) {
            h = h * 31 + chave.charAt(i);
        }
        return Math.abs(h % m);
    }

    public void inserir(Pet pet) {
        int posicao = hash(pet.getChave());

        No novo = new No(pet);
        novo.setProximo(baldes[posicao]);
        baldes[posicao] = novo;

        quantidade++;
    }

    public void exibir() {
        for (int i = 0; i < m; i++) {
            System.out.println("Balde " + i + ": ");

            if (baldes[i] == null) {
                System.out.println("(vazio)");
            } else {
                No atual = baldes[i];
                while (atual != null) {
                    System.out.print(atual.getPet().getNome() + " (" + atual.getPet().getMicrochip() + ")");
                    if (atual.getProximo() != null) {
                        System.out.print(" -> ");
                    }
                    atual = atual.getProximo();
                }
                System.out.println();
            }
        }

    }
    public ArrayList<Pet> buscarPorNome(String nome) {
        ArrayList<Pet> encontrados = new ArrayList<>();
        String chave = Util.normalizar(nome);
        int posicao = hash(chave);

        No atual = baldes[posicao];
        while (atual != null) {
            if (atual.getPet().getChave().equals(chave)) {
                encontrados.add(atual.getPet());
            }
            atual = atual.getProximo();
        }
        return encontrados;
    }

    public Pet buscarPorMicrochip(int microchip) {
        for (int i = 0; i < m; i++) {
            No atual = baldes[i];
            while (atual != null) {
                if (atual.getPet().getMicrochip() == microchip) {
                    return atual.getPet();
                }
                atual = atual.getProximo();
            }
        }
        return null;
    }

    public boolean removerPorMicrochip(int microchip) {
        for (int i = 0; i < m; i++) {
            No anterior = null;
            No atual = baldes[i];

            while (atual != null) {
                if (atual.getPet().getMicrochip() == microchip) {
                    if (anterior == null) {
                        baldes[i] = atual.getProximo();
                    } else {
                        anterior.setProximo(atual.getProximo());
                    }
                    quantidade--;
                    return true;
                }
                anterior = atual;
                atual = atual.getProximo();
            }
        }
        return false;
    }

    public int getQuantidade() { return quantidade; }

    public void exibirEstatisticas() {
        int vazios = 0;
        int maiorLista = 0;
        int colisoesTotais = 0;
        int colisoesReais = 0;

        for (int i = 0; i < m; i++) {
            if (baldes[i] == null) {
                vazios++;
                continue;
            }

            int tamanho = 0;
            No atual = baldes[i];
            while (atual != null) {
                tamanho++;
                atual = atual.getProximo();
            }

            if (tamanho > maiorLista) maiorLista = tamanho;
            colisoesTotais += tamanho - 1;

            int distintas = contarChavesDistintas(baldes[i]);
            colisoesReais += distintas - 1;
        }

        double fator = (double) quantidade / m;

        System.out.println("Tamanho da tabela (M): " + m);
        System.out.println("Pets cadastrados: " + quantidade);
        System.out.printf("Fator de carga: %.2f%n", fator);
        System.out.println("Baldes vazios: " + vazios);
        System.out.println("Maior lista: " + maiorLista);
        System.out.println("Colisões totais (balde ocupado): " + colisoesTotais);
        System.out.println("Colisões reais (chaves diferentes): " + colisoesReais);
    }

    private int contarChavesDistintas(No inicio) {
        int distintas = 0;
        No atual = inicio;
        while (atual != null) {
            boolean repetida = false;
            No anterior = inicio;
            while (anterior != atual) {
                if (anterior.getPet().getChave().equals(atual.getPet().getChave())) {
                    repetida = true;
                    break;
                }
                anterior = anterior.getProximo();
            }
            if (!repetida) distintas++;
            atual = atual.getProximo();
        }
        return distintas;
    }

    public ArrayList<Pet> listarTodos() {
        ArrayList<Pet> todos = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            No atual = baldes[i];
            while (atual != null) {
                todos.add(atual.getPet());
                atual = atual.getProximo();
            }
        }
        return todos;
    }


}


