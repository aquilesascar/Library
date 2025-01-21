import java.util.Scanner;

public class Bibliotecario extends Usuario{
    String telefone;
    int totalDevolucoes = 0;

    public Bibliotecario(String nome, String email, String senha, String telefone, int totalDevolucoes) {
        super("Seu José", "seujose2000@gmail.com", "Zezinho", 0); //limite de emprestimo não se aplica para o bibliotecario
        this.telefone = "31996659292";
        this.totalDevolucoes = totalDevolucoes;
    }

    //acho que esse não fica aqui pq nao tem como acessar os arrays, mas vou deixar pra perguntar antes




    @Override
    public void menu(Usuario usuarios, Biblioteca biblioteca) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("MENU DO BIBLIOTECÁRIO");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Registrar devolução");
            System.out.println("3. Consultar total de devoluções realizadas");
            System.out.println("4. Logout");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    biblioteca.cadastraUsuario();
                    break;
                case 2:
                    System.out.println("Título do livro:");
                    sc.nextLine();
                    String tituloLivro = sc.nextLine();
                    System.out.println("Nome do usuário:");
                    String nomeUsuario = sc.nextLine();
                    if(biblioteca.realizarDevolucao(nomeUsuario,tituloLivro)){
                        System.out.println("Devolução realizada com sucesso!");
                    }else{
                        System.out.println("Ocorreu um erro. Não foi possivél fazer a devolução.");
                    };
                    break;
                case 3:
                    System.out.println("Total de devoluções realizadas: " + totalDevolucoes);
                    break;
                case 4:
                    System.out.println("Logout realizado com sucesso.");
                    return; // Sai do menu
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        }
    }

}
