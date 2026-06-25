# VidaPlena – Sistema de Gestão de Clínica Multidisciplinar

## Descrição do Sistema

A clínica VidaPlena agora conta com nosso sistema de gerenciamento desenvolvido em Java com os princípios da programação orientada a objetos

O sistema permite o cadastro e gerenciamento de pacientes, profissionais, consultas e pagamentos

Nosso projeto foi desenvolvido aplicando, dentre outros, os conceitos de abstração, encapsulamento, herança, polimorfismo, interfaces e exceções

---

# Funcionalidades

## Gestão de Pacientes

* Cadastro simplificado de pacientes.
* Cadastro completo de pacientes.
* Atualização de dados cadastrais.
* Controle de CPF único.
* Desativação de pacientes.
* Busca rápida por CPF.

## Gestão de Profissionais

* Cadastro de profissionais.
* Cadastro de fisioterapeutas.
* Cadastro de psicólogos.
* Cadastro de nutricionistas.
* Cadastro de clínicos gerais.
* Atualização de dados profissionais.

## Gestão de Consultas

* Agendamento por profissional.
* Agendamento por especialidade.
* Remarcação de consultas.
* Cancelamento de consultas.
* Controle de conflitos de agenda.
* Sugestão automática de horários alternativos.

## Gestão de Atendimentos

* Registro de atendimento clínico.
* Criação automática de prontuário.
* Registro de observações.
* Registro de diagnóstico.
* Registro de procedimentos realizados.

## Gestão Financeira

### Pagamento em Dinheiro/PIX

* Aplicação automática de 5% de desconto.

### Pagamento em Cartão

* Até 3 parcelas sem juros.
* De 4 a 6 parcelas com acréscimo progressivo de 2,5% por parcela excedente.

### Pagamento por Convênio

* SaúdePlus: 40% de cobertura.
* VidaMais: 30% de cobertura.
* BemEstar: 50% de cobertura.

## Relatórios

* Relatório unificado de pessoas cadastradas.
* Relatório financeiro.
* Relatório de consultas.
* Relatório de atendimentos.
* Exportação de dados através da interface Exportavel.

## Tratamento de Exceções

O sistema implementa as seguintes exceções personalizadas:

* PacienteInativoException
* PacienteNaoEncontradoException
* ProfissionalNaoEncontradoException
* HorarioIndisponivelException
* ConsultaNaoEncontradaException
* OperacaoInvalidaException
* PagamentoInvalidoException
* ConvenioNaoCobreException

---

# Estruturas de Dados Utilizadas

## ArrayList

Utilizado para armazenar:

* Pacientes
* Profissionais
* Consultas
* Atendimentos
* Pagamentos
* Pessoas cadastradas

## HashSet

Utilizado para controle de CPFs únicos.

## HashMap

Utilizado para:

* Busca de pacientes por CPF.
* Busca de profissionais por nome.

---
# Como Utilizar o Sistema

## 1. Cadastrar o Paciente

1. Escolha a opção "Cadastrar Paciente".
2. Informe os dados solicitados.
3. O sistema valida o CPF.
4. O paciente é armazenado no sistema.

## 2. Cadastrar Profissional

1. Escolha a opção "Cadastrar Profissional".
2. Selecione a especialidade.
3. Informe os dados profissionais.
4. O profissional é registrado.

## 3. Agendar Consulta

1. Escolha a opção "Agendar Consulta".
2. Informe o CPF do paciente.
3. Escolha o profissional ou especialidade.
4. Informe data e horário.
5. O sistema verifica disponibilidade.
6. Em caso de conflito, um horário alternativo é sugerido.

## 4. Cancelar Consulta

1. Escolha a opção "Cancelar Consulta".
2. Localize a consulta.
3. Confirme o cancelamento.

## 5. Remarcar Consulta

1. Escolha a opção "Remarcar Consulta".
2. Selecione a consulta.
3. Informe nova data e horário.
4. O sistema registra a alteração.

## 6. Registrar Atendimento

1. Selecione uma consulta realizada.
2. Informe observações clínicas.
3. Informe diagnóstico.
4. Informe procedimentos realizados.
5. O prontuário é criado automaticamente.

## 7. Processar Pagamento

1. Escolha a forma de pagamento.
2. Informe os dados necessários.
3. O sistema calcula automaticamente descontos, juros ou cobertura de convênio.
4. O pagamento é registrado.

## 8. Emitir Relatórios

1. Escolha a opção "Relatórios".
2. Selecione o relatório desejado.
3. O sistema exibe as informações consolidadas.

---

# Conceitos de Programação Orientada a Objetos Aplicados ao projeto:

* Encapsulamento
* Herança
* Abstração
* Polimorfismo
* Sobrecarga
* Sobrescrita
* Interfaces
* Associação
* Agregação
* Composição
* Tratamento de Exceções
* Collections Framework

---

# Integrantes do Grupo:

Arthur Dias
Ezdras Virgulino
Jairo George
João Victor Serrano
José Guilherme Ferreira Alves
Pedro 
Thales Alberto
