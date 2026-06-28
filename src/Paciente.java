public class Paciente extends Pessoa {
    private String cpf;
    private int idade;
    private String telefone;
    private String convenioNome;
    private String status;

    public Paciente(String nome, String cpf) {
        super(nome);
        setCpf(cpf);
        this.telefone = "";
        this.convenioNome = "";
        this.status = "ativo";
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        this(nome, cpf);
        setIdade(idade);
        this.telefone = (telefone != null) ? telefone : "";
    }

    public Paciente(String nome, String cpf, int idade, String telefone, String convenioNome) {
        this(nome, cpf, idade, telefone);
        setConvenioNome(convenioNome);
    }

    public String getCpf() { return cpf; }
    public int getIdade() { return idade; }
    public String getTelefone() { return telefone; }
    public String getConvenioNome() { return convenioNome; }
    public String getStatus() { return status; }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty())
            throw new IllegalArgumentException("CPF nao pode ser vazio.");
        this.cpf = cpf.trim();
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 150)
            throw new IllegalArgumentException("Idade invalida.");
        this.idade = idade;
    }

    public void setTelefone(String telefone) {
        this.telefone = (telefone != null) ? telefone : "";
    }

    public void setConvenioNome(String convenioNome) {
        this.convenioNome = (convenioNome != null) ? convenioNome : "";
    }

    public boolean estaAtivo() { return "ativo".equals(status); }
    public boolean isAtivo() { return estaAtivo(); }

    public void desativar() { this.status = "inativo"; }
    public void ativar() { this.status = "ativo"; }

    public void complementar(int idade, String telefone) {
        setIdade(idade);
        setTelefone(telefone);
    }

    public void complementar(String telefone, String convenioNome) {
        setTelefone(telefone);
        setConvenioNome(convenioNome);
    }

    public void complementar(int idade, String telefone, String convenioNome) {
        setIdade(idade);
        setTelefone(telefone);
        setConvenioNome(convenioNome);
    }

    @Override
    public String exibirResumo() {
        return "Paciente: " + getNome() + " | CPF: " + cpf + " | Status: " + status;
    }
}
