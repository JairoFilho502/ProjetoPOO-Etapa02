import java.util.ArrayList;
import java.util.List;

public class Prontuario {
    public String observacoes;
    public String diagnostico;
    public List<String> procedimentos; // R10: Coleções em vez de arrays fixos
    public String dataRegistro;

    // R8: O CONSTRUTOR deve ser package-private (sem public nem private)
    Prontuario(String observacoes, String diagnostico, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new ArrayList<>();
        this.dataRegistro = dataRegistro;
    }
}