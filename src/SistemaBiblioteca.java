import java.util.Scanner;

public class SistemaBiblioteca {

    //menu inicial da biblioteca
    public static void menuInicial(Biblioteca biblioteca) {
        // Criação de um objeto Scanner para leitura de entrada do usuário
        Scanner sc = new Scanner(System.in);

        // Loop infinito para manter o menu rodando até que o usuário escolha sair
        while (true) {
            // Exibe o cabeçalho e as opções do menu
            System.out.println("BIBLIOTECA MUNICIPAL DE OURO BRANCO");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.println("Escolha uma opção: ");

            // Lê a opção escolhida pelo usuário
            int opcao = sc.nextInt();

            // Estrutura switch para processar a escolha do usuário
            switch (opcao) {
                case 1:
                    // Se a opção for 1, chama o método login passando a biblioteca como parâmetro
                    login(biblioteca);
                    break;
                case 2:
                    // Se a opção for 2, exibe mensagem de saída e chama métodos para descarregar dados
                    System.out.println("Saindo do sistema...");
                    biblioteca.descarregarDadosAcervo();       // Salva os dados do acervo antes de sair
                    biblioteca.descarrecarUsuarioAluno();     // Salva os dados dos alunos
                    biblioteca.descarrecarUsuarioProfessor(); // Salva os dados dos professores
                    biblioteca.descarregarDadosEmprestimo();  // Salva os dados dos empréstimos

                    return; // Encerra o método, finalizando o programa
                default:
                    // Se a opção for inválida, exibe uma mensagem de erro
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

        //busca pelo Array de usuários
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