import java.util.Scanner;

public class Aluno extends MembroBiblioteca{
    int matricula;
    String curso;


    public Aluno(String nome, String email, String senha, int matricula, String curso, int limiteEmprestimo) {
        super(nome, email, senha, 2);
        this.matricula = matricula;
        this.curso = curso;
    }


}
