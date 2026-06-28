package model;

import java.util.ArrayList;
import java.util.List;

// mostra o oplano de saude
public class Convenio {
    private String nome;
    private double percentualCobertura;
    private List<String> especialidadesCobertas;

    // por padrão cobre 40% (0.4)
    public Convenio(String nome) {
        this.nome = nome != null ? nome.trim() : "";
        this.percentualCobertura = 0.4;
        this.especialidadesCobertas = new ArrayList<>();
    }

    // construtor com cobertura personalizada
    public Convenio(String nome, double percentualCobertura) {
        this.nome = nome != null ? nome.trim() : "";
        // garante que a cobertura fica entre 0 e 1
        this.percentualCobertura = (percentualCobertura >= 0 && percentualCobertura <= 1)
                ? percentualCobertura
                : 0.0;
        this.especialidadesCobertas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public double getPercentualCobertura() {
        return percentualCobertura;
    }

    public List<String> getEspecialidadesCobertas() {
        return especialidadesCobertas;
    }

    public void setNome(String nome) {
        if (nome != null)
            this.nome = nome.trim();
    }

    // não deixa colocar valor fora de 0 a 1
    public void setPercentualCobertura(double p) {
        if (p >= 0 && p <= 1)
            this.percentualCobertura = p;
    }

    // salva especialidade em minúsculo pra facilitar a comparação depois
    public void adicionarEspecialidade(String especialidade) {
        if (especialidade != null && !especialidade.trim().isEmpty())
            especialidadesCobertas.add(especialidade.toLowerCase().trim());
    }

    // verifica se o convênio cobre aquela especialidade específica
    public boolean possuiCoberturaEspecialidade(String especialidade) {
        if (especialidade == null)
            return false;
        return especialidadesCobertas.contains(especialidade.toLowerCase().trim());
    }

    public String exibirResumo() {
        return "Convenio: " + nome
                + " | Cobertura: " + (int) (percentualCobertura * 100)
                + "% | Especialidades: " + especialidadesCobertas.size();
    }

    @Override
    public String toString() {
        return nome + " (" + (int) (percentualCobertura * 100) + "% cobertura)";
    }
}
