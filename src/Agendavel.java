package interfaces;

// qualquer coisa que pode ser agendada precisa implementar esses três métodos
public interface Agendavel {
    void agendar();   // marca como agendado
    void cancelar();  // cancela o agendamento
    void remarcar();  // muda pra outro horário
}
