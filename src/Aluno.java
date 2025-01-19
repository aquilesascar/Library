public class Aluno extends Usuario{
    int matricula;
    String curso;
    int limiteEmprestimo = 2;

    public Aluno(String nome, String email, String senha, int matricula, String curso, int limiteEmprestimo) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.curso = curso;
    }


    //método pra fazer emprestimo
    public void emprestimo(){
        //pegar livros emprestado, percorres array de livros
    }

    //metodo para devolução
    public void devolucao(){
        //
    }

    //método para verificar se está no limite ou não
    public boolean verificarLimiteEmprestimo(){

        return false;
    }
}
