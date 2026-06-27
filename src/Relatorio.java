public class Relatorio {

    // SOBRECARGA: mesma assinatura de método com parâmetros diferentes (R4)
    public static void gerarRelatorio(Consulta[] consultas, int totalConsultas,
                                      Atendimento[] atendimentos, int totalAtendimentos) {
        System.out.println("\n=== RELATORIO GERAL ===");
        for (int i = 0; i < totalConsultas; i++) {
            System.out.println(consultas[i].exibirResumo());
            String diag = buscarDiagnostico(i, atendimentos, totalAtendimentos);
            if (!diag.isEmpty()) System.out.println("  Diagnostico: " + diag);
            System.out.println("---");
        }
    }

    public static void gerarRelatorio(Consulta[] consultas, int totalConsultas,
                                      Atendimento[] atendimentos, int totalAtendimentos,
                                      String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].getNomeProfissional().equals(nomeProfissional)) {
                System.out.println(consultas[i].exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos, totalAtendimentos);
                if (!diag.isEmpty()) System.out.println("  Diagnostico: " + diag);
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta encontrada para esse profissional.");
    }

    public static void gerarRelatorio(Consulta[] consultas, int totalConsultas,
                                      Atendimento[] atendimentos, int totalAtendimentos,
                                      String dataInicio, String dataFim) {
        System.out.println("\n=== RELATORIO - " + dataInicio + " a " + dataFim + " ===");
        for (int i = 0; i < totalConsultas; i++) {
            if (estaNoIntervalo(consultas[i].getData(), dataInicio, dataFim)) {
                System.out.println(consultas[i].exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos, totalAtendimentos);
                if (!diag.isEmpty()) System.out.println("  Diagnostico: " + diag);
                System.out.println("---");
            }
        }
    }

    public static void gerarResumoFinanceiro(Consulta[] consultas, int totalConsultas,
                                             Pagamento[] pagamentos, int totalPagamentos,
                                             double[] multas, int totalMultas) {
        int realizadas = 0;
        int canceladas = 0;
        double totalFaturado = 0;
        double totalEmMultas = 0;

        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].getStatus().equals("realizada")) realizadas++;
            if (consultas[i].getStatus().equals("cancelada")) canceladas++;
        }

        for (int i = 0; i < totalPagamentos; i++) {
            // calcularValorFinal() substitui o campo valorFinal que não existe na classe abstrata
            totalFaturado += pagamentos[i].calcularValorFinal();
        }

        for (int i = 0; i < totalMultas; i++) {
            totalEmMultas += multas[i];
        }

        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.println("Atendimentos realizados: " + realizadas);
        System.out.println("Total faturado: R$" + Math.round(totalFaturado * 100.0) / 100.0);
        System.out.println("Cancelamentos: " + canceladas);
        System.out.println("Total em multas: R$" + Math.round(totalEmMultas * 100.0) / 100.0);
    }

    public static String buscarDiagnostico(int indiceConsulta, Atendimento[] atendimentos, int total) {
        for (int i = 0; i < total; i++) {
            if (atendimentos[i].getIndiceConsulta() == indiceConsulta) {
                return atendimentos[i].getDiagnostico();
            }
        }
        return "";
    }

    public static boolean estaNoIntervalo(String data, String inicio, String fim) {
        int valorData = converterDataParaNumero(data);
        int valorInicio = converterDataParaNumero(inicio);
        int valorFim = converterDataParaNumero(fim);
        return valorData >= valorInicio && valorData <= valorFim;
    }

    private static int converterDataParaNumero(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        return ano * 10000 + mes * 100 + dia;
    }
}
