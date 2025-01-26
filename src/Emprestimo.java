import java.time.LocalDate;

class Emprestimo {
    private String usuario;
    private String obra;
    private LocalDate dataEmprestimo;
    private int diasEmprestado;
    private LocalDate dataDevolucao;

    public Emprestimo(String usuario, String obra, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.usuario = usuario;
        this.obra = obra;
        this.dataEmprestimo = dataEmprestimo;
        this.diasEmprestado= 7;
        this.dataDevolucao = dataDevolucao;

    }


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

    public boolean isAtrasado() {
        LocalDate dataDevolucaoLocalDate = dataEmprestimo.plusDays(diasEmprestado);
        return LocalDate.now().isAfter(dataDevolucaoLocalDate);
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        LocalDate data = dataEmprestimo.plusDays(diasEmprestado);
        return  "Obra emprestada: "+obra +'\''+
                "Usuario: "+usuario +'\''+
                "Data do Emprestimo: " + dataEmprestimo +'\''+
                "Data prevista para devolução: "+ data.getDayOfMonth()+"/"+data.getMonthValue()+"/"+data.getYear();
    }


}

