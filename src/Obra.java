import java.time.LocalDate;

public class Obra {
    private int id;
    private String titulo;
    private int quantDisponivel;

    public Obra(int id, String titulo, int quantDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.quantDisponivel = quantDisponivel;
    }



    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getQuantDisponivel() {
        return quantDisponivel;
    }

    @Override
    public String toString() {
        return "ID ='" + id + '\'' +
                ", Título ='" + titulo + '\'' +
                ", Quantidade disponível =" + quantDisponivel;
    }


    public void incrementarQuantidadeDisponivel() {
        quantDisponivel++;
    }

    public void decrementarQuantidadeDisponivel() {
        quantDisponivel--;
    }
}
