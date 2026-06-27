import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Etapas 10, 13, 14, 15 — centraliza toda a lógica de negócio.
// Main.java só lê Scanner, chama métodos aqui e exibe resultados (separação de responsabilidades).
public class ClinicaServico {

    // ===================== R10 — INFRAESTRUTURA DE COLEÇÕES (Seção 4.2 do guia) =====================

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
    // Necessária para relatório polimórfico (R5): percorre com p.exibirResumo() — ligação dinâmica
    private List<Pessoa> pessoasCadastradas = new ArrayList<>();

    // R10: HashSet<String> — controle de CPFs únicos; add() e contains() em O(1); previne duplicatas
    private Set<String> cpfsCadastrados = new HashSet<>();

    // R10: HashMap<CPF, Paciente> — busca direta por CPF em O(1) sem percorrer lista (Jornada 27)
    private Map<String, Paciente> pacientesPorCpf = new HashMap<>();

    // R10: HashMap<Nome, Profissional> — busca por nome em O(1); necessária para agendamento rápido
    private Map<String, Profissional> profissionaisPorNome = new HashMap<>();

    // Registro financeiro de multas separado das consultas
    private List<Double> multas = new ArrayList<>();

    // ===================== PACIENTES =====================

    public void cadastrarPaciente(String nome, String cpf) {
        // R10: HashSet.add() retorna false automaticamente se CPF já existe — O(1), sem loop
        if (!cpfsCadastrados.add(cpf)) {
            throw new IllegalArgumentException("CPF " + cpf + " já cadastrado no sistema.");
        }
        Paciente p = new Paciente(nome, cpf);
        pacientes.add(p);
        pessoasCadastradas.add(p);    // lista unificada para relatório polimórfico (Jornada 15)
        pacientesPorCpf.put(cpf, p);  // índice rápido para busca O(1) (Jornada 27)
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
        // R10: HashMap.get() em O(1) — mais eficiente que loop linear sobre lista
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
        return new ArrayList<>(pacientes); // cópia defensiva — evita modificação externa
    }

    // ===================== PROFISSIONAIS =====================

    public void cadastrarProfissional(String nome, String especialidade,
                                      String registro, double valor) {
        if (!Profissional.especialidadeValida(especialidade)) {
            throw new IllegalArgumentException("Especialidade inválida: " + especialidade
                    + ". Válidas: clinica geral, fisioterapia, psicologia, nutricao.");
        }
        // Profissional é abstrata — instancia a subclasse correta pela especialidade (polimorfismo)
        Profissional novo;
        switch (especialidade.toLowerCase()) {
            case "fisioterapia": novo = new Fisioterapeuta(nome, registro, valor, 0);    break;
            case "psicologia":   novo = new Psicologo(nome, registro, valor, "");        break;
            case "nutricao":     novo = new Nutricionista(nome, registro, valor, "");    break;
            default:             novo = new ClinicoGeral(nome, registro, valor);         break; // clinica geral
        }
        profissionais.add(novo);
        pessoasCadastradas.add(novo);          // lista unificada para relatório polimórfico
        profissionaisPorNome.put(nome, novo);  // índice rápido para agendamento
    }

    public Profissional buscarProfissional(String nome) throws ProfissionalNaoEncontradoException {
        Profissional p = profissionaisPorNome.get(nome);
        if (p == null) {
            throw new ProfissionalNaoEncontradoException("Profissional '" + nome + "' não encontrado.");
        }
        return p;
    }

