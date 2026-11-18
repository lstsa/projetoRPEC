/**
 * Nó da lista ligada que forma cada balde da tabela hash (encadeamento). Foi
 * implementado pelo grupo, pois estruturas prontas como LinkedList são proibidas.
 */

public class No {
    private Pet pet;
    private No proximo;

    public No(Pet pet) {
        this.pet = pet;
        this.proximo = null;
    }

    public Pet getPet() {
        return pet;
    }

    public No getProximo() {
        return proximo;
    }

    /**
     * Único setter da classe: o encadeamento muda na inserção e na remoção, mas o
     * pet guardado em cada nó nunca troca.
     */

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}
