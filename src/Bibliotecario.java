public class Bibliotecario extends Usuario{
    String telefone;
    int totalDevolucoes = 0;

    public Bibliotecario(String nome, String email, String senha, String telefone, int totalDevolucoes) {
        super(nome, email, senha);
        this.telefone = telefone;
        this.totalDevolucoes = totalDevolucoes;
    }


    public void cadastraUsuario(){

    }

    public void cadastraDevolucao(){

    }


}
