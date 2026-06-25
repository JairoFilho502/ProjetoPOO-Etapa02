// R9: Exceção personalizada - lançada quando horário já está ocupado ou profissional não atende no dia
public class HorarioIndisponivelException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }

    public HorarioIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
