import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Pet> pets = LeitorArquivo.ler("dados/pets.txt");

        System.out.println("Total: " + pets.size());
        System.out.println();

        for (Pet p : pets) {
            System.out.println(p);
            System.out.println("   nome: " + p.getNome() + " -> chave: " + p.getChave());
        }
    }
}