import java.util.ArrayList;

public class TabelaHash {
    private No[] baldes;
    private int m;
    private int quantidade;

    public TabelaHash(int m) {
        this.m = m;
        this.baldes = new No[m];
        this.quantidade = 0;
    }

    private int hash(String chave) {
        int soma = 0;
         for (int i = 0; i < chave.length(); i++) {
             soma += chave.charAt(i);
         }

         return soma % m;
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

        String chave = Util.normalizer(nome);
        int posicao = hash(chave);

        No atual = baldes[posicao];
        while (atual != null) {
            if (atual.getPet().getNome().equals(chave)) {
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
}


