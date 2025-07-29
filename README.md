# Sistema Distribuído - Restaurante Universitário (RU)

Um sistema distribuído completo para gerenciamento de um Restaurante Universitário, desenvolvido com arquitetura de microserviços usando NestJS, Redis, MySQL e Docker, com frontend JavaFX.

## 🚀 Execução Rápida do Backend

### Pré-requisitos
- Docker e Docker Compose instalados
- Node.js 20+ (para execução local sem Docker)
- Git

### Execução com Docker (Recomendado)

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd Sistema-distribuidos-com-NestJS

# Execute todos os serviços
docker-compose up --build

# Para executar em background
docker-compose up -d --build
```

### Testando os Serviços

Após a execução, os seguintes serviços estarão disponíveis:

- **API Gateway**: http://localhost:3000
- **Cardápio**: Microserviço Redis (porta 3001 internamente)
- **CRUD Tickets**: Microserviço Redis (porta 3002 internamente)
- **CRUD Usuários**: Microserviço Redis (porta 3003 internamente)
- **CRUD Opções**: Microserviço Redis (porta 3004 internamente)
- **Relatórios**: Microserviço Redis (porta 3005 internamente)
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
  "tipo": "estudante"
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
- **Docker**: Containerização dos serviços
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

# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn javafx:run

# Alternativamente, compile e execute
mvn clean compile exec:java
```

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

#### Backend

```bash
# Para cada microserviço, execute:

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

#### Redis (necessário para microserviços)

```bash
# Com Docker
docker run -d -p 6379:6379 redis:6-alpine

# Ou instale localmente
# Windows: Baixe do site oficial
# Linux: sudo apt-get install redis-server
# macOS: brew install redis
```

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
├── docker-compose.yml    # Configuração Docker
├── Dockerfile           # Build das imagens
└── README.md           # Este arquivo
```

## 🔧 Configuração

### Variáveis de Ambiente

Crie arquivos `.env` em cada microserviço se necessário:

```env
# Exemplo para cada serviço
REDIS_HOST=localhost
REDIS_PORT=6379
PORT=3000
NODE_ENV=development
```

### Configuração de Banco de Dados

O sistema atualmente usa Redis para comunicação entre microserviços. Para adicionar MySQL:

1. Adicione o serviço MySQL ao `docker-compose.yml`
2. Configure as conexões nos microserviços
3. Execute as migrações necessárias
