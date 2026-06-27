import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClinicaServico {

    // ===================== R10 — INFRAESTRUTURA DE COLEÇÕES =====================

    private List<Paciente>     pacientes          = new ArrayList<>();
    private List<Profissional> profissionais      = new ArrayList<>();
    private List<Consulta>     consultas          = new ArrayList<>();
    private List<Atendimento>  atendimentos       = new ArrayList<>();
    private List<Pagamento>    pagamentos         = new ArrayList<>();
    private List<Pessoa>       pessoasCadastradas = new ArrayList<>();
    private Set<String>                cpfsCadastrados    = new HashSet<>();
    private Map<String, Paciente>      pacientesPorCpf    = new HashMap<>();
    private Map<String, Profissional>  profissionaisPorNome = new HashMap<>();
    private List<Double>       multas             = new ArrayList<>();

    // ===================== PACIENTES =====================

    public void cadastrarPaciente(String nome, String cpf) {
        // R10: HashSet.add() retorna false automaticamente se CPF já existe — O(1), sem loop
        if (!cpfsCadastrados.add(cpf)) {
            throw new IllegalArgumentException("CPF " + cpf + " já cadastrado no sistema.");
        }
        Paciente p = new Paciente(nome, cpf);
        pacientes.add(p);
        pessoasCadastradas.add(p);   // lista unificada para relatório polimórfico
        pacientesPorCpf.put(cpf, p); // índice rápido para busca O(1)
    }

    // SOBRECARGA: complementar sem convênio (R4)
    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void complementarPaciente(String cpf, int idade, String telefone)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).complementar(idade, telefone);
    }

    // SOBRECARGA: complementar com convênio (R4)
    public void complementarPaciente(String cpf, int idade, String telefone, String nomeConvenio)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).complementar(idade, telefone, nomeConvenio);
    }

    // Jornada 27: busca rápida O(1) via HashMap — sem percorrer ArrayList
    public Paciente buscarPaciente(String cpf) throws PacienteNaoEncontradoException {
        Paciente p = pacientesPorCpf.get(cpf);
        if (p == null) {
            throw new PacienteNaoEncontradoException("CPF " + cpf + " não encontrado no sistema.");
        }
        return p;
    }

    public void desativarPaciente(String cpf) throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).desativar();
    }

    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes); // cópia defensiva
    }
}
