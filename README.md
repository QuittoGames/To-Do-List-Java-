<div align="center">

# ToDoList Java

### *Um Gerenciador de Tarefas Simples, Eficiente e Persistente*

<p align="center">
  <img src="https://img.icons8.com/color/150/000000/todo-list.png" alt="ToDo List" width="150" />
</p>

[![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![Jackson](https://img.shields.io/badge/Jackson_JSON-000000?style=for-the-badge&logo=json&logoColor=white)](https://github.com/FasterXML/jackson)

**ToDoList** é uma aplicação construída em Java puro utilizando o padrão de arquitetura em camadas (MVC simplificado). O projeto apresenta criação, conclusão e remoção de tarefas, garantindo um estado persistente em disco no formato JSON, aliados a algoritmos eficientes de busca no banco de dados em memória.

[Features](#features) • [Arquitetura](#arquitetura) • [Instalação](#instalação) • [Uso](#uso) • [Licença](#licença)

</div>

---

## Features

- **Gerenciamento Completo**: Capacidade de criar (`add`), concluir (`complete`) e remover (`remove`) tarefas no sistema.
- **Persistência Baseada em Arquivo**: Estado da aplicação é salvo em um arquivo `tasks.json` localizado no próprio projeto, processado pela biblioteca **Jackson Databind**.
- **Busca Otimizada**: Implementação manual de **Busca Binária** (`binarySearchTask`) em coleção ordenada no Service para localizar rapidamente tarefas pelo seu `UUID`.
- **Arquitetura Limpa**: Divisão clara de responsabilidades isolando controle de fluxo (`Controller`), lógica de negócios (`Services`) e acesso direto a arquivos (`Data`).
- **Prevenção de Erros**: Verificações robustas contra `NullPointerException` e argumentos inválidos na camada de serviço, intergradadas ao log de erros no Controller.

> **💡 Observação de Arquitetura (Algoritmos):** Para garantir o funcionamento da Busca Binária implementada, os itens são sempre ordenados em cópias locais usando a própria API de Streams (`Comparator.comparing(Task::getId)`). Após encontrar o objeto pelo UUID, um DTO customizado (`BinarySeachRenponderDTO`) carrega a tarefa e o índice devolvidos para finalização ou remoção exata.

---

## Arquitetura (MCV)

```text
┌─────────────────────────────────────────────┐
│                                             │
│                Ponto de Entrada             │
│                  (Main.java)                │
│                                             │
└──────────────────┬──────────────────────────┘
                   │
                   │  Instanciação e Chamadas Iniciais
                   │
        ┌──────────▼─────────────┐
        │                        │
        │      Controller        │
        │   (TaskController)     │
        │                        │
        │  • Intercepta Fluxo    │
        │  • Tratamento Erros    │
        │  • Logs de Ação        │
        │                        │
        └──────────┬─────────────┘
                   │
                   │  Delega Lógica de Negócio (Try/Catch)
                   │
        ┌──────────▼─────────────┐
        │                        │
        │       Services         │
        │     (TaskService)      │
        │                        │
        │  • Regras de Negócio   │
        │  • Busca Binária       │
        │  • Validação Contratos │
        │                        │
        └──────────┬─────────────┘
                   │
                   │  Acesso e Conversão de Dados
                   │
        ┌──────────▼─────────────┐
        │                        │
        │      Data Layer        │
        │ (Data / DataService)   │
        │                        │
        │  • Manipulação Jackson │
        │  • Leitura/Escrita I/O │
        │                        │
        └──────────┬─────────────┘
                   │
                   │  Leitura e Escrita Local
                   │
        ┌──────────▼─────────────┐
        │                        │
        │       JSON Local       │
        │     (tasks.json)       │
        │                        │
        └────────────────────────┘
```

---

## Instalação

### Pré-requisitos

- **Java 17+**: [Instalar Java](https://adoptium.net/)
- **Maven 3+**: [Instalar Maven](https://maven.apache.org/download.cgi)

### Clone o Repositório

```bash
git clone https://github.com/QuittoGames/To-Do-List-Java-.git
cd To-Do-List-Java-
```

### Build e Execução

**Via Terminal (Maven):**
Para baixar a dependência do Jackson Databind e compilar as classes, use:

```bash
mvn clean install
```

E para rodar diretamente pelo terminal:
```bash
mvn exec:java -Dexec.mainClass="com.project.Main"
```

**Via IDE (VS Code, IntelliJ, etc) - Recomendado:**
1. Abra a pasta do projeto clonado na sua IDE.
2. Certifique-se de que sua IDE sincronizou as dependências do `pom.xml`.
3. Navegue até o arquivo `src/main/java/com/project/Main.java`.
4. Clique em **Run** ou **Debug** no método `main`.

---

## Uso

O projeto no momento está configurado para demonstração e provas de conceito através do método `Main`. 

Para testar o fluxo completo de inicialização, criação de tarefas de teste e conclusão de uma delas, execute a classe principal em sua IDE ou via Maven:

```bash
mvn exec:java -Dexec.mainClass="com.project.Main"
```

### Fluxo demonstrado no `Main`:
1. Uma task principal é criada diretamente (com Status: `false`).
2. Adicionadas `Task 1` a `Task 4`.
3. É disparada a conclusão da tarefa recém-criada através de `controller.completeTask(id)`.
4. Os dados são sincronizados no arquivo `tasks.json` formatados em Pretty Print JSON.

---

## Organização do Projeto

Abaixo a divisão principal dos diretórios refetindo o padrão adotado:

- **`src/.../com/project/Main.java`**: Arquivo base e ponto de entrada da demonstração.
- **`src/.../Controller/`**: `TaskController.java` (Repassa as ações aos serviços, tratando e printando exceções no terminal).
- **`src/.../Services/`**: 
  - `TaskService.java`: Motor das regras do negócio, pesquisa binária e delegações.
  - `DataService.java`: Interação estrita de leitura e escrita do `tasks.json` por via da biblioteca Jackson Node/ObjectMapper.
- **`src/.../Model/`**: Entidade `Task.java` contendo as propriedades e tags `@JsonProperty`.
- **`src/.../DTO/`**: `BinarySeachRenponderDTO.java` usado como transportador de retorno em pesquisas complexas.
- **`src/.../data/`**: `Data.java` atuando como ponte da lista estática local que alimenta os serviços em memória.

---

## Licença

MIT License

---

<div align="center">

[⬆ Voltar ao topo](#todolist-java)

</div>