// Necessário para Profissional poder ser instanciado com especialidade "clinica geral"
// (Profissional é abstract - Main.java não pode usar "new Profissional(...)" diretamente)
public class MedicoGeral extends Profissional {

    public MedicoGeral(String nome, String registroProfissional, double valorConsulta) {
        super(nome, "clinica geral", registroProfissional, valorConsulta);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Tipo: Médico Geral";
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento != null) {
            String obs = atendimento.getObservacoes();
            if (obs == null || obs.isEmpty()) {
                atendimento.setObservacoes("[Clínica Geral]");
            } else {
                atendimento.setObservacoes(obs + " [Clínica Geral]");
            }
        }
    }
}
