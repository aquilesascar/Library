public class Professor extends Usuario{
    String departamento;
    int limiteEmprestimo = 10;


    public Professor(String nome, String email, String senha, String departamento, int limiteEmprestimo) {
        super(nome, email, senha);
        this.departamento = departamento;
        this.limiteEmprestimo = limiteEmprestimo;
    }
}
