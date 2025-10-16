# 📱 ToDoApp - Android Clean Architecture

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API%2024%2B-green.svg)](https://developer.android.com/)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-orange.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[![MVVM](https://img.shields.io/badge/Pattern-MVVM-purple.svg)](https://developer.android.com/jetpack/guide)
[![Hilt](https://img.shields.io/badge/DI-Hilt-red.svg)](https://dagger.dev/hilt/)

Um aplicativo de lista de tarefas moderno desenvolvido em Android seguindo as melhores práticas de Clean Architecture, MVVM e princípios SOLID.

## 🎯 Sobre o Projeto

O ToDoApp é uma aplicação Android completa que demonstra o uso de arquiteturas modernas e boas práticas de desenvolvimento. O projeto foi estruturado seguindo Clean Architecture com separação clara de responsabilidades entre as camadas de apresentação, domínio e dados.

### ✨ Funcionalidades

- ✅ **CRUD de Tarefas**: Criar, visualizar, editar e deletar tarefas
- 🔄 **Atualização Reativa**: Interface atualiza automaticamente com StateFlow
- 🎨 **UI Moderna**: Interface limpa e intuitiva com Material Design
- 🏗️ **Arquitetura Limpa**: Separação clara de responsabilidades
- 🧪 **Testes Abrangentes**: Testes unitários e instrumentados
- 💉 **Injeção de Dependência**: Gerenciamento automático com Hilt

## 🏗️ Arquitetura

O projeto segue **Clean Architecture** com as seguintes camadas:

```
📱 ToDoApp - Clean Architecture + MVVM + Hilt
├── 🎯 Domain Layer (Regras de Negócio)
│   ├── Model: Task
│   ├── Repository: TaskRepository (interface)
│   └── Use Cases: GetTasks, AddTask, DeleteTask, ToggleTask
├── 💾 Data Layer (Fonte de Dados)
│   ├── Room Database
│   ├── Mappers
│   └── Repository Implementation
├── 🎨 Presentation Layer (UI)
│   ├── ViewModel (MVVM)
│   ├── UI State Management
│   └── Fragments + Adapter
└── 🔧 Dependency Injection (Hilt)
    ├── Database Module
    └── Repository Module
```

### 🧩 Padrões e Tecnologias

- **Clean Architecture**: Separação clara entre camadas
- **MVVM**: Model-View-ViewModel para UI
- **Repository Pattern**: Abstração da fonte de dados
- **Use Cases**: Encapsulamento da lógica de negócio
- **Dependency Injection**: Hilt para injeção de dependências
- **Reactive Programming**: StateFlow para observação de dados
- **SOLID Principles**: Código extensível e manutenível

## 🛠️ Tecnologias Utilizadas

### Core Android
- **Kotlin** - Linguagem principal
- **Android SDK** - API 24+ (Android 7.0+)
- **View Binding** - Binding de views type-safe

### Arquitetura e Padrões
- **Clean Architecture** - Organização em camadas
- **MVVM** - Padrão de apresentação
- **Repository Pattern** - Abstração de dados
- **Use Cases** - Lógica de negócio encapsulada

### Dependências
- **Room Database** - Persistência local
- **Hilt** - Injeção de dependências
- **Navigation Component** - Navegação entre telas
- **Coroutines & Flow** - Programação assíncrona
- **Material Design** - Componentes de UI

### Testes
- **JUnit 4** - Testes unitários
- **Mockito** - Mocking de dependências
- **Espresso** - Testes de UI
- **Room Testing** - Testes de banco de dados

## 📁 Estrutura do Projeto

```
app/src/main/java/com/android/todoapp/
├── data/                           # Camada de Dados
│   ├── local/                      # Room Database
│   │   ├── AppDatabase.kt
│   │   ├── TaskDao.kt
│   │   └── TaskEntity.kt
│   ├── mapper/                     # Conversores
│   │   └── TaskMapper.kt
│   └── repository/                 # Implementação do Repository
│       └── TaskRepository.kt
├── domain/                         # Camada de Domínio
│   ├── model/                      # Modelos de domínio
│   │   └── Task.kt
│   ├── repository/                 # Interfaces
│   │   └── TaskRepository.kt
│   └── usecase/                    # Casos de uso
│       ├── GetTasksUseCase.kt
│       ├── AddTaskUseCase.kt
│       ├── DeleteTaskUseCase.kt
│       └── ToggleTaskUseCase.kt
├── presentation/                   # Camada de Apresentação
│   ├── ui/
│   │   ├── list/                   # Lista de tarefas
│   │   │   ├── TodoListFragment.kt
│   │   │   └── TodoAdapter.kt
│   │   └── form/                   # Formulário
│   │       └── TodoFormFragment.kt
│   ├── viewmodel/                  # ViewModels
│   │   ├── TodoViewModel.kt
│   │   ├── TodoUiState.kt
│   │   └── TodoUiEvent.kt
│   └── main/
│       └── MainActivity.kt
├── di/                            # Injeção de Dependência
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
└── ToDoApplication.kt             # Application class
```

## 🚀 Como Executar

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- JDK 11 ou superior
- Android SDK API 24+

## 🧪 Testes
O projeto inclui testes abrangentes:

### Testes Unitários
```bash
./gradlew test
```
- Testes de Use Cases
- Testes de ViewModel
- Testes de Repository

### Testes Instrumentados
```bash
./gradlew connectedAndroidTest
```
- Testes de UI com Espresso
- Testes de Room Database
- Testes de integração

## 📱 Screenshots

| Lista de Tarefas | Adicionar Tarefa |
|------------------|------------------|
| ![Lista](screenshots/list.png) | ![Formulário](screenshots/form.png) |

## 🎯 Principais Conceitos Demonstrados

### Clean Architecture
- **Separação de responsabilidades** entre camadas
- **Independência de frameworks** externos
- **Testabilidade** de cada componente
- **Flexibilidade** para mudanças

### SOLID Principles
- **SRP**: Cada classe tem uma única responsabilidade
- **OCP**: Aberto para extensão, fechado para modificação
- **LSP**: Substituição de Liskov respeitada
- **ISP**: Interfaces segregadas e específicas
- **DIP**: Dependência de abstrações, não implementações

### MVVM Pattern
- **ViewModel**: Gerencia estado da UI
- **LiveData/StateFlow**: Observação reativa de dados
- **Separation of Concerns**: UI separada da lógica de negócio
