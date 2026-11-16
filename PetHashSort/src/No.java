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

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}