    public void adicionarHorarioProfissional(String nome, String dia, String periodo)
            throws ProfissionalNaoEncontradoException {
        // AGREGAÇÃO: HorarioDisponivel existe independentemente do profissional (R8)
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

    // ===================== CONSULTAS =====================

    // SOBRECARGA: agendar sem informar tipo — usa "inicial" por padrão (R4)
    public void agendarConsulta(String cpfPac, String nomeProf, String data, String horario)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {
        agendarConsulta(cpfPac, nomeProf, data, horario, "inicial");
    }

    // SOBRECARGA: agendar informando tipo explícito (R4)
    public void agendarConsulta(String cpfPac, String nomeProf, String data,
                                String horario, String tipo)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {

        Paciente pac = buscarPaciente(cpfPac); // HashMap O(1)
        if (!pac.isAtivo()) {
            throw new PacienteInativoException(
                    "Paciente " + pac.getNome() + " está inativo. Reative antes de agendar.");
        }

        Profissional prof = buscarProfissional(nomeProf); // HashMap O(1)

        String diaSemana = calcularDiaSemana(data);
        if (!prof.atendeNoDia(diaSemana)) {
            throw new HorarioIndisponivelException(
                    prof.getNome() + " não possui expediente no dia " + diaSemana + ".");
        }
        if (verificarConflito(nomeProf, data, horario)) {
            throw new HorarioIndisponivelException(
                    "Horário " + horario + " já está ocupado para " + nomeProf + " em " + data + ".");
        }

        consultas.add(new Consulta(cpfPac, nomeProf, data, horario, tipo));
    }

    // Jornada 6: busca profissional disponível pela especialidade
    public void agendarPorEspecialidade(String cpfPac, String especialidade,
                                         String data, String horario)
            throws PacienteNaoEncontradoException, PacienteInativoException,
                   ProfissionalNaoEncontradoException, HorarioIndisponivelException {

        Paciente pac = buscarPaciente(cpfPac);
        if (!pac.isAtivo()) {
            throw new PacienteInativoException(
                    "Paciente " + pac.getNome() + " está inativo.");
        }

        String diaSemana = calcularDiaSemana(data);
        Profissional escolhido = null;
        for (Profissional p : profissionais) {
            if (p.getEspecialidade().equalsIgnoreCase(especialidade)
                    && p.atendeNoDia(diaSemana)
                    && !verificarConflito(p.getNome(), data, horario)) {
                escolhido = p;
                break;
            }
        }

        if (escolhido == null) {
            throw new ProfissionalNaoEncontradoException(
                    "Nenhum profissional de " + especialidade
                    + " disponível em " + data + " às " + horario + ".");
        }
        consultas.add(new Consulta(cpfPac, escolhido.getNome(), data, horario));
    }

    // Jornada 7/19: retorna horário alternativo para o profissional no mesmo dia
    public String sugerirHorario(String nomeProf, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste = (h < 10 ? "0" + h : "" + h) + ":00";
            if (!verificarConflito(nomeProf, data, teste)) return teste;
        }
        return ""; // sem horário disponível
    }

    // Jornada 9: cancelar consulta — retorna valor da multa (0.0 se sem multa)
    public double cancelarConsulta(String cpf, String data, String horario,
                                    String horaAtual, String motivo)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {

        Consulta c = buscarConsultaPorChave(cpf, data, horario, null);

        if (c.getStatus().equals("realizada")) {
            throw new OperacaoInvalidaException(
                    "Consultas concluídas são imutáveis e não podem ser canceladas.");
        }
        if (c.getStatus().equals("cancelada")) {
            throw new OperacaoInvalidaException("A consulta já está cancelada.");
        }

        double multa = 0;
        try {
            int hConsulta = Integer.parseInt(horario.substring(0, 2));
            int hAgora    = Integer.parseInt(horaAtual.substring(0, 2));
            if (hConsulta - hAgora < 2) {
                multa = 50.0;
                multas.add(multa);
            }
        } catch (NumberFormatException e) {
            // formato de horário inválido — cancela sem multa
        }

        if (motivo == null || motivo.isEmpty()) c.cancelar();
        else c.cancelar(motivo);

        return multa;
    }

