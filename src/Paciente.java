public class Paciente {

    private String nome;
    private String cpf;
    private int    idade;
    private String telefone;
    private String convenioNome;  // a classe Convenio sera adicionada no proximo commit
    private String status;        // substituiu o boolean ativo

    public Paciente(String nome, String cpf) {
        this.nome        = nome;
        this.cpf         = cpf;
        this.idade       = 0;
        this.telefone    = "";
        this.convenioNome = "";
        this.status      = "ativo";
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        this.nome        = nome;
        this.cpf         = cpf;
        this.idade       = idade;
        this.telefone    = telefone;
        this.convenioNome = "";
        this.status      = "ativo";
    }

    // construtor com todos os dados
    public Paciente(String nome, String cpf, int idade, String telefone, String convenioNome) {
        this.nome        = nome;
        this.cpf         = cpf;
        this.idade       = idade;
        this.telefone    = telefone;
        this.convenioNome = convenioNome;
        this.status      = "ativo";
    }

    // --- getters ---
    public String getNome()         { return nome; }
    public String getCpf()          { return cpf; }
    public int    getIdade()        { return idade; }
    public String getTelefone()     { return telefone; }
    public String getConvenioNome() { return convenioNome; }
    public String getStatus()       { return status; }

    // --- setters ---
    public void setNome(String nome)                { this.nome = nome; }
    public void setCpf(String cpf)                  { this.cpf = cpf; }
    public void setIdade(int idade)                 { this.idade = idade; }
    public void setTelefone(String telefone)        { this.telefone = telefone; }
    public void setConvenioNome(String convenioNome){ this.convenioNome = convenioNome; }
    public void setStatus(String status)            { this.status = status; }

    // verifica rapido se paciente pode ser atendido
    public boolean estaAtivo() {
        return "ativo".equals(status);
    }

    // atualiza so idade e telefone
    public void complementar(int idade, String telefone) {
        this.idade    = idade;
        this.telefone = telefone;
    }

    // atualiza tudo incluindo convenio
    public void complementar(int idade, String telefone, String convenioNome) {
        this.idade        = idade;
        this.telefone     = telefone;
        this.convenioNome = convenioNome;
    }

    public void desativar() {
        this.status = "inativo";
    }

    public String exibirResumo() {
        return "Nome: " + nome + " | CPF: " + cpf + " | Idade: " + idade
                + " | Tel: " + telefone + " | Convenio: " + convenioNome
                + " | Status: " + status;
    }
}
