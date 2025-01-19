import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<String> usuarios = new ArrayList();
    private ArrayList<String> obras = new ArrayList();
    private ArrayList<String> emprestimos = new ArrayList();

    public void menuInicial() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("BIBLIOTECA MUNICIPAL DE OURO BRANCO");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            switch (opcao) {
                case 1:

                    break;

                case 2:

                    break;

                default:
                    System.out.println("Opção inválida!");

            }
        }
    }

    private void carregarDados() {
        // Carregar dados de obras a partir do arquivo CSV
        String caminhoArquivo = "acervo.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");

                // Ignorar cabeçalho
                if (campos[0].equalsIgnoreCase("ID")) {
                    continue;
                }

                String id = campos[0].trim();
                String titulo = campos[1].trim();
                String quantDisponivel = campos[2].trim();

                obras.add(new Obra(id, titulo, quantDisponivel));
            }
            System.out.println("Dados de obras carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados de obras: " + e.getMessage());
        }
    }
}


