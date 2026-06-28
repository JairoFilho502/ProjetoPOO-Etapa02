package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Profissional extends Pessoa {
    private String                 especialidade;
    private String                 registroProfissional;
    private double                 valorConsulta;
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

    public void atualizar(String registro, double valor) {
        if (validarRegistro(registro)) {
            this.registroProfissional = registro;
            this.valorConsulta        = valor;
        }
    }

    public void atualizar(String registro, double valor, List<HorarioDisponivel> novosHorarios) {
        if (validarRegistro(registro)) {
            this.registroProfissional = registro;
            this.valorConsulta        = valor;
            if (novosHorarios != null)
                this.horarios = new ArrayList<>(novosHorarios);
        }
    }

    public void adicionarHorario(HorarioDisponivel horario) {
        if (horario != null && !horarios.contains(horario)) horarios.add(horario);
    }

    public boolean removerHorario(HorarioDisponivel horario) {
        return horarios.remove(horario);
    }

    public boolean atendeNoDia(String dia) {
        for (HorarioDisponivel h : horarios)
            if (h.getDiaSemana() != null && h.getDiaSemana().equalsIgnoreCase(dia)) return true;
        return false;
    }

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

    public static boolean especialidadeValida(String esp) {
        if (esp == null) return false;
        String e = esp.trim().toLowerCase();
        return e.equals("clinica geral") || e.equals("fisioterapia")
                || e.equals("psicologia") || e.equals("nutricao");
    }

    // Metodo fabrica — permite que Main crie subclasses sem acessar diretamente
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

// -------------------------------------------------------

class Fisioterapeuta extends Profissional {
    private int totalSessoesPrevistas;

    public Fisioterapeuta(String nome, String registro, double valor, int sessoes) {
        super(nome, "fisioterapia", registro, valor);
        this.totalSessoesPrevistas = sessoes;
    }

    public int getTotalSessoesPrevistas() { return totalSessoesPrevistas; }
    public void setTotalSessoesPrevistas(int s) { if (s >= 0) this.totalSessoesPrevistas = s; }

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

// -------------------------------------------------------

class Psicologo extends Profissional {
    private String abordagem;

    public Psicologo(String nome, String registro, double valor, String abordagem) {
        super(nome, "psicologia", registro, valor);
        this.abordagem = abordagem != null ? abordagem : "";
    }

    public String getAbordagem()           { return abordagem; }
    public void   setAbordagem(String a)   { this.abordagem = a != null ? a : ""; }

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

class Nutricionista extends Profissional {
    private String focoPlanoNutricional;

    public Nutricionista(String nome, String registro, double valor, String foco) {
        super(nome, "nutricao", registro, valor);
        this.focoPlanoNutricional = foco != null ? foco : "";
    }

    public String getFocoPlanoNutricional()       { return focoPlanoNutricional; }
    public void   setFocoPlanoNutricional(String f) { this.focoPlanoNutricional = f != null ? f : ""; }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String info = "[Nutricao - Foco: " + focoPlanoNutricional + "]";
        String obs  = atendimento.getObservacoes();
        atendimento.setObservacoes(obs.isEmpty() ? info : obs + " " + info);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Foco Nutricional: " + focoPlanoNutricional;
    }
}

// -------------------------------------------------------

class ClinicoGeral extends Profissional {

    public ClinicoGeral(String nome, String registro, double valor) {
        super(nome, "clinica geral", registro, valor);
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String obs = atendimento.getObservacoes();
        atendimento.setObservacoes(obs.isEmpty() ? "[Clinica Geral]" : obs + " [Clinica Geral]");
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Tipo: Clinico Geral";
    }
}
