// importando bibliotecas de collections
import java.util.ArrayList;
import java.util.List;


// Classe PROFISSIONAL tornada abstract
public abstract class Profissional extends Pessoa{
    public String especialidade;
    public String registroProfissional;
    public double valorConsulta;

    // alterando dias disponíveis para objetos HorarioDisponivel
    public List<HorarioDisponivel> horarios;

    // só nome e especialidade
    // adaptado para chamar nome e inicializar lista
    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta){
        super(nome); // Corrigido: A classe Pessoa só pede o nome!
        
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horarios = new ArrayList<>(); // inicializando a lista de horários
    }

    public void atualizar(String registro, double valor) {
        if(validarRegistro(registro)){
            this.registroProfissional = registro;
            this.valorConsulta = valor;
        }
    }
        
    
    public void atualizar(String registro, double valor, List<HorarioDisponivel> novosHorarios){
    if(validarRegistro(registro)){
        this.registroProfissional = registro;
        this.valorConsulta = valor;
        this.horarios = novosHorarios;
    }
   }

    // verifica se o profissional atende naquele dia
    public boolean atendeNoDia(String dia){
        for (HorarioDisponivel horario : horarios) {
            if (horario.getDiaSemana().equalsIgnoreCase(dia)){
                return true;
            }
        }
        return false;
    } 

    public void adicionarHorario(HorarioDisponivel horario) {
        if (horario != null && !horarios.contains(horario)){
            horarios.add(horario);
        }
    }

    public boolean removerHorario(HorarioDisponivel horario){
        return horarios.remove(horario);
    }

    public List<HorarioDisponivel> getHorarios() {
        return horarios;
    }

    public void listarHorarios() {
        if (horarios.isEmpty()) {
            System.out.println("Nenhum horário disponível cadastrado");
                return;
        }

        for (HorarioDisponivel horario : horarios) {
        
            System.out.println("-" + horario);
        }
    }

    public List<HorarioDisponivel> buscarHorariosAlternativos(HorarioDisponivel horarioConflitante) {
        List<HorarioDisponivel> alternativas = new ArrayList<>();

        for (HorarioDisponivel horario : horarios) {
            if (!horario.equals(horarioConflitante)) {
                alternativas.add(horario);
            }
        }

        return alternativas;
    }

    // valida as especialidades aceitas pela clínica
    public static boolean especialidadeValida(String esp) {
        if (esp.equals("clinica geral")) return true;
        if (esp.equals("fisioterapia")) return true;
        if (esp.equals("psicologia")) return true;
        if (esp.equals("nutricao")) return true;
        return false;
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Espec: " + especialidade + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta + " | Horários: " + horarios.toString();
    }

    // declaração método abstrato
    public abstract void registrarEspecifico(Atendimento atendimento);

    // adicionando metodo protected
    protected boolean validarRegistro(String registro) {
        if (registro == null || registro.isEmpty()) {
            System.out.println("Erro: Registro profissional inválido");
            return false;
        }
        return true;
    }
}

// classe fisioterapeuta
class Fisioterapeuta extends Profissional {

    public int totalSessoesPrevistas;

    public Fisioterapeuta(String nome, String registroProfissional, double valorConsulta, int sessoes) {
        super(nome, "fisioterapia", registroProfissional, valorConsulta);
        this.totalSessoesPrevistas = sessoes;
    }

    // override de exibirResumo
    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Sessões Previstas: " + totalSessoesPrevistas;
    }

    // override obrigatório de registro específico
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null && atendimento.prontuario != null) {
            if (atendimento.prontuario.observacoes == null || atendimento.prontuario.observacoes.isEmpty()) {
                atendimento.prontuario.observacoes = "[Fisioterapia]";
            } else {
                atendimento.prontuario.observacoes += " [Fisioterapia]";
            }
        }
    }
}

// classe psicologo
class Psicologo extends Profissional {

    public String abordagem;

    public Psicologo(String nome, String registroProfissional, double valorConsulta, String abordagem) {
        super(nome, "psicologia", registroProfissional, valorConsulta);
        this.abordagem = abordagem;
    }

    // override de exibirResumo
    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Abordagem: " + abordagem;
    }

// override de registrarEspecifico
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null && atendimento.prontuario != null) {
            String info = "[Psicologia - " + this.abordagem + "]";
            if (atendimento.prontuario.observacoes == null || atendimento.prontuario.observacoes.isEmpty()) {
                atendimento.prontuario.observacoes = info;
            } else {
                atendimento.prontuario.observacoes += " " + info;
            }
        }
    }
}

// classe dos nutricionistas
class Nutricionista extends Profissional {
    public String focoPlanoNutricional;

      public Nutricionista(String nome, String registroProfissional, double valorConsulta, String focoPlanoNutricional) {  
        super(nome, "nutricao", registroProfissional, valorConsulta);
        this.focoPlanoNutricional = focoPlanoNutricional;
    }

    // override de exibirResumo
    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Foco Nutricional: " + focoPlanoNutricional;
    }

// override de registrarEspecifico
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null && atendimento.prontuario != null) {
            String info = "[Nutrição -- Foco: " + this.focoPlanoNutricional + "]";
            if (atendimento.prontuario.observacoes == null || atendimento.prontuario.observacoes.isEmpty()) {
                atendimento.prontuario.observacoes = info;
            } else {
                atendimento.prontuario.observacoes += " " + info;
            }
        }
    }
}










