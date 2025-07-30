# Sistema Distribuído - Restaurante Universitário (RU)

Um sistema distribuído completo para gerenciamento do Restaurante Universitário da UFRPE, desenvolvido com arquitetura de microsserviços usando NestJS, Redis e MySQL, com frontend JavaFX.

## 🚀 Execução do Backend

### Pré-requisitos
- Node.js 20+ (para execução local sem Docker)
- Git
- MySQL Server
- Redis

Rode os scripts .sql necessários para configurar o banco de dados
Locais:
- cardapio/src/dataBase/database-cardapio.sql
- cruds-opcoes/src/dataBase/database-opcoes.sql
- cruds-ticket/src/dataBase/database-ticket.sql
- cruds-usuario/src/dataBase/database-usuario.sql

Execute o [Redis](https://redis.io/downloads/)



```bash
# Para cada microsserviço, execute em um terminal:

# API Gateway
cd api-gateway
npm install
npm run start:dev

# Cardápio
cd cardapio
npm install
npm run start:dev

# Usuários
cd cruds-usuario
npm install
npm run start:dev

# Tickets
cd cruds-ticket
npm install
npm run start:dev

# Opções
cd cruds-opcoes
npm install
npm run start:dev

# Relatórios
cd relatorio
npm install
npm run start:dev
```

### Testando os Serviços

Após a execução, os seguintes serviços estarão disponíveis:

- **API Gateway**: http://localhost:3000
- **Cardápio**: Microserviço Redis
- **CRUD Tickets**: Microserviço Redis
- **CRUD Usuários**: Microserviço Redis
- **CRUD Opções**: Microserviço Redis
- **Relatórios**: Microserviço Redis
- **Redis**: localhost:6379

### Endpoints da API Gateway

#### Usuários
```bash
# Listar todos os usuários
GET http://localhost:3000/usuarios

# Login
POST http://localhost:3000/usuarios/signin
Content-Type: application/json
{
  "cpf": "12345678900",
  "senha": "senha123"
}

# Cadastro
POST http://localhost:3000/usuarios/signup
Content-Type: application/json
{
  "cpf": "12345678900",
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "senha123",
  "tipo": "funcionario"
}

# Buscar usuário por CPF
POST http://localhost:3000/usuarios/buscar
Content-Type: application/json
{
  "cpf": "12345678900"
}
```

#### Cardápio
```bash
# Listar cardápios
GET http://localhost:3000/cardapio

# Criar cardápio
POST http://localhost:3000/cardapio
```

#### Tickets
```bash
# Listar tickets
GET http://localhost:3000/ticket

# Criar ticket
POST http://localhost:3000/ticket
```

#### Opções de Refeição
```bash
# Listar opções
GET http://localhost:3000/opcao

# Criar opção
POST http://localhost:3000/opcao
```

#### Relatórios
```bash
# Gerar relatórios
GET http://localhost:3000/relatorio
```

## 📋 Arquitetura do Sistema

### Microserviços

1. **API Gateway** (Porta 3000)
   - Ponto de entrada único para todas as requisições
   - Roteamento para microserviços internos
   - Validação de dados

2. **Cardápio** (Microserviço Redis)
   - Gerenciamento de cardápios semanais
   - CRUD de refeições e opções

3. **CRUD Usuários** (Microserviço Redis)
   - Autenticação e autorização
   - Gerenciamento de estudantes e funcionários

4. **CRUD Tickets** (Microserviço Redis)
   - Sistema de tickets para refeições
   - Controle de disponibilidade

5. **CRUD Opções** (Microserviço Redis)
   - Gerenciamento de opções de refeição
   - Tipos de refeição (almoço, jantar, etc.)

6. **Relatórios** (Microserviço Redis)
   - Geração de relatórios de uso
   - Estatísticas do sistema

### Tecnologias Utilizadas

#### Backend
- **NestJS**: Framework Node.js para construção de aplicações escaláveis
- **Redis**: Sistema de cache e comunicação entre microserviços
- **TypeScript**: Linguagem de programação
- **Class Validator**: Validação de dados
- **RxJS**: Programação reativa

#### Frontend
- **JavaFX**: Interface gráfica desktop
- **Java 17**: Linguagem de programação
- **Maven**: Gerenciamento de dependências
- **FXML**: Definição de interfaces

## 🖥️ Execução do Frontend JavaFX

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Backend executando (ver seção anterior)

### Configuração e Execução

```bash
# Navegue para o diretório do frontend
cd RU

# Compile e rode o projeto
mvn clean compile exec:java -Dexec.mainClass=app.Aplicativo
```
Para testar a aplicação há contas pré-cadastradas diretamente no MySQL com os seguintes dados:
<br>Funcionário -> CPF: 98765432100; Senha: senhaFuncionario
<br>Estudante -> CPF: 11223344550; Senha: senhaEstudante

### Executar com Java diretamente

```bash
# Compile primeiro
mvn clean compile

# Execute a classe principal
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.media -cp target/classes app.Aplicativo
```

### Estrutura do Frontend

- **app/Aplicativo.java**: Classe principal da aplicação
- **controllers/**: Controladores das telas FXML
- **models/**: Modelos de dados (Usuario, Ticket, etc.)
- **negocio/**: Lógica de negócio
- **dados/**: Acesso a dados e repositórios
- **resources/**: Arquivos FXML e recursos

### Funcionalidades do Frontend

1. **Tela de Login**
   - Autenticação de usuários
   - Validação de CPF e senha

2. **Dashboard do Estudante**
   - Visualização de cardápio
   - Solicitação de tickets
   - Histórico de refeições

3. **Dashboard do Funcionário**
   - Gerenciamento de cardápios
   - Validação de tickets
   - Relatórios administrativos

## 🛠️ Desenvolvimento

### Execução Local (Sem Docker)


### Scripts Disponíveis

Cada microserviço NestJS possui os seguintes scripts:

```bash
npm run start          # Execução normal
npm run start:dev      # Modo desenvolvimento (watch)
npm run start:debug    # Modo debug
npm run build          # Build para produção
npm run test           # Testes unitários
npm run test:e2e       # Testes end-to-end
npm run lint           # Verificação de código
```

### Estrutura de Pastas

```
Sistema-distribuidos-com-NestJS/
├── api-gateway/           # Gateway principal
├── cardapio/             # Microserviço de cardápio
├── cruds-opcoes/         # Microserviço de opções
├── cruds-ticket/         # Microserviço de tickets
├── cruds-usuario/        # Microserviço de usuários
├── relatorio/            # Microserviço de relatórios
├── RU/                   # Frontend JavaFX
└── README.md           # Este arquivo
```
