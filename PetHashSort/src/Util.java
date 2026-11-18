/**
 * Funções auxiliares usadas por mais de uma classe.
 */

import java.text.Normalizer;

public class Util {

    /**
     * Normaliza um nome para ser usado como chave: sem acento, em maiúsculas e sem
     * espaços nas pontas. Exemplos: "Paçoca" vira "PACOCA" e "  thor " vira "THOR".
     * Funcionamento: a forma NFD separa a letra do acento, o \p{M} remove as marcas de
     * acento e toUpperCase/trim padronizam o resto. Assim "Luna" e "luna" caem no mesmo
     * balde e a busca ignora acentos e diferença entre maiúsculas e minúsculas.
     */

    public static String normalizar(String texto) {
        String decomposto = Normalizer.normalize(texto, Normalizer.Form.NFD);

        String semAcento = decomposto.replaceAll("\\p{M}", "");

        return semAcento.toUpperCase().trim();
    }
}
