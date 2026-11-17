import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner teclado = new Scanner(System.in);
    private static TabelaHash tabela;

    public static void main(String[] args) throws IOException {
        tabela = new TabelaHash(16,2);
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
                case 5: tabela.exibirEstatisticas(); break;
                case 6: organizarSalaEspera();break;
                case 7: modoPassoAPasso(); break;
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
        System.out.println("║  5 - Estatísticas da tabela              ║");
        System.out.println("║  6 - Organizar sala de espera            ║");
        System.out.println("║  7 - Modo Passo a Passo                  ║");
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

    private static void reconstruirTabela() {
        int m = lerInteiro("Novo tamanho M: ");
        int tipo = lerInteiro("Função hash (1 = soma, 2 = grupo): ");
        if (m < 1 || (tipo != 1 && tipo != 2)) {
            System.out.println("Valores inválidos.");
            return;
        }
        TabelaHash nova = new TabelaHash(m, tipo);
        for (Pet p : tabela.listarTodos()) {
            nova.inserir(p);
        }
        tabela = nova;
        System.out.println("Tabela reconstruída com M = " + m + " e função " + tipo + ".");
    }

    private static void organizarSalaEspera() {
        ArrayList<Pet> lista = tabela.listarTodos();
        Pet[] original = new Pet[lista.size()];
        for (int i = 0; i < original.length; i++) {
            original[i] = lista.get(i);
        }

        System.out.println("1 - Bubble Sort  2 - Insertion Sort  3 - Quick Sort  4 - Todos");
        int opcao = lerInteiro("Algoritmo: ");
        if (opcao < 1 || opcao > 4) {
            System.out.println("Opção inválida.");
            return;
        }

        for (int alg = 1; alg <= 3; alg++) {
            if (opcao != 4 && opcao != alg) continue;

            Pet[] copia = Ordenacao.copiar(original);
            Contador c = new Contador();
            String nome;

            if (alg == 1) { nome = "Bubble Sort";    Ordenacao.bubbleSort(copia, c); }
            else if (alg == 2) { nome = "Insertion Sort"; Ordenacao.insertionSort(copia, c); }
            else { nome = "Quick Sort"; Ordenacao.quickSort(copia, c); }

            System.out.println();
            System.out.println("=== " + nome + " ===");
            System.out.println("Ordenado corretamente? " + Ordenacao.estaOrdenado(copia));
            System.out.println("Comparações: " + c.comparacoes + " | Trocas: " + c.trocas);

            if (opcao != 4) {
                System.out.println("Fila da sala de espera:");
                for (int i = 0; i < copia.length; i++) {
                    System.out.println((i + 1) + "º " + copia[i]);
                }
            }
        }
    }

    private static void modoPassoAPasso() {
        ArrayList<Pet> lista = tabela.listarTodos();

        int n = lerInteiro("Quantos pets na sala de espera (2 a 10)? ");
        if (n < 2 || n > 10) {
            System.out.println("Escolha entre 2 e 10.");
            return;
        }
        if (n > lista.size()) {
            System.out.println("A tabela só tem " + lista.size() + " pets.");
            return;
        }

        Pet[] original = new Pet[n];
        for (int i = 0; i < n; i++) {
            original[i] = lista.get(i);
        }

        System.out.println("Fila inicial:");
        for (Pet p : original) System.out.println("  " + p);

        System.out.println("1 - Bubble Sort  2 - Insertion Sort  3 - Quick Sort");
        int alg = lerInteiro("Algoritmo: ");
        if (alg < 1 || alg > 3) {
            System.out.println("Opção inválida.");
            return;
        }

        Pet[] copia = Ordenacao.copiar(original);
        Contador c = new Contador();

        if (alg == 1) PassoAPasso.bubbleSort(copia, c);
        else if (alg == 2) PassoAPasso.insertionSort(copia, c);
        else PassoAPasso.quickSort(copia, c);

        System.out.println();
        System.out.println("Fila final (ordenada? " + Ordenacao.estaOrdenado(copia) + "):");
        for (Pet p : copia) System.out.println("  " + p);
        System.out.println("Total → comparações: " + c.comparacoes + " | trocas: " + c.trocas);
    }

}