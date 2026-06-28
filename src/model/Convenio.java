package model;

import java.util.ArrayList;
import java.util.List;

public class Convenio {
    private String       nome;
    private double       percentualCobertura;
    private List<String> especialidadesCobertas;

    public Convenio(String nome) {
        this.nome                  = nome != null ? nome.trim() : "";
        this.percentualCobertura   = 0.4;
        this.especialidadesCobertas = new ArrayList<>();
    }

    public Convenio(String nome, double percentualCobertura) {
        this.nome                  = nome != null ? nome.trim() : "";
        this.percentualCobertura   = (percentualCobertura >= 0 && percentualCobertura <= 1)
                                     ? percentualCobertura : 0.0;
        this.especialidadesCobertas = new ArrayList<>();
    }

    public String       getNome()                   { return nome; }
    public double       getPercentualCobertura()    { return percentualCobertura; }
    public List<String> getEspecialidadesCobertas() { return especialidadesCobertas; }

    public void setNome(String nome) { if (nome != null) this.nome = nome.trim(); }

    public void setPercentualCobertura(double p) {
        if (p >= 0 && p <= 1) this.percentualCobertura = p;
    }

    public void adicionarEspecialidade(String especialidade) {
        if (especialidade != null && !especialidade.trim().isEmpty())
            especialidadesCobertas.add(especialidade.toLowerCase().trim());
    }

    public boolean possuiCoberturaEspecialidade(String especialidade) {
        if (especialidade == null) return false;
        return especialidadesCobertas.contains(especialidade.toLowerCase().trim());
    }

    public String exibirResumo() {
        return "Convenio: " + nome
                + " | Cobertura: " + (int)(percentualCobertura * 100)
                + "% | Especialidades: " + especialidadesCobertas.size();
    }

    @Override
    public String toString() {
        return nome + " (" + (int)(percentualCobertura * 100) + "% cobertura)";
    }
}
