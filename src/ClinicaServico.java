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

    public List<Paciente> listarPacientes() { return new ArrayList<>(pacientes); }

    // ===================== PROFISSIONAIS =====================

    public void cadastrarProfissional(String nome, String especialidade,
                                      String registro, double valor) {
        if (!Profissional.especialidadeValida(especialidade)) {
            throw new IllegalArgumentException("Especialidade inválida: " + especialidade);
        }
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
        for (Profissional p : profissionais) {
            if (p.getEspecialidade().equalsIgnoreCase(especialidade)) resultado.add(p);
        }
        return resultado;
    }

    // ===================== CONSULTAS =====================

    public void agendarConsulta(String cpfPac, String nomeProf, String data, String horario)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {
        agendarConsulta(cpfPac, nomeProf, data, horario, "inicial");
    }

    public void agendarConsulta(String cpfPac, String nomeProf, String data,
                                String horario, String tipo)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {
        Paciente pac = buscarPaciente(cpfPac);
        if (!pac.isAtivo()) throw new PacienteInativoException(
                "Paciente " + pac.getNome() + " está inativo.");
        Profissional prof = buscarProfissional(nomeProf);
        String diaSemana = calcularDiaSemana(data);
        if (!prof.atendeNoDia(diaSemana)) throw new HorarioIndisponivelException(
                prof.getNome() + " não possui expediente no dia " + diaSemana + ".");
        if (verificarConflito(nomeProf, data, horario)) throw new HorarioIndisponivelException(
                "Horário " + horario + " já está ocupado para " + nomeProf + " em " + data + ".");
        consultas.add(new Consulta(cpfPac, nomeProf, data, horario, tipo));
    }

    public void agendarPorEspecialidade(String cpfPac, String especialidade,
                                         String data, String horario)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {
        Paciente pac = buscarPaciente(cpfPac);
        if (!pac.isAtivo()) throw new PacienteInativoException(
                "Paciente " + pac.getNome() + " está inativo.");
        String diaSemana = calcularDiaSemana(data);
        Profissional escolhido = null;
        for (Profissional p : profissionais) {
            if (p.getEspecialidade().equalsIgnoreCase(especialidade)
                    && p.atendeNoDia(diaSemana)
                    && !verificarConflito(p.getNome(), data, horario)) {
                escolhido = p; break;
            }
        }
        if (escolhido == null) throw new ProfissionalNaoEncontradoException(
                "Nenhum profissional de " + especialidade + " disponível em " + data + " às " + horario + ".");
        consultas.add(new Consulta(cpfPac, escolhido.getNome(), data, horario));
    }

    public String sugerirHorario(String nomeProf, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste = (h < 10 ? "0" + h : "" + h) + ":00";
            if (!verificarConflito(nomeProf, data, teste)) return teste;
        }
        return "";
    }

    public double cancelarConsulta(String cpf, String data, String horario,
                                    String horaAtual, String motivo)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta c = buscarConsultaPorChave(cpf, data, horario, null);
        if (c.getStatus().equals("realizada"))
            throw new OperacaoInvalidaException("Consultas concluídas não podem ser canceladas.");
        if (c.getStatus().equals("cancelada"))
            throw new OperacaoInvalidaException("A consulta já está cancelada.");
        double multa = 0;
        try {
            int hC = Integer.parseInt(horario.substring(0, 2));
            int hA = Integer.parseInt(horaAtual.substring(0, 2));
            if (hC - hA < 2) { multa = 50.0; multas.add(multa); }
        } catch (NumberFormatException e) { /* cancela sem multa */ }
        if (motivo == null || motivo.isEmpty()) c.cancelar();
        else c.cancelar(motivo);
        return multa;
    }

    public void remarcarConsulta(String cpf, String dataOrig, String horarioOrig,
                                  String novaData, String novoHorario)
            throws ConsultaNaoEncontradaException, HorarioIndisponivelException,
                   ProfissionalNaoEncontradoException {
        Consulta c = buscarConsultaPorChave(cpf, dataOrig, horarioOrig, "agendada");
        String nomeProf = c.getNomeProfissional();
        if (!novaData.equals(dataOrig)) {
            Profissional prof = profissionaisPorNome.get(nomeProf);
            if (prof != null) {
                String dia = calcularDiaSemana(novaData);
                if (!prof.atendeNoDia(dia)) throw new HorarioIndisponivelException(
                        "Profissional não possui expediente no dia " + dia + ".");
            }
        }
        if (verificarConflito(nomeProf, novaData, novoHorario)) throw new HorarioIndisponivelException(
                "Horário " + novoHorario + " já está ocupado em " + novaData + ".");
        c.remarcar();
        consultas.add(new Consulta(cpf, nomeProf, novaData, novoHorario, c.getTipo()));
    }

    public List<Consulta> listarConsultas() { return new ArrayList<>(consultas); }

    public List<Consulta> buscarConsultasPorPaciente(String cpf)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf);
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : consultas) if (c.getCpfPaciente().equals(cpf)) resultado.add(c);
        return resultado;
    }

    public Consulta buscarConsulta(String cpf, String data, String horario)
            throws ConsultaNaoEncontradaException {
        return buscarConsultaPorChave(cpf, data, horario, null);
    }

    // ===================== ATENDIMENTOS =====================

    // SOBRECARGA: registrar apenas com observações (R4)
    public void registrarAtendimento(int idxConsulta, String obs, String dataRegistro)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta c = validarConsultaParaAtendimento(idxConsulta);
        atendimentos.add(new Atendimento(idxConsulta, obs, dataRegistro));
        c.realizar();
    }

    // SOBRECARGA: registrar com diagnóstico (R4)
    public void registrarAtendimento(int idxConsulta, String obs, String diagnostico,
                                     String dataRegistro)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta c = validarConsultaParaAtendimento(idxConsulta);
        atendimentos.add(new Atendimento(idxConsulta, obs, diagnostico, dataRegistro));
        c.realizar();
    }

    // SOBRECARGA: registrar completo com procedimentos (R4)
    public void registrarAtendimentoCompleto(int idxConsulta, String obs, String diagnostico,
                                              String dataRegistro, String[] procedimentos, int qtd)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta c = validarConsultaParaAtendimento(idxConsulta);
        Atendimento at = new Atendimento(idxConsulta, obs, diagnostico, dataRegistro);
        at.adicionarProcedimentos(procedimentos, qtd);
        atendimentos.add(at);
        c.realizar();
    }

    public Atendimento getUltimoAtendimento() {
        if (atendimentos.isEmpty()) return null;
        return atendimentos.get(atendimentos.size() - 1);
    }

    // ===================== PAGAMENTOS =====================

    public Pagamento processarPagamento(int idxConsulta, double valor, String tipo)
            throws ConsultaNaoEncontradaException, PagamentoInvalidoException,
                   ConvenioNaoCobreException, PacienteNaoEncontradoException,
                   ProfissionalNaoEncontradoException {

        if (idxConsulta < 0 || idxConsulta >= consultas.size())
            throw new ConsultaNaoEncontradaException("Índice de consulta inválido: " + idxConsulta);
        if (valor < 0) throw new PagamentoInvalidoException(
                "Valor não pode ser negativo: R$" + String.format("%.2f", valor));

        Pagamento pg;
        switch (tipo.toLowerCase()) {
            case "dinheiro":
            case "pix":
                // LIGAÇÃO DINÂMICA: calcularValorFinal() em PagamentoDinheiro aplica 5% de desconto
                pg = new PagamentoDinheiro(idxConsulta, valor);
                break;

            case "cartao":
                // calcularValorFinal(): até 3x sem taxa; 4x–6x: +2,5% por parcela extra
                pg = new PagamentoCartao(idxConsulta, valor, 1);
                break;

            case "convenio":
                // calcularValorFinal(): aplica percentualCobertura real do convênio (R8 ASSOCIAÇÃO)
                Consulta c = consultas.get(idxConsulta);
                Paciente pac = buscarPaciente(c.getCpfPaciente());
                Convenio convenio = pac.getConvenio();
                if (convenio == null) throw new PagamentoInvalidoException(
                        "Paciente não possui convênio cadastrado.");
                Profissional prof = buscarProfissional(c.getNomeProfissional());
                if (!convenio.cobreespecialidade(prof.getEspecialidade()))
                    throw new ConvenioNaoCobreException(
                            "Convênio " + convenio.getNome()
                            + " não cobre a especialidade: " + prof.getEspecialidade()
                            + ". Especialidades cobertas: " + convenio.getEspecialidadesCobertas());
                pg = new PagamentoConvenio(idxConsulta, valor, convenio);
                break;

            default:
                throw new PagamentoInvalidoException(
                        "Forma inválida: '" + tipo + "'. Válidas: dinheiro, pix, cartao, convenio.");
        }

        pagamentos.add(pg);
        return pg;
    }

    // Calcula valor com descontos automáticos usando dados reais do sistema
    public double calcularValorAutomatico(int idxConsulta, double multaExtra)
            throws ConsultaNaoEncontradaException, PacienteNaoEncontradoException,
                   ProfissionalNaoEncontradoException {
        if (idxConsulta < 0 || idxConsulta >= consultas.size())
            throw new ConsultaNaoEncontradaException("Índice inválido: " + idxConsulta);
        Consulta c     = consultas.get(idxConsulta);
        Profissional p = buscarProfissional(c.getNomeProfissional());
        Paciente pac   = buscarPaciente(c.getCpfPaciente());
        double valorBase = p.getValorConsulta();
        double desconto  = 0;
        if (c.getTipo().equals("retorno")) desconto += 20;
        Convenio convenio = pac.getConvenio();
        if (convenio != null) {
            // percentualCobertura: 0.40 (SaúdePlus), 0.30 (VidaMais), 0.50 (BemEstar)
            desconto += convenio.getPercentualCobertura() * 100;
        }
        return valorBase * (1.0 - desconto / 100.0) + multaExtra;
    }

    public List<Pagamento> listarPagamentos() { return new ArrayList<>(pagamentos); }

    // ===================== MÉTODOS PRIVADOS =====================

    private boolean verificarConflito(String nomeProf, String data, String horario) {
        for (Consulta c : consultas) {
            if (c.getNomeProfissional().equals(nomeProf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)
                    && c.getStatus().equals("agendada")) return true;
        }
        return false;
    }

    private Consulta buscarConsultaPorChave(String cpf, String data, String horario,
                                             String statusFiltro)
            throws ConsultaNaoEncontradaException {
        for (Consulta c : consultas) {
            if (c.getCpfPaciente().equals(cpf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)
                    && (statusFiltro == null || c.getStatus().equals(statusFiltro))) return c;
        }
        throw new ConsultaNaoEncontradaException(
                "Nenhum agendamento para CPF " + cpf + " em " + data + " às " + horario + ".");
    }

    private Consulta validarConsultaParaAtendimento(int idx)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        if (idx < 0 || idx >= consultas.size())
            throw new ConsultaNaoEncontradaException("Índice inválido: " + idx);
        Consulta c = consultas.get(idx);
        if (!c.getStatus().equals("agendada")) throw new OperacaoInvalidaException(
                "Exige status 'agendada'. Atual: " + c.getStatus());
        return c;
    }

    private String calcularDiaSemana(String data) {
        try {
            int dia = Integer.parseInt(data.substring(0, 2));
            int mes = Integer.parseInt(data.substring(3, 5));
            int ano = Integer.parseInt(data.substring(6, 10));
            if (mes < 3) { mes += 12; ano--; }
            int k = ano % 100, j = ano / 100;
            int r = (dia + (13 * (mes + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
            if (r < 0) r += 7;
            String[] nomes = {"sabado","domingo","segunda","terca","quarta","quinta","sexta"};
            return nomes[r];
        } catch (Exception e) { return ""; }
    }
}
