package servico;

import model.Paciente;
import model.Profissional;
import model.Consulta;
import model.Atendimento;
import model.HorarioDisponivel;
import model.Pessoa;
import financeiro.Pagamento;
import excecoes.ConsultaNaoEncontradaException;
import excecoes.PacienteNaoEncontradoException;
import excecoes.ProfissionalNaoEncontradoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClinicaServico {

    // R10 — 10 colecoes distintas
    private List<Paciente>            pacientes            = new ArrayList<>();
    private List<Profissional>        profissionais        = new ArrayList<>();
    private List<Consulta>            consultas            = new ArrayList<>();
    private List<Atendimento>         atendimentos         = new ArrayList<>();
    private List<Pagamento>           pagamentos           = new ArrayList<>();
    private List<Pessoa>              pessoasCadastradas   = new ArrayList<>();
    private Set<String>               cpfsCadastrados      = new HashSet<>();
    private Map<String, Paciente>     pacientesPorCpf      = new HashMap<>();
    private Map<String, Profissional> profissionaisPorNome = new HashMap<>();
    private List<Double>              multas               = new ArrayList<>();

    // ===================== PACIENTES =====================

    public void cadastrarPaciente(String nome, String cpf) {
        if (!cpfsCadastrados.add(cpf))
            throw new IllegalArgumentException("CPF " + cpf + " ja cadastrado.");
        Paciente p = new Paciente(nome, cpf);
        pacientes.add(p);
        pessoasCadastradas.add(p);
        pacientesPorCpf.put(cpf, p);
    }

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
        if (p == null) throw new PacienteNaoEncontradoException("CPF " + cpf + " nao encontrado.");
        return p;
    }

    public void desativarPaciente(String cpf) throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).desativar();
    }

    public List<Paciente> listarPacientes() { return new ArrayList<>(pacientes); }

    // ===================== PROFISSIONAIS =====================

    public void cadastrarProfissional(String nome, String especialidade,
                                      String registro, double valor) {
        if (!Profissional.especialidadeValida(especialidade))
            throw new IllegalArgumentException("Especialidade invalida: " + especialidade);
        Profissional novo = Profissional.criar(nome, especialidade, registro, valor);
        profissionais.add(novo);
        pessoasCadastradas.add(novo);
        profissionaisPorNome.put(nome, novo);
    }

    public Profissional buscarProfissional(String nome) throws ProfissionalNaoEncontradoException {
        Profissional p = profissionaisPorNome.get(nome);
        if (p == null)
            throw new ProfissionalNaoEncontradoException("Profissional '" + nome + "' nao encontrado.");
        return p;
    }

    public void adicionarHorarioProfissional(String nome, String dia, String periodo)
            throws ProfissionalNaoEncontradoException {
        buscarProfissional(nome).adicionarHorario(new HorarioDisponivel(dia, periodo));
    }

    public void atualizarProfissional(String nome, String registro, double valor)
            throws ProfissionalNaoEncontradoException {
        buscarProfissional(nome).atualizar(registro, valor);
    }

    public void atualizarProfissional(String nome, String registro, double valor,
                                      List<HorarioDisponivel> horarios)
            throws ProfissionalNaoEncontradoException {
        buscarProfissional(nome).atualizar(registro, valor, horarios);
    }

    public List<Profissional> listarProfissionais() { return new ArrayList<>(profissionais); }

    public List<Profissional> filtrarPorEspecialidade(String especialidade) {
        List<Profissional> resultado = new ArrayList<>();
        for (Profissional p : profissionais)
            if (p.getEspecialidade().equalsIgnoreCase(especialidade)) resultado.add(p);
        return resultado;
    }

    // ===================== CONSULTAS =====================

    public void agendarConsulta(String cpf, String nomeProfissional, String data, String horario)
            throws PacienteNaoEncontradoException, ProfissionalNaoEncontradoException {
        if (!buscarPaciente(cpf).estaAtivo())
            throw new IllegalStateException("Paciente inativo.");
        buscarProfissional(nomeProfissional);
        consultas.add(new Consulta(cpf, nomeProfissional, data, horario));
    }

    public void cancelarConsulta(String cpf, String data, String horario)
            throws ConsultaNaoEncontradaException {
        encontrarConsulta(cpf, data, horario).cancelar();
    }

    public void registrarAtendimento(int indiceConsulta, String obs, String diagnostico)
            throws ConsultaNaoEncontradaException {
        if (indiceConsulta < 0 || indiceConsulta >= consultas.size())
            throw new ConsultaNaoEncontradaException("Indice de consulta invalido.");
        Consulta c = consultas.get(indiceConsulta);
        if (!c.getStatus().equals("agendada"))
            throw new ConsultaNaoEncontradaException("Consulta nao esta agendada.");
        atendimentos.add(new Atendimento(indiceConsulta, obs, diagnostico));
        c.realizar();
    }

    public void registrarPagamento(Pagamento pagamento) { pagamentos.add(pagamento); }
    public void adicionarMulta(double valor)            { multas.add(valor); }

    public List<Consulta>    listarConsultas()    { return new ArrayList<>(consultas); }
    public List<Atendimento> listarAtendimentos() { return new ArrayList<>(atendimentos); }
    public List<Pagamento>   listarPagamentos()   { return new ArrayList<>(pagamentos); }
    public List<Double>      listarMultas()       { return new ArrayList<>(multas); }
    public List<Pessoa>      listarPessoas()      { return new ArrayList<>(pessoasCadastradas); }

    private Consulta encontrarConsulta(String cpf, String data, String horario)
            throws ConsultaNaoEncontradaException {
        for (Consulta c : consultas) {
            if (c.getCpfPaciente().equals(cpf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)
                    && c.getStatus().equals("agendada"))
                return c;
        }
        throw new ConsultaNaoEncontradaException(
                "Consulta nao encontrada: CPF=" + cpf + " Data=" + data + " Hora=" + horario);
    }
}
