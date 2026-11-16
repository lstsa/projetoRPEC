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

    private static void cadastrarPet() {
        int microchip = lerInteiro("Microchip (8 dígitos): ");
        if (microchip < 10000000 || microchip > 99999999) {
            System.out.println("Microchip deve ter 8 dígitos (10000000 a 99999999).");
            return;
        }
        if (tabela.buscarPorMicrochip(microchip) != null) {
            System.out.println("Já existe um pet com esse microchip.");
            return;
        }

        System.out.print("Nome: ");
        String nome = teclado.nextLine().trim();
        System.out.print("Espécie: ");
        String especie = teclado.nextLine().trim();
        System.out.print("Mania: ");
        String mania = teclado.nextLine().trim();

        if (nome.isEmpty() || especie.isEmpty() || mania.isEmpty()) {
            System.out.println("Nenhum campo pode ficar vazio.");
            return;
        }
        if (nome.contains(";") || especie.contains(";") || mania.contains(";")) {
            System.out.println("Não use ponto e vírgula nos textos.");
            return;
        }

        tabela.inserir(new Pet(microchip, nome, especie, mania));
        System.out.println("Pet cadastrado!");
    }

    private static void buscarPets() {
        System.out.print("Nome do pet: ");
        String nome = teclado.nextLine();

        ArrayList<Pet> resultado = tabela.buscarPorNome(nome);
        if (resultado.isEmpty()) {
            System.out.println("Nenhum pet encontrado com esse nome.");
        } else {
            System.out.println(resultado.size() + " pet(s) encontrado(s):");
            for (Pet p : resultado) {
                System.out.println("  " + p);
            }
        }
    }

    private static void removerPet() {
        int microchip = lerInteiro("Microchip do pet a remover: ");

        Pet pet = tabela.buscarPorMicrochip(microchip);
        if (pet == null) {
            System.out.println("Nenhum pet com esse microchip.");
            return;
        }
        if (tabela.removerPorMicrochip(microchip)) {
            System.out.println("Removido: " + pet);
        }
    }
}