public abstract class Pessoa {

    private String nome;
    private String cpf;
    private String telefone;
    private String dataNascimento;

    // construtor minimo - nome e cpf sao obrigatorios
    public Pessoa(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
        this.telefone       = "";
        this.dataNascimento = "";
    }

    // construtor completo
    public Pessoa(String nome, String cpf, String dataNascimento, String telefone) {
        setNome(nome);
        setCpf(cpf);
        this.dataNascimento = (dataNascimento != null) ? dataNascimento : "";
        this.telefone       = (telefone != null)       ? telefone       : "";
    }

    // --- getters ---
    public String getNome()          { return nome; }
    public String getCpf()           { return cpf; }
    public String getTelefone()      { return telefone; }
    public String getDataNascimento(){ return dataNascimento; }

    // --- setters com validacao ---

    // cpf nao pode ser nulo, vazio ou so espacos (R1 obrigatorio)
    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF invalido: nao pode ser vazio.");
        }
        this.cpf = cpf;
    }

    // nome tambem nao pode ser vazio - sem nome o cadastro nao tem identidade
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido: nao pode ser vazio.");
        }
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = (telefone != null) ? telefone : "";
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = (dataNascimento != null) ? dataNascimento : "";
    }

    // monta linha basica com dados de Pessoa - subclasses usam no exibirResumo
    protected String formatarDadosBase() {
        String nasc = dataNascimento.isEmpty() ? "nao informado" : dataNascimento;
        String tel  = telefone.isEmpty()       ? "nao informado" : telefone;
        return "Nome: " + nome + " | CPF: " + cpf
                + " | Nasc: " + nasc + " | Tel: " + tel;
    }

    // cada subclasse define como mostra o proprio resumo
    public abstract void exibirResumo();
}
