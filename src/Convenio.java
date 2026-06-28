// ASSOCIACAO: Paciente conhece Convenio, mas ambos existem de forma independente
public class Convenio {
    private String nome;
    private double percentualCobertura;

    // SOBRECARGA: construtor completo
    public Convenio(String nome, double percentualCobertura) {
        this.nome = nome != null ? nome.trim() : "";
        this.percentualCobertura = (percentualCobertura >= 0 && percentualCobertura <= 1)
                ? percentualCobertura : 0.0;
    }

    // SOBRECARGA: construtor com cobertura padrao de 40%
    public Convenio(String nome) {
        this(nome, 0.4);
    }

    public String getNome()                  { return nome; }
    public double getPercentualCobertura()   { return percentualCobertura; }

    public void setNome(String nome) {
        if (nome != null) this.nome = nome.trim();
    }

    public void setPercentualCobertura(double p) {
        if (p >= 0 && p <= 1) this.percentualCobertura = p;
    }

    @Override
    public String toString() {
        return nome + " (" + (int)(percentualCobertura * 100) + "% cobertura)";
    }
}
