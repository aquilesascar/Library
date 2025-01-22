abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    private int limiteEmprestimo;

    public Usuario(String nome, String email, String senha, int limiteEmprestimo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.limiteEmprestimo = limiteEmprestimo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    protected abstract void menu(Usuario usuario ,Biblioteca biblioteca);

    protected boolean verificarLimiteEmprestimo() {
        return limiteEmprestimo > 0;
    }

    public void decrementarLimiteEmprestimo() {
        if (limiteEmprestimo > 0) {
            limiteEmprestimo--;
        }
    }

    protected void incrementarLimiteEmprestimo() {
            limiteEmprestimo++;
        }


}