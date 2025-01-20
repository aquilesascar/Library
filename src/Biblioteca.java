import java.io.*;
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

    private void login(Scanner scanner) {
        System.out.println("Digite seu email: ");
        String email = scanner.next();
        System.out.println("Digite sua senha: ");
        String senha = scanner.next();

        for (int i =0; i < usuarios.size(); i++) {

            Usuario u = usuarios.get(i); //pegue o usuário do ArrayList
            if (u.getEmail().equals(email) && u.getSenha().equals(senha) ) { //verifique email e senha

                System.out.println("Bem-vindo, " + u.getNome() + "!");
                usuarios.get(i).menu(u);
                return;
            }
        }
        System.out.println("Email ou senha inválidos. Tente novamente.");

    }


    private void carregarDados() {
        // Carregar dados de obras a partir do arquivo CSV
        String arquivo = "acervo.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                int id=Integer.parseInt(pedacosLinha[0]);
                String nome=pedacosLinha[1];
                int quantidade = Integer.parseInt(pedacosLinha[2]);
                //String id, String titulo, String autor, String quantDisponivel
                obras.add(new Obra(id,nome,quantidade));
            }
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }

}


