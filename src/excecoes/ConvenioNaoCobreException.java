// R9: Exceção personalizada - lançada quando a especialidade da consulta não é coberta
//     pelo convênio do paciente
public class ConvenioNaoCobreException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public ConvenioNaoCobreException(String mensagem) {
        super(mensagem);
    }

    public ConvenioNaoCobreException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
