# 🚀 ToDoApp Development Environment

Este repositório inclui configurações para desenvolvimento em GitHub Codespaces e VS Code Dev Containers.

## 🛠️ Ferramentas Incluídas

### Android Development
- **Android SDK** 34
- **Build Tools** 34.0.0
- **Platform Tools** mais recente
- **Android Emulator** configurado

### Development Tools
- **JDK 11** (OpenJDK)
- **Gradle** 8.0+
- **Git** configurado
- **VS Code** com extensões Kotlin

### VS Code Extensions
- Kotlin Language Support
- Gradle for Java
- Android Development
- JSON/YAML Support

## 🚀 Como Usar

### GitHub Codespaces
1. Clique em "Code" no repositório
2. Selecione "Codespaces"
3. Clique em "Create codespace on main"
4. Aguarde o ambiente ser configurado

### VS Code Dev Containers
1. Instale a extensão "Dev Containers"
2. Abra o projeto no VS Code
3. Clique em "Reopen in Container"
4. Aguarde o ambiente ser configurado

## 📱 Desenvolvimento Android

### Emulador
```bash
# Iniciar emulador
emulator -avd ToDoApp_Emulator

# Listar emuladores
emulator -list-avds
```

### Build e Testes
```bash
# Build do projeto
./gradlew build

# Testes unitários
./gradlew test

# Testes instrumentados
./gradlew connectedAndroidTest

# Lint
./gradlew lint
```

### Debug
```bash
# Instalar APK
./gradlew installDebug

# Logcat
adb logcat | grep ToDoApp
```

## 🔧 Configuração

### Variáveis de Ambiente
- `ANDROID_HOME`: `/opt/android-sdk`
- `JAVA_HOME`: `/usr/local/openjdk-11`

### Portas Expostas
- **8080**: Android Emulator
- **3000**: Development Server

## 📚 Recursos

- [Android Development Guide](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Gradle User Guide](https://docs.gradle.org/)
- [VS Code Dev Containers](https://code.visualstudio.com/docs/remote/containers)

## 🐛 Troubleshooting

### Problemas Comuns
1. **Emulador não inicia**: Verifique se o emulador está instalado
2. **Build falha**: Execute `./gradlew clean build`
3. **Dependências**: Execute `./gradlew --refresh-dependencies`

### Logs
```bash
# Logs do container
docker logs <container_id>

# Logs do Android
adb logcat
```

## 🤝 Contribuição

Para contribuir com melhorias no ambiente de desenvolvimento:
1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Faça commit das mudanças
4. Abra um Pull Request

---

**Última atualização**: Janeiro 2024
**Versão**: 1.0.0
