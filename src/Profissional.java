import java.util.ArrayList;
import java.util.List;





// Classe PROFISSIONAL tornada abstract
public abstract class Profissional extends Pessoa {
    public String especialidade;
    public String registroProfissional;
    public double valorConsulta;
    public List<String> horarios; // alterando dias disponiveis por horario com genrics




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
        if (validarRegistro(registro)) {                // uso do metodo protegido}

        
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



// acrescentando a primeira subclasses:




// classe fisioterapeuta






class Fisioterapeuta extends Profissional{

    public int totalSessoesPrevistas;



    public Fisioterapeuta(String nome, String registroProfissional, double valorConsulta, int sessoes) {

        super(nome, "fisioterapia", registroProfissional, valorConsulta);

        this.totalSessoesPrevistas = sessoes;

    }



    // acrescentando o override de exibirresumo

    @Override 

    public String exibirResumo() {

        return super.exibirResumo() + " | Sessões Previstas: " + totalSessoesPrevistas;



    }



    // override de registroespecifico

    @Override

    public void registrarEspecifico(Atendimento atendimento){

        if(atendimento != null) {

            if (atendimento.observacoes == null || atendimento.observacoes.isEmpty()) {
                atendimento.observacoes = "[Fisioterapia]";
            } else{
                atendimento.observacoes += "[Fisioterapia]";
            }


        }

    }

}







// acrescentando a segunda classe psicologo(jornadas 16 e 25)

class Psicologo extends Profissional {

    public String abordagem;



    public Psicologo(String nome, String registroProfissional, double valorConsulta, String abordagem){



        super(nome, "psicologia", registroProfissional, valorConsulta);

        this.abordagem = abordagem;



    }



    

    // override de exibirresumo

    @Override

    public String exibirResumo(){

        return super.exibirResumo() + " | Abordagem: " + abordagem;

    }









    // acrescentando o override obrigatório de registro especifico

    @Override

    public void registrarEspecifico(Atendimento atendimento) {

        if(atendimento != null){
            String info = "[Psicologia - " + this.abordagem + "] ";
            if (atendimento.observacoes == null || atendimento.observacoes.isEmpty()) {
                atendimento.observacoes = info;
            } else {
                atendimento.observacoes += " " + info;
            }
        }


        }

    }

}


