import java.time.LocalDate;

public class Obra {
    //variáveis
    private int id; //ID da obra
    private String titulo; //Tiítulo da Obra
    private int quantDisponivel; //Quantidade disponível da obra

    //construtor
    public Obra(int id, String titulo, int quantDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.quantDisponivel = quantDisponivel;
    }

    public int getId() {
        return id;
    } //Retorna o ID

    public String getTitulo() {
        return titulo;
    } //Retorna o título

    public int getQuantDisponivel() {
        return quantDisponivel;
    } //Retorna aquantidade disponível

    //Método toString para printar objeto
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
