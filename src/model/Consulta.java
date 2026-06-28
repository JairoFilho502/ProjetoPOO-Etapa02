package model;

import interfaces.Agendavel;
import interfaces.Exportavel;

// uma consulta liga o paciente ao profissional num dia e horário específico
public class Consulta implements Agendavel, Exportavel {
    // Consulta referencia Paciente e Profissional por identificadores
    // sem manter referência direta aos objetos
    private String cpfPaciente;
    private String nomeProfissional;
    private String data;
    private String horario;
    private String tipo;
    private String status;

    // construtor padrão, tipo "inicial" e status "agendada"
    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario) {
        this.cpfPaciente      = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data             = data;
        this.horario          = horario;
        this.tipo             = "inicial";
        this.status           = "agendada";
    }

    // usuario informa o tipo (inicial, retorno, avaliação)
    public Consulta(String cpfPaciente, String nomeProfissional,
                    String data, String horario, String tipo) {
        this.cpfPaciente      = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data             = data;
        this.horario          = horario;
        this.tipo             = tipo != null ? tipo : "inicial";
        this.status           = "agendada";
    }

    // construtor completo, quando ja se sabe o status
    public Consulta(String cpfPaciente, String nomeProfissional,
                    String data, String horario, String tipo, String status) {
        this.cpfPaciente      = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data             = data;
        this.horario          = horario;
        this.tipo             = tipo != null ? tipo : "inicial";
        this.status           = status != null ? status : "agendada";
    }

    public String getCpfPaciente()      { return cpfPaciente; }
    public String getNomeProfissional() { return nomeProfissional; }
    public String getData()             { return data; }
    public String getHorario()          { return horario; }
    public String getTipo()             { return tipo; }
    public String getStatus()           { return status; }

    public void setData(String data)       { if (data != null) this.data = data; }
    public void setHorario(String horario) { if (horario != null) this.horario = horario; }
    public void setStatus(String status)   { if (status != null) this.status = status; }

    // implementacoes da interface Agendavel
    @Override public void agendar()  { this.status = "agendada"; }
    @Override public void cancelar() { this.status = "cancelada"; }
    @Override public void remarcar() { this.status = "remarcada"; }

    // marca a consulta como realizada apos o atendimento
    public void realizar() { this.status = "realizada"; }

    // cancelamento com motivo, retorna mensagem
    public String cancelar(String motivo) {
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    public String exibirResumo() {
        return "Paciente(CPF): " + cpfPaciente
                + " | Prof: " + nomeProfissional
                + " | Data: " + data
                + " | Hora: " + horario
                + " | Tipo: " + tipo
                + " | Status: " + status;
    }

    @Override
    public String exportarDados() {
        return "Consulta: CPF=" + cpfPaciente
                + " | Prof=" + nomeProfissional
                + " | Data=" + data
                + " | Status=" + status;
    }
}
