package model;

import interfaces.Exportavel;

public class Atendimento implements Exportavel {
    private int      indiceConsulta;
    private String   observacoes;
    private String   diagnostico;
    private String[] procedimentos;
    private int      totalProcedimentos;

    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta     = indiceConsulta;
        this.observacoes        = observacoes != null ? observacoes : "";
        this.diagnostico        = "";
        this.procedimentos      = new String[10];
        this.totalProcedimentos = 0;
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this(indiceConsulta, observacoes);
        this.diagnostico = diagnostico != null ? diagnostico : "";
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico,
                       String[] procedimentos, int totalProcedimentos) {
        this(indiceConsulta, observacoes, diagnostico);
        this.totalProcedimentos = totalProcedimentos;
        for (int i = 0; i < totalProcedimentos; i++)
            this.procedimentos[i] = procedimentos[i];
    }

    public int      getIndiceConsulta()     { return indiceConsulta; }
    public String   getObservacoes()        { return observacoes; }
    public String   getDiagnostico()        { return diagnostico; }
    public String[] getProcedimentos()      { return procedimentos; }
    public int      getTotalProcedimentos() { return totalProcedimentos; }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes != null ? observacoes : "";
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico != null ? diagnostico : "";
    }

    public void adicionarProcedimento(String procedimento) {
        if (totalProcedimentos < 10)
            procedimentos[totalProcedimentos++] = procedimento;
    }

    public void adicionarProcedimento(String[] procs, int quantidade) {
        for (int i = 0; i < quantidade && totalProcedimentos < 10; i++)
            procedimentos[totalProcedimentos++] = procs[i];
    }

    public String exibirResumo() {
        String resumo = "Observacoes: " + observacoes;
        if (!diagnostico.isEmpty()) resumo += "\nDiagnostico: " + diagnostico;
        if (totalProcedimentos > 0) {
            resumo += "\nProcedimentos: ";
            for (int i = 0; i < totalProcedimentos; i++) {
                resumo += procedimentos[i];
                if (i < totalProcedimentos - 1) resumo += ", ";
            }
        }
        return resumo;
    }

    @Override
    public String exportarDados() {
        return "Atendimento | Consulta#" + indiceConsulta + " | Obs: " + observacoes;
    }
}
