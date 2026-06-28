
public class Atendimento implements Exportavel {
    public int indiceConsulta;
    
    //  Atendimento gerencia o ciclo de vida de Prontuario
    public Prontuario prontuario;

    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta = indiceConsulta;
        // Prontuário é instanciado exclusivamente aqui dentro
        this.prontuario = new Prontuario(observacoes, "", "27/06/2026");
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this.indiceConsulta = indiceConsulta;
        // Prontuário só existe dentro de Atendimento se Atendimento for removido, Prontuário também vai ser
        this.prontuario = new Prontuario(observacoes, diagnostico, "27/06/2026");
    }

    // Construtor legado dos meninos adaptado para salvar na List do Prontuário 
    public Atendimento(int indiceConsulta, String observacoes, String diagnostico, String[] procedimentosAntigos, int total) {
        this.indiceConsulta = indiceConsulta;
        //  Prontuário só existe dentro de Atendimento se Atendimento for removido, Prontuário também vai ser
        this.prontuario = new Prontuario(observacoes, diagnostico, "27/06/2026");
        
        for (int i = 0; i < total; i++) {
            this.prontuario.procedimentos.add(procedimentosAntigos[i]);
        }
    }

    public void adicionarProcedimento(String procedimento) {
        if (this.prontuario != null) {
            this.prontuario.procedimentos.add(procedimento);
        }
    }

    // SOBRECARGA: Mantendo o método antigo de adicionar vários procedimentos por segurança 
    public void adicionarProcedimento(String[] procs, int quantidade) {
        if (this.prontuario != null) {
            for (int i = 0; i < quantidade; i++) {
                this.prontuario.procedimentos.add(procs[i]);
            }
        }
    }

    public String exibirResumo() {
        if (prontuario == null) return "Sem prontuário.";
        String resumo = "Observacoes: " + prontuario.observacoes;
        if (!prontuario.diagnostico.equals("")) {
            resumo += "\nDiagnostico: " + prontuario.diagnostico;
        }
        if (!prontuario.procedimentos.isEmpty()) {
            resumo += "\nProcedimentos: " + String.join(", ", prontuario.procedimentos);
        }
        return resumo;
    }

    @Override
    public String exportarDados() {
        // Implementação obrigatória da interface Exportavel
        return "Atendimento | Consulta ID: " + indiceConsulta + " | Obs: " + (prontuario != null ? prontuario.observacoes : "N/A");
    }
}