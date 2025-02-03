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

    //retorna empréstimos.
    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    //construtor com os dados do bibliotecário, que no caso, é o Seu José
    public Biblioteca() {
        this.usuarios.add(new Bibliotecario("Seu José", "seujose2000@gmail.com", "Zezinho", "31996659292"));
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
            Obra obra = null;
            //Exibir opções de busca
            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID da obra: ");
                    int idBusca = sc.nextInt();
                    obra = buscaId(idBusca);
                    if (obra != null) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                    } else {
                        System.out.println("Obra com ID " + idBusca + " não encontrada.");
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome (título) da obra: ");
                    String tituloBusca = sc.nextLine();
                    obra = buscaTitulo(tituloBusca);
                    if (obra != null) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                    } else {

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
            if (emprestimo.getUsuario().equals(usuario)) {
                if (emprestimo.isAtrasado()) {
                    return false;
                }
            }

        }
        return true;
    }

    //método busca usuário no array de usuários
    private Usuario buscaUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
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
        } else {
            System.out.println("Obra ou usuário incorreto.");
            return false;
        }

    }

    //Méoto para realizar empréstimos dos livros
    public boolean realizarEmprestimo(String nomeUsuario, String titulo) {
        //Verificando se usuario existe
        Usuario usuario = buscaUsuario(nomeUsuario);
        //Verifica se a classe de usuário é uma instância de MembroBiblioteca
        if (usuario instanceof MembroBiblioteca) {
            //Verifica se o usuário cumpre os critérios para pegar um livro emprestado
            if (podeEmprestarLivro(usuario) && ((MembroBiblioteca) usuario).verificarLimiteEmprestimo()) {
                //verificando se a obra existe
                Obra obra = buscaTitulo(titulo);
                if (obra != null && obra.getQuantDisponivel() > 0) {
                    //incrementa o número de livros pegos emprestados do usuário
                    ((MembroBiblioteca) usuario).incrementarLivrosEmprestados();
                    //Decrementa a quantidade de obras disponível
                    obra.decrementarQuantidadeDisponivel();
                    //Adiciona a obra na lista de livors emprestados
                    emprestimos.add(new Emprestimo(usuario.getNome(), obra.getTitulo(), LocalDate.now(), null));
                    return true;
                    //Mensagens exibidas aos usuários caso não seja possível fazer um empréstimo
                } else {
                    System.out.println("Obra não encontrada.");
                    return false;
                }

            } else {
                System.out.println("Usuario já excedeu o limite de emprestimos ou está com está com algum livro atrasado.");
                return false;
            }
        } else {
            System.out.println("Usuario não é um Aluno ou Professor.");
            return false;
        }

    }


    public void carregarDadosAcervo() {
        // Define o nome do arquivo principal a ser carregado
        String arquivo = "acervoAtualizado.txt";
        File file = new File(arquivo);
        // Verifica se o arquivo existe; se não existir, tenta carregar um arquivo alternativo
        if (!file.exists()) {
            arquivo = "acervo.csv"; //Define o arquivo alternativo
            file = new File(arquivo);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            linha = reader.readLine(); //Lê a primeira linha do arquivo descartando o cabeçário
            //Loop para processar todas as linhas do arquivo
            while (linha != null) {
                //Divide a linha usando a vírgula como delimitador
                String pedacosLinha[] = linha.split(",");
                //Verifica se possui pelo menos três elementos
                if (pedacosLinha.length >= 3) {
                    int id = Integer.parseInt(pedacosLinha[0]); //Converte ID para inteiro
                    String nome = pedacosLinha[1]; //Obtem o nome da obra
                    int quantidade = Integer.parseInt(pedacosLinha[2]); //Converte a quantidade disponível para inteiro
                    //Adiciona a obra à lista de obras
                    obras.add(new Obra(id, nome, quantidade));

                }
                //Lê a proxima linha do arquivo
                linha = reader.readLine();
            }
            //Exibe a mensagem ao carregar dados
            System.out.println("Arquivo " + arquivo + " carregSado com sucesso!");
            // Trata o erro caso o arquivo não seja encontrado
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo incorreto");
            // Trata o erro caso ocorra um problema na leitura do arquivo
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }

    public void carregarDadosEmprestimo() {
        // Define o nome do arquivo que contém os dados dos empréstimos
        String arquivo = "emprestimos.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            // Lê a primeira linha do arquivo (geralmente cabeçalho, então é descartada)
            String linha = reader.readLine();
            linha = reader.readLine(); // Lê a segunda linha (primeiro dado válido)

            // Loop para processar todas as linhas do arquivo
            while (linha != null) {
                // Divide a linha em partes usando a vírgula como delimitador
                String pedacosLinha[] = linha.split(",");

                // Verifica se a linha possui pelo menos 4 elementos (Obra, Usuário, Data de Empréstimo, Data de Devolução)
                if (pedacosLinha.length >= 4) {
                    String obra = pedacosLinha[0]; // Obtém o nome da obra emprestada
                    String nomeUsuario = pedacosLinha[1]; // Obtém o nome do usuário que pegou emprestado

                    try {
                        // Converte a string da data de empréstimo para um objeto LocalDate
                        LocalDate dataEmprestimo = LocalDate.parse(pedacosLinha[2]);
                        LocalDate dataDevolucao;

                        // Verifica se a data de devolução está definida ou se é "null"
                        if (pedacosLinha[3].equals("null")) {
                            dataDevolucao = null; // Se for "null", define como null
                        } else {
                            dataDevolucao = LocalDate.parse(pedacosLinha[3]); // Caso contrário, converte para LocalDate
                        }

                        // Adiciona um novo empréstimo à lista de empréstimos
                        emprestimos.add(new Emprestimo(nomeUsuario, obra, dataEmprestimo, dataDevolucao));
                    } catch (DateTimeException erro) {
                        // Trata erro caso a data esteja em um formato inválido
                        System.out.println("Formato de data inválido.");
                    }
                }
                // Lê a próxima linha do arquivo
                linha = reader.readLine();
            }
            // Exibe mensagem de sucesso ao carregar os dados
            System.out.println("Arquivo " + arquivo + " carregado com sucesso!");
        } catch (FileNotFoundException erro) {
            // Trata o erro caso o arquivo não seja encontrado
            System.out.println("Caminho do arquivo incorreto");
        } catch (IOException erroLeitura) {
            // Trata o erro caso ocorra um problema na leitura do arquivo
            System.out.println("Erro na leitura dos dados");
        }
    }

    public void carregarUsuariosProfessor() {
        // Define o nome do arquivo que contém os dados dos professores
        String arquivo = "usuariosProfessor.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            // Lê a primeira linha do arquivo (geralmente cabeçalho, então é descartada)
            String linha = reader.readLine();
            linha = reader.readLine(); // Lê a segunda linha (primeiro dado válido)

            // Loop para processar todas as linhas do arquivo
            while (linha != null) {
                // Divide a linha em partes usando a vírgula como delimitador
                String pedacosLinha[] = linha.split(",");

                // Verifica se a linha possui pelo menos 5 elementos (Nome, Email, Senha, Departamento, Livros Emprestados)
                if (pedacosLinha.length >= 5) {
                    // Extrai os dados do professor
                    String nomeUsuario = pedacosLinha[0]; // Nome do professor
                    String email = pedacosLinha[1];       // Email do professor
                    String senha = pedacosLinha[2];       // Senha do professor
                    String departamento = pedacosLinha[3]; // Departamento do professor
                    int livrosEmprestados = Integer.parseInt(pedacosLinha[4]); // Número de livros emprestados

                    // Cria um objeto Professor e adiciona à lista de usuários
                    usuarios.add(new Professor(nomeUsuario, email, senha, departamento, livrosEmprestados));
                }
                // Lê a próxima linha do arquivo
                linha = reader.readLine();
            }
            // Exibe mensagem de sucesso ao carregar os dados
            System.out.println("Arquivo " + arquivo + " carregado com sucesso!");
        } catch (FileNotFoundException erro) {
            // Trata o erro caso o arquivo não seja encontrado
            System.out.println("Caminho do arquivo " + arquivo + " incorreto");
        } catch (IOException erroLeitura) {
            // Trata o erro caso ocorra um problema na leitura do arquivo
            System.out.println("Erro na leitura dos dados");
        }
    }

    public void carregarUsuariosAluno() {
        // Define o nome do arquivo que contém os dados dos alunos
        String arquivo = "usuariosAluno.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            // Lê a primeira linha do arquivo (geralmente cabeçalho, então é descartada)
            String linha = reader.readLine();
            linha = reader.readLine(); // Lê a segunda linha (primeiro dado válido)

            // Loop para processar todas as linhas do arquivo
            while (linha != null) {
                // Divide a linha em partes usando a vírgula como delimitador
                String pedacosLinha[] = linha.split(",");

                // Verifica se a linha possui pelo menos 6 elementos (Nome, Email, Senha, Matrícula, Curso, Livros Emprestados)
                if (pedacosLinha.length >= 6) {
                    // Extrai os dados do aluno
                    String nomeUsuario = pedacosLinha[0]; // Nome do aluno
                    String email = pedacosLinha[1];       // Email do aluno
                    String senha = pedacosLinha[2];       // Senha do aluno
                    String matricula = pedacosLinha[3];   // Número de matrícula
                    String curso = pedacosLinha[4];       // Curso do aluno
                    int livrosEmprestados = Integer.parseInt(pedacosLinha[5]); // Quantidade de livros emprestados

                    // Cria um objeto Aluno e adiciona à lista de usuários
                    usuarios.add(new Aluno(nomeUsuario, email, senha, matricula, curso, livrosEmprestados));
                }
                // Lê a próxima linha do arquivo
                linha = reader.readLine();
            }
            // Exibe mensagem de sucesso ao carregar os dados
            System.out.println("Arquivo " + arquivo + " carregado com sucesso!");
        } catch (FileNotFoundException erro) {
            // Trata o erro caso o arquivo não seja encontrado
            System.out.println("Caminho do arquivo " + arquivo + " incorreto");
        } catch (IOException erroLeitura) {
            // Trata o erro caso ocorra um problema na leitura do arquivo
            System.out.println("Erro na leitura dos dados");
        }
    }


    public void descarrecarUsuarioAluno() {
        // Define o nome do arquivo onde os dados dos alunos serão salvos
        String arquivo = "usuariosAluno.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            // Escreve o cabeçalho do arquivo CSV
            writer.write("Nome,Email,Senha,Matrícula,Curso,LivrosEmprestados");
            writer.newLine(); // Pula para a próxima linha

            // Percorre a lista de usuários para salvar apenas os objetos do tipo Aluno
            for (Usuario usuario : usuarios) {
                if (usuario instanceof Aluno) {
                    // Escreve os dados do aluno no formato CSV
                    writer.write(usuario.getNome() + "," + usuario.getEmail() + "," + usuario.getSenha() + ","
                            + ((Aluno) usuario).getMatricula() + "," + ((Aluno) usuario).getCurso() + ","
                            + ((Aluno) usuario).getlivrosEmprestados());
                    writer.newLine(); // Pula para a próxima linha
                }
            }
            // Exibe mensagem de sucesso após a atualização do arquivo
            System.out.println("Dados do arquivo " + arquivo + " atualizado com sucesso.");

        } catch (IOException erro) {
            // Exibe mensagem de erro caso ocorra algum problema na escrita do arquivo
            System.out.println("Erro ao salvar dados do arquivo " + arquivo);
        }
    }


    public void descarrecarUsuarioProfessor() {
        // Define o nome do arquivo onde os dados dos professores serão salvos
        String arquivo = "usuariosProfessor.txt";

        // Tenta abrir o arquivo para escrita usando um BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            // Escreve o cabeçalho do arquivo CSV
            writer.write("Nome,Email,Senha,Departamento,Livros Emprestados");
            writer.newLine(); // Pula para a próxima linha

            // Itera sobre a lista de usuários
            for (Usuario usuario : usuarios) {
                // Verifica se o usuário é uma instância de Professor
                if (usuario instanceof Professor) {
                    // Escreve os dados do professor no arquivo CSV
                    writer.write(usuario.getNome() + "," + usuario.getEmail() + "," + usuario.getSenha() + ","
                            + ((Professor) usuario).getDepartamento() + "," + ((Professor) usuario).getlivrosEmprestados());
                    writer.newLine(); // Pula para a próxima linha após escrever os dados do professor
                }
            }

            // Mensagem de sucesso indicando que os dados foram atualizados no arquivo
            System.out.println("Dados do arquivo " + arquivo + " atualizado com sucesso.");

        } catch (IOException erro) {
            // Captura e trata exceções de I/O (Input/Output) que podem ocorrer durante a escrita no arquivo
            System.out.println("Erro ao salvar dados do arquivo " + arquivo);
        }
    }

    public void descarregarDadosAcervo() {
        String arquivo = "acervoAtualizado.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write("ID,Título,Quantidade");
            writer.newLine();
            for (Obra obra : obras) {
                writer.write(obra.getId() + "," + obra.getTitulo() + "," + obra.getQuantDisponivel());
                writer.newLine();
            }
            System.out.println("Dados do acervo atualizado com sucesso.");

        } catch (IOException erro) {
            System.out.println("Erro ao salvar daddos do acervo");
        }
    }

    public void descarregarDadosEmprestimo() {
        // Define o nome do arquivo onde os dados dos empréstimos serão salvos
        String arquivo = "emprestimos.txt";

        // Tenta abrir o arquivo para escrita usando um BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            // Escreve o cabeçalho do arquivo CSV
            writer.write("Obra,Usuario,Data de Emprestimo, Data de Devolução");
            writer.newLine(); // Pula para a próxima linha

            // Itera sobre a lista de empréstimos
            for (Emprestimo emprestimo : emprestimos) {
                // Escreve os dados de cada empréstimo no arquivo CSV
                writer.write(emprestimo.getObra() + "," + emprestimo.getUsuario() + ","
                        + emprestimo.getDataEmprestimo() + "," + emprestimo.getDataDevolucao());
                writer.newLine(); // Pula para a próxima linha após escrever os dados do empréstimo
            }

            // Mensagem de sucesso indicando que os dados de empréstimo foram atualizados no arquivo
            System.out.println("Dados de emprestimo atualizado com sucesso.");

        } catch (IOException erro) {
            // Captura e trata exceções de I/O (Input/Output) que podem ocorrer durante a escrita no arquivo
            System.out.println("Erro ao salvar dados de Emprestimo");
        }
    }

    //mas somente o bibliotecário pode cadastrar novos usuários, estou na classe certa? não sei, perguntar para a Estella!
    public void cadastraUsuario() {
        // Cria um objeto Scanner para ler entradas do usuário
        Scanner sc = new Scanner(System.in);

        // Solicita e lê o nome do usuário
        System.out.println("Digite o nome do usuário: ");
        String nome = sc.nextLine();

        // Solicita e lê o email do usuário
        System.out.println("Digite o email do usuário: ");
        String email = sc.nextLine();

        // Verifica se o email já está cadastrado na lista de usuários
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                // Se o email já existe, exibe uma mensagem de erro e encerra o método
                System.err.println("Erro: Email já cadastrado.");
                return;
            }
        }

        // Variável para armazenar a escolha do tipo de usuário
        int tipoUsuario;

        // Loop para garantir que o usuário selecione uma opção válida (1 ou 2)
        do {
            // Solicita e lê a senha do usuário
            System.out.println("Digite a senha do usuário: ");
            String senha = sc.nextLine();

            // Exibe as opções de tipo de usuário
            System.out.println("Selecione o tipo de usuário:");
            System.out.println("1 - Aluno");
            System.out.println("2 - Professor");

            // Lê a escolha do tipo de usuário
            tipoUsuario = sc.nextInt();

            // Variável para armazenar o novo usuário criado
            Usuario novoUsuario = null;

            // Switch para tratar a escolha do tipo de usuário
            switch (tipoUsuario) {
                case 1: // Caso o usuário seja um Aluno
                    System.out.println("Digite o número de matrícula do aluno: ");
                    sc.nextLine(); // Consumir a nova linha pendente
                    String matricula = sc.nextLine();

                    System.out.println("Digite o curso do aluno: ");
                    String curso = sc.nextLine();

                    // Cria um novo objeto Aluno com os dados fornecidos
                    novoUsuario = new Aluno(nome, email, senha, matricula, curso, 0); // O último parâmetro (0) parece ser o limite de empréstimos
                    usuarios.add(novoUsuario); // Adiciona o novo aluno à lista de usuários
                    break;

                case 2: // Caso o usuário seja um Professor
                    System.out.println("Digite o departamento do professor: ");
                    sc.nextLine(); // Consumir a nova linha pendente
                    String departamento = sc.nextLine();

                    // Cria um novo objeto Professor com os dados fornecidos
                    novoUsuario = new Professor(nome, email, senha, departamento, 0); // O último parâmetro (0) parece ser o limite de empréstimos
                    usuarios.add(novoUsuario); // Adiciona o novo professor à lista de usuários
                    break;

                default: // Caso a opção seja inválida
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (tipoUsuario < 1 || tipoUsuario > 2); // Repete o loop se a opção for inválida
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios; //Retorna lista de usuários
    }

    public void relatorioAtrasados() {
        // Obtém a data atual usando a classe LocalDate
        LocalDate data = LocalDate.now();

        // Cria o nome do arquivo com base na data atual
        String arquivo = "relatorioAtrasados_" + data.getDayOfMonth() + "_" + data.getMonthValue() + "_" + data.getYear();

        // Tenta abrir o arquivo para escrita usando um BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            // Escreve o cabeçalho do relatório no arquivo
            writer.write("RELATÓRIO DE LIVROS ATRASADOS");
            writer.newLine(); // Pula para a próxima linha

            // Itera sobre a lista de empréstimos
            for (Emprestimo emprestimo : emprestimos) {
                // Verifica se o empréstimo está atrasado
                if (emprestimo.isAtrasado()) {
                    // Escreve os detalhes do empréstimo atrasado no arquivo
                    writer.write(String.valueOf(emprestimo));
                    writer.newLine(); // Pula para a próxima linha após escrever os detalhes
                }
            }

            // Mensagem de sucesso indicando que o arquivo foi gerado
            System.out.println("Um arquivo com o nome " + arquivo + " foi gerado.");

        } catch (IOException erro) {
            // Captura e trata exceções de I/O (Input/Output) que podem ocorrer durante a escrita no arquivo
            System.out.println("Erro ao tentar escrever no arquivo " + arquivo);
        }
    }

    public void relatorioEmprestados() {
        // Obtém a data atual usando a classe LocalDate
        LocalDate data = LocalDate.now();

        // Cria o nome do arquivo com base na data atual
        String arquivo = "relatorioEmprestados_" + data.getDayOfMonth() + "_" + data.getMonthValue() + "_" + data.getYear();

        // Tenta abrir o arquivo para escrita usando um BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            // Escreve o cabeçalho do relatório no arquivo
            writer.write("RELATÓRIO DE LIVROS EMPRESTADOS");
            writer.newLine(); // Pula para a próxima linha

            // Itera sobre a lista de empréstimos
            for (Emprestimo emprestimo : emprestimos) {
                // Verifica se a data de devolução é nula (ou seja, o livro ainda está emprestado)
                if (emprestimo.getDataDevolucao() == null) {
                    // Escreve os detalhes do empréstimo no arquivo
                    writer.write(String.valueOf(emprestimo));
                    writer.newLine(); // Pula para a próxima linha após escrever os detalhes
                }
            }

            // Mensagem de sucesso indicando que o relatório foi gerado
            System.out.println("Relatório de livros emprestados gerado com sucesso.");
            System.out.println("O nome do arquivo é: " + arquivo);

        } catch (IOException erro) {
            // Captura e trata exceções de I/O (Input/Output) que podem ocorrer durante a escrita no arquivo
            System.out.println("Erro ao tentar escrever no arquivo " + arquivo);
        }
    }
}