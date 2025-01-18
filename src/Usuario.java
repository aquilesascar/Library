public class Usuario {
    String nome;
    String email;
    String senha;

    public boolean login(String nome, String senha) {
        //implementar
        return this.nome.equals(nome) && this.senha.equals(senha);
    }

    //implementar logout
    public void logout(){

    }
}
