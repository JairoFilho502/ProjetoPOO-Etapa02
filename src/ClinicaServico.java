import java.util.ArrayList;
import java.util.List;

public class ClinicaServico {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Profissional> profissionais = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();
    private List<Atendimento> atendimentos = new ArrayList<>();
    private List<Pagamento> pagamentos = new ArrayList<>();
    private List<Double> multas = new ArrayList<>();

    public boolean cadastrarPaciente(Paciente p) {
        for (Paciente pac : pacientes) {
            if (pac.cpf.equals(p.cpf)) return false;
        }
        pacientes.add(p);
        return true;
    }

    public boolean cadastrarProfissional(Profissional p) {
        for (Profissional prof : profissionais) {
            if (prof.nome.equals(p.nome)) return false;
        }
        profissionais.add(p);
        return true;
    }

    public boolean agendarConsulta(Consulta c) {
        consultas.add(c);
        return true;
    }

    public boolean registrarAtendimento(Atendimento a) {
        atendimentos.add(a);
        consultas.get(a.indiceConsulta).realizar();
        return true;
    }

    public boolean registrarPagamento(Pagamento p) {
        pagamentos.add(p);
        return true;
    }
}