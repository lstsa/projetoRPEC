import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner teclado = new Scanner(System.in);
    private static TabelaHash tabela;

    public static void main(String[] args) throws IOException {
        tabela = new TabelaHash(13);
        for (Pet p : LeitorArquivo.ler("dados/pets.txt")) {
            tabela.inserir(p);
        }

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha: ");

            switch (opcao) {
                case 1: cadastrarPet(); break;
                case 2: buscarPets(); break;
                case 3: removerPet(); break;
                case 4: tabela.exibir(); break;
                case 0: System.out.println("Fechando a clínica. Até logo!"); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║             PET HASH & SORT              ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  1 - Cadastrar pet                       ║");
        System.out.println("║  2 - Buscar pets por nome                ║");
        System.out.println("║  3 - Remover pet (microchip)             ║");
        System.out.println("║  4 - Mostrar fichário (tabela hash)      ║");
        System.out.println("║  0 - Fechar a clínica                    ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = teclado.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }
}