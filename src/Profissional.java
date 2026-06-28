import java.util.ArrayList; // usando bibliotecas collections para usar Lists
import java.util.List;




public abstract class Profissional extends Pessoa{ // hierarquia com a superclasse pessoa
    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private List<HorarioDisponivel> horarios;

    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta){
    super(nome);
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horarios = new ArrayList<>();
    }

    public String getEspecialidade(){ // usando método getters
         return especialidade; }
    public String getRegistroProfissional() { 
        return registroProfissional; }
    public double getValorConsulta() { 
        return valorConsulta; }
    public List<HorarioDisponivel> getHorarios() { 
        return horarios; }

        public void atualizar(String registro, double valor) {
        if (validarRegistro(registro)) {    
            this.registroProfissional = registro;
            this.valorConsulta = valor;
        }
    }

    public void atualizar(String registro, double valor, List<HorarioDisponivel>novosHorarios){ // uso de generics para evitar horarios duplicados
    if (validarRegistro(registro)){    
            this.registroProfissional = registro;
            this.valorConsulta = valor;
            this.horarios = new ArrayList<>(novosHorarios);
        }
    }

    public boolean atendeNoDia(String dia) {
        for (HorarioDisponivel h : horarios) {
            if (h.getDiaSemana() != null && h.getDiaSemana().equalsIgnoreCase(dia))
            
            return true;
        }
        
        return false;
    }

    public void adicionarHorario(HorarioDisponivel horario) {    
        if (horario != null && !horarios.contains(horario)) horarios.add(horario);
    }

    public boolean removerHorario(HorarioDisponivel horario) {
        return horarios.remove(horario);
    }


    public void listarHorarios() {
        if (horarios.isEmpty()) {
            System.out.println("Não há horário disponível");
        }
        for (HorarioDisponivel h : horarios) 
        System.out.println("-" + h);
    }

    public List<HorarioDisponivel>buscarHorariosAlternativos(HorarioDisponivel horarioConflitante) {
    List<HorarioDisponivel> alt = new ArrayList<>();
        for (HorarioDisponivel h : horarios) {    
            if (!h.equals(horarioConflitante)) alt.add(h);
        }
        return alt;
    }

    public static boolean especialidadeValida(String esp) {
        if (esp == null) return false;
        return esp.equals("clinico geral") || esp.equals("fisioterapia")
                || esp.equals("psicologia") || esp.equals("nutricao");
    }

    @Override
    public String exibirResumo() {    
        return "Nome: " + getNome() + "|Especialidaded: " + especialidade + "|Registro: " + registroProfissional + "|Valor: R$" + valorConsulta + "|Horarios: " + horarios.toString();
            
    }

    public abstract void registrarEspecifico(Atendimento atendimento);

    protected boolean validarRegistro(String registro) { // uso do modificador de acesso protect
        if (registro == null || registro.isEmpty()) {    
            System.out.println("Eroo: Registro profissional invalido.");
            return false;
        }
        return true;
    }
}

class Fisioterapeuta extends Profissional {
    private int totalSessoesPrevistas;

    public Fisioterapeuta(String nome, String registroProfissional, double valorConsulta, int sessoes){
    super(nome, "fisioterapia", registroProfissional, valorConsulta);    
        this.totalSessoesPrevistas = sessoes;
    }

    public int getTotalSessoesPrevistas() {
        return totalSessoesPrevistas;
    }
    public void setTotalSessoesPrevistas(int s){
        if (s >= 0){
        this.totalSessoesPrevistas = s;}
    }
    @Override
    public String exibirResumo() {    
        return super.exibirResumo() + "| Sessoes Previstas: " + totalSessoesPrevistas;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) return;
        String obs = atendimento.getObservacoes();
        atendimento.setObservacoes(obs == null || obs.isEmpty() ? "[Fisioterapia]" : obs + " [Fisioterapia]");
    }
}

class Psicologo extends Profissional {
    private String abordagem;

    public Psicologo(String nome, String registroProfissional, double valorConsulta, String abordagem) {
        super(nome, "psicologia", registroProfissional, valorConsulta);
        this.abordagem = abordagem;
    }

    public String getAbordagem() { 
        
        return abordagem;
     }
    public void setAbordagem(String abordagem) { 
        this.abordagem = abordagem != null ? abordagem : "";
     }

    @Override
     public String exibirResumo() {
        return super.exibirResumo() + "|Abordagem: " + abordagem; 

     }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) 
        
        return;
        
        String info = "[Psicologia - " + abordagem + "]";
        String obs = atendimento.getObservacoes();

        atendimento.setObservacoes(obs == null || obs.isEmpty() ? info : obs + " " + info);
    }
}

    class Nutricionista extends Profissional {
    private String focoPlanoNutricional;

    public Nutricionista(String nome, String registroProfissional, double valorConsulta, String foco){
        super(nome, "nutricao", registroProfissional, valorConsulta);
        this.focoPlanoNutricional = foco;
    }

    public String getFocoPlanoNutricional() {
        return focoPlanoNutricional;
    }
    public void setFocoPlanoNutricional(String f){
        this.focoPlanoNutricional = f != null ? f: "";
    }

    @Override
    public String exibirResumo(){
        return 
        super.exibirResumo() + "| Foco Nutricional: " + focoPlanoNutricional;

    }


    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) 
        return;
        
        String info = "[Nutricao -- Foco: " + focoPlanoNutricional +" ] ";
        
        String obs = atendimento.getObservacoes();
        atendimento.setObservacoes(obs == null || obs.isEmpty() ? info : obs + " " + info);
    }
}

class ClinicoGeral extends Profissional {
    public ClinicoGeral(String nome, String registroProfissional, double valorConsulta){
        super(nome, "Clinico Geral", registroProfissional, valorConsulta);
    }

    @Override
    public String exibirResumo() { 

        return super.exibirResumo() + " | Tipo: Clinico Geral";
    
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        if (atendimento == null) 
        
        return;
        
        String obs = atendimento.getObservacoes();
        atendimento.setObservacoes(obs == null || obs.isEmpty() ? "[Clinica Geral]" : obs + " [Clinica Geral]");
    }
}
