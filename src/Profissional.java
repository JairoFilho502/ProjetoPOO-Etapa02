import java.util.ArrayList;
import java.util.List;





// Classe tornada abstract
public abstract class Profissional extends Pessoa {
    public String especialidade;
    public String registroProfissional;
    public double valorConsulta;
    public List<String> horarios; // alterando dias disponiveis por horario com genrics
    public int totalDias;

    // so nome e especialidade
    // adaptado para chamar nome e inicializar lista
    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome); // chama o construtor da classe base pessoa
        this.especialidade = especialidade;
        this.registroProfissional = registroProfissional;
        this.valorConsulta = valorConsulta;
        this.horarios = new ArrayList<>(); //inicializando a lista de horarios
        
    }

    public void atualizar(String registro, double valor) {
        if (validarRegistro(registro)) { // uso do metodo protegido}
        this.registroProfissional = registro;
        this.valorConsulta = valor;
        }
    }

    public void atualizar(String registro, double valor, List<String> novosHorarios) {
        if (validarRegistro(registro)){
            this.registroProfissional = registro;
            this.valorConsulta = valor;
            this.horarios = novosHorarios;
        }
    }        

    // verifica se o profissional atende naquele dia
    public boolean atendeNoDia(String dia) {
        return this.horarios.contains(dia);
    }
  

    // valida as especialidades aceitas pela clinica
    public static boolean especialidadeValida(String esp) {
        if (esp.equals("clinica geral")) return true;
        if (esp.equals("fisioterapia")) return true;
        if (esp.equals("psicologia")) return true;
        if (esp.equals("nutricao")) return true;
        return false;
    }


    @Override
    public String exibirResumo(){
        return super.exibirResumo() + " | Espec: " + especialidade + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta + " | Dias: " + horarios.toString();
    }


    // declaração método abstrato( jornadas 17 e 25
    public abstract void registrarEspecifico(Atendimento atendimento);

    // adicionando metodo protected
    protected boolean validarRegistro(String registro) {
        if (registro == null || registro.isEmpty()){
            System.out.println("Erro: Registro profissional inválido");
            return false;
        }
        return true;
    
    }



// acrescentando as subclasses 

