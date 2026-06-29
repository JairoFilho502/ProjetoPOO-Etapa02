package model;

// representa um paciente da clínica, herda os dados basicos de Pessoa
public class Paciente extends Pessoa {
    // ASSOCIAÇÃO: Paciente conhece Convenio, mas ambos existem independentemente
    private Convenio convenio;
    private String   status;

    // cadastro minimo: so nome e CPF
    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.convenio = null;
        this.status   = "ativo";
    }

    // com mais dados mas ainda sem convenio
    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf, idade, telefone);
        this.convenio = null;
        this.status   = "ativo";
    }

    // cadastro completo com convenio
    public Paciente(String nome, String cpf, int idade, String telefone, String nomeConvenio) {
        super(nome, cpf, idade, telefone);
        // cria o convenio se o nome nao for vazio
        this.convenio = (nomeConvenio != null && !nomeConvenio.trim().isEmpty())
                        ? new Convenio(nomeConvenio) : null;
        this.status   = "ativo";
    }

    public Convenio getConvenio()     { return convenio; }
    public String   getStatus()       { return status; }

    // retorna so o nome do convenio se nao tiver
    public String getConvenioNome() {
        return convenio != null ? convenio.getNome() : "";
    }

    public void setConvenio(Convenio convenio) { this.convenio = convenio; }

    // so aceita "ativo" ou "inativo", qualquer outra coisa da erro
    public void setStatus(String status) {
        if (status == null || (!status.equals("ativo") && !status.equals("inativo")))
            throw new IllegalArgumentException("Status invalido: use 'ativo' ou 'inativo'.");
        this.status = status;
    }

    public boolean estaAtivo() { return "ativo".equals(status); }

    // preenche dados que faltaram no cadastro inicial (sem convenio)
    public void complementar(int idade, String telefone) {
        setIdade(idade);
        setTelefone(telefone);
    }

    // mesma operação mas incluindo convenio
    public void complementar(int idade, String telefone, String nomeConvenio) {
        setIdade(idade);
        setTelefone(telefone);
        this.convenio = (nomeConvenio != null && !nomeConvenio.trim().isEmpty())
                        ? new Convenio(nomeConvenio) : null;
    }

    public void desativar() { this.status = "inativo"; }
    public void ativar()    { this.status = "ativo"; }

    @Override
    public String exibirResumo() {
        // mostra "sem convenio" se o paciente nao tiver nenhum
        String conv = convenio != null
                ? "Convenio: " + convenio.getNome()
                  + " (" + (int)(convenio.getPercentualCobertura() * 100) + "%)"
                : "Convenio: sem convenio";
        return formatarDadosBase() + " | Status: " + status + " | " + conv;
    }
}
