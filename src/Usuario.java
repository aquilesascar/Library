abstract class Usuario {
    //variáveis
    private String nome;
    private String email;
    private String senha;

    //construtor
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    //Get´s.
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    //método abstrato do Menu, pois são necessários vários Menu´s com funções diferentes
    protected abstract void menu(Usuario usuario, Biblioteca biblioteca);
}

