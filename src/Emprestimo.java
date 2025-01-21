import java.time.LocalDate;

class Emprestimo {
    private Usuario usuario;
    private Obra obra;
    private LocalDate dataEmprestimo;
    private int diasEmprestado;

    public Emprestimo(Usuario usuario, Obra obra, LocalDate dataEmprestimo) {
        this.usuario = usuario;
        this.obra = obra;
        this.dataEmprestimo = dataEmprestimo;
        this.diasEmprestado= 7;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Obra getObra() {
        return obra;
    }

    public boolean isAtrasado() {
        LocalDate dataDevolucaoLocalDate = dataEmprestimo.plusDays(diasEmprestado);
        return LocalDate.now().isAfter(dataDevolucaoLocalDate);
    }

}
