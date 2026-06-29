package model;

// superclasse base, subclasses dela são: paciente e profissional


public abstract class Pessoa {
    private String nome;
    private String cpf;
    private int    idade;
    private String telefone;




    // construtor mínimo, só com nome!
    public Pessoa(String nome) {
        setNome(nome);
        this.cpf      = "";
        this.idade    = 0;
        this.telefone = "";
    }

 
    public Pessoa(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
        this.idade    = 0;
        this.telefone = "";
    }






    // construtor completo
    public Pessoa(String nome, String cpf, int idade, String telefone) {
        setNome(nome);
        setCpf(cpf);
        setIdade(idade);
        setTelefone(telefone);
    }

    public String getNome()     { return nome; }
    public String getCpf()      { return cpf; }
    public int    getIdade()    { return idade; }
    public String getTelefone() { return telefone; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome nao pode ser vazio.");
        this.nome = nome.trim();
    }

    // CPFnão pode ser vazio
    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty())
            throw new IllegalArgumentException("CPF nao pode ser vazio.");
        this.cpf = cpf.trim();
    }




    // ninguém tem idade negativa ne
    public void setIdade(int idade) {
        if (idade < 0)
            throw new IllegalArgumentException("Idade nao pode ser negativa.");
        this.idade = idade;
    }

    // telefone pode ser vazio, então trata null aqui
    public void setTelefone(String telefone) {
        this.telefone = telefone != null ? telefone.trim() : "";
    }

    // monta uma string com os dados básicos praexibir
    protected String formatarDadosBase() {
        return "Nome: " + nome + " | CPF: " + cpf
                + " | Idade: " + idade + " | Tel: " + telefone;
    }

    
    public abstract String exibirResumo();
}
