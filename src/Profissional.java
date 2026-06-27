import java.util.ArrayList;
import java.util.List;

// R6: classe abstrata - não instanciar diretamente
public abstract class Profissional extends Pessoa {

    // R1: atributos privados (antes eram public)
    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private List<HorarioDisponivel> horarios;

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome);
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horarios = new ArrayList<>();
    }

    // R1: getters
    public String getEspecialidade() { return especialidade; }
    public String getRegistroProfissional() { return registroProfissional; }
    public double getValorConsulta() { return valorConsulta; }
    public List<HorarioDisponivel> getHorarios() { return horarios; }

    // R1: setter com validação
    public void setValorConsulta(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor da consulta não pode ser negativo.");
        }
        this.valorConsulta = valor;
    }

    // SOBRECARGA de atualizar (R4)
    public void atualizar(String registro, double valor) {
        if (validarRegistro(registro)) {
            this.registroProfissional = registro;
            setValorConsulta(valor);
        }
    }

    public void atualizar(String registro, double valor, List<HorarioDisponivel> novosHorarios) {
        if (validarRegistro(registro)) {
            this.registroProfissional = registro;
            setValorConsulta(valor);
            this.horarios = novosHorarios;
        }
    }

    public boolean atendeNoDia(String dia) {
        for (HorarioDisponivel horario : horarios) {
            if (horario.getDiaSemana().equalsIgnoreCase(dia)) return true;
        }
        return false;
    }

    public void adicionarHorario(HorarioDisponivel horario) {
        if (horario != null && !horarios.contains(horario)) horarios.add(horario);
    }

    public boolean removerHorario(HorarioDisponivel horario) {
        return horarios.remove(horario);
    }

    public void listarHorarios() {
        if (horarios.isEmpty()) {
            System.out.println("Nenhum horário disponível cadastrado.");
            return;
        }
        for (HorarioDisponivel horario : horarios) System.out.println("- " + horario);
    }

    public List<HorarioDisponivel> buscarHorariosAlternativos(HorarioDisponivel horarioConflitante) {
        List<HorarioDisponivel> alternativas = new ArrayList<>();
        for (HorarioDisponivel horario : horarios) {
            if (!horario.equals(horarioConflitante)) alternativas.add(horario);
        }
        return alternativas;
    }

    public static boolean especialidadeValida(String esp) {
        return esp.equals("clinica geral") || esp.equals("fisioterapia")
                || esp.equals("psicologia") || esp.equals("nutricao");
    }

    // R4: sobrescrita de exibirResumo (abstract em Pessoa)
    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Espec: " + especialidade
                + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta
                + " | Horários: " + horarios.toString();
    }

    public abstract void registrarEspecifico(Atendimento atendimento);

    // R2: método protected - acessível apenas por subclasses
    protected boolean validarRegistro(String registro) {
        if (registro == null || registro.isEmpty()) {
            System.out.println("Erro: Registro profissional inválido");
            return false;
        }
        return true;
    }
}

// R3: hierarquia 3 níveis - Pessoa → Profissional → Fisioterapeuta
class Fisioterapeuta extends Profissional {

    public int totalSessoesPrevistas;

    public Fisioterapeuta(String nome, String registroProfissional, double valorConsulta, int sessoes) {
        super(nome, "fisioterapia", registroProfissional, valorConsulta);
        this.totalSessoesPrevistas = sessoes;
    }

    // R4: sobrescrita de exibirResumo
    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Sessões Previstas: " + totalSessoesPrevistas;
    }

    // R4: sobrescrita de registrarEspecifico
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null) {
            String obs = atendimento.getObservacoes();
            if (obs == null || obs.isEmpty()) {
                atendimento.setObservacoes("[Fisioterapia]");
            } else {
                atendimento.setObservacoes(obs + " [Fisioterapia]");
            }
        }
    }
}

class Psicologo extends Profissional {

    public String abordagem;

    public Psicologo(String nome, String registroProfissional, double valorConsulta, String abordagem) {
        super(nome, "psicologia", registroProfissional, valorConsulta);
        this.abordagem = abordagem;
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Abordagem: " + abordagem;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null) {
            String info = "[Psicologia - " + this.abordagem + "]";
            String obs = atendimento.getObservacoes();
            if (obs == null || obs.isEmpty()) {
                atendimento.setObservacoes(info);
            } else {
                atendimento.setObservacoes(obs + " " + info);
            }
        }
    }
}
