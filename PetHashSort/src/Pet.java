/**
 * Ficha de um pet (paciente da clínica). Guarda o nome como foi digitado, para
 * exibição, e a chave normalizada (ex.: "PACOCA"), usada na tabela hash e calculada
 * uma vez no construtor. O microchip é int entre 10000000 e 99999999: comparar
 * inteiros é mais barato que comparar texto e o intervalo evita perder zeros à
 * esquerda. Os dados não mudam depois de criados, por isso não há setters.
 */

public class Pet {
    private int microchip;
    private String nome;
    private String chave;
    private String especie;
    private String mania;

    /**
     * Cria um pet e calcula sua chave a partir do nome.
     */

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

    /**
     * Texto da ficha, usado na busca, na exibição da fila e na narração do passo a passo.
     */

    @Override
    public String toString() {
        return nome + " (" + microchip + ") " + especie + " " + mania;
    }
}
