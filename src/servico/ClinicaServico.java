package servico;

import model.Paciente;
import model.Profissional;
import model.Consulta;
import model.Atendimento;
import model.HorarioDisponivel;
import model.Pessoa;
import financeiro.Pagamento;
import excecoes.ConsultaNaoEncontradaException;
import excecoes.HorarioIndisponivelException;
import excecoes.PacienteInativoException;
import excecoes.PacienteNaoEncontradoException;
import excecoes.ProfissionalNaoEncontradoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClinicaServico {

    // R10 — 10 coleções distintas com tipos apropriados para cada uso

    // ArrayList<Paciente>: ordem de inserção importa — pacientes listados na ordem
    // de cadastro
    private List<Paciente> pacientes = new ArrayList<>();

    // ArrayList<Profissional>: ordem de inserção importa — profissionais listados
    // na ordem de cadastro
    private List<Profissional> profissionais = new ArrayList<>();

    // ArrayList<Consulta>: ordem de inserção importa — índice usado para
    // referenciar atendimentos
    private List<Consulta> consultas = new ArrayList<>();

    // ArrayList<Atendimento>: mantém histórico cronológico dos atendimentos
    private List<Atendimento> atendimentos = new ArrayList<>();

    // ArrayList<Pagamento>: mantém histórico cronológico dos pagamentos
    private List<Pagamento> pagamentos = new ArrayList<>();

    // ArrayList<Pessoa>: lista unificada para relatório polimórfico (R5)
    private List<Pessoa> pessoasCadastradas = new ArrayList<>();

    // HashSet<String>: sem duplicatas e busca O(1) — ideal para verificar CPF já
    // cadastrado
    private Set<String> cpfsCadastrados = new HashSet<>();

    // HashMap<String, Paciente>: acesso direto por CPF sem percorrer lista — O(1)
    // médio
    private Map<String, Paciente> pacientesPorCpf = new HashMap<>();

    // HashMap<String, Profissional>: acesso direto por nome — O(1) médio
    private Map<String, Profissional> profissionaisPorNome = new HashMap<>();

    // ArrayList<Double>: mantém histórico de multas na ordem em que foram aplicadas
    private List<Double> multas = new ArrayList<>();

    // pacientes

    public void cadastrarPaciente(String nome, String cpf) {

        // cpfsCadastrados.add retorna false se o CPF já existir no HashSet
        if (!cpfsCadastrados.add(cpf))
            throw new IllegalArgumentException("CPF " + cpf + " ja cadastrado.");
        Paciente p = new Paciente(nome, cpf);
        pacientes.add(p);
        pessoasCadastradas.add(p);
        pacientesPorCpf.put(cpf, p);
    }

    // SOBRECARGA: complementar sem convênio

    public void complementarPaciente(String cpf, int idade, String telefone)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).complementar(idade, telefone);
    }

    // SOBRECARGA: complementar com convênio
    public void complementarPaciente(String cpf, int idade, String telefone, String nomeConvenio)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).complementar(idade, telefone, nomeConvenio);
    }

    // busca pelo HashMap: O(1) médio, muito mais rápido que percorrer a lista
    // inteira

    public Paciente buscarPaciente(String cpf) throws PacienteNaoEncontradoException {
        Paciente p = pacientesPorCpf.get(cpf);
        if (p == null)
            throw new PacienteNaoEncontradoException("CPF " + cpf + " nao encontrado.");
        return p;
    }

    public void desativarPaciente(String cpf) throws PacienteNaoEncontradoException {
        buscarPaciente(cpf).desativar();
    }

    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes);
    }

    // Profissionais

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

    // SOBRECARGA: atualizar sem horários
    public void atualizarProfissional(String nome, String registro, double valor)
            throws ProfissionalNaoEncontradoException {
        buscarProfissional(nome).atualizar(registro, valor);
    }

    // SOBRECARGA: atualizar com lista de horários
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
        for (Profissional p : profissionais)
            if (p.getEspecialidade().equalsIgnoreCase(especialidade))
                resultado.add(p);
        return resultado;
    }

    // Consultas

    // verifica conflito de horário e paciente ativo antes de agendar..
    public void agendarConsulta(String cpf, String nomeProfissional, String data, String horario)
            throws PacienteNaoEncontradoException, ProfissionalNaoEncontradoException,
            PacienteInativoException, HorarioIndisponivelException {

        if (!buscarPaciente(cpf).estaAtivo())
            throw new PacienteInativoException("Paciente " + cpf + " esta inativo.");
        buscarProfissional(nomeProfissional);

        if (temConflito(nomeProfissional, data, horario))
            throw new HorarioIndisponivelException(
                    "Horario " + horario + " em " + data + " indisponivel para " + nomeProfissional);
        consultas.add(new Consulta(cpf, nomeProfissional, data, horario));
    }

    // SOBRECARGA: agendar informando o tipo da consulta

    public void agendarConsulta(String cpf, String nomeProfissional, String data, String horario,
            String tipo)
            throws PacienteNaoEncontradoException, ProfissionalNaoEncontradoException,
            PacienteInativoException, HorarioIndisponivelException {
        if (!buscarPaciente(cpf).estaAtivo())
            throw new PacienteInativoException("Paciente " + cpf + " esta inativo.");
        buscarProfissional(nomeProfissional);
        if (temConflito(nomeProfissional, data, horario))
            throw new HorarioIndisponivelException(
                    "Horario " + horario + " em " + data + " indisponivel para " + nomeProfissional);
        consultas.add(new Consulta(cpf, nomeProfissional, data, horario, tipo));
    }

    public void cancelarConsulta(String cpf, String data, String horario)
            throws ConsultaNaoEncontradaException {
        encontrarConsulta(cpf, data, horario).cancelar();
    }

    // registra atendimento a partir de um objeto Atendimento já construído pelo
    // Main.
    public void registrarAtendimento(Atendimento atendimento, int indiceConsulta)
            throws ConsultaNaoEncontradaException {
        if (indiceConsulta < 0 || indiceConsulta >= consultas.size())
            throw new ConsultaNaoEncontradaException("Indice de consulta invalido.");
        Consulta c = consultas.get(indiceConsulta);
        if (!c.getStatus().equals("agendada"))
            throw new ConsultaNaoEncontradaException("Consulta nao esta agendada.");
        atendimentos.add(atendimento);
        c.realizar();
    }

    public void registrarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public void adicionarMulta(double valor) {
        multas.add(valor);
    }

    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }

    public List<Atendimento> listarAtendimentos() {
        return new ArrayList<>(atendimentos);
    }

    public List<Pagamento> listarPagamentos() {
        return new ArrayList<>(pagamentos);
    }

    public List<Double> listarMultas() {
        return new ArrayList<>(multas);
    }

    public List<Pessoa> listarPessoas() {
        return new ArrayList<>(pessoasCadastradas);
    }

    // verifica se o profissional já tem consulta agendada naquele dia/horário
    public boolean temConflito(String nomeProf, String data, String horario) {
        for (Consulta c : consultas) {
            if (c.getNomeProfissional().equals(nomeProf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)
                    && c.getStatus().equals("agendada"))
                return true;
        }
        return false;
    }

    // testa horários das 8h às 18h e retorna o primeiro que estiver livre.
    public String sugerirHorario(String nomeProf, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste = (h < 10 ? "0" + h : "" + h) + ":00";
            if (!temConflito(nomeProf, data, teste))
                return teste;
        }
        return "";
    }

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
