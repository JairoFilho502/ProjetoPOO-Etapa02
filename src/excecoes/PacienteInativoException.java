// R9: Exceção personalizada - lançada ao tentar agendar consulta para paciente inativo
public class PacienteInativoException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public PacienteInativoException(String mensagem) {
        super(mensagem);
    }

    public PacienteInativoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
