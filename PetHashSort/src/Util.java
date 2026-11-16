import java.text.Normalizer;

public class Util {

    public static String normalizar(String texto) {
        String decomposto = Normalizer.normalize(texto, Normalizer.Form.NFD);

        String semAcento = decomposto.replaceAll("\\p{M}", "");

        return semAcento.toUpperCase().trim();
    }
}
