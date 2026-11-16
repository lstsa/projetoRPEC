public class Pet {
    private int microchip;
    private String nome;
    private String chave;
    private String especie;
    private String mania;

    public Pet(int microchip,String nome, String especie, String mania) {
        this.microchip = microchip;
        this.nome = nome;
        this.chave = Util.normalizar(nome);
        this.especie = especie;
        this.mania = mania;
    }

    public int getMicrochip() {
        return microchip;
    }

    public String getNome() {
        return nome;
    }

    public String getChave() {
        return chave;
    }

    public String getEspecie() {
        return especie;
    }

    public String getMania() {
        return mania;
    }

    @Override
    public String toString() {
        return nome + " (" + microchip + ") " + especie + " " + mania;
    }
}
