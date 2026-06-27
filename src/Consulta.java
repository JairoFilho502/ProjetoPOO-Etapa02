public class Consulta {

    // R1: todos os atributos privados
    private String cpfPaciente;
    private String nomeProfissional;
    private String data;
    private String horario;
    private String tipo;
    private String status;

    // SOBRECARGA de construtores (R4)
    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    public Consulta(String cpfPaciente, String nomeProfissional, String data, String horario, String tipo) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    public Consulta(String cpfPaciente, String nomeProfissional, String data,
                    String horario, String tipo, String status) {
        this.cpfPaciente = cpfPaciente;
        this.nomeProfissional = nomeProfissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    // R1: getters
    public String getCpfPaciente() { return cpfPaciente; }
    public String getNomeProfissional() { return nomeProfissional; }
    public String getData() { return data; }
    public String getHorario() { return horario; }
    public String getTipo() { return tipo; }
    public String getStatus() { return status; }

    // R1: setters
    public void setData(String data) { this.data = data; }
    public void setHorario(String horario) { this.horario = horario; }

    // SOBRECARGA de cancelar (R4)
    public void cancelar() {
        this.status = "cancelada";
    }

    public String cancelar(String motivo) {
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    public void remarcar() { this.status = "remarcada"; }
    public void realizar() { this.status = "realizada"; }

    public String exibirResumo() {
        return "Paciente(CPF): " + cpfPaciente + " | Prof: " + nomeProfissional
                + " | Data: " + data + " | Hora: " + horario
                + " | Tipo: " + tipo + " | Status: " + status;
    }
}
