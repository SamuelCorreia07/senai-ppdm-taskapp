# TaskApp 📝

Um aplicativo Android moderno para gerenciamento de tarefas, desenvolvido em **Kotlin** utilizando a arquitetura **MVVM** e persistência de dados local com **Room Database**.

Este projeto foi desenvolvido como parte da disciplina de Programação para Dispositivos Móveis (PPDM), demonstrando práticas recomendadas de desenvolvimento Android moderno.

## 📱 Funcionalidades

O aplicativo vai além de um CRUD básico, implementando recursos avançados de interface e gerenciamento de dados:

* **Adicionar Tarefas:** Criação rápida de novas tarefas com validação de campo vazio.
* **Lista Reativa:** As tarefas são exibidas e atualizadas em tempo real usando `RecyclerView` e `Flow`.
* **Marcar como Concluída:** Checkbox interativo que atualiza o status da tarefa e aplica um efeito visual (taxado) no texto.
* **Excluir Tarefa:** Botão para remover tarefas individualmente.
* **✨ Filtro de Status:** (Extensão) Filtragem dinâmica da lista entre "Todas", "Pendentes" e "Concluídas" via Spinner.
* **✨ Edição de Título:** (Extensão) Funcionalidade de clique longo (Long Press) para editar o texto de uma tarefa existente.
* **✨ Ordenação Cronológica:** (Extensão) As tarefas são ordenadas automaticamente pela data de criação (mais recentes no topo).

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes bibliotecas e ferramentas:

* **Linguagem:** [Kotlin](https://kotlinlang.org/) (v2.0.21)
* **Arquitetura:** MVVM (Model-View-ViewModel) com padrão Repository.
* **Persistência de Dados:**
    * [Room Persistence Library](https://developer.android.com/training/data-storage/room) (v2.6.1)
    * **KSP** (Kotlin Symbol Processing) para processamento de anotações (substituto moderno ao kapt).
* **Assincronismo & Reatividade:**
    * [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) para operações em background.
    * [Kotlin Flow](https://developer.android.com/kotlin/flow) e `LiveData` para fluxos de dados reativos.
* **Interface de Usuário (UI):**
    * XML Layouts com Material Design Components.
    * RecyclerView com `ListAdapter` e `DiffUtil` para atualizações eficientes.
    * ConstraintLayout.
* **Gerenciamento de Dependências:**
    * Gradle com Kotlin DSL (`build.gradle.kts`).
    * Version Catalogs (`libs.versions.toml`) para centralização de versões.

## 📂 Estrutura do Projeto

O código está organizado em pacotes seguindo a separação de responsabilidades:

```text
com.example.taskapp
├── data/              # Camada de Dados (Room)
│   ├── TaskDao.kt     # Interface de acesso ao banco (Queries SQL)
│   ├── TaskEntity.kt  # Modelo da tabela (inclui data de criação)
│   └── TaskDatabase.kt# Configuração do banco (Singleton)
├── repository/
│   └── TaskRepository.kt
├── viewmodel/         # Gerenciamento de estado da UI
│   ├── TaskViewModel.kt (Lógica de filtro e CRUD)
│   └── TaskViewModelFactory.kt
├── MainActivity.kt    # Camada de UI (Activity)
├── TaskAdapter.kt     # Adaptador da lista (RecyclerView)
└── TaskApplication.kt # Ponto de entrada (Inicialização do DB)
```

## 🚀 Como Executar o Projeto

### Pré-requisitos
* **Android Studio**.
* **JDK 11** ou superior configurado no projeto.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SamuelCorreia07/senai-ppdm-taskapp.git](https://github.com/SamuelCorreia07/senai-ppdm-taskapp.git)
    ```

2.  **Abra no Android Studio:**
    Selecione a pasta raiz do projeto.

3.  **Sincronize o Gradle:**
    Aguarde o download das dependências (incluindo o KSP).

4.  **Execute:**
    Conecte um dispositivo físico ou inicie um emulador e clique em "Run" (**Shift + F10**).

## ⚠️ Observações sobre Banco de Dados

Este projeto utiliza a estratégia de `fallbackToDestructiveMigration()`. Isso significa que, se houver alterações na estrutura da tabela (Entity) em futuras versões, o banco de dados local será limpo e recriado automaticamente para evitar erros de conflito de versão.


## 🏫 Créditos

* **Instituição:** SENAI - Serviço Nacional de Aprendizagem Industrial.
* **Curso:** Técnico em Desenvolvimento de Sistemas.
* **Disciplina:** PPDM - Programação para Dispositivos Móveis.
* **Professor:** Tércio B. Ribeiro.


**Desenvolvido por Samuel Correia Moreira**
