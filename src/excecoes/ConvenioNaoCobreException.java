package excecoes;

// convenio do paciente nao cobre o tipo de consulta solicitada
public class ConvenioNaoCobreException extends Exception {
    public ConvenioNaoCobreException(String mensagem) { super(mensagem); }
    public ConvenioNaoCobreException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
