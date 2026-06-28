import java.util.HashMap;
import java.util.HashSet;

public class Paciente extends Pessoa {

    // HashSet para garantir unicidade de CPF — add() e contains() sao O(1)
    // JUSTIFICATIVA: HashSet nao permite duplicatas, ideal para verificar se CPF ja existe
    private static HashSet<String> cpfsCadastrados = new HashSet<>();

    // HashMap para busca direta de paciente por CPF — get() e O(1)
    // JUSTIFICATIVA: mais eficiente que percorrer lista; acessa o paciente pela chave CPF
    private static HashMap<String, Paciente> pacientesPorCpf = new HashMap<>();

    // ASSOCIAÇÃO: Paciente conhece Convenio, mas ambos existem independentemente.
    private Convenio convenio;
    private String   status;

    // SOBRECARGA: mesmo nome, parametros diferentes (resolvido em tempo de compilacao)
    public Paciente(String nome, String cpf) {
        super(nome, cpf);
        this.convenio = null;
        this.status   = "ativo";
    }

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

    public Convenio getConvenio() { return convenio; }
    public String   getStatus()   { return status; }

    public void setConvenio(Convenio convenio) { this.convenio = convenio; }
    public void setStatus(String status)       { this.status   = status; }

    public boolean estaAtivo() {
        return "ativo".equals(status);
    }

    // cadastra paciente com controle de CPF duplicado no HashSet (Jornada 2 + 28)
    public static Paciente cadastrar(String nome, String cpf) {
        if (cpfsCadastrados.contains(cpf)) {
            System.out.println("CPF " + cpf + " ja cadastrado. Operacao bloqueada.");
            return null;
        }
        Paciente p = new Paciente(nome, cpf);
        cpfsCadastrados.add(cpf);
        pacientesPorCpf.put(cpf, p);
        return p;
    }

    // busca paciente pelo CPF no HashMap — O(1) (Jornada 2 / apoio Jornada 27)
    public static Paciente buscarPorCpf(String cpf) {
        return pacientesPorCpf.get(cpf);
    }

    public void complementar(String dataNascimento, String telefone) {
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
    }

    public void complementar(String dataNascimento, String telefone, Convenio convenio) {
        setDataNascimento(dataNascimento);
        setTelefone(telefone);
        this.convenio = convenio;
    }

    public void desativar() {
        this.status = "inativo";
    }

    // reativa o paciente quando necessario
    public void ativar() {
        this.status = "ativo";
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
