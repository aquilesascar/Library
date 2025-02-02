import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    //Array que guarda usuários
    private ArrayList<Usuario> usuarios = new ArrayList();
    //Array que guarda obras
    private ArrayList<Obra> obras = new ArrayList();
    //Array que guarda empréstimos
    private ArrayList<Emprestimo> emprestimos = new ArrayList();

    //retorna empréstimos
    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    //construtor com os dados do bibliotecário, que no caso, é o Seu José
    public Biblioteca() {
        this.usuarios.add(new Bibliotecario("Seu José", "seujose2000@gmail.com", "Zezinho", "31996659292", 0));
        //os métodos são chamados no construtor da biblioteca para já carregar todos os arquivos ao iniciar
        carregarDadosAcervo();
        carregarUsuariosAluno();
        carregarUsuariosProfessor();
        carregarDadosEmprestimo();
    }

    //método para consultar obras do acervo
    public void consultarObras() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            //opções de busca
            System.out.println("Deseja buscar a obra por:");
            System.out.println("1 - ID");
            System.out.println("2 - Nome");
            System.out.println("3- Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            //leitura da opção
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
                case 3:
                    System.out.println("Saindo do buscador...");
                    return;

                default:
                    System.err.println("Opção inválida.Tente novamente");
            }
        }
    }

    //método que percorre array de obras para buscar o título desejado
    private Obra buscaTitulo(String tituloBusca) {
        for (Obra obra : obras) {
            if (obra.getTitulo().equalsIgnoreCase(tituloBusca)) {
                 return obra;
            }
        }
        return null;
    }

    //método que percorre array de obras para buscar o id da obra desejada
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

    //método booleano que percorre array de emprestimos e retorna true caso possa emprestar ou falso se não puder
    private boolean podeEmprestarLivro(Usuario usuario) {
        for (Emprestimo emprestimo : emprestimos) {
            if(emprestimo.getUsuario().equals(usuario)){
                if(emprestimo.isAtrasado()){
                    return false;
                }
            }

        }
        return true;
    }

    //método busca usuário no array de usuários
    private Usuario buscaUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if(usuario.getNome().equalsIgnoreCase(nome)){
                return usuario;
            }
        }
        return null;
    }

    //método para realizar devolução do livro emprestado
    public boolean realizarDevolucao(String nome, String titulo) {
        //busca do título
        Obra obra = buscaTitulo(titulo);
        //busca usuário
        Usuario usuario = buscaUsuario(nome);
        //verifica se o usuário e a obra existem
        if (obra != null && usuario != null) {
            //percorre o array de emprestimos
            for (Emprestimo emprestimo : emprestimos) {
                //verifica usuário e obra
                if (emprestimo.getUsuario().equalsIgnoreCase(usuario.getNome())) {
                    if (emprestimo.getObra().equalsIgnoreCase(obra.getTitulo())) {
                        //set a data da devolução
                        emprestimo.setDataDevolucao(LocalDate.now());
                        //incrementa a quantidade do exemplar disponível
                        obra.incrementarQuantidadeDisponivel();
                        //verifica se usuário é uma instância de MembroBiblioteca
                        if (usuario instanceof MembroBiblioteca) {
                            //casting pra chamada do método de outra classe
                            ((MembroBiblioteca) usuario).decrementarLivrosEmprestados();
                        }
                        return true;
                    }
                }
            }
            //mensagens para alertar usuário
            System.out.println("Usuário ou Obra não constam na lista de livros emprestados");
            return false;
        }else {
            System.out.println("Obra ou usuário incorreto.");
            return false;
        }

    }

    public boolean realizarEmprestimo(String nomeUsuario, String titulo) {
        //Verificando se usuario existe
        Usuario usuario = buscaUsuario(nomeUsuario);
        if(usuario instanceof MembroBiblioteca) {
            if (podeEmprestarLivro(usuario) && ((MembroBiblioteca) usuario).verificarLimiteEmprestimo()) {
                //verificando se a obra existe
                Obra obra = buscaTitulo(titulo);
                if ( obra != null && obra.getQuantDisponivel() > 0) {
                     ((MembroBiblioteca) usuario).incrementarLivrosEmprestados();
                    obra.decrementarQuantidadeDisponivel();
                    emprestimos.add(new Emprestimo(usuario.getNome(), obra.getTitulo(), LocalDate.now(), null));
                    return true;
                }else{
                    System.out.println("Obra não encontrada.");
                    return false;
                }

            }else{
                System.out.println("Usuario já excedeu o limite de emprestimos ou está com está com algum livro atrasado.");
                return false;
            }
        }else{
            System.out.println("Usuario não é um Aluno ou Professor.");
            return false;
        }

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
                      LocalDate dataDevolucao;
                      if(pedacosLinha[3].equals("null")){
                          dataDevolucao=null;
                      }else{
                      dataDevolucao = LocalDate.parse(pedacosLinha[3]);}

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
    public void carregarUsuariosProfessor(){
        String arquivo = "usuariosProfessor.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            linha=reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                if(pedacosLinha.length>=4) {
                   // String nome, String email, String senha, String departamento
                    String nomeUsuario = pedacosLinha[0];
                    String email = pedacosLinha[1];
                    String senha=pedacosLinha[2];
                    String departamento = pedacosLinha[3];
                    int livrosEmprestados = Integer.parseInt(pedacosLinha[4]);

                    usuarios.add(new Professor(nomeUsuario, email, senha, departamento, livrosEmprestados));
                }
                linha = reader.readLine();

            }
            System.out.println("Arquivo "+arquivo+" carregado com sucesso!"  );
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo "+arquivo+" incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }

    public void carregarUsuariosAluno(){
        String arquivo = "usuariosAluno.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            linha=reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                if(pedacosLinha.length>=5) {
                    //String nome, String email, String senha, String matricula, String curso
                    String nomeUsuario = pedacosLinha[0];
                    String email = pedacosLinha[1];
                    String senha = pedacosLinha[2];
                    String matricula = pedacosLinha[3];
                    String curso = pedacosLinha[4];
                    int livrosEmprestados = Integer.parseInt(pedacosLinha[5]);

                    usuarios.add(new Aluno(nomeUsuario, email, senha, matricula, curso, livrosEmprestados));
                }
                linha = reader.readLine();

            }
            System.out.println("Arquivo "+arquivo+" carregado com sucesso!"  );
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo "+arquivo+" incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }


    public void descarrecarUsuarioAluno(){
        String arquivo = "usuariosAluno.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))){
            writer.write("Nome,Email,Senha,Matrícula,Curso,LivrosEmprestados");
            writer.newLine();
            for(Usuario usuario : usuarios){
                if(usuario instanceof Aluno) {

                    //String nome, String email, String senha, int matricula, String curso, int limiteEmprestimo
                    writer.write(usuario.getNome()+","+usuario.getEmail()+","+usuario.getSenha()+","+((Aluno) usuario).getMatricula()+","
                            +((Aluno) usuario).getCurso()+","+((Aluno) usuario).getlivrosEmprestados());
                    writer.newLine();
                }
            }
            System.out.println("Dados do arquivo "+arquivo+" atualizado com sucesso.");

        }catch (IOException erro) {
            System.out.println("Erro ao salvar daddos do arquivo "+arquivo);}
    }

    public void descarrecarUsuarioProfessor(){
        String arquivo = "usuariosProfessor.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))){
            writer.write("Nome,Email,Senha,Departamento,Livros Emprestados");
            writer.newLine();
            for(Usuario usuario : usuarios){
                if(usuario instanceof Professor) {
                    //String nome, String email, String senha, String departamento
                    writer.write(usuario.getNome()+","+usuario.getEmail()+","+usuario.getSenha()+","
                            +((Professor) usuario).getDepartamento()+","+((Professor) usuario).getlivrosEmprestados());
                    writer.newLine();
                }
            }
            System.out.println("Dados do arquivo "+arquivo+" atualizado com sucesso.");

        }catch (IOException erro) {
            System.out.println("Erro ao salvar daddos do arquivo "+arquivo);}
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
        String arquivo = "emprestimos.txt";
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
                    sc.nextLine();
                    String matricula = sc.nextLine();
                    System.out.println("Digite o curso do aluno: ");
                    String curso = sc.nextLine();

                    novoUsuario = new Aluno(nome, email, senha, matricula, curso,0);
                    usuarios.add(novoUsuario);//limite de empréstimos para alunos é 2
                    break;

                case 2:
                    System.out.println("Digite o departamento do professor: ");
                    sc.nextLine();
                    String departamento = sc.nextLine();

                    novoUsuario = new Professor(nome, email, senha, departamento,0);
                    usuarios.add(novoUsuario);
                    break;

                default:
                    System.err.println("Opção inválida.Tente novamente");

            }
        }while(tipoUsuario<1|| tipoUsuario>2);

    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void relatorioAtrasados(){
        LocalDate data= LocalDate.now();
        String arquivo = "relatorioAtrasados_"+data.getDayOfMonth()+"_"+data.getMonthValue()+"_"+data.getYear();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))){
            writer.write("RELATÓRIO DE LIVROS ATRASADOS");
            writer.newLine();
            for(Emprestimo emprestimo: emprestimos){
                if (emprestimo.isAtrasado()){
                    writer.write(String.valueOf(emprestimo));
                    writer.newLine();
                }
            }
            System.out.println("Um arquivo com o nome "+arquivo+" foi gerado.");

        }catch(IOException erro) {
            System.out.println("Erro ao tentar escrever no arquivo"+arquivo);
        }
    }

    public void relatorioEmprestados(){
        LocalDate data= LocalDate.now();
        String arquivo = "relatorioEmprestados_"+data.getDayOfMonth()+"_"+data.getMonthValue()+"_"+data.getYear();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))){
            writer.write("RELATÓRIO DE LIVROS EMPRESTADOS");
            writer.newLine();
            for(Emprestimo emprestimo: emprestimos){
                if(emprestimo.getDataDevolucao() ==null){
                    writer.write(String.valueOf(emprestimo));
                    writer.newLine();
                }
            }
            System.out.println("Relatório de livros emprestados gerado com sucesso.");
            System.out.println("O nome do arquivo é: "+arquivo);

        }catch (IOException erro){
            System.out.println("Erro ao tentar escrever no arquivo"+arquivo);
        }
    }


}