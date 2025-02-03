import java.util.Scanner;

//classe Bibliotecario herda de Usuario
public class Bibliotecario extends Usuario{
    String telefone;

    //construtor
    public Bibliotecario(String nome, String email, String senha, String telefone) {
        super(nome, email, senha); //limite de emprestimo não se aplica para o bibliotecario
        this.telefone = telefone;

    }

    //método que é subscrito pois é necessário vários Menus com diferentes funções
    @Override
    public void menu(Usuario usuarios, Biblioteca biblioteca) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            //opções
            System.out.println("MENU DO BIBLIOTECÁRIO");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Registrar devolução");
            System.out.println("3. Relatório de Livros Atrasados");
            System.out.println("4. Relatório de Livros Emprestados");
            System.out.println("5. Logout");
            System.out.print("Escolha uma opção: ");
            //leitura da opção
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    //Chamada de método para cadastrar o usuário
                    biblioteca.cadastraUsuario();
                    break;
                case 2:
                    System.out.println("Título do livro:");
                    sc.nextLine();
                    String tituloLivro = sc.nextLine();
                    System.out.println("Nome do usuário:");
                    String nomeUsuario = sc.nextLine();
                    //verificação se foi possível fazer a devolução do livro
                    if(biblioteca.realizarDevolucao(nomeUsuario,tituloLivro)){
                        //Mensagem de suceso
                        System.out.println("Devolução realizada com sucesso!");
                    }else{
                        //Mensagem caso não seja possível fazer a devolução
                        System.out.println("Ocorreu um erro. Não foi possivél fazer a devolução.");
                    };
                    break;
                case 3:
                    //Chama método que faz relatórios de livros atrasados
                    biblioteca.relatorioAtrasados();
                    break;
                case 4:
                    //Chama método que faz relatórios de livros emprestados
                    biblioteca.relatorioEmprestados();
                    break;

                case 5:
                    System.out.println("Logout realizado com sucesso.");
                    sc.nextLine();
                    return; // Sai do menu
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        }
    }

}
