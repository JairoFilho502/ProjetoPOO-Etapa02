public class Consulta implements Agendavel, Exportavel {
    
    // ASSOCIAÇÃO: Consulta conhece Paciente e Profissional por referência direta ao objeto 
    public Paciente paciente;
    public Profissional profissional;
    public String data;
    public String horario;
    public String tipo;
    public String status;

    // Construtor 1: Sem tipo definido (assume inicial) 
    public Consulta(Paciente paciente, Profissional profissional, String data, String horario) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = "inicial";
        this.status = "agendada";
    }

    // Construtor 2: Completo com Tipo 
    public Consulta(Paciente paciente, Profissional profissional, String data, String horario, String tipo) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = "agendada";
    }

    // Construtor 3: Para uso na remarcação (permite setar o status direto) 
    public Consulta(Paciente paciente, Profissional profissional, String data,
                    String horario, String tipo, String status) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
    }

    @Override 
    public void cancelar() {
        this.status = "cancelada";
    }

    @Override 
    public void remarcar() {
        this.status = "remarcada";
    }

    // SOBRECARGA: mesmo nome, parâmetros diferentes (resolvido em tempo de compilação) 
    public String cancelar(String motivo) {
        this.status = "cancelada";
        return "Consulta cancelada. Motivo: " + motivo;
    }

    public String exibirResumo() {
        // Para o Paciente, usamos o getter porque o seu colega já o criou
        String nomePac = (paciente != null) ? paciente.getNome() : "Desconhecido";
        // Para o Profissional, usamos o atributo direto .nome porque o getter não existe em Pessoa
        String nomeProf = (profissional != null) ? profissional.nome : "Desconhecido";
        
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
        // Requisito R7: contrato obrigatório da interface Exportavel
        String cpfPac = (paciente != null) ? paciente.getCpf() : "N/A";
        String nomeProf = (profissional != null) ? profissional.nome : "N/A";
        
        return "Consulta: Paciente(CPF)=" + cpfPac + " | Prof=" + nomeProf + " | Data=" + data + " | Status=" + status;
    }
}