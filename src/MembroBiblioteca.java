import java.util.Scanner;

public abstract class MembroBiblioteca extends Usuario {
    public MembroBiblioteca(String nome, String email, String senha, int limiteEmprestimo) {
        super(nome, email, senha, limiteEmprestimo);
    }

    @Override
    protected void menu(Usuario usuario, Biblioteca biblioteca) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Consultar obras");
            System.out.println("2. Realizar empréstimo");
            System.out.println("3. Realizar devolução");
            System.out.println("4. Logout");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    biblioteca.consultarObras();
                    break;
                case 2:
                    System.out.print("Digite o título da obra para empréstimo: ");
                    sc.nextLine();
                    String tituloEmprestimo = sc.nextLine();
                    if(!biblioteca.realizarEmprestimo(usuario,tituloEmprestimo)){
                        System.out.println("Emprestimo feito com sucesso!");
                    }else{
                        System.out.println("Obra não encontrada!");
                    }
                    break;
                case 3:
                    System.out.print("Digite o título da obra para devolução: ");
                    sc.nextLine();
                    String tituloDevolucao = sc.nextLine();
                    biblioteca.realizarDevolucao(usuario,tituloDevolucao);
                    break;
                case 4:
                    System.out.println("Logout realizado com sucesso.");
                    return;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        }
    }

}


