import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Usuario> usuarios = new ArrayList();
    private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Emprestimo> emprestimos = new ArrayList();

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
   public Biblioteca( ){
        this.usuarios.add(new Bibliotecario("Seu José", "seujose2000@gmail.com", "Zezinho", "31996659292",0));
   }

    public void consultarObras() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Deseja buscar a obra por:");
            System.out.println("1 - ID");
            System.out.println("2 - Nome");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();
            Obra obra=null;
            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID da obra: ");
                    int idBusca = sc.nextInt();
                    obra = buscaId(idBusca);
                    if(obra!=null) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                    }else {
                        System.out.println("Obra com ID " + idBusca + " não encontrada.");
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome (título) da obra: ");
                    String tituloBusca = sc.nextLine();
                    obra= buscaTitulo(tituloBusca);
                    if(obra!=null) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                    }else {

                        System.out.println("Obra com título \"" + tituloBusca + "\" não encontrada.");
                    }
                    break;

                default:
                    System.err.println("Opção inválida.Tente novamente");
            }
        }
    }

    private Obra buscaTitulo(String tituloBusca) {
        for (Obra obra : obras) {
            if (obra.getTitulo().equalsIgnoreCase(tituloBusca)) {
                 return obra;
            }
        }
        return null;
    }

    private Obra buscaId(int idBusca) {
        for (Obra obra : obras) {
            if (obra.getId() == idBusca) {
                return obra;
            }
        }
        return null;
    }



    public ArrayList<Obra> getObras() {
        return obras;
    }

    private boolean podeEmprestarLivro(Usuario usuario){
        for (Emprestimo emprestimo : emprestimos) {
            if(emprestimo.getUsuario().equals(usuario)){
                if(emprestimo.isAtrasado()){
                    return false;
                }
            }

        }
        return true;
    }
    private Usuario buscaUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if(usuario.equals(nome)){
                return usuario;
            }
        }
        return null;
    }

    public boolean realizarDevolucao(String nome,String titulo){
        Obra obra = buscaTitulo(titulo);
        Usuario usuario = buscaUsuario(nome);
        if(obra!=null && usuario!=null){
            for (int i=0; i<emprestimos.size(); i++) {
                if(emprestimos.get(i).getUsuario().equals(usuario)){
                    if(emprestimos.get(i).getObra().equals(obra)){
                        obra.incrementarQuantidadeDisponivel();
                        emprestimos.remove(i);
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean realizarEmprestimo(String nomeUsuario, String titulo) {
        //Verificando se usuario existe
        Usuario usuario = buscaUsuario(nomeUsuario);
        if(podeEmprestarLivro(usuario) && usuario!=null) {
            //verificando se a obra existe
            Obra obra = buscaTitulo(titulo);
            if (usuario.verificarLimiteEmprestimo() && obra != null && obra.getQuantDisponivel() > 0) {
                usuario.decrementarLimiteEmprestimo();
                obra.decrementarQuantidadeDisponivel();
                emprestimos.add(new Emprestimo(usuario.getNome(), obra.getTitulo(), LocalDate.now(), null));
                return true;
            }
            return false;
        }
        return false;
    }


    public void carregarDadosAcervo() {
        // Carregar dados de obras a partir do arquivo CSV
        String arquivo = "acervoAtualizado.txt";
        File file = new File(arquivo);
        if(!file.exists()){
            arquivo = "acervo.csv";
            file=new File(arquivo);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            linha=reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                if(pedacosLinha.length>=3) {
                    int id = Integer.parseInt(pedacosLinha[0]);
                    String nome = pedacosLinha[1];
                    int quantidade = Integer.parseInt(pedacosLinha[2]);
                    //String id, String titulo, String autor, String quantDisponivel
                    obras.add(new Obra(id, nome, quantidade));

                }
                linha = reader.readLine();
            }
            System.out.println("Arquivo "+arquivo+" carregado com sucesso!"  );
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }
    public void carregarDadosEmprestimo() {
        // Carregar dados de obras a partir do arquivo CSV
        String arquivo = "emprestimos.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            linha=reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                if(pedacosLinha.length>=4) {
                    String obra = pedacosLinha[0];
                    String nomeUsuario = pedacosLinha[1];
                  try {
                      LocalDate dataEmprestimo = LocalDate.parse(pedacosLinha[2]);
                      LocalDate dataDevolucao = LocalDate.parse(pedacosLinha[3]);

                      emprestimos.add(new Emprestimo(nomeUsuario, obra, dataEmprestimo, dataDevolucao));
                  }catch (DateTimeException erro) {
                      System.out.println("Formato de data Invalido.");
                  }

                }
                linha = reader.readLine();
            }
            System.out.println("Arquivo "+arquivo+" carregado com sucesso!"  );
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }


    public void descarregarDadosAcervo(){
        String arquivo = "acervoAtualizado.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))){
            writer.write("ID,Título,Quantidade");
            writer.newLine();
            for(Obra obra : obras){
                writer.write(obra.getId()+","+obra.getTitulo()+","+obra.getQuantDisponivel());
                writer.newLine();
            }
            System.out.println("Dados do acervo atualizado com sucesso.");

    }catch (IOException erro) {
        System.out.println("Erro ao salvar daddos do acervo");}
    }

    public void descarregarDadosEmprestimo(){
        String arquivo = "emprestimoAtualizado.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))){
            writer.write("Obra,Usuario,Data de Emprestimo, Data de Devolução");
            writer.newLine();
            for(Emprestimo emprestimo : emprestimos){
                writer.write(emprestimo.getObra()+","+emprestimo.getUsuario()+","+emprestimo.getDataEmprestimo()+","+emprestimo.getDataDevolucao());
                writer.newLine();
            }
            System.out.println("Dados de emprestimo atualizado com sucesso.");

        }catch (IOException erro) {
            System.out.println("Erro ao salvar daddos de Emprestimo");}

    }
    //mas somente o bibliotecário pode cadastrar novos usuários, estou na classe certa? não sei, perguntar para a Estella!
    public void cadastraUsuario() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o nome do usuário: ");
        String nome = sc.nextLine();
        System.out.println("Digite o email do usuário: ");
        String email = sc.nextLine();

        // Verifica se o email já está cadastrado
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                System.err.println("Erro: Email já cadastrado.");
                return;
            }
        }
        int tipoUsuario;
        do {
            System.out.println("Digite a senha do usuário: ");
            String senha = sc.nextLine();
            System.out.println("Selecione o tipo de usuário:");
            System.out.println("1 - Aluno");
            System.out.println("2 - Professor");
            tipoUsuario = sc.nextInt();

            Usuario novoUsuario = null;
            switch (tipoUsuario) {
                case 1:
                    System.out.println("Digite o número de matrícula do aluno: ");
                    int matricula = sc.nextInt();
                    System.out.println("Digite o curso do aluno: ");
                    sc.nextLine();
                    String curso = sc.nextLine();

                    novoUsuario = new Aluno(nome, email, senha, matricula, curso, 2); //limite de empréstimos para alunos é 2
                    break;

                case 2:
                    System.out.println("Digite o departamento do professor: ");
                    String departamento = sc.nextLine();

                    novoUsuario = new Professor(nome, email, senha, departamento);
                    break;

                default:
                    System.err.println("Opção inválida.Tente novamente");

            }
        }while(tipoUsuario<1|| tipoUsuario>2);

    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}