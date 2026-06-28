import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Paciente[] pacientes = new Paciente[100];
    static int totalPacientes = 0;

    static Profissional[] profissionais = new Profissional[50];
    static int totalProfissionais = 0;

    static Consulta[] consultas = new Consulta[200];
    static int totalConsultas = 0;

    static Atendimento[] atendimentos = new Atendimento[200];
    static int totalAtendimentos = 0;

    static Pagamento[] pagamentos = new Pagamento[200];
    static int totalPagamentos = 0;

    static double[] multas = new double[100];
    static int totalMultas = 0;

    static Scanner sc = new Scanner(System.in);

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
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1: menuPacientes(); break;
                case 2: menuProfissionais(); break;
                case 3: menuConsultas(); break;
                case 4: menuAtendimentos(); break;
                case 5: menuPagamentos(); break;
                case 6: menuRelatorios(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
        System.out.println("Sistema encerrado.");
    }

    // ---- PACIENTES ----

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
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: cadastrarPaciente(); break;
                case 2: complementarPaciente(); break;
                case 3: buscarPaciente(); break;
                case 4: listarPacientes(); break;
                case 5: desativarPaciente(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        if (buscarIndicePaciente(cpf) != -1) {
            System.out.println("CPF ja cadastrado!");
            return;
        }

        System.out.print("Tipo (1-Minimo / 2-Com idade e tel / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        if (tipo == 1) {
            pacientes[totalPacientes] = new Paciente(nome, cpf);
        } else if (tipo == 2) {
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            pacientes[totalPacientes] = new Paciente(nome, cpf, idade, tel);
        } else {
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());
            System.out.print("Telefone: ");
            String tel = sc.nextLine();
            System.out.print("Convenio: ");
            String conv = sc.nextLine();
            pacientes[totalPacientes] = new Paciente(nome, cpf, idade, tel, conv);
        }
        totalPacientes++;
        System.out.println("Paciente cadastrado com sucesso!");
    }

    public static void complementarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        int idx = buscarIndicePaciente(cpf);
        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }

        System.out.print("Vai informar convenio? (1-Nao / 2-Sim): ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("Idade: ");
        int idade = Integer.parseInt(sc.nextLine());
        System.out.print("Telefone: ");
        String tel = sc.nextLine();

        if (tipo == 1) {
            pacientes[idx].complementar(idade, tel);
        } else {
            System.out.print("Convenio: ");
            String conv = sc.nextLine();
            pacientes[idx].complementar(idade, tel, conv);
        }
        System.out.println("Cadastro atualizado!");
    }

    public static void buscarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        int idx = buscarIndicePaciente(cpf);
        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
        } else {
            System.out.println(pacientes[idx].exibirResumo());
        }
    }

    public static void listarPacientes() {
        if (totalPacientes == 0) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (int i = 0; i < totalPacientes; i++) {
            System.out.println(pacientes[i].exibirResumo());
        }
    }

    public static void desativarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        int idx = buscarIndicePaciente(cpf);
        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
        } else {
            pacientes[idx].desativar();
            System.out.println("Paciente desativado.");
        }
    }

    public static int buscarIndicePaciente(String cpf) {
        for (int i = 0; i < totalPacientes; i++) {
            if (pacientes[i].getCpf().equals(cpf)) return i;
        }
        return -1;
    }

    // ---- PROFISSIONAIS ----

    public static void menuProfissionais() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar cadastro");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: cadastrarProfissional(); break;
                case 2: atualizarProfissional(); break;
                case 3: listarProfissionais(); break;
                case 4: filtrarProfissionais(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void cadastrarProfissional() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Especialidade (clinica geral/fisioterapia/psicologia/nutricao): ");
        String esp = sc.nextLine();

        if (!Profissional.especialidadeValida(esp)) {
            System.out.println("Especialidade invalida!");
            return;
        }

        System.out.print("Tipo (1-Minimo / 2-Com registro e valor / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        String reg = "";
        double valor = 0;

        if (tipo >= 2) {
            System.out.print("Registro: ");
            reg = sc.nextLine();
            System.out.print("Valor consulta: ");
            valor = Double.parseDouble(sc.nextLine());
        }

        Profissional novo;
        switch (esp) {
            case "fisioterapia": novo = new Fisioterapeuta(nome, reg, valor, 0); break;
            case "psicologia":   novo = new Psicologo(nome, reg, valor, "");    break;
            case "nutricao":     novo = new Nutricionista(nome, reg, valor, ""); break;
            default:             novo = new ClinicoGeral(nome, reg, valor);      break;
        }

        if (tipo == 3) {
            System.out.print("Quantos dias atende? ");
            int qtd = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < qtd; i++) {
                System.out.print("Dia " + (i + 1) + " (ex: segunda): ");
                String dia = sc.nextLine();
                System.out.print("Turno (manha/tarde): ");
                String turno = sc.nextLine();
                novo.adicionarHorario(new HorarioDisponivel(dia, turno));
            }
        }

        profissionais[totalProfissionais] = novo;
        totalProfissionais++;
        System.out.println("Profissional cadastrado!");
    }

    public static void atualizarProfissional() {
        System.out.print("Nome do profissional: ");
        String nome = sc.nextLine();
        int idx = buscarIndiceProfissional(nome);
        if (idx == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        System.out.print("Vai informar dias? (1-Nao / 2-Sim): ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("Registro: ");
        String reg = sc.nextLine();
        System.out.print("Valor consulta: ");
        double valor = Double.parseDouble(sc.nextLine());

        if (tipo == 1) {
            profissionais[idx].atualizar(reg, valor);
        } else {
            System.out.print("Quantos dias? ");
            int qtd = Integer.parseInt(sc.nextLine());
            List<HorarioDisponivel> horarios = new ArrayList<>();
            for (int i = 0; i < qtd; i++) {
                System.out.print("Dia " + (i + 1) + " (ex: segunda): ");
                String dia = sc.nextLine();
                System.out.print("Turno (manha/tarde): ");
                String turno = sc.nextLine();
                horarios.add(new HorarioDisponivel(dia, turno));
            }
            profissionais[idx].atualizar(reg, valor, horarios);
        }
        System.out.println("Profissional atualizado!");
    }

    public static void listarProfissionais() {
        if (totalProfissionais == 0) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }
        for (int i = 0; i < totalProfissionais; i++) {
            System.out.println(profissionais[i].exibirResumo());
        }
    }

    public static void filtrarProfissionais() {
        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < totalProfissionais; i++) {
            if (profissionais[i].getEspecialidade().equals(esp)) {
                System.out.println(profissionais[i].exibirResumo());
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhum profissional com essa especialidade.");
    }

    public static int buscarIndiceProfissional(String nome) {
        for (int i = 0; i < totalProfissionais; i++) {
            if (profissionais[i].getNome().equals(nome)) return i;
        }
        return -1;
    }

    // ---- CONSULTAS ----

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
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: agendarComProfissional(); break;
                case 2: agendarPorEspecialidade(); break;
                case 3: cancelarConsulta(); break;
                case 4: remarcarConsulta(); break;
                case 5: listarConsultas(); break;
                case 6: buscarConsultasPorPaciente(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void agendarComProfissional() {
        System.out.print("CPF do paciente: ");
        String cpf = sc.nextLine();
        int idxPac = buscarIndicePaciente(cpf);
        if (idxPac == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }
        if (!pacientes[idxPac].estaAtivo()) {
            System.out.println("Paciente inativo. Nao e possivel agendar.");
            return;
        }

        System.out.print("Nome do profissional: ");
        String nomeProf = sc.nextLine();
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }
        if (profissionais[idxProf].getValorConsulta() == 0) {
            System.out.println("Profissional sem valor definido. Nao pode agendar.");
            return;
        }

        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        String diaSemana = descobrirDiaSemana(data);
        if (!profissionais[idxProf].atendeNoDia(diaSemana)) {
            System.out.println("Profissional nao atende nesse dia.");
            return;
        }

        if (temConflito(nomeProf, data, horario)) {
            System.out.println("Horario ocupado!");
            String sugestao = sugerirHorario(nomeProf, data);
            if (sugestao.isEmpty()) {
                System.out.println("Nenhum horario disponivel nesse dia.");
                return;
            }
            System.out.println("Sugestao: " + sugestao);
            System.out.print("Aceita? (1-Sim / 2-Nao): ");
            int aceita = Integer.parseInt(sc.nextLine());
            if (aceita == 1) {
                horario = sugestao;
            } else {
                return;
            }
        }

        System.out.print("Informar tipo? (1-Nao / 2-Sim): ");
        int infoTipo = Integer.parseInt(sc.nextLine());

        if (infoTipo == 1) {
            consultas[totalConsultas] = new Consulta(cpf, nomeProf, data, horario);
        } else {
            System.out.print("Tipo (inicial/retorno/avaliacao): ");
            String tipo = sc.nextLine();
            consultas[totalConsultas] = new Consulta(cpf, nomeProf, data, horario, tipo);
        }
        totalConsultas++;
        System.out.println("Consulta agendada com sucesso!");
    }

    public static void agendarPorEspecialidade() {
        System.out.print("CPF do paciente: ");
        String cpf = sc.nextLine();
        int idxPac = buscarIndicePaciente(cpf);
        if (idxPac == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }
        if (!pacientes[idxPac].estaAtivo()) {
            System.out.println("Paciente inativo. Nao e possivel agendar.");
            return;
        }

        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        String diaSemana = descobrirDiaSemana(data);

        int idxProf = -1;
        for (int i = 0; i < totalProfissionais; i++) {
            if (profissionais[i].getEspecialidade().equals(esp)
                    && profissionais[i].getValorConsulta() > 0
                    && profissionais[i].atendeNoDia(diaSemana)
                    && !temConflito(profissionais[i].getNome(), data, horario)) {
                idxProf = i;
                break;
            }
        }

        if (idxProf == -1) {
            System.out.println("Nenhum profissional disponivel.");
            return;
        }

        consultas[totalConsultas] = new Consulta(cpf, profissionais[idxProf].getNome(), data, horario);
        totalConsultas++;
        System.out.println("Consulta agendada com " + profissionais[idxProf].getNome() + "!");
    }

    public static void cancelarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        int idx = -1;
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].getCpfPaciente().equals(cpf)
                    && consultas[i].getData().equals(data)
                    && consultas[i].getHorario().equals(horario)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("Consulta nao encontrada.");
            return;
        }
        if (consultas[idx].getStatus().equals("realizada")) {
            System.out.println("Consulta ja realizada. Nao pode cancelar.");
            return;
        }
        if (consultas[idx].getStatus().equals("cancelada")) {
            System.out.println("Consulta ja cancelada.");
            return;
        }

        System.out.print("Horario atual (HH:MM): ");
        String horaAtual = sc.nextLine();

        int hConsulta = Integer.parseInt(horario.substring(0, 2));
        int hAgora = Integer.parseInt(horaAtual.substring(0, 2));
        int diff = hConsulta - hAgora;

        if (diff < 2) {
            System.out.println("Multa de R$50.00 aplicada!");
            multas[totalMultas] = 50.0;
            totalMultas++;
        }

        System.out.print("Informar motivo? (1-Nao / 2-Sim): ");
        int temMotivo = Integer.parseInt(sc.nextLine());

        if (temMotivo == 1) {
            consultas[idx].cancelar();
        } else {
            System.out.print("Motivo: ");
            String motivo = sc.nextLine();
            String msg = consultas[idx].cancelar(motivo);
            System.out.println(msg);
        }
        System.out.println("Consulta cancelada.");
    }

    public static void remarcarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Data original (DD/MM/AAAA): ");
        String dataOrig = sc.nextLine();
        System.out.print("Horario original (HH:MM): ");
        String horarioOrig = sc.nextLine();

        int idx = -1;
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].getCpfPaciente().equals(cpf)
                    && consultas[i].getData().equals(dataOrig)
                    && consultas[i].getHorario().equals(horarioOrig)
                    && consultas[i].getStatus().equals("agendada")) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("Consulta agendada nao encontrada.");
            return;
        }

        System.out.print("Apenas trocar horario no mesmo dia? (1-Sim / 2-Nao): ");
        int tipo = Integer.parseInt(sc.nextLine());

        String novaData;
        String novoHorario;

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

        String nomeProf = consultas[idx].getNomeProfissional();
        int idxProf = buscarIndiceProfissional(nomeProf);

        if (tipo == 2 && idxProf != -1) {
            String dia = descobrirDiaSemana(novaData);
            if (!profissionais[idxProf].atendeNoDia(dia)) {
                System.out.println("Profissional nao atende nesse dia.");
                return;
            }
        }

        if (temConflito(nomeProf, novaData, novoHorario)) {
            System.out.println("Horario ocupado. Nao foi possivel remarcar.");
            return;
        }

        consultas[idx].remarcar();
        consultas[totalConsultas] = new Consulta(cpf, nomeProf, novaData, novoHorario, consultas[idx].getTipo());
        totalConsultas++;
        System.out.println("Consulta remarcada com sucesso!");
    }

    public static void listarConsultas() {
        if (totalConsultas == 0) {
            System.out.println("Nenhuma consulta.");
            return;
        }
        for (int i = 0; i < totalConsultas; i++) {
            System.out.println("[" + i + "] " + consultas[i].exibirResumo());
        }
    }

    public static void buscarConsultasPorPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].getCpfPaciente().equals(cpf)) {
                System.out.println("[" + i + "] " + consultas[i].exibirResumo());
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta encontrada.");
    }

    public static boolean temConflito(String nomeProf, String data, String horario) {
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].getNomeProfissional().equals(nomeProf)
                    && consultas[i].getData().equals(data)
                    && consultas[i].getHorario().equals(horario)
                    && consultas[i].getStatus().equals("agendada")) {
                return true;
            }
        }
        return false;
    }

    public static String sugerirHorario(String nomeProf, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste = (h < 10 ? "0" + h : "" + h) + ":00";
            if (!temConflito(nomeProf, data, teste)) return teste;
        }
        return "";
    }

    public static String descobrirDiaSemana(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));

        if (mes < 3) { mes += 12; ano--; }

        int k = ano % 100;
        int j = ano / 100;
        int resultado = (dia + (13 * (mes + 1)) / 5 + k + k/4 + j/4 - 2*j) % 7;
        if (resultado < 0) resultado += 7;

        String[] nomes = {"sabado", "domingo", "segunda", "terca", "quarta", "quinta", "sexta"};
        return nomes[resultado];
    }

    // ---- ATENDIMENTOS ----

    public static void menuAtendimentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            if (op == 1) registrarAtendimento();
        }
    }

    public static void registrarAtendimento() {
        System.out.print("Indice da consulta: ");
        int idxConsulta = Integer.parseInt(sc.nextLine());

        if (idxConsulta < 0 || idxConsulta >= totalConsultas) {
            System.out.println("Indice invalido.");
            return;
        }
        if (!consultas[idxConsulta].getStatus().equals("agendada")) {
            System.out.println("So pode registrar atendimento em consulta agendada.");
            return;
        }

        System.out.print("Observacoes: ");
        String obs = sc.nextLine();

        System.out.print("Tipo de registro (1-So obs / 2-Com diagnostico / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        if (tipo == 1) {
            atendimentos[totalAtendimentos] = new Atendimento(idxConsulta, obs);

        } else if (tipo == 2) {
            System.out.print("Diagnostico: ");
            String diag = sc.nextLine();
            atendimentos[totalAtendimentos] = new Atendimento(idxConsulta, obs, diag);

        } else {
            System.out.print("Diagnostico: ");
            String diag = sc.nextLine();

            System.out.print("Como informar procedimentos? (1-Um por vez / 2-Todos de uma vez): ");
            int forma = Integer.parseInt(sc.nextLine());

            String[] procs = new String[10];
            int qtdProcs = 0;

            if (forma == 1) {
                String proc = "";
                while (!proc.equals("fim") && qtdProcs < 10) {
                    System.out.print("Procedimento (ou 'fim'): ");
                    proc = sc.nextLine();
                    if (!proc.equals("fim")) {
                        procs[qtdProcs] = proc;
                        qtdProcs++;
                    }
                }
            } else {
                System.out.print("Quantos? ");
                qtdProcs = Integer.parseInt(sc.nextLine());
                if (qtdProcs > 10) qtdProcs = 10;
                for (int i = 0; i < qtdProcs; i++) {
                    System.out.print("Proc " + (i + 1) + ": ");
                    procs[i] = sc.nextLine();
                }
            }
            atendimentos[totalAtendimentos] = new Atendimento(idxConsulta, obs, diag, procs, qtdProcs);
        }

        consultas[idxConsulta].realizar();
        totalAtendimentos++;
        System.out.println("\n--- RESUMO ---");
        System.out.println(atendimentos[totalAtendimentos - 1].exibirResumo());
        System.out.println("Consulta marcada como realizada.");
    }

    // ---- PAGAMENTOS ----

    public static void menuPagamentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n PAGAMENTOS ");
            System.out.println("1 - Pagamento direto");
            System.out.println("2 - Pagamento automatico");
            System.out.println("3 - Listar pagamentos (Teste Polimorfismo)");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: pagamentoDireto(); break;
                case 2: pagamentoAutomatico(); break;
                case 3:
                    System.out.println("\n LISTAGEM DE PAGAMENTOS - POLIMORFISMO ");
                    Pagamento[] pagamentosDoDia = new Pagamento[3];
                    pagamentosDoDia[0] = new PagamentoDinheiro(101, 150.0);
                    pagamentosDoDia[1] = new PagamentoCartao(102, 200.0, 4);
                    pagamentosDoDia[2] = new PagamentoConvenio(103, 300.0, "Pediatria", 0.4);
                    for (Pagamento p : pagamentosDoDia) {
                        System.out.println(p.exibirResumo()
                                + " | Valor Final: R$" + Math.round(p.calcularValorFinal() * 100.0) / 100.0);
                    }
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void pagamentoDireto() {
        System.out.print("Indice da consulta: ");
        int idxConsulta = Integer.parseInt(sc.nextLine());

        if (idxConsulta < 0 || idxConsulta >= totalConsultas) {
            System.out.println("Indice invalido.");
            return;
        }

        System.out.print("Valor: ");
        double valor = Double.parseDouble(sc.nextLine());
        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPag = sc.nextLine();

        if (tipoPag.equals("cartao")) {
            System.out.print("Parcelas (1 a 6): ");
            int parc = Integer.parseInt(sc.nextLine());
            if (parc < 1) parc = 1;
            if (parc > 6) parc = 6;
            pagamentos[totalPagamentos] = new PagamentoCartao(idxConsulta, valor, parc);
        } else if (tipoPag.equals("convenio")) {
            System.out.print("Especialidade convenio: ");
            String esp = sc.nextLine();
            System.out.print("Taxa cobertura (0.0 a 1.0): ");
            double taxa = Double.parseDouble(sc.nextLine());
            pagamentos[totalPagamentos] = new PagamentoConvenio(idxConsulta, valor, esp, taxa);
        } else {
            pagamentos[totalPagamentos] = new PagamentoDinheiro(idxConsulta, valor);
        }
        totalPagamentos++;
        System.out.println("Pagamento registrado!");
    }

    public static void pagamentoAutomatico() {
        System.out.print("Indice da consulta: ");
        int idxConsulta = Integer.parseInt(sc.nextLine());

        if (idxConsulta < 0 || idxConsulta >= totalConsultas) {
            System.out.println("Indice invalido.");
            return;
        }

        String nomeProf = consultas[idxConsulta].getNomeProfissional();
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            System.out.println("Profissional da consulta nao encontrado.");
            return;
        }
        double valorBase = profissionais[idxProf].getValorConsulta();

        String cpfPac = consultas[idxConsulta].getCpfPaciente();
        int idxPac = buscarIndicePaciente(cpfPac);

        boolean temConvenio = idxPac != -1 && !pacientes[idxPac].getConvenioNome().isEmpty();
        boolean ehRetorno = consultas[idxConsulta].getTipo().equals("retorno");

        double desconto = 0;
        if (ehRetorno) desconto += 20;
        if (temConvenio) desconto += 40;

        System.out.print("Tem multa pendente? (1-Nao / 2-Sim): ");
        int temMulta = Integer.parseInt(sc.nextLine());
        double valorMulta = 0;

        double valorFinal;
        if (temMulta == 1 && desconto == 0) {
            valorFinal = valorBase;
        } else if (temMulta == 1) {
            valorFinal = valorBase * (1 - desconto / 100.0);
        } else {
            System.out.print("Valor da multa: ");
            valorMulta = Double.parseDouble(sc.nextLine());
            valorFinal = valorBase * (1 - desconto / 100.0) + valorMulta;
        }

        System.out.println("Valor base: R$" + valorBase);
        System.out.println("Desconto: " + desconto + "%");
        if (valorMulta > 0) System.out.println("Multa: R$" + valorMulta);
        System.out.println("Valor final: R$" + (Math.round(valorFinal * 100.0) / 100.0));

        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPag = sc.nextLine();

        if (tipoPag.equals("cartao")) {
            System.out.print("Parcelas (1 a 6): ");
            int parc = Integer.parseInt(sc.nextLine());
            if (parc < 1) parc = 1;
            if (parc > 6) parc = 6;
            pagamentos[totalPagamentos] = new PagamentoCartao(idxConsulta, valorFinal, parc);
        } else if (tipoPag.equals("convenio") && temConvenio) {
            String espConvenio = profissionais[idxProf].getEspecialidade();
            pagamentos[totalPagamentos] = new PagamentoConvenio(idxConsulta, valorFinal, espConvenio, 0.4);
        } else {
            pagamentos[totalPagamentos] = new PagamentoDinheiro(idxConsulta, valorFinal);
        }
        totalPagamentos++;
        System.out.println("Pagamento registrado!");
    }

    // ---- RELATORIOS ----

    public static void menuRelatorios() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Geral");
            System.out.println("2 - Por profissional");
            System.out.println("3 - Por periodo");
            System.out.println("4 - Resumo financeiro");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    Relatorio.gerarRelatorio(consultas, totalConsultas, atendimentos, totalAtendimentos);
                    break;
                case 2:
                    System.out.print("Nome do profissional: ");
                    String nome = sc.nextLine();
                    Relatorio.gerarRelatorio(consultas, totalConsultas, atendimentos, totalAtendimentos, nome);
                    break;
                case 3:
                    System.out.print("Data inicio (DD/MM/AAAA): ");
                    String ini = sc.nextLine();
                    System.out.print("Data fim (DD/MM/AAAA): ");
                    String fim = sc.nextLine();
                    Relatorio.gerarRelatorio(consultas, totalConsultas, atendimentos, totalAtendimentos, ini, fim);
                    break;
                case 4:
                    Relatorio.gerarResumoFinanceiro(consultas, totalConsultas, pagamentos, totalPagamentos, multas, totalMultas);
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }
}
