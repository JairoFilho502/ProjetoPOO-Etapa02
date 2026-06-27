public class Atendimento {

    // R1: todos os atributos privados
    private int indiceConsulta;
    private String observacoes;
    private String diagnostico;
    private String[] procedimentos;
    private int totalProcedimentos;

    // SOBRECARGA de construtores (R4)
    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes = observacoes;
        this.diagnostico = "";
        this.procedimentos = new String[10];
        this.totalProcedimentos = 0;
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new String[10];
        this.totalProcedimentos = 0;
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico,
                       String[] procedimentos, int totalProcedimentos) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new String[10];
        this.totalProcedimentos = totalProcedimentos;
        for (int i = 0; i < totalProcedimentos; i++) {
            this.procedimentos[i] = procedimentos[i];
        }
    }

    // R1: getters
    public int getIndiceConsulta() { return indiceConsulta; }
    public String getObservacoes() { return observacoes; }
    public String getDiagnostico() { return diagnostico; }
    public int getTotalProcedimentos() { return totalProcedimentos; }

    // R1: setters (observacoes é acessado por Fisioterapeuta, Psicologo e Nutricionista)
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    // SOBRECARGA de adicionarProcedimento (R4)
    public void adicionarProcedimento(String procedimento) {
        if (totalProcedimentos < 10) {
            procedimentos[totalProcedimentos] = procedimento;
            totalProcedimentos++;
        }
    }

    public void adicionarProcedimento(String[] procs, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            if (totalProcedimentos < 10) {
                procedimentos[totalProcedimentos] = procs[i];
                totalProcedimentos++;
            }
        }
    }

    public String exibirResumo() {
        String resumo = "Observacoes: " + observacoes;

        if (!diagnostico.isEmpty()) {
            resumo += "\nDiagnostico: " + diagnostico;
        }

        if (totalProcedimentos > 0) {
            resumo += "\nProcedimentos: ";
            for (int i = 0; i < totalProcedimentos; i++) {
                resumo += procedimentos[i];
                if (i < totalProcedimentos - 1) resumo += ", ";
            }
        }
        return resumo;
    }
}
