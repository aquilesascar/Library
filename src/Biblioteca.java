import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Usuario> usuarios = new ArrayList();
    private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Emprestimo> emprestimos = new ArrayList();
    private ArrayList<Usuario> usuarioLogado;

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
                    System.err.println("Opção inválida!");

            }
        }
    }

    public void menuUsuario() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Principal");
            System.out.println("1. Consultar obras");
            System.out.println("2. Realizar empréstimo");
            System.out.println("3. Realizar devolução");
            System.out.println("4. Logout");
            int opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    //consultaObras();
                    break;
                case 2:
                    //emprestimo();
                    break;
                case 3:
                    //devolucao();
                    break;
                case 4:
                    //logout();
                    break;
                default:
                    System.err.println("Opção inválida!");

            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Digite seu email: ");
        String email = scanner.next();
        System.out.println("Digite sua senha: ");
        String senha = scanner.next();

        for (int i =0; i < usuarios.size(); i++) {

            Usuario u = usuarios.get(i); //pegue o usuário do ArrayList
            if (u.getEmail().equals(email) && u.getSenha().equals(senha) ) { //verifique email e senha
                usuarioLogado = usuarios; //armazene o usuário logado
                System.out.println("Bem-vindo, " + u.getNome() + "!");
                //menuUsuario(scanner);
                return;
            }
        }
        System.out.println("Email ou senha inválidos. Tente novamente.");

    }

    private void menuUsuario(Scanner scanner) {

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
            System.out.println("Dados das obras carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados de obras: " + e.getMessage());
        }
    }
}


