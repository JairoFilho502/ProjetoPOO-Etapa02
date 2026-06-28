import java.util.HashMap;
import java.util.HashSet;

public class Paciente extends Pessoa {

    // HashSet para garantir unicidade de CPF — add() e contains() sao O(1)
    private static HashSet<String>         cpfsCadastrados = new HashSet<>();

    // HashMap para busca direta de paciente por CPF — get() e O(1)
    private static HashMap<String, Paciente> pacientesPorCpf = new HashMap<>();

    // ASSOCIACAO: Paciente conhece Convenio, mas ambos existem independentemente
    private Convenio convenio;
    private String   status;

    // SOBRECARGA: construtores com diferentes quantidades de parametros
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

    // construtor completo — recebe nome do convenio como String e cria o objeto internamente
    public Paciente(String nome, String cpf, int idade, String telefone, String nomeConvenio) {
        super(nome, cpf, idade, telefone);
        this.convenio = (nomeConvenio != null && !nomeConvenio.trim().isEmpty())
                ? new Convenio(nomeConvenio) : null;
        this.status   = "ativo";
    }

    // --- getters ---
    public Convenio getConvenio()    { return convenio; }
    public String   getStatus()      { return status; }

    // retorna apenas o nome do convenio (String) — usado em Main e ClinicaServico
    public String getConvenioNome() {
        return convenio != null ? convenio.getNome() : "";
    }

    // --- setters com validacao ---
    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void setStatus(String status) {
        if (status == null || (!status.equals("ativo") && !status.equals("inativo")))
            throw new IllegalArgumentException("Status invalido: use 'ativo' ou 'inativo'.");
        this.status = status;
    }

    public boolean estaAtivo() { return "ativo".equals(status); }

    // cadastro estatico com controle de CPF duplicado
    public static Paciente cadastrar(String nome, String cpf) {
        try {
            if (cpfsCadastrados.contains(cpf)) {
                System.out.println("CPF " + cpf + " ja cadastrado. Operacao bloqueada.");
                return null;
            }
            Paciente p = new Paciente(nome, cpf);
            cpfsCadastrados.add(cpf);
            pacientesPorCpf.put(cpf, p);
            return p;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar paciente: " + e.getMessage());
            return null;
        }
    }

    // busca O(1) via HashMap
    public static Paciente buscarPorCpf(String cpf) {
        return pacientesPorCpf.get(cpf);
    }

    // SOBRECARGA: complementar sem convenio
    public void complementar(int idade, String telefone) {
        setIdade(idade);
        setTelefone(telefone);
    }

    // SOBRECARGA: complementar com convenio (aceita String para ser compativel com Main)
    public void complementar(int idade, String telefone, String nomeConvenio) {
        setIdade(idade);
        setTelefone(telefone);
        this.convenio = (nomeConvenio != null && !nomeConvenio.trim().isEmpty())
                ? new Convenio(nomeConvenio) : null;
    }

    public void desativar() { this.status = "inativo"; }
    public void ativar()    { this.status = "ativo"; }

    // SOBRESCRITA + LIGACAO DINAMICA: quando chamado via referencia Pessoa, este metodo e executado
    @Override
    public String exibirResumo() {
        String conv = convenio != null
                ? "Convenio: " + convenio.getNome()
                  + " (" + (int)(convenio.getPercentualCobertura() * 100) + "%)"
                : "Convenio: sem convenio";
        return formatarDadosBase() + " | Status: " + status + " | " + conv;
    }
}
