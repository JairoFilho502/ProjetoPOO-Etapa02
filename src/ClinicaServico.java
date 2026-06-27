rt java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClinicaServico {

    // R10: ArrayList<Paciente> — mantém ordem de inserção; acesso por índice O(1); tamanho dinâmico
    private List<Paciente> pacientes = new ArrayList<>();

    // R10: ArrayList<Profissional> — suporta hierarquia polimórfica (Fisioterapeuta, Psicologo, etc.)
    private List<Profissional> profissionais = new ArrayList<>();

    // R10: ArrayList<Consulta> — lista editável; remarcação cria nova consulta preservando histórico
    private List<Consulta> consultas = new ArrayList<>();

    // R10: ArrayList<Atendimento> — histórico clínico ordenado cronologicamente por inserção
    private List<Atendimento> atendimentos = new ArrayList<>();

    // R10: ArrayList<Pagamento> — acesso polimórfico por calcularValorFinal() (ligação dinâmica)
    private List<Pagamento> pagamentos = new ArrayList<>();

    // R10: ArrayList<Pessoa> — lista UNIFICADA de todos os cadastrados (Paciente + Profissional)
    private List<Pessoa> pessoasCadastradas = new ArrayList<>();

    // R10: HashSet<String> — controle de CPFs únicos; add() e contains() em O(1); previne duplicatas
    private Set<String> cpfsCadastrados = new HashSet<>();

    // R10: HashMap<CPF, Paciente> — busca direta por CPF em O(1) sem percorrer lista (Jornada 27)
    private Map<String, Paciente> pacientesPorCpf = new HashMap<>();

    // R10: HashMap<Nome, Profissional> — busca por nome em O(1); necessária para agendamento rápido
    private Map<String, Profissional> profissionaisPorNome = new HashMap<>();

    // Registro financeiro de multas.
    private List<Double> multas = new ArrayList<>();
}
