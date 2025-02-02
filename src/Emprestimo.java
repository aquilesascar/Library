import java.time.LocalDate;

class Emprestimo {
    //variáveis
    private String usuario;
    private String obra;
    private LocalDate dataEmprestimo;
    private int diasEmprestado;
    private LocalDate dataDevolucao;

    //construtor.
    public Emprestimo(String usuario, String obra, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.usuario = usuario;
        this.obra = obra;
        this.dataEmprestimo = dataEmprestimo;
        this.diasEmprestado= 7;
        this.dataDevolucao = dataDevolucao;

    }

    //Get´s
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getObra() {
        return obra;
    }

    //método que verifica se a pessoa está atrasada com o livro emprestado
    public boolean isAtrasado() {
        LocalDate dataDevolucaoLocalDate = dataEmprestimo.plusDays(diasEmprestado);
        return LocalDate.now().isAfter(dataDevolucaoLocalDate);
    }

    //Set da data de devolução
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    //método toString para melhor leitura
    @Override
    public String toString() {
        LocalDate data = dataEmprestimo.plusDays(diasEmprestado);
        return  "Obra emprestada: "+obra +'\''+
                "Usuario: "+usuario +'\''+
                "Data do Emprestimo: " + dataEmprestimo +'\''+
                "Data prevista para devolução: "+ data;
    }


}

