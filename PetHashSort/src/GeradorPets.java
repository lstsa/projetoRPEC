import java.util.Random;

public class GeradorPets {

    private static final String[] NOMES = {
            "Luna", "Thor", "Bob", "Mel", "Zeus", "Amora", "Lola", "Nina",
            "Fred", "Toddy", "Simba", "Frida", "Pipoca", "Paçoca", "Chico", "Pandora",
            "Amoroso", "Picachu", "Vampeta", "Imperador", "Profeta", "Fabuloso", "Rústico", "Chulapa"
    };
    private static final String[] SOBRENOMES = {
            "Jr", "Mini", "Neto", "Bella", "da Silva", "Rato", "Cabeção", "Pé de Anjo"
    };
    private static final String[] ESPECIES = {
            "cão", "gato", "cachorra", "gata", "coelho", "hamster", "papagaio", "calopsita",
            "furão", "tartaruga", "jabuti", "periquito", "porquinho-da-índia"
    };
    private static final String[] MANIAS = {
            "rouba meias", "late para o aspirador", "mia para a geladeira",
            "dorme em cima do teclado", "derruba copos da mesa",
            "amassa pãozinho", "esconde chaves", "persegue o rabo",
            "uiva para sirene", "bebe da torneira", "cava o quintal",
            "arranca teclas", "encara a parede", "corre de madrugada"
    };


    public static Pet[] gerarOrdenado(int n, long semente) {
        Random rnd = new Random(semente);
        Pet[] pets = new Pet[n];
        int fatia = (99999999 - 10000000 + 1) / n;

        for (int i = 0; i < n; i++) {
            int microchip = 10000000 + i * fatia + rnd.nextInt(fatia);

            String nome = NOMES[rnd.nextInt(NOMES.length)];
            if (rnd.nextInt(3) == 0) {
                nome = nome + " " + SOBRENOMES[rnd.nextInt(SOBRENOMES.length)];
            }
            String especie = ESPECIES[rnd.nextInt(ESPECIES.length)];
            String mania = MANIAS[rnd.nextInt(MANIAS.length)];

            pets[i] = new Pet(microchip, nome, especie, mania);
        }
        return pets;
    }

    public static Pet[] embaralhar(Pet[] original, long semente) {
        Pet[] v = Ordenacao.copiar(original);
        Random rnd = new Random(semente);
        for (int i = v.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Pet temp = v[i];
            v[i] = v[j];
            v[j] = temp;
        }
        return v;
    }

    public static Pet[] inverter(Pet[] original) {
        Pet[] v = new Pet[original.length];
        for (int i = 0; i < original.length; i++) {
            v[i] = original[original.length - 1 - i];
        }
        return v;
    }

    public static Pet[] gerarAleatorio(int n, long semente) {
        return embaralhar(gerarOrdenado(n, semente), semente + 1);
    }
}
