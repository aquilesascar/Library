class Emprestimo {
    private Usuario usuario;
    private Obra obra;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(Usuario usuario, Obra obra, String dataEmprestimo, String dataDevolucao) {
        this.usuario = usuario;
        this.obra = obra;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

}
