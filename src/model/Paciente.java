package model;

public class Paciente extends Pessoa {
    private Convenio convenio;
    private String   status;

    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.convenio = null;
        this.status   = "ativo";
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf, idade, telefone);
        this.convenio = null;
        this.status   = "ativo";
    }

    public Paciente(String nome, String cpf, int idade, String telefone, String nomeConvenio) {
        super(nome, cpf, idade, telefone);
        this.convenio = (nomeConvenio != null && !nomeConvenio.trim().isEmpty())
                        ? new Convenio(nomeConvenio) : null;
        this.status   = "ativo";
    }

    public Convenio getConvenio()     { return convenio; }
    public String   getStatus()       { return status; }

    public String getConvenioNome() {
        return convenio != null ? convenio.getNome() : "";
    }

    public void setConvenio(Convenio convenio) { this.convenio = convenio; }

    public void setStatus(String status) {
        if (status == null || (!status.equals("ativo") && !status.equals("inativo")))
            throw new IllegalArgumentException("Status invalido: use 'ativo' ou 'inativo'.");
        this.status = status;
    }

    public boolean estaAtivo() { return "ativo".equals(status); }

    public void complementar(int idade, String telefone) {
        setIdade(idade);
        setTelefone(telefone);
    }

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
        String conv = convenio != null
                ? "Convenio: " + convenio.getNome()
                  + " (" + (int)(convenio.getPercentualCobertura() * 100) + "%)"
                : "Convenio: sem convenio";
        return formatarDadosBase() + " | Status: " + status + " | " + conv;
    }
}