    // Jornada 10: remarcação cria nova consulta e mantém histórico
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
                if (!prof.atendeNoDia(dia)) {
                    throw new HorarioIndisponivelException(
                            "Profissional não possui expediente no dia " + dia + ".");
                }
            }
        }

        if (verificarConflito(nomeProf, novaData, novoHorario)) {
            throw new HorarioIndisponivelException(
                    "Horário " + novoHorario + " já está ocupado em " + novaData + ".");
        }

        c.remarcar(); // marca original como remarcada
        consultas.add(new Consulta(cpf, nomeProf, novaData, novoHorario, c.getTipo()));
    }

    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }

    public List<Consulta> buscarConsultasPorPaciente(String cpf)
            throws PacienteNaoEncontradoException {
        buscarPaciente(cpf); // valida existência antes de filtrar
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta c : consultas) {
            if (c.getCpfPaciente().equals(cpf)) resultado.add(c);
        }
        return resultado;
    }

    // Jornada 13: busca por filtros (CPF + data + horário)
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

        if (idxConsulta < 0 || idxConsulta >= consultas.size()) {
            throw new ConsultaNaoEncontradaException(
                    "Índice de consulta inválido: " + idxConsulta);
        }
        if (valor < 0) {
            throw new PagamentoInvalidoException(
                    "Valor não pode ser negativo: R$" + String.format("%.2f", valor));
        }

        Pagamento pg;
        switch (tipo.toLowerCase()) {
            case "dinheiro":
            case "pix":
                // LIGAÇÃO DINÂMICA: calcularValorFinal() em PagamentoDinheiro aplica 5% de desconto
                pg = new PagamentoDinheiro(idxConsulta, valor);
                break;

            case "cartao":
                // PagamentoCartao criado por I5 — solicitar número de parcelas
                // calcularValorFinal() aplica: até 3x sem taxa; 4x–6x: +2,5% por parcela extra
                pg = new PagamentoCartao(idxConsulta, valor, 1);
                break;

            case "convenio":
                // PagamentoConvenio criado por I5 — usa Convenio do paciente (ASSOCIAÇÃO I1)
                // calcularValorFinal() aplica percentualCobertura do convênio
                Consulta c = consultas.get(idxConsulta);
                Paciente pac = buscarPaciente(c.getCpfPaciente());
                Convenio convenio = pac.getConvenio();
                if (convenio == null) {
                    throw new PagamentoInvalidoException(
                            "Paciente não possui convênio cadastrado.");
                }
                Profissional prof = buscarProfissional(c.getNomeProfissional());
                if (!convenio.cobreespecialidade(prof.getEspecialidade())) {
                    throw new ConvenioNaoCobreException(
                            "Convênio " + convenio.getNome()
                            + " não cobre a especialidade: " + prof.getEspecialidade()
                            + ". Especialidades cobertas: " + convenio.getEspecialidadesCobertas());
                }
                pg = new PagamentoConvenio(idxConsulta, valor, convenio);
                break;

            default:
                throw new PagamentoInvalidoException(
                        "Forma de pagamento inválida: '" + tipo
                        + "'. Válidas: dinheiro, pix, cartao, convenio.");
        }

        pagamentos.add(pg);
        return pg;
    }

    // Calcula valor com descontos automáticos usando os dados reais do sistema
    public double calcularValorAutomatico(int idxConsulta, double multaExtra)
            throws ConsultaNaoEncontradaException, PacienteNaoEncontradoException,
                   ProfissionalNaoEncontradoException {

        if (idxConsulta < 0 || idxConsulta >= consultas.size()) {
            throw new ConsultaNaoEncontradaException("Índice inválido: " + idxConsulta);
        }

        Consulta c    = consultas.get(idxConsulta);
        Profissional p = buscarProfissional(c.getNomeProfissional());
        Paciente pac   = buscarPaciente(c.getCpfPaciente());

        double valorBase = p.getValorConsulta();
        double desconto  = 0;

        if (c.getTipo().equals("retorno")) desconto += 20; // 20% desconto em retorno

        // Desconto via convênio: usa percentualCobertura real do objeto Convenio (R8 ASSOCIAÇÃO)
        Convenio convenio = pac.getConvenio();
        if (convenio != null) {
            // percentualCobertura: 0.40 (SaúdePlus), 0.30 (VidaMais), 0.50 (BemEstar)
            desconto += convenio.getPercentualCobertura() * 100;
        }

        return valorBase * (1.0 - desconto / 100.0) + multaExtra;
    }

    public List<Pagamento> listarPagamentos() {
        return new ArrayList<>(pagamentos);
    }

    // ===================== RELATÓRIOS — Etapas 13, 14, 15 =====================

    // Jornada 13: relatório geral de consultas
    public void relatorioGeral() {
        System.out.println("\n=== RELATÓRIO GERAL ===");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta registrada.");
            return;
        }
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println("[" + i + "] " + consultas.get(i).exibirResumo());
            String diag = buscarDiagnostico(i);
            if (!diag.isEmpty()) System.out.println("    Diagnóstico: " + diag);
            System.out.println("---");
        }
    }

    // Jornada 13: por profissional
    public void relatorioPorProfissional(String nomeProf) {
        System.out.println("\n=== RELATÓRIO — " + nomeProf + " ===");
        boolean achou = false;
        for (Consulta c : consultas) {
            if (c.getNomeProfissional().equals(nomeProf)) {
                System.out.println(c.exibirResumo());
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta registrada para esse profissional.");
    }

    // Jornada 13: por período (usa Relatorio.estaNoIntervalo para comparar datas)
    public void relatorioPorPeriodo(String dataInicio, String dataFim) {
        System.out.println("\n=== RELATÓRIO — " + dataInicio + " a " + dataFim + " ===");
        boolean achou = false;
        for (Consulta c : consultas) {
            if (Relatorio.estaNoIntervalo(c.getData(), dataInicio, dataFim)) {
                System.out.println(c.exibirResumo());
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta no período informado.");
    }

    // Jornada 15: relatório UNIFICADO — percorre List<Pessoa> com Ligação Dinâmica (R5)
    public void relatorioUnificado() {
        System.out.println("\n=== RELATÓRIO UNIFICADO DE CADASTROS ===");
        int totalPac = 0, totalProf = 0;

        for (Pessoa p : pessoasCadastradas) {
            // R5 — LIGAÇÃO DINÂMICA: exibirResumo() invocado via referência Pessoa
            // O método executado é resolvido em TEMPO DE EXECUÇÃO pelo tipo real do objeto
            System.out.println(p.exibirResumo()); // ligação dinâmica

            // R5 — instanceof para Dynamic Casting seguro: acessa métodos específicos da subclasse
            if (p instanceof Paciente) {
                Paciente pac = (Paciente) p; // cast seguro — instanceof garante o tipo
                if (!pac.isAtivo()) System.out.println("  [PACIENTE INATIVO]");
                totalPac++;
            } else if (p instanceof Profissional) {
                Profissional prof = (Profissional) p; // cast seguro — instanceof garante o tipo
                System.out.println("  Horários cadastrados: " + prof.getHorarios().size());
                totalProf++;
            }
            System.out.println("---");
        }

        System.out.println("\nTotal de pacientes    : " + totalPac);
        System.out.println("Total de profissionais: " + totalProf);
        System.out.println("Total geral           : " + pessoasCadastradas.size());
    }

    // Jornada 15: relatório financeiro — percorre List<Pagamento> com Ligação Dinâmica (R5)
    public void relatorioFinanceiro() {
        System.out.println("\n=== RELATÓRIO FINANCEIRO ===");
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }

        double totalFaturado = 0;
        double totalMultas   = 0;

        for (Pagamento pg : pagamentos) {
            // R5 — LIGAÇÃO DINÂMICA: calcularValorFinal() resolvido em TEMPO DE EXECUÇÃO
            // PagamentoDinheiro, PagamentoCartao e PagamentoConvenio retornam valores diferentes
            double vf = pg.calcularValorFinal(); // ligação dinâmica
            System.out.println(pg.exibirResumo()
                    + " | Final: " + Relatorio.formatarMoeda(vf));
            totalFaturado += vf;
        }

        for (double m : multas) totalMultas += m;

        System.out.println("\nTotal faturado      : " + Relatorio.formatarMoeda(totalFaturado));
        System.out.println("Total em multas     : " + Relatorio.formatarMoeda(totalMultas));
        System.out.println("Consultas realizadas: " + contarPorStatus("realizada"));
        System.out.println("Consultas canceladas: " + contarPorStatus("cancelada"));
    }

    // Jornada 26: exportação operacional — percorre List<Exportavel> (R7)
    public void exportarDados() {
        System.out.println("\n=== EXPORTAÇÃO DE DADOS OPERACIONAIS ===");

        // R7: lista unificada de objetos que implementam Exportavel (polimorfismo via interface)
        List<Exportavel> itens = new ArrayList<>();
        for (Consulta   c  : consultas)    itens.add(c);
        for (Atendimento a : atendimentos) itens.add(a);
        for (Pagamento  pg : pagamentos)   itens.add(pg);

        if (itens.isEmpty()) {
            System.out.println("Nenhum dado operacional para exportar.");
            return;
        }

        // R7: chamada polimórfica via interface — cada tipo retorna seu próprio formato
        // LIGAÇÃO DINÂMICA: exportarDados() resolvido em runtime pelo tipo real (Consulta/Atendimento/Pagamento)
        for (Exportavel item : itens) {
            System.out.println(item.exportarDados()); // ligação dinâmica via interface
            System.out.println("---");
        }
        System.out.println("Total exportado: " + itens.size() + " registros.");
    }

    // ===================== MÉTODOS PRIVADOS — lógica interna =====================

    private boolean verificarConflito(String nomeProf, String data, String horario) {
        for (Consulta c : consultas) {
            if (c.getNomeProfissional().equals(nomeProf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)
                    && c.getStatus().equals("agendada")) {
                return true;
            }
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
                    && (statusFiltro == null || c.getStatus().equals(statusFiltro))) {
                return c;
            }
        }
        throw new ConsultaNaoEncontradaException(
                "Nenhum agendamento encontrado para CPF " + cpf
                + " em " + data + " às " + horario + ".");
    }

    private Consulta validarConsultaParaAtendimento(int idx)
            throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        if (idx < 0 || idx >= consultas.size()) {
            throw new ConsultaNaoEncontradaException("Índice de consulta inválido: " + idx);
        }
        Consulta c = consultas.get(idx);
        if (!c.getStatus().equals("agendada")) {
            throw new OperacaoInvalidaException(
                    "Registro de atendimento exige consulta com status 'agendada'. "
                    + "Status atual: " + c.getStatus());
        }
        return c;
    }

    private String buscarDiagnostico(int idxConsulta) {
        for (Atendimento a : atendimentos) {
            if (a.getIndiceConsulta() == idxConsulta) return a.getDiagnostico();
        }
        return "";
    }

    private int contarPorStatus(String status) {
        int count = 0;
        for (Consulta c : consultas) if (c.getStatus().equals(status)) count++;
        return count;
    }

    // Algoritmo de Zeller — converte DD/MM/AAAA para nome do dia da semana (lógica de negócio)
    private String calcularDiaSemana(String data) {
        try {
            int dia = Integer.parseInt(data.substring(0, 2));
            int mes = Integer.parseInt(data.substring(3, 5));
            int ano = Integer.parseInt(data.substring(6, 10));

            if (mes < 3) { mes += 12; ano--; }
            int k = ano % 100;
            int j = ano / 100;
            int resultado = (dia + (13 * (mes + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
            if (resultado < 0) resultado += 7;

            String[] nomes = {"sabado", "domingo", "segunda", "terca", "quarta", "quinta", "sexta"};
            return nomes[resultado];
        } catch (Exception e) {
            return ""; // data inválida — não bloqueia o sistema
        }
    }
}
