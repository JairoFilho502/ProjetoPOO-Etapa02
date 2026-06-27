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
    private Set<String>                cpfsCadastrados      = new HashSet<>();
    private Map<String, Paciente>      pacientesPorCpf      = new HashMap<>();
    private Map<String, Profissional>  profissionaisPorNome = new HashMap<>();
    private List<Double>       multas             = new ArrayList<>();

    // ===================== PACIENTES =====================

    public void cadastrarPaciente(String nome, String cpf) {
        if (!cpfsCadastrados.add(cpf)) {
            throw new IllegalArgumentException("CPF " + cpf + " já cadastrado no sistema.");
        }
        Paciente p = new Paciente(nome, cpf);
        pacientes.add(p);
        pessoasCadastradas.add(p);
        pacientesPorCpf.put(cpf, p);
    }

    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação)
    public void complementarPaciente(String cpf, int idade, String telefone)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).complementar(idade, telefone);
    }

    public void complementarPaciente(String cpf, int idade, String telefone, String nomeConvenio)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).complementar(idade, telefone, nomeConvenio);
    }

    public Paciente buscarPaciente(String cpf) throws PacienteNaoEncontradoException {
        Paciente p = pacientesPorCpf.get(cpf);
        if (p == null) throw new PacienteNaoEncontradoException("CPF " + cpf + " não encontrado.");
        return p;
    }

    public void desativarPaciente(String cpf) throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).desativar();
    }

    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes);
    }

    // ===================== PROFISSIONAIS =====================

    public void cadastrarProfissional(String nome, String especialidade,
                                      String registro, double valor) {
        if (!Profissional.especialidadeValida(especialidade)) {
            throw new IllegalArgumentException("Especialidade inválida: " + especialidade
                    + ". Válidas: clinica geral, fisioterapia, psicologia, nutricao.");
        }
        // Profissional é abstrata — instancia a subclasse correta (polimorfismo)
        Profissional novo;
        switch (especialidade.toLowerCase()) {
            case "fisioterapia": novo = new Fisioterapeuta(nome, registro, valor, 0); break;
            case "psicologia":   novo = new Psicologo(nome, registro, valor, "");    break;
            case "nutricao":     novo = new Nutricionista(nome, registro, valor, ""); break;
            default:             novo = new ClinicoGeral(nome, registro, valor);      break;
        }
        profissionais.add(novo);
        pessoasCadastradas.add(novo);
        profissionaisPorNome.put(nome, novo);
    }

    public Profissional buscarProfissional(String nome) throws ProfissionalNaoEncontradoException {
        Profissional p = profissionaisPorNome.get(nome);
        if (p == null) throw new ProfissionalNaoEncontradoException(
                "Profissional '" + nome + "' não encontrado.");
        return p;
    }

    public void adicionarHorarioProfissional(String nome, String dia, String periodo)
            throws ProfissionalNaoEncontradoException {
        // AGREGAÇÃO: HorarioDisponivel existe independentemente do Profissional (R8)
        buscarProfissional(nome).adicionarHorario(new HorarioDisponivel(dia, periodo));
    }

    // SOBRECARGA: atualizar sem redefinir horários (R4)
    public void atualizarProfissional(String nome, String registro, double valor)
            throws ProfissionalNaoEncontradoException {
        buscarProfissional(nome).atualizar(registro, valor);
    }

    // SOBRECARGA: atualizar substituindo lista de horários (R4)
    public void atualizarProfissional(String nome, String registro, double valor,
                                       List<HorarioDisponivel> horarios)
            throws ProfissionalNaoEncontradoException {
        buscarProfissional(nome).atualizar(registro, valor, horarios);
    }

    public List<Profissional> listarProfissionais() {
        return new ArrayList<>(profissionais);
    }

    public List<Profissional> filtrarPorEspecialidade(String especialidade) {
        List<Profissional> resultado = new ArrayList<>();
        for (Profissional p : profissionais) {
            if (p.getEspecialidade().equalsIgnoreCase(especialidade)) resultado.add(p);
        }
        return resultado;
    }
}
