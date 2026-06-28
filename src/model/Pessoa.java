package model;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private int    idade;
    private String telefone;

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

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty())
            throw new IllegalArgumentException("CPF nao pode ser vazio.");
        this.cpf = cpf.trim();
    }

    public void setIdade(int idade) {
        if (idade < 0)
            throw new IllegalArgumentException("Idade nao pode ser negativa.");
        this.idade = idade;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone != null ? telefone.trim() : "";
    }

    protected String formatarDadosBase() {
        return "Nome: " + nome + " | CPF: " + cpf
                + " | Idade: " + idade + " | Tel: " + telefone;
    }

    public abstract String exibirResumo();
}
