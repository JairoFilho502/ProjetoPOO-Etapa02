import interfaces_horario.Agendavel;
import interfaces_horario.Exportavel;

import excecoes.PacienteInativoException;
import excecoes.OperacaoInvalidaException;

public class Consulta implements Agendavel, Exportavel {
    
    public Paciente paciente;
    public Profissional profissional;
    public String data;
    public String horario;
    public String tipo;
    public String status;

    public Consulta(Paciente paciente, Profissional profissional, String data, String horario) throws PacienteInativoException {
        validarPaciente(paciente);
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    public Consulta(Paciente paciente, Profissional profissional, String data, String horario, String tipo) throws PacienteInativoException {
        validarPaciente(paciente);
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    public Consulta(Paciente paciente, Profissional profissional, String data, String horario, String tipo, String status) throws PacienteInativoException {
        validarPaciente(paciente);
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    // Validação da tua regra de negócio
    private void validarPaciente(Paciente pac) throws PacienteInativoException {
        if (pac != null && !pac.isAtivo()) {
            throw new PacienteInativoException("Não é possível agendar: o paciente está inativo.");
        }
    }

    @Override
    public void cancelar() throws OperacaoInvalidaException {
        if (this.status.equals("cancelada")) {
            throw new OperacaoInvalidaException("Esta consulta já está cancelada.");
        }
        this.status = "cancelada";
    }

    @Override
    public void remarcar() throws OperacaoInvalidaException {
        if (this.status.equals("cancelada")) {
            throw new OperacaoInvalidaException("Não é possível remarcar uma consulta cancelada.");
        }
        this.status = "remarcada";
    }

    // Sobrecarga
    public String cancelar(String motivo) throws OperacaoInvalidaException {
        if (this.status.equals("cancelada")) {
            throw new OperacaoInvalidaException("Esta consulta já está cancelada.");
        }
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    public String exibirResumo() {
        String nomePac = (paciente != null) ? paciente.getNome() : "Desconhecido";
        String nomeProf = (profissional != null) ? profissional.getNome() : "Desconhecido";
        
        return "Paciente: " + nomePac + " | Prof: " + nomeProf
                + " | Data: " + data + " | Hora: " + horario
                + " | Tipo: " + tipo + " | Status: " + status;
    }

    @Override
    public void agendar() {
        this.status = "agendada";
    }

    @Override
    public String exportarDados() {
        String cpfPac = (paciente != null) ? paciente.getCpf() : "N/A";
        String nomeProf = (profissional != null) ? profissional.getNome() : "N/A";
        
        return "Consulta: Paciente(CPF)=" + cpfPac + " | Prof=" + nomeProf + " | Data=" + data + " | Status=" + status;
    }
}