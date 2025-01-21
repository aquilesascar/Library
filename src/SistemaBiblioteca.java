import java.util.Scanner;

public class SistemaBiblioteca {
    Biblioteca IfOuroBranco = new Biblioteca();
    public void menuInicial(Biblioteca biblioteca) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("BIBLIOTECA MUNICIPAL DE OURO BRANCO");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    login(biblioteca);
                    break;
                case 2:
                    System.out.println("Saindo do sistema...");
                    return; // Encerra o programa
                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }
    private void login( Biblioteca biblioteca) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu email: ");
        String email = scanner.next();
        System.out.println("Digite sua senha: ");
        String senha = scanner.next();

        for (int i =0; i < biblioteca.getUsuarios().size(); i++) {

            Usuario u = biblioteca.getUsuarios().get(i); //pegue o usuário do ArrayList
            if (u.getEmail().equals(email) && u.getSenha().equals(senha) ) { //verifique email e senha

                System.out.println("Bem-vindo, " + u.getNome() + "!");
                biblioteca.getUsuarios().get(i).menu(u, biblioteca);
                return;
            }
        }
        System.out.println("Email ou senha inválidos. Tente novamente.");

    }

}
