import java.util.Scanner;

//MembroBiblioteca herda de Usuario
public abstract class MembroBiblioteca extends Usuario {
    private int limiteEmprestimo;
    private int livrosEmprestados;

    //Construtor
    public MembroBiblioteca(String nome, String email, String senha, int limiteEmprestimo, int livrosEmprestados) {
        super(nome, email, senha);
        this.limiteEmprestimo = limiteEmprestimo;
        this.livrosEmprestados = livrosEmprestados;
    }

    public int getlivrosEmprestados() {
        return livrosEmprestados;
    }

    //Método menu subscrito
    @Override
    protected void menu(Usuario usuario, Biblioteca biblioteca) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Consultar obras");
            System.out.println("2. Realizar empréstimo");
            System.out.println("3. Logout");
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
                    if(biblioteca.realizarEmprestimo(usuario.getNome(),tituloEmprestimo)){
                        System.out.println("Emprestimo feito com sucesso!");
                    }else{
                        System.out.println("Emprestimo não realizado!.");
                    }
                    break;

                case 3:
                    System.out.println("Logout realizado com sucesso.");
                    sc.nextLine();
                    return;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        }
    }

    //método booleano que verifica limite de empréstimo, professor tem 10 e aluno tem 2 livros de limite
    protected boolean verificarLimiteEmprestimo() {
        return (limiteEmprestimo -livrosEmprestados) > 0;
    }

    //método que incrementa os livros emprestados
    public void incrementarLivrosEmprestados() {
            livrosEmprestados++;
    }

    //método que decrementa os livros emprestados
    public void decrementarLivrosEmprestados() {
        livrosEmprestados--;
    }





}




