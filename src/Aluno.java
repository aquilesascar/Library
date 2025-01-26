import java.util.Scanner;

public class Aluno extends MembroBiblioteca{
    String matricula;
    String curso;


    public Aluno(String nome, String email, String senha, String matricula, String curso, int livrosEmprestados) {
        super(nome, email, senha, 2, livrosEmprestados);
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

}
