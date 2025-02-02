import java.util.Scanner;

//Classe Professor herda de MembroBiblioteca.
public class Professor extends MembroBiblioteca{
    String departamento;
    public Professor(String nome, String email, String senha, String departamento, int livorsEmprestados) {
        super(nome, email, senha, 10,livorsEmprestados);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }
}
