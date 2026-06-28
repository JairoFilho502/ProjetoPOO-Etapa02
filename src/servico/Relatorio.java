package servico;

import model.Consulta;
import model.Atendimento;
import financeiro.Pagamento;
import java.util.List;





// gera os relatórios do sistema a partir das coleções do ClinicaServico
public class Relatorio {

   



// sobrecarga de método: relatório geral de todas as consultas
    public static void gerarRelatorio(List<Consulta> consultas, List<Atendimento> atendimentos) {
        System.out.println("\n== RELATóRIO GERAL ===");
        if (consultas.isEmpty()) { System.out.println("nenhuma consulta registrada."); return; }
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println(consultas.get(i).exibirResumo());
            String diag = buscarDiagnostico(i, atendimentos);
            if (!diag.isEmpty()) System.out.println("  Diagnostico: " + diag);
            System.out.println("---");
        }
    }





    // filtra as consultas de um profissional específico
    public static void gerarRelatorio(List<Consulta> consultas, List<Atendimento> atendimentos,
                                      String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getNomeProfissional().equalsIgnoreCase(nomeProfissional)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.isEmpty()) System.out.println("  Diagnostico: " + diag);
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta encontrada para este profissional.");
    }

    // mostra apenas as consultas dentro do intervalo de datas informado
    public static void gerarRelatorio(List<Consulta> consultas, List<Atendimento> atendimentos,
                                      String dataInicio, String dataFim) {
        System.out.println("\n=== RELATORIO - " + dataInicio + " a " + dataFim + " ===");
        boolean achou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if (estaNoIntervalo(consultas.get(i).getData(), dataInicio, dataFim)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.isEmpty()) System.out.println("  Diagnostico: " + diag);
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) System.out.println("nenhuma consulta no periodo informado.");
    }

    






// somatório de tudo consultas realizadas, cancelamentos, receita e multas
    public static void gerarResumoFinanceiro(List<Consulta> consultas,
                                            
 List<Pagamento> pagamentos,
                                            


 List<Double> multas) {
        int    realizadas    = 0;
        int    canceladas    = 0;
        double totalFaturado = 0;
        double totalEmMultas = 0;

        for (Consulta c : consultas) {
            if (c.getStatus().equals("realizada")) realizadas++;
            if (c.getStatus().equals("cancelada")) canceladas++;
        }
        for (Pagamento p : pagamentos) totalFaturado += p.calcularValorFinal();
        for (double m : multas)        totalEmMultas += m;

        System.out.println("\n=== RESUMO FINANCEIRO ==");
        System.out.println("Atendimentos realizados : " + realizadas);
        System.out.println("Cancelamentos           : " + canceladas);
        

System.out.println("Total faturado          : R$" + Math.round(totalFaturado * 100.0) / 100.0);
        


System.out.println("Total em multas         : R$" + Math.round(totalEmMultas * 100.0) / 100.0);
    }

    public static String buscarDiagnostico(int idxConsulta, List<Atendimento> atendimentos) {
        for (Atendimento a : atendimentos)
            if (a.getIndiceConsulta() == idxConsulta) return a.getDiagnostico();
        return "";
    }

    public static boolean estaNoIntervalo(String data, String inicio, String fim) {
        int v = converterDataParaNumero(data);
        return v >= converterDataParaNumero(inicio) && v <= converterDataParaNumero(fim);
    }




    // conversão da data pra um número inteiro pra facilitar a comparação
    private static int converterDataParaNumero(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        return ano * 10000 + mes * 100 + dia;
    }
}
