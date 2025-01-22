import java.util.Scanner;

public class Professor extends MembroBiblioteca{
    String departamento;
    public Professor(String nome, String email, String senha, String departamento) {
        super(nome, email, senha, 10);
        this.departamento = departamento;
    }

}
