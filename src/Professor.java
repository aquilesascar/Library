import java.util.Scanner;

public class Professor extends Usuario{
    String departamento;
    int limiteEmprestimo = 10;


    public Professor(String nome, String email, String senha, String departamento, int limiteEmprestimo) {
        super(nome, email, senha, 10);
        this.departamento = departamento;
        this.limiteEmprestimo = limiteEmprestimo;
    }

    @Override
    protected void menu(Usuario usuarios) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("MENU DO PROFESSOR");
            System.out.println("1. Consultar obras");
            System.out.println("2. Realizar empréstimo");
            System.out.println("3. Realizar devolução");
            System.out.println("4. Logout");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    //consultarObras();
                    break;
                case 2:
                    System.out.print("Digite o título da obra para empréstimo: ");
                    sc.nextLine();
                    String tituloEmprestimo = sc.nextLine();
                    //emprestimo(usuarios, tituloEmprestimo);
                    break;
                case 3:
                    System.out.print("Digite o título da obra para devolução: ");
                    sc.nextLine();
                    String tituloDevolucao = sc.nextLine();
                    //devolucao(usuarios, tituloDevolucao);
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
