import java.util.ArrayList;
import java.util.Scanner;

public class Aluno extends Usuario{
    int matricula;
    String curso;
    int limiteEmprestimo = 2;

    public Aluno(String nome, String email, String senha, int matricula, String curso, int limiteEmprestimo) {
        super(nome, email, senha, 2);
        this.matricula = matricula;
        this.curso = curso;
    }


    // Método para fazer empréstimo
    public void emprestimo(String nomeUsuario, ArrayList<Usuario> usuarios) {
        //percore o array de usuários para encontrar o usuário que deseja fazer o empréstimo
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario)) {
                if (usuario.verificarLimiteEmprestimo()) {
                    usuario.decrementarLimiteEmprestimo();
                    System.out.println("Empréstimo realizado com sucesso para " + nomeUsuario + "!");
                } else {
                    System.out.println("Você atingiu o limite de empréstimos, " + nomeUsuario + ".");
                }
                return;
            }
        }
        //caso o usuário não seja encontrado no array
        System.out.println("Usuário " + nomeUsuario + " não encontrado.");
    }


    // Método para devolução
    public void devolucao(String nomeUsuario, String tituloObra, ArrayList<Usuario> usuarios, ArrayList<Obra> obras) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario)) {
                for (Obra obra : obras) {
                    if (obra.getTitulo().equals(tituloObra)) {
                        usuario.incrementarLimiteEmprestimo();
                        obra.incrementarQuantidadeDisponivel();

                        System.out.println("Devolução realizada com sucesso para " + nomeUsuario + "!");
                        return;
                    }
                }
                System.out.println("Obra não encontrada no sistema: " + tituloObra);
                return;
            }
        }
        System.out.println("Usuário " + nomeUsuario + " não encontrado.");
    }


    //método para verificar se está no limite ou não
    public boolean verificarLimiteEmprestimo(){
        if (limiteEmprestimo >= 0){
            return true;
        }
        return false;
    }

    @Override
    protected void menu(Usuario usuarios) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("MENU DO ALUNO");
            System.out.println("1 - Consultar obras");
            System.out.println("2 - Realizar emprestimo");
            System.out.println("3 - Realizar devolucao");
            System.out.println("4 - Logout");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            switch(opcao){
                case 1:
                    //consultarObras();
                    break;
                case 2:
                    if (verificarLimiteEmprestimo()){
                        System.out.println("Você não pode mais realizar emprestimo!");
                    }else{
                        //emprestimo(nomeUsuario, usuarios);
                    }
                    break;
                case 3:
                    //devolucao();
                    break;
                case 4:
                    //logout();
                    break;
            }
        }
    }
}
