package model;

import java.util.ArrayList;
import java.util.List;

// Prontuario so existe dentro de Atendimento
class Prontuario {
    private String       diagnostico;
    // ArrayList<String>: ordem de insercao importa 
    // procedimentos listados na sequencia em que ocorreram
    private List<String> procedimentos;

    // construtor privado: so atendimento cria
    Prontuario(String diagnostico) {
        this.diagnostico   = diagnostico != null ? diagnostico : "";
        this.procedimentos = new ArrayList<>();
    }

    // adicionar um procedimento por vez
    void adicionarProcedimento(String proc) {
        if (proc != null && !proc.trim().isEmpty()) procedimentos.add(proc.trim());
    }

    // adicionar varios procedimentos
    void adicionarProcedimentos(String[] procs, int quantidade) {
        for (int i = 0; i < quantidade; i++) adicionarProcedimento(procs[i]);
    }

    String       getDiagnostico()   { return diagnostico; }
    List<String> getProcedimentos() { return new ArrayList<>(procedimentos); }

    String resumo() {
        if (diagnostico.isEmpty() && procedimentos.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        if (!diagnostico.isEmpty()) sb.append("Diagnostico: ").append(diagnostico);
        if (!procedimentos.isEmpty()) {
            if (sb.length() > 0) sb.append("\n");
            sb.append("Procedimentos: ").append(String.join(", ", procedimentos));
        }
        return sb.toString();
    }
}
