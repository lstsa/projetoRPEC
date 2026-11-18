import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Lê o arquivo de dados dos pets (formato: microchip;nome;especie;mania, um por
 * linha). O separador é ";" porque as manias podem conter vírgulas.
 */

public class LeitorArquivo {

    /**
     * Lê o arquivo em UTF-8 e devolve a lista de pets válidos. Linhas vazias e
     * comentários (#) são ignorados. Cada linha é validada em sequência (4 campos,
     * número válido, 8 dígitos e microchip único); as inválidas são rejeitadas com aviso
     * do número da linha, sem interromper a leitura. Ao final, mostra um resumo. O
     * ArrayList guarda apenas os dados lidos; a tabela hash e a ordenação não dependem dele.
     */

    public static ArrayList<Pet> ler(String caminho) throws IOException {
        ArrayList<Pet> pets = new ArrayList<>();
        int numeroLinha = 0;
        int rejeitadas = 0;

        try (BufferedReader leitor = Files.newBufferedReader(
                Paths.get(caminho), StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                linha = linha.trim();

                if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes.length != 4) {
                    System.out.println("Linha " + numeroLinha + " ignorada: esperado 4 campos, achei " + partes.length);
                    rejeitadas++;
                    continue;
                }

                int microchip;
                try {
                    microchip = Integer.parseInt(partes[0].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Linha " + numeroLinha + " ignorada: microchip inválido (" + partes[0] + ")");
                    rejeitadas++;
                    continue;
                }

                if (microchip < 10000000 || microchip > 99999999) {
                    System.out.println("Linha " + numeroLinha + " ignorada: microchip fora de 8 dígitos (" + microchip + ")");
                    rejeitadas++;
                    continue;
                }

                if (existeMicrochip(pets, microchip)) {
                    System.out.println("Linha " + numeroLinha + " ignorada: microchip repetido (" + microchip + ")");
                    rejeitadas++;
                    continue;
                }

                pets.add(new Pet(microchip, partes[1].trim(), partes[2].trim(), partes[3].trim()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        System.out.println("Leitura concluída: " + pets.size() + " pets válidos, " + rejeitadas + " linhas rejeitadas.");
        return pets;
    }

    /**
     * Verifica por busca linear se o microchip já foi lido. É suficiente porque a
     * leitura acontece uma única vez e com poucos pets.
     */

    private static boolean existeMicrochip(ArrayList<Pet> pets, int microchip) {
        for (Pet pet : pets) {
            if (pet.getMicrochip() == microchip) {
                return true;
            }
        }
        return false;
    }
}
