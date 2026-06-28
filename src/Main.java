import model.Paciente;
import model.Profissional;
import model.Pessoa;
import model.Consulta;
import model.Atendimento;
import model.HorarioDisponivel;
import financeiro.Pagamento;
import servico.ClinicaServico;
import servico.Relatorio;
import excecoes.ConsultaNaoEncontradaException;
import excecoes.HorarioIndisponivelException;
import excecoes.PacienteInativoException;
import excecoes.PacienteNaoEncontradoException;
import excecoes.PagamentoInvalidoException;
import excecoes.ProfissionalNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // toda a lógica de negócio e armazenamento delegada ao ClinicaServico (R10)
    static ClinicaServico servico = new ClinicaServico();

    // scanner compartilhado por todos os métodos do menu
    static Scanner sc = new Scanner(System.in);

    // fica pedindo até o usuário digitar um número válido
    public static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: informe um numero inteiro valido.");
            }
        }
    }

    // SOBRECARGA: mesma ideia mas pra número decimal
    public static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: informe um numero decimal valido.");
            }
        }
    }

    // menu principal
    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== CLINICA VIDAPLENA ===");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("3 - Consultas");
            System.out.println("4 - Atendimentos");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Relatorios");
            System.out.println("0 - Sair");
            opcao = lerInteiro("Escolha: ");
            switch (opcao) {
                case 1:
                    menuPacientes();
                    break;
                case 2:
                    menuProfissionais();
                    break;
                case 3:
                    menuConsultas();
                    break;
                case 4:
                    menuAtendimentos();
                    break;
                case 5:
                    menuPagamentos();
                    break;
                case 6:
                    menuRelatorios();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
        System.out.println("Sistema encerrado.");
    }

    // pacientes.

    public static void menuPacientes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Complementar cadastro");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Desativar");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");
            switch (op) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    complementarPaciente();
                    break;
                case 3:
                    buscarPaciente();
                    break;
                case 4:
                    listarPacientes();
                    break;
                case 5:
                    desativarPaciente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        int tipo = lerInteiro("Tipo (1-Minimo / 2-Com idade e tel / 3-Completo): ");
        try {
            servico.cadastrarPaciente(nome, cpf);
            if (tipo >= 2) {
                int idade = lerInteiro("Idade: ");
                System.out.print("Telefone: ");
                String tel = sc.nextLine();
                if (tipo == 3) {
                    System.out.print("Convenio: ");
                    servico.complementarPaciente(cpf, idade, tel, sc.nextLine());
                } else {
                    servico.complementarPaciente(cpf, idade, tel);
                }
            }
            System.out.println("Paciente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public static void complementarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        int tipo = lerInteiro("Vai informar convenio? (1-Nao / 2-Sim): ");
        int idade = lerInteiro("Idade: ");
        System.out.print("Telefone: ");
        String tel = sc.nextLine();
        try {
            if (tipo == 1) {
                servico.complementarPaciente(cpf, idade, tel);
            } else {
                System.out.print("Convenio: ");
                servico.complementarPaciente(cpf, idade, tel, sc.nextLine());
            }
            System.out.println("Cadastro atualizado!");
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Paciente nao encontrado.");
        }
    }

    public static void buscarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        try {
            System.out.println(servico.buscarPaciente(cpf).exibirResumo());
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Paciente nao encontrado.");
        }
    }

    public static void listarPacientes() {
        List<Paciente> lista = servico.listarPacientes();
        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente p : lista)
            System.out.println(p.exibirResumo());
    }

    public static void desativarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        try {
            servico.desativarPaciente(cpf);
            System.out.println("Paciente desativado.");
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Paciente nao encontrado.");
        }
    }

    // profissionais
    public static void menuProfissionais() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar cadastro");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");

            switch (op) {
                case 1:
                    cadastrarProfissional();
                    break;
                case 2:
                    atualizarProfissional();
                    break;
                case 3:
                    listarProfissionais();
                    break;
                case 4:
                    filtrarProfissionais();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void cadastrarProfissional() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Especialidade (clinica geral/fisioterapia/psicologia/nutricao): ");
        String esp = sc.nextLine().trim().toLowerCase();

        int tipo = lerInteiro("Tipo (1-Minimo / 2-Com registro e valor / 3-Completo): ");
        String reg = "";
        double valor = 0;
        if (tipo >= 2) {
            System.out.print("Registro: ");
            reg = sc.nextLine();
            valor = lerDouble("Valor consulta: ");
        }

        try {
            servico.cadastrarProfissional(nome, esp, reg, valor);
            if (tipo == 3) {
                int qtd = lerInteiro("Quantos dias atende? ");
                for (int i = 0; i < qtd; i++) {
                    System.out.print("Dia " + (i + 1) + " (ex: segunda): ");
                    String dia = sc.nextLine();
                    System.out.print("Turno (manha/tarde): ");
                    try {
                        servico.adicionarHorarioProfissional(nome, dia, sc.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Horario invalido: " + e.getMessage());
                    }
                }
            }
            System.out.println("Profissional cadastrado!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (ProfissionalNaoEncontradoException e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public static void atualizarProfissional() {
        System.out.print("Nome do profissional: ");
        String nome = sc.nextLine();
        int tipo = lerInteiro("Vai informar dias? (1-Nao / 2-Sim): ");
        System.out.print("Registro: ");
        String reg = sc.nextLine();
        double valor = lerDouble("Valor consulta: ");
        try {
            if (tipo == 1) {
                servico.atualizarProfissional(nome, reg, valor);
            } else {
                int qtd = lerInteiro("Quantos dias? ");
                List<HorarioDisponivel> horarios = new ArrayList<>();
                for (int i = 0; i < qtd; i++) {
                    System.out.print("Dia " + (i + 1) + ": ");
                    String dia = sc.nextLine();
                    System.out.print("Turno (manha/tarde): ");
                    try {
                        horarios.add(new HorarioDisponivel(dia, sc.nextLine()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Horario invalido: " + e.getMessage());
                    }
                }
                servico.atualizarProfissional(nome, reg, valor, horarios);
            }
            System.out.println("Profissional atualizado!");
        } catch (ProfissionalNaoEncontradoException e) {
            System.out.println("Profissional nao encontrado.");
        }
    }

    public static void listarProfissionais() {
        List<Profissional> lista = servico.listarProfissionais();
        if (lista.isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }
        for (Profissional p : lista)
            System.out.println(p.exibirResumo());
    }

    public static void filtrarProfissionais() {
        System.out.print("Especialidade: ");
        String esp = sc.nextLine().trim().toLowerCase();
        List<Profissional> lista = servico.filtrarPorEspecialidade(esp);
        if (lista.isEmpty())
            System.out.println("Nenhum profissional com essa especialidade.");
        else
            for (Profissional p : lista)
                System.out.println(p.exibirResumo());
    }

    // consultas

    public static void menuConsultas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1 - Agendar (escolher profissional)");
            System.out.println("2 - Agendar (busca por especialidade)");
            System.out.println("3 - Cancelar");
            System.out.println("4 - Remarcar");
            System.out.println("5 - Listar todas");
            System.out.println("6 - Buscar por CPF");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");
            switch (op) {
                case 1:
                    agendarComProfissional();
                    break;
                case 2:
                    agendarPorEspecialidade();
                    break;
                case 3:
                    cancelarConsulta();
                    break;
                case 4:
                    remarcarConsulta();
                    break;
                case 5:
                    listarConsultas();
                    break;
                case 6:
                    buscarConsultasPorPaciente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void agendarComProfissional() {
        System.out.print("CPF do paciente: ");
        String cpf = sc.nextLine();
        System.out.print("Nome do profissional: ");
        String nomeProf = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        boolean agendado = false;
        try {
            Profissional prof = servico.buscarProfissional(nomeProf);
            if (prof.getValorConsulta() == 0) {
                System.out.println("Profissional sem valor de consulta definido.");
                return;
            }
            String diaSemana = descobrirDiaSemana(data);
            if (!prof.atendeNoDia(diaSemana)) {
                System.out.println("Profissional nao atende nesse dia (" + diaSemana + ").");
                return;
            }

            // verifica conflito — se ocupado, oferece sugestão antes de tentar agendar
            if (servico.temConflito(nomeProf, data, horario)) {
                System.out.println("Horario ocupado!");
                String sugestao = servico.sugerirHorario(nomeProf, data);
                if (sugestao.isEmpty()) {
                    System.out.println("Nenhum horario disponivel.");
                    return;
                }
                System.out.println("Sugestao: " + sugestao);
                if (lerInteiro("Aceita? (1-Sim / 2-Nao): ") == 1)
                    horario = sugestao;
                else
                    return;
            }

            int infoTipo = lerInteiro("Informar tipo? (1-Nao / 2-Sim): ");
            if (infoTipo == 1) {
                servico.agendarConsulta(cpf, nomeProf, data, horario);
            } else {
                System.out.print("Tipo (inicial/retorno/avaliacao): ");
                servico.agendarConsulta(cpf, nomeProf, data, horario, sc.nextLine());
            }
            System.out.println("Consulta agendada com sucesso!");
            agendado = true;
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Paciente nao encontrado.");
        } catch (ProfissionalNaoEncontradoException e) {
            System.out.println("Profissional nao encontrado.");
        } catch (PacienteInativoException e) {
            System.out.println("Paciente inativo — nao e possivel agendar consulta.");
        } catch (HorarioIndisponivelException e) {
            System.out.println("Horario indisponivel: " + e.getMessage());
        } finally {

            // finally: garante que o resultado da operação seja sempre informado ao usuário
            System.out.println("--- Operacao de agendamento finalizada ---");
        }
    }

    public static void agendarPorEspecialidade() {
        System.out.print("CPF do paciente: ");
        String cpf = sc.nextLine();
        System.out.print("Especialidade: ");
        String esp = sc.nextLine().trim().toLowerCase();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        try {
            servico.buscarPaciente(cpf); // valida existência antes de buscar profissional
            String diaSemana = descobrirDiaSemana(data);
            List<Profissional> candidatos = servico.filtrarPorEspecialidade(esp);
            String nomeProfEscolhido = null;
            for (Profissional p : candidatos) {
                if (p.getValorConsulta() > 0
                        && p.atendeNoDia(diaSemana)
                        && !servico.temConflito(p.getNome(), data, horario)) {
                    nomeProfEscolhido = p.getNome();
                    break;
                }
            }
            if (nomeProfEscolhido == null) {
                System.out.println("Nenhum profissional disponivel.");
                return;
            }
            servico.agendarConsulta(cpf, nomeProfEscolhido, data, horario);
            System.out.println("Consulta agendada com " + nomeProfEscolhido + "!");
        } catch (PacienteNaoEncontradoException e) {
            System.out.println("Paciente nao encontrado.");
        } catch (ProfissionalNaoEncontradoException e) {
            System.out.println("Profissional nao encontrado.");
        } catch (PacienteInativoException e) {
            System.out.println("Paciente inativo.");
        } catch (HorarioIndisponivelException e) {
            System.out.println("Horario indisponivel.");
        }
    }

    public static void cancelarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        List<Consulta> lista = servico.listarConsultas();
        int idx = -1;
        for (int i = 0; i < lista.size(); i++) {
            Consulta c = lista.get(i);
            if (c.getCpfPaciente().equals(cpf)
                    && c.getData().equals(data)
                    && c.getHorario().equals(horario)) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            System.out.println("Consulta nao encontrada.");
            return;
        }
        if (lista.get(idx).getStatus().equals("realizada")) {
            System.out.println("Consulta ja realizada.");
            return;
        }
        if (lista.get(idx).getStatus().equals("cancelada")) {
            System.out.println("Consulta ja cancelada.");
            return;
        }

        System.out.print("Horario atual (HH:MM) para verificar multa: ");
        String horaAtual = sc.nextLine();
        try {
            int hConsulta = Integer.parseInt(horario.substring(0, 2));
            int hAgora = Integer.parseInt(horaAtual.substring(0, 2));
            if (hConsulta - hAgora < 2) {
                System.out.println("Multa de R$50.00 aplicada!");
                servico.adicionarMulta(50.0);
            }
        } catch (NumberFormatException e) {
            System.out.println("Horario invalido, multa nao calculada.");
        }

        int temMotivo = lerInteiro("Informar motivo? (1-Nao / 2-Sim): ");
        try {
            if (temMotivo == 1)
                servico.cancelarConsulta(cpf, data, horario);
            else {
                System.out.print("Motivo: ");
                sc.nextLine();
                servico.cancelarConsulta(cpf, data, horario);
            }
            System.out.println("Consulta cancelada.");
        } catch (ConsultaNaoEncontradaException e) {
            System.out.println("Erro ao cancelar: " + e.getMessage());
        }
    }

    public static void remarcarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Data original (DD/MM/AAAA): ");
        String dataOrig = sc.nextLine();
        System.out.print("Horario original (HH:MM): ");
        String horarioOrig = sc.nextLine();

        List<Consulta> lista = servico.listarConsultas();
        int idx = -1;
        for (int i = 0; i < lista.size(); i++) {
            Consulta c = lista.get(i);
            if (c.getCpfPaciente().equals(cpf)
                    && c.getData().equals(dataOrig)
                    && c.getHorario().equals(horarioOrig)
                    && c.getStatus().equals("agendada")) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            System.out.println("Consulta agendada nao encontrada.");
            return;
        }

        int tipo = lerInteiro("Apenas trocar horario no mesmo dia? (1-Sim / 2-Nao): ");
        String novaData, novoHorario;
        if (tipo == 1) {
            novaData = dataOrig;
            System.out.print("Novo horario: ");
            novoHorario = sc.nextLine();
        } else {
            System.out.print("Nova data (DD/MM/AAAA): ");
            novaData = sc.nextLine();
            System.out.print("Novo horario (HH:MM): ");
            novoHorario = sc.nextLine();
        }

        String nomeProf = lista.get(idx).getNomeProfissional();
        try {
            if (tipo == 2) {
                Profissional prof = servico.buscarProfissional(nomeProf);
                if (!prof.atendeNoDia(descobrirDiaSemana(novaData))) {
                    System.out.println("Profissional nao atende nesse dia.");
                    return;
                }
            }
            if (servico.temConflito(nomeProf, novaData, novoHorario)) {
                System.out.println("Horario ocupado. Nao foi possivel remarcar.");
                return;
            }
            servico.cancelarConsulta(cpf, dataOrig, horarioOrig);
            servico.agendarConsulta(cpf, nomeProf, novaData, novoHorario, lista.get(idx).getTipo());
            System.out.println("Consulta remarcada com sucesso!");
        } catch (ProfissionalNaoEncontradoException | ConsultaNaoEncontradaException
                | PacienteNaoEncontradoException | PacienteInativoException
                | HorarioIndisponivelException e) {
            System.out.println("Erro ao remarcar: " + e.getMessage());
        }
    }

    public static void listarConsultas() {
        List<Consulta> lista = servico.listarConsultas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma consulta registrada.");
            return;
        }
        for (int i = 0; i < lista.size(); i++)
            System.out.println("[" + i + "] " + lista.get(i).exibirResumo());
    }

    public static void buscarConsultasPorPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        boolean achou = false;
        List<Consulta> lista = servico.listarConsultas();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCpfPaciente().equals(cpf)) {
                System.out.println("[" + i + "] " + lista.get(i).exibirResumo());
                achou = true;
            }
        }
        if (!achou)
            System.out.println("Nenhuma consulta encontrada para esse CPF.");
    }

    // descobrir o dia da semana a partir de uma data
    public static String descobrirDiaSemana(String data) {
        try {
            int dia = Integer.parseInt(data.substring(0, 2));
            int mes = Integer.parseInt(data.substring(3, 5));
            int ano = Integer.parseInt(data.substring(6, 10));
            if (mes < 3) {
                mes += 12;
                ano--;
            }
            int k = ano % 100, j = ano / 100;
            int resultado = (dia + (13 * (mes + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
            if (resultado < 0)
                resultado += 7;
            String[] nomes = { "sabado", "domingo", "segunda", "terca", "quarta", "quinta", "sexta" };
            return nomes[resultado];
        } catch (Exception e) {
            return "";
        }
    }

    // atendimentos

    public static void menuAtendimentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar atendimento");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");
            if (op == 1)
                registrarAtendimento();
        }
    }

    public static void registrarAtendimento() {
        listarConsultas();
        int idxConsulta = lerInteiro("Indice da consulta: ");
        List<Consulta> lista = servico.listarConsultas();
        if (idxConsulta < 0 || idxConsulta >= lista.size()) {
            System.out.println("Indice invalido.");
            return;
        }
        if (!lista.get(idxConsulta).getStatus().equals("agendada")) {
            System.out.println("Somente consultas agendadas podem ser atendidas.");
            return;
        }

        System.out.print("Observacoes: ");
        String obs = sc.nextLine();
        int tipo = lerInteiro("Tipo (1-So obs / 2-Com diagnostico / 3-Completo): ");

        Atendimento at;
        if (tipo == 1) {
            at = new Atendimento(idxConsulta, obs);
        } else if (tipo == 2) {
            System.out.print("Diagnostico: ");
            at = new Atendimento(idxConsulta, obs, sc.nextLine());
        } else {
            System.out.print("Diagnostico: ");
            String diag = sc.nextLine();
            int forma = lerInteiro("Procedimentos (1-Um por vez / 2-Todos de uma vez): ");
            at = new Atendimento(idxConsulta, obs, diag);
            if (forma == 1) {
                String proc = "";
                while (!proc.equals("fim")) {
                    System.out.print("Procedimento (ou 'fim'): ");
                    proc = sc.nextLine();
                    if (!proc.equals("fim"))
                        at.adicionarProcedimento(proc);
                }
            } else {
                int qtd = lerInteiro("Quantos? ");
                for (int i = 0; i < qtd; i++) {
                    System.out.print("Proc " + (i + 1) + ": ");
                    at.adicionarProcedimento(sc.nextLine());
                }
            }
        }

        // chama registrarEspecifico do profissional (LIGAÇÃO DINÂMICA: o método
        // executado
        // depende do tipo REAL do profissional — Fisioterapeuta, Psicologo, etc.)
        String nomeProf = lista.get(idxConsulta).getNomeProfissional();
        try {
            Profissional prof = servico.buscarProfissional(nomeProf);
            prof.registrarEspecifico(at); // LIGAÇÃO DINÂMICA: Fisioterapeuta? Psicologo? Nutricionista? ClinicoGeral?
        } catch (ProfissionalNaoEncontradoException e) {
            // profissional pode não existir mais; atendimento prossegue sem a tag
            // específica
        }

        try {
            servico.registrarAtendimento(at, idxConsulta);
            System.out.println("\n--- RESUMO DO ATENDIMENTO ---");
            System.out.println(at.exibirResumo());
            System.out.println("Consulta marcada como realizada.");
        } catch (ConsultaNaoEncontradaException e) {
            System.out.println("Erro ao registrar atendimento: " + e.getMessage());
        }
    }

    // pagamentos

    public static void menuPagamentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Pagamento direto");
            System.out.println("2 - Pagamento automatico");
            System.out.println("3 - Demonstrar polimorfismo");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");
            switch (op) {
                case 1:
                    pagamentoDireto();
                    break;
                case 2:
                    pagamentoAutomatico();
                    break;
                case 3:
                    demonstrarPolimorfismo();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void pagamentoDireto() {
        listarConsultas();
        int idxConsulta = lerInteiro("Indice da consulta: ");
        List<Consulta> lista = servico.listarConsultas();
        if (idxConsulta < 0 || idxConsulta >= lista.size()) {
            System.out.println("Indice invalido.");
            return;
        }
        double valor = lerDouble("Valor: ");
        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPag = sc.nextLine().trim().toLowerCase();
        try {
            Pagamento pag;
            if (tipoPag.equals("cartao")) {
                int parc = lerInteiro("Parcelas (1 a 6): ");
                if (parc < 1)
                    parc = 1;
                if (parc > 6)
                    parc = 6;
                pag = Pagamento.criarCartao(idxConsulta, valor, parc);
            } else if (tipoPag.equals("convenio")) {
                System.out.print("Especialidade: ");
                String esp = sc.nextLine();
                double taxa = lerDouble("Taxa cobertura (0.0 a 1.0): ");
                pag = Pagamento.criarConvenio(idxConsulta, valor, esp, taxa);
            } else {
                pag = Pagamento.criarDinheiro(idxConsulta, valor);
            }
            servico.registrarPagamento(pag);
            System.out.println("Pagamento registrado!");
            System.out.println(pag.exibirResumo()
                    + " | Final: R$" + Math.round(pag.calcularValorFinal() * 100.0) / 100.0);
        } catch (PagamentoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {

            // garante exibição da mensagem de encerramento independente de sucesso ou falha
            System.out.println("--- Operacao de pagamento finalizada ---");
        }
    }

    public static void pagamentoAutomatico() {
        listarConsultas();
        int idxConsulta = lerInteiro("Indice da consulta: ");
        List<Consulta> lista = servico.listarConsultas();
        if (idxConsulta < 0 || idxConsulta >= lista.size()) {
            System.out.println("Indice invalido.");
            return;
        }

        Consulta c = lista.get(idxConsulta);
        double valorBase = 0;
        boolean temConvenio = false;
        try {
            Profissional prof = servico.buscarProfissional(c.getNomeProfissional());
            valorBase = prof.getValorConsulta();
            Paciente pac = servico.buscarPaciente(c.getCpfPaciente());
            temConvenio = !pac.getConvenioNome().isEmpty();
        } catch (ProfissionalNaoEncontradoException | PacienteNaoEncontradoException e) {
            System.out.println("Erro ao buscar dados: " + e.getMessage());
            return;
        }

        boolean ehRetorno = c.getTipo().equals("retorno");
        double desconto = 0;
        if (ehRetorno)
            desconto += 20;
        if (temConvenio)
            desconto += 40;

        double valorMulta = 0;
        if (lerInteiro("Tem multa pendente? (1-Nao / 2-Sim): ") == 2)
            valorMulta = lerDouble("Valor da multa: ");

        double valorFinal = valorBase * (1 - desconto / 100.0) + valorMulta;
        System.out.println("Valor base : R$" + valorBase + " | Desconto: " + desconto + "%");
        if (valorMulta > 0)
            System.out.println("Multa      : R$" + valorMulta);
        System.out.println("Valor final: R$" + Math.round(valorFinal * 100.0) / 100.0);

        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPag = sc.nextLine().trim().toLowerCase();
        try {
            Pagamento pag;
            if (tipoPag.equals("cartao")) {
                int parc = lerInteiro("Parcelas (1 a 6): ");
                if (parc < 1)
                    parc = 1;
                if (parc > 6)
                    parc = 6;
                pag = Pagamento.criarCartao(idxConsulta, valorFinal, parc);
            } else if (tipoPag.equals("convenio") && temConvenio) {
                String espProf = "";
                try {
                    espProf = servico.buscarProfissional(c.getNomeProfissional()).getEspecialidade();
                } catch (ProfissionalNaoEncontradoException e) {
                    espProf = "geral";
                }
                pag = Pagamento.criarConvenio(idxConsulta, valorFinal, espProf, 0.4);
            } else {
                pag = Pagamento.criarDinheiro(idxConsulta, valorFinal);
            }
            servico.registrarPagamento(pag);
            System.out.println("Pagamento registrado com sucesso!");
        } catch (PagamentoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // cria exemplos fixos pra mostrar como o polimorfismo funciona nos pagamentos
    public static void demonstrarPolimorfismo() {
        System.out.println("\n=== POLIMORFISMO - TIPOS DE PAGAMENTO ===");

        // LIGAÇÃO DINÂMICA: três objetos tratados como Pagamento — calcularValorFinal()
        // e
        // exibirResumo() resolvem para a subclasse correta em tempo de execução
        Pagamento[] exemplos = {
                Pagamento.criarDinheiro(101, 150.0),
                Pagamento.criarCartao(102, 200.0, 4),
                Pagamento.criarConvenio(103, 300.0, "Pediatria", 0.4)
        };
        for (Pagamento p : exemplos)
            System.out.println(p.exibirResumo()
                    + " | Final: R$" + Math.round(p.calcularValorFinal() * 100.0) / 100.0);

        List<Pagamento> pagamentos = servico.listarPagamentos();
        if (!pagamentos.isEmpty()) {
            System.out.println("\n--- Pagamentos do sistema ---");
            for (int i = 0; i < pagamentos.size(); i++)
                System.out.println("[" + i + "] " + pagamentos.get(i).exibirResumo()
                        + " | Final: R$" + Math.round(pagamentos.get(i).calcularValorFinal() * 100.0) / 100.0);
        }
    }

    // relatorios

    public static void menuRelatorios() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Geral");
            System.out.println("2 - Por profissional");
            System.out.println("3 - Por periodo");
            System.out.println("4 - Resumo financeiro");
            System.out.println("5 - Relatorio unificado (Pacientes + Profissionais)");
            System.out.println("0 - Voltar");
            op = lerInteiro("Opcao: ");
            switch (op) {
                case 1:
                    Relatorio.gerarRelatorio(
                            servico.listarConsultas(), servico.listarAtendimentos());
                    break;
                case 2:
                    System.out.print("Nome do profissional: ");
                    Relatorio.gerarRelatorio(
                            servico.listarConsultas(), servico.listarAtendimentos(), sc.nextLine());
                    break;
                case 3:
                    System.out.print("Data inicio (DD/MM/AAAA): ");
                    String ini = sc.nextLine();
                    System.out.print("Data fim (DD/MM/AAAA): ");
                    Relatorio.gerarRelatorio(
                            servico.listarConsultas(), servico.listarAtendimentos(), ini, sc.nextLine());
                    break;
                case 4:
                    Relatorio.gerarResumoFinanceiro(
                            servico.listarConsultas(), servico.listarPagamentos(), servico.listarMultas());
                    break;
                case 5:
                    relatorioUnificado();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    // Etapa 14 — Relatório Unificado usando List<Pessoa> com instanceof e ligação
    // dinâmica
    public static void relatorioUnificado() {
        List<Pessoa> pessoas = servico.listarPessoas();
        System.out.println("\n=== RELATORIO UNIFICADO DE PESSOAS ===");
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }
        for (Pessoa p : pessoas) {
            // instanceof: identifica o tipo real do objeto para exibir a categoria correta
            if (p instanceof Paciente) {
                System.out.print("[PACIENTE]      ");
            } else if (p instanceof Profissional) {
                System.out.print("[PROFISSIONAL]  ");
            }
            // LIGAÇÃO DINÂMICA: o método exibirResumo() executado depende do tipo REAL do
            // objeto
            // em tempo de execução — Paciente.exibirResumo(), ClinicoGeral.exibirResumo(),
            // etc.
            System.out.println(p.exibirResumo());
        }
        System.out.println("Total: " + pessoas.size() + " pessoa(s) cadastrada(s).");
    }
}
