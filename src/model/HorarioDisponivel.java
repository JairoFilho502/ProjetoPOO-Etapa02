package model;
import java.util.Objects;



// guarda um dia e turno em que o profissional estará disponível
public class HorarioDisponivel {
    private String diaSemana;
    private String turno;

    public HorarioDisponivel(String diaSemana, String turno) {
        setDiaSemana(diaSemana);
        setTurno(turno);
    }

    public String getDiaSemana() { 
return diaSemana; }
    
public String getTurno()     { 
return turno; }

    


// salva o dia em minúsculo pra não ter problema com comparações!!
    public void setDiaSemana(String diaSemana) {
        if (diaSemana == null || diaSemana.trim().isEmpty())
            throw new IllegalArgumentException("dia da semana nao pode ser vazio.");
        this.diaSemana = diaSemana.trim().toLowerCase();
    }

    





// só aceita manha ou tarde, qlq outra coisa joga pra exceção
    public void setTurno(String turno) {
        if (turno == null || turno.trim().isEmpty())
            throw new IllegalArgumentException("Turno nao pode ser vazio.");
        String v = turno.trim().toLowerCase();
        if (!v.equals("manha") && !v.equals("tarde"))
            throw new IllegalArgumentException("turno inválido. Use manha ou tarde.");
        this.turno = v;
    }

    @Override
    public String toString() { return diaSemana + " - " + turno; }

   



 // dois horários são iguais se têm o mesmo dia e turno
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HorarioDisponivel)) return false;
        HorarioDisponivel o = (HorarioDisponivel) obj;
        return diaSemana.equalsIgnoreCase(o.diaSemana) && turno.equalsIgnoreCase(o.turno);
    }

    

// precisa sobrescrever o hashCode junto com o equals, senão dá bug nas coleções
    @Override
    public int hashCode() {
        return Objects.hash(diaSemana.toLowerCase(), turno.toLowerCase());
    }
}
