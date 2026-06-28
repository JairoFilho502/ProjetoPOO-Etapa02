public class Paciente extends Pessoa {

    // ASSOCIAÇÃO: Paciente conhece Convenio, mas ambos existem independentemente.
    private Convenio convenio;
    private String   status;

    // SOBRECARGA: mesmo nome, parametros diferentes (resolvido em tempo de compilacao)
    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.convenio = null;
        this.status   = "ativo";
    }

    // construtor com nascimento e telefone
    public Paciente(String nome, String cpf, String dataNascimento, String telefone) {
        super(nome, cpf, dataNascimento, telefone);
        this.convenio = null;
        this.status   = "ativo";
    }

    // construtor completo com convenio
    public Paciente(String nome, String cpf, String dataNascimento, String telefone, Convenio convenio) {
        super(nome, cpf, dataNascimento, telefone);
        this.convenio = convenio;
        this.status   = "ativo";
    }

    // --- getters proprios (nome, cpf, tel, dataNascimento vem de Pessoa via heranca) ---
    public Convenio getConvenio() { return convenio; }
    public String   getStatus()   { return status; }

    // --- setters proprios ---
    public void setConvenio(Convenio convenio) { this.convenio = convenio; }
    public void setStatus(String status)       { this.status   = status; }

    public boolean estaAtivo() {
        return "ativo".equals(status);
    }

    // atualiza nascimento e telefone usando setters herdados de Pessoa
    public void complementar(String dataNascimento, String telefone) {
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
    }

    // atualiza dados com convenio incluido
    public void complementar(String dataNascimento, String telefone, Convenio convenio) {
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
        this.convenio = convenio;
    }

    public void desativar() {
        this.status = "inativo";
    }

    // SOBRESCRITA: mesmo nome e parametros, classe filha redefine comportamento (resolvido em tempo de execucao)
    @Override
    public void exibirResumo() {
        System.out.println(formatarDadosBase());
        System.out.println("Status: " + status);
        if (convenio != null) {
            System.out.println("Convenio: " + convenio.getNome()
                    + " | Cobertura: " + (int)(convenio.getPercentualCobertura() * 100) + "%");
        } else {
            System.out.println("Convenio: sem convenio");
        }
    }
}
