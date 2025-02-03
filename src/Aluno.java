import java.util.Scanner;
//Aluno herda da classe MembroBiblioteca
public class Aluno extends MembroBiblioteca{
    String matricula; //referente a matrícula do aluno
    String curso; //Referente ao curso do alnu

    //Construtor
    public Aluno(String nome, String email, String senha, String matricula, String curso, int livrosEmprestados) {
        super(nome, email, senha, 2, livrosEmprestados);
        this.matricula = matricula;
        this.curso = curso;
    }

    //Retorna matrícula
    public String getMatricula() {
        return matricula;
    }

    //Retorna o curso
    public String getCurso() {
        return curso;
    }
}
