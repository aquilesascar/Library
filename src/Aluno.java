import java.util.ArrayList;
import java.util.Scanner;

public class Aluno extends Usuario{
    int matricula;
    String curso;


    public Aluno(String nome, String email, String senha, int matricula, String curso, int limiteEmprestimo) {
        super(nome, email, senha, 2);
        this.matricula = matricula;
        this.curso = curso;
    }

    @Override
    protected void menu(Usuario usuarios) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("MENU DO ALUNO");
            System.out.println("1 - Consultar obras");
            System.out.println("2 - Realizar emprestimo");
            System.out.println("3 - Realizar devolucao");
            System.out.println("4 - Logout");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    //consultarObras();
                    break;
                case 2:
                    if (verificarLimiteEmprestimo()){
                        System.out.println("Você não pode mais realizar emprestimo!");
                    }else{
                        //emprestimo(nomeUsuario, usuarios);
                    }
                    break;
                case 3:
                    //devolucao();
                    break;
                case 4:
                    //logout();
                    break;
            }
        }
    }
}
