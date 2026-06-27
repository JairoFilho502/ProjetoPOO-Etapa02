public class Paciente {

    // ASSOCIAÇÃO: Paciente conhece Convenio, mas ambos existem independentemente.
    private Convenio convenio;    // era String convenioNome, agora referencia o objeto real
    private String nome;
    private String cpf;
    private int    idade;
    private String telefone;
    private String status;

    public Paciente(String nome, String cpf) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.idade    = 0;
        this.telefone = "";
        this.convenio = null;
        this.status   = "ativo";
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.idade    = idade;
        this.telefone = telefone;
        this.convenio = null;
        this.status   = "ativo";
    }

    // construtor com todos os dados - convenio pode ser null
    public Paciente(String nome, String cpf, int idade, String telefone, Convenio convenio) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.idade    = idade;
        this.telefone = telefone;
        this.convenio = convenio;
        this.status   = "ativo";
    }

    // --- getters ---
    public String   getNome()     { return nome; }
    public String   getCpf()      { return cpf; }
    public int      getIdade()    { return idade; }
    public String   getTelefone() { return telefone; }
    public Convenio getConvenio() { return convenio; }
    public String   getStatus()   { return status; }

    // --- setters ---
    public void setNome(String nome)          { this.nome = nome; }
    public void setCpf(String cpf)            { this.cpf = cpf; }
    public void setIdade(int idade)           { this.idade = idade; }
    public void setTelefone(String telefone)  { this.telefone = telefone; }
    public void setConvenio(Convenio convenio){ this.convenio = convenio; }
    public void setStatus(String status)      { this.status = status; }

    public boolean estaAtivo() {
        return "ativo".equals(status);
    }

    // atualiza so idade e telefone
    public void complementar(int idade, String telefone) {
        this.idade    = idade;
        this.telefone = telefone;
    }

    // atualiza dados com convenio incluido
    public void complementar(int idade, String telefone, Convenio convenio) {
        this.idade    = idade;
        this.telefone = telefone;
        this.convenio = convenio;
    }

    public void desativar() {
        this.status = "inativo";
    }

    public String exibirResumo() {
        String nomeConvenio = (convenio != null) ? convenio.getNome() : "sem convenio";
        return "Nome: " + nome + " | CPF: " + cpf + " | Idade: " + idade
                + " | Tel: " + telefone + " | Convenio: " + nomeConvenio
                + " | Status: " + status;
    }
}
