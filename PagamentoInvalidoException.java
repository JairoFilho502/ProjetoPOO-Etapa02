package excecoes;

// lancada quando tentamos fazer algo com um paciente que foi desativado
public class PacienteInativoException extends Exception {
    public PacienteInativoException(String mensagem) { super(mensagem); }
    public PacienteInativoException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
