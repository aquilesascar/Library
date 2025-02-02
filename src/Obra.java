import java.time.LocalDate;

public class Obra {
    //variáveis
    private int id;
    private String titulo;
    private int quantDisponivel;

    //construtor
    public Obra(int id, String titulo, int quantDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.quantDisponivel = quantDisponivel;
    }

    //Get´s
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getQuantDisponivel() {
        return quantDisponivel;
    }

    //Método toString para printar objeto.
    @Override
    public String toString() {
        return "ID ='" + id + '\'' +
                ", Título ='" + titulo + '\'' +
                ", Quantidade disponível =" + quantDisponivel;
    }

    //método que incrementaa a quantidade dispoível da obra
    public void incrementarQuantidadeDisponivel() {
        quantDisponivel++;
    }

    //método que decrementa a quantidade dispoível da obra
    public void decrementarQuantidadeDisponivel() {
        quantDisponivel--;
    }
}
