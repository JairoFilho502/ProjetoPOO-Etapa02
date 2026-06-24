public class Nutricionista extends Profissional {
    public String focoNutricional;

    public Nutricionista(String nome, String registroProfissional, double valorConsulta, String focoNutricional) {
        super(nome, "nutricao", registroProfissional, valorConsulta);
        this.focoNutricional = focoNutricional;
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Foco Nutricional: " + focoNutricional;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null) {
            String info = "[Nutrição - Foco: " + focoNutricional + "]";
            if (atendimento.observacoes == null || atendimento.observacoes.isEmpty()) {
                atendimento.observacoes = info;
            } else {
                atendimento.observacoes += " " + info;
            }
        }
    }
}