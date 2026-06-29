package model;

import java.util.ArrayList;
import java.util.List;





// subclasse de pessoa
public abstract class Profissional extends Pessoa {
    private String                 especialidade;
    private String                 registroProfissional;
    private double                 valorConsulta;
    

// AGREGAÇÃO: horarios existem independentemente do profissional 
    private List<HorarioDisponivel> horarios;

    public Profissional(String nome, String especialidade,
                        String registroProfissional, double valorConsulta) {
        super(nome);
        this.especialidade        = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta        = valorConsulta;
        this.horarios             = new ArrayList<>();
    }

    public String                  getEspecialidade()        { return especialidade; }
    public String                  getRegistroProfissional() { return registroProfissional; }
    public double                  getValorConsulta()        { return valorConsulta; }
    public List<HorarioDisponivel> getHorarios()             { return horarios; }

    
// SOBRECARGA: atualiza só registro e valor, sem mexer nos horarios
    public void atualizar(String registro, double valor) {
        if (validarRegistro(registro)) {
            this.registroProfissional = registro;
            this.valorConsulta        = valor;
        }
    }

    // SOBRECARGA: atualiza tudo incluindo a lista de horários disponíveis

    public void atualizar(String registro, double valor, List<HorarioDisponivel> novosHorarios) {
        if (validarRegistro(registro)) {
            this.registroProfissional = registro;
            this.valorConsulta        = valor;
            if (novosHorarios != null)
                this.horarios = new ArrayList<>(novosHorarios);
        }
    }

    // não adiciona duplicata na lista de horários

    public void adicionarHorario(HorarioDisponivel horario) {
        if (horario != null && !horarios.contains(horario)) horarios.add(horario);
    }

    public boolean removerHorario(HorarioDisponivel horario) {
        return horarios.remove(horario);
    }

    // verifica se o profissional tem algum horário cadastrado naquele dia
    public boolean atendeNoDia(String dia) {
        for (HorarioDisponivel h : horarios)
            if (h.getDiaSemana() != null && h.getDiaSemana().equalsIgnoreCase(dia)) return true;
        return false;
    }

    // retorna todos os horários menos o que está em conflito
    public List<HorarioDisponivel> buscarHorariosAlternativos(HorarioDisponivel conflitante) {
        List<HorarioDisponivel> alt = new ArrayList<>();
        for (HorarioDisponivel h : horarios)
            if (!h.equals(conflitante)) alt.add(h);
        return alt;
    }

    public void listarHorarios() {
        if (horarios.isEmpty()) { System.out.println("Nenhum horario disponivel."); return; }
        for (HorarioDisponivel h : horarios) System.out.println("- " + h);
    }

    // as quatro especialidades aceitas pelo sistema
    public static boolean especialidadeValida(String esp) {
        if (esp == null) return false;
        String e = esp.trim().toLowerCase();
        return e.equals("clinica geral") || e.equals("fisioterapia")
                || e.equals("psicologia") || e.equals("nutricao");
    }

    public static Profissional criar(String nome, String especialidade,
                                     String registro, double valor) {
        switch (especialidade.trim().toLowerCase()) {
            case "fisioterapia": return new Fisioterapeuta(nome, registro, valor, 0);
            case "psicologia":   return new Psicologo(nome, registro, valor, "");
            case "nutricao":     return new Nutricionista(nome, registro, valor, "");
            default:             return new ClinicoGeral(nome, registro, valor);
        }
    }

    protected boolean validarRegistro(String registro) {
        if (registro == null || registro.isEmpty()) {
            System.out.println("Erro: Registro profissional invalido.");
            return false;
        }
        return true;
    }

    // sobrescrita: cada especialidade implementa do seu jeito
    public abstract void registrarEspecifico(Atendimento atendimento);

    @Override
    public String exibirResumo() {
        return "Nome: " + getNome()
                + " | Especialidade: " + especialidade
                + " | Registro: " + registroProfissional
                + " | Valor: R$" + valorConsulta
                + " | Horarios: " + horarios;
    }
}

// ---- subclasses de Profissional ----

class Fisioterapeuta extends Profissional {
    private int totalSessoesPrevistas;

    public Fisioterapeuta(String nome, String registro, double valor, int sessoes) {
        super(nome, "fisioterapia", registro, valor);
        this.totalSessoesPrevistas = sessoes;
    }

    public int getTotalSessoesPrevistas() { return totalSessoesPrevistas; }
    public void setTotalSessoesPrevistas(int s) { if (s >= 0) this.totalSessoesPrevistas = s; }

    // SOBRESCRITA: mesmo nome e parâmetros, Fisioterapeuta redefine comportamento (resolvido em tempo de execução)
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String obs = atendimento.getObservacoes();
        atendimento.setObservacoes(obs.isEmpty() ? "[Fisioterapia]" : obs + " [Fisioterapia]");
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Sessoes Previstas: " + totalSessoesPrevistas;
    }
}

class Psicologo extends Profissional {
    private String abordagem;

    public Psicologo(String nome, String registro, double valor, String abordagem) {
        super(nome, "psicologia", registro, valor);
        this.abordagem = abordagem != null ? abordagem : "";
    }

    public String getAbordagem()         { return abordagem; }
    public void   setAbordagem(String a) { this.abordagem = a != null ? a : ""; }

    // SOBRESCRITA: marca nas observações qual foi a abordagem usada na sessão
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String info = "[Psicologia - " + abordagem + "]";
        String obs  = atendimento.getObservacoes();
        atendimento.setObservacoes(obs.isEmpty() ? info : obs + " " + info);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Abordagem: " + abordagem;
    }
}

// -------------------------------------------------------






// nutricionista 





class Nutricionista extends Profissional {
    private String planoAlimentar; 
    public Nutricionista(String nome, String registro, double valor, String plano) {
        super(nome, "nutricao", registro, valor);
        this.planoAlimentar = plano != null ? plano : "";
    }

    public String getPlanoAlimentar()        { return planoAlimentar; }
    public void   setPlanoAlimentar(String p) { this.planoAlimentar = p != null ? p : ""; }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String info = "[Nutricao - Plano: " + planoAlimentar + "]";
        String obs  = atendimento.getObservacoes();
        atendimento.setObservacoes(obs.isEmpty() ? info : obs + " " + info);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Plano Alimentar: " + planoAlimentar;
    }
}

class ClinicoGeral extends Profissional {
    private String encaminhamento; // encaminhamento para especialista quando necessário

    public ClinicoGeral(String nome, String registro, double valor) {
        super(nome, "clinica geral", registro, valor);
        this.encaminhamento = "";
    }

    public String getEncaminhamento()          { return encaminhamento; }
    public void   setEncaminhamento(String enc) { this.encaminhamento = enc != null ? enc : ""; }

    // SOBRESCRITA: adiciona tag [Clinica Geral] e encaminhamento (se houver) nas observações
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String info = "[Clinica Geral" + (encaminhamento.isEmpty() ? "" : " - Enc: " + encaminhamento) + "]";
        String obs  = atendimento.getObservacoes();
        atendimento.setObservacoes(obs.isEmpty() ? info : obs + " " + info);
    }

    @Override
    public String exibirResumo() {
        String base = super.exibirResumo() + " | Tipo: Clinico Geral";
        return encaminhamento.isEmpty() ? base : base + " | Encaminhamento: " + encaminhamento;
    }
}
