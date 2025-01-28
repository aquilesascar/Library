import java.util.Scanner;

public class SistemaBiblioteca {

    //menu inicial da biblioteca
    public static  void menuInicial(Biblioteca biblioteca) {
        String acervo= "acervo.csv";
        String dadosusuario="dadosDoUsuario.txt";
        String historicoDeEmprestimo="historicoDeEmprestimo.txt";
        String obras="obras.txt";

        biblioteca.carregarDados(acervo,obras);
        biblioteca

        Scanner sc = new Scanner(System.in);

        while (true) {
            //opções
            System.out.println("BIBLIOTECA MUNICIPAL DE OURO BRANCO");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.println("Escolha uma opção: ");
            //leitura da opção
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    login(biblioteca);
                    break;
                case 2:
                    System.out.println("Saindo do sistema...");
                    biblioteca.descarregarDadosAcervo();
                    biblioteca.descarrecarUsuarioAluno();
                    biblioteca.descarrecarUsuarioProfessor();
                    biblioteca.descarregarDadosEmprestimo();
                    return; // Encerra o programa
                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }
    //método de login
    private static void login( Biblioteca biblioteca) {
        Scanner scanner = new Scanner(System.in);
        //solicita email
        System.out.println("Digite seu email: ");
        String email = scanner.nextLine();
        //solicita senha
        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();

        //busca pelo Arrau de usuários
        for (int i =0; i < biblioteca.getUsuarios().size(); i++) {

            Usuario u = biblioteca.getUsuarios().get(i); //pegue o usuário do ArrayList
            if (u.getEmail().equals(email) && u.getSenha().equals(senha) ) { //verifique email e senha
                //print de mensagem
                System.out.println("Bem-vindo, " + u.getNome() + "!");
                biblioteca.getUsuarios().get(i).menu(u, biblioteca);
                return;
            }
        }
        System.err.println("Email ou senha inválidos. Tente novamente.");

    }

}