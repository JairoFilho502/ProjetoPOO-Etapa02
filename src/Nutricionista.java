// Arquivo movido de raiz do projeto para src/ durante refatoração
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
            String obs = atendimento.getObservacoes();
            if (obs == null || obs.isEmpty()) {
                atendimento.setObservacoes(info);
            } else {
                atendimento.setObservacoes(obs + " " + info);
            }
        }
    }
}
