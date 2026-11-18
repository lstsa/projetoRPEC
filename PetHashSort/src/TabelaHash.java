import java.util.ArrayList;

/**
 * Tabela hash implementada pelo grupo, com tratamento de colisões por
 * encadeamento (lista ligada em cada balde). A chave é o nome normalizado do pet.
 * M é o número de baldes, não a quantidade de itens por balde. Cada tabela usa uma
 * única função hash (1 = soma dos códigos, 2 = polinomial), fixada na criação: trocar
 * depois de inserir faria a busca olhar o balde errado.
 */

public class TabelaHash {
    private No[] baldes;
    private int m;
    private int quantidade;
    private int tipoHash;

    /**
     * Cria uma tabela vazia com m baldes e a função hash escolhida (1 ou 2).
     */

    public TabelaHash(int m, int tipoHash) {
        this.m = m;
        this.tipoHash = tipoHash;
        this.baldes = new No[m];
        this.quantidade = 0;
    }

    /**
     * Escolhe a função hash conforme o tipo da tabela. Inserção e busca chamam apenas
     * este método, sem saber qual função está em uso.
     */

    private int hash(String chave) {
        if (tipoHash == 2) {
            return hash2(chave);
        }
        return hash1(chave);
    }

    /**
     * Função 1: soma dos códigos dos caracteres, reduzida ao intervalo [0, m-1] pelo
     * resto da divisão. Ignora a ordem das letras (ROMA e AMOR dão o mesmo resultado).
     */

    private int hash1(String chave) {
        int soma = 0;
         for (int i = 0; i < chave.length(); i++) {
             soma += chave.charAt(i);
         }

         return soma % m;
    }

    /**
     * Função 2 (do grupo): polinomial de base 31, calculada pelo método de Horner. A
     * cada letra o acumulado é multiplicado por 31, então a posição da letra influencia o
     * resultado. 31 é primo e ímpar (o mesmo valor do String.hashCode do Java). O
     * acumulado pode estourar o int e ficar negativo; por isso o resto (%) é calculado
     * antes do Math.abs, pois Math.abs de Integer.MIN_VALUE continuaria negativo.
     */

    private int hash2(String chave) {
        int h = 0;
        for (int i = 0; i < chave.length(); i++) {
            h = h * 31 + chave.charAt(i);
        }
        return Math.abs(h % m);
    }

    /**
     * Insere o pet no início da lista do seu balde, em tempo constante O(1). A ordem das
     * operações importa: o novo nó primeiro aponta para o antigo primeiro e só depois
     * passa a ser o primeiro; se inverter, perde-se o resto da lista.
     */

    public void inserir(Pet pet) {
        int posicao = hash(pet.getChave());

        No novo = new No(pet);
        novo.setProximo(baldes[posicao]);
        baldes[posicao] = novo;

        quantidade++;
    }

    /**
     * Mostra a tabela balde por balde, inclusive os vazios, para visualizar a
     * distribuição dos pets.
     */

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

    /**
     * Devolve todos os pets com o nome informado (lista vazia se não houver nenhum).
     * Olha apenas o balde calculado pela função hash e compara a chave normalizada
     * dentro dele, pois o balde pode conter nomes diferentes (colisão real).
     */

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

    /**
     * Busca um pet pelo microchip, ou devolve null. A busca é linear em todos os
     * baldes: a tabela é organizada por nome, então o microchip não indica em qual
     * balde o pet está.
     */

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

    /**
     * Remove o pet com o microchip informado e devolve false se ele não existir. Usa
     * dois ponteiros (anterior e atual), pois é preciso alterar o "proximo" do nó anterior
     * ao removido. Há dois casos: remover o primeiro do balde (o balde passa a apontar
     * para o segundo) ou remover do meio ou do fim (o anterior "pula" o removido).
     */

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

    /**
     * Calcula e mostra as estatísticas da tabela em uma única passada pelos baldes:
     * - Fator de carga: quantidade / M (média de pets por balde).
     * - Baldes vazios e tamanho da maior lista.
     * - Colisões totais: cada nó além do primeiro de um balde (inclui nomes repetidos).
     * - Colisões reais: chaves diferentes além da primeira em cada balde. Só estas medem
     *   a qualidade da função hash, pois nomes iguais nenhuma função consegue separar.
     */

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

    /**
     * Conta quantas chaves diferentes existem numa lista: para cada nó, verifica se a
     * mesma chave já apareceu antes dele. Tem custo quadrático, mas os baldes são pequenos.
     */

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

    /**
     * Devolve todos os pets da tabela, percorrendo os baldes. Usado para reconstruir a
     * tabela e para montar o vetor que será ordenado.
     */

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


