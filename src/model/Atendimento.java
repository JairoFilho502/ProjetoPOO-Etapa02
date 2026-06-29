package model;

import interfaces.Exportavel;
import java.util.List;
// 







// COMPOSIÇÃO: Prontuário só existe dentro de Atendimento — se Atendimento for removido, Prontuário também é

public class Atendimento implements Exportavel {
    private int      indiceConsulta;
    private String   observacoes;
    private Prontuario prontuario; 





// sobrecarga
    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes    = observacoes != null ? observacoes : "";
        this.prontuario     = new Prontuario("");
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this(indiceConsulta, observacoes);
        this.prontuario = new Prontuario(diagnostico);
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico,
                       String[] procedimentos, int totalProcedimentos) {
        this(indiceConsulta, observacoes, diagnostico);
        this.prontuario.adicionarProcedimentos(procedimentos, totalProcedimentos);
    }

    public int    getIndiceConsulta() { return indiceConsulta; }
    public String getObservacoes()    { return observacoes; }


    // delega pro prontuario para manter o encapsulamento
    public String       getDiagnostico()   { return prontuario.getDiagnostico(); }
    public List<String> getProcedimentos() { return prontuario.getProcedimentos(); }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes != null ? observacoes : "";
    }

    public void adicionarProcedimento(String procedimento) {
        prontuario.adicionarProcedimento(procedimento);
    }

    public String exibirResumo() {
        String resumo = "Observacoes: " + observacoes;
        String resumoProntuario = prontuario.resumo();
        if (!resumoProntuario.isEmpty()) resumo += "\n" + resumoProntuario;
        return resumo;
    }

    @Override
    public String exportarDados() {
        return "Atendimento | Consulta" + indiceConsulta + " | Obs: " + observacoes;
    }
}
