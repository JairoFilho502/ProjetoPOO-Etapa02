package excecoes;

// lancada quando o horario que o paciente quer ja esta ocupado
public class HorarioIndisponivelException extends Exception {
    public HorarioIndisponivelException(String mensagem) { super(mensagem); }
    public HorarioIndisponivelException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
