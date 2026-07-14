
<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=java" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?logo=spring" alt="Spring Boot 3.5">
  <img src="https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql" alt="PostgreSQL 17">
  <img src="https://img.shields.io/badge/React-19-61DAFB?logo=react" alt="React 19">
  <img src="https://img.shields.io/badge/license-MIT-green" alt="MIT License">
</p>

<h1 align="center">🍽️ Akari ERP</h1>

<p align="center">
  <strong>Sistema de Gestão de Produção Alimentícia para Buffets e Restaurantes</strong>
  <br>
  Planeje cardápios, calcule custos por pessoa, e gere listas de compras em Excel — tudo em um só lugar.
</p>

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Funcionalidades](#-funcionalidades)
- [Stack Tecnológica](#-stack-tecnológica)
- [Arquitetura](#-arquitetura)
- [Modelagem de Dados](#-modelagem-de-dados)
- [Começando](#-começando)
  - [Pré-requisitos](#pré-requisitos)
  - [Configuração](#configuração)
  - [Execução](#execução)
- [API REST](#-api-rest)
- [Frontend](#-frontend)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Roadmap](#-roadmap)
- [Licença](#-licença)

---

## 🎯 Visão Geral

**Akari** é um ERP voltado para o segmento de alimentação — buffets, restaurantes, cozinhas industriais e serviços de coffee break. O sistema resolve o problema real de **planejamento de produção**: dado um cardápio e o número de convidados, ele calcula automaticamente as quantidades exatas de cada ingrediente, o custo por pessoa, e gera uma lista de compras pronta para o mercado.

O nome **Akari** (明かり) vem do japonês e significa "luz" — a clareza que o sistema traz para a gestão da produção.

> ⚡ Projeto construído como portfólio para demonstrar proficiência em **Java 21**, **Spring Boot**, **PostgreSQL**, e integração **REST + React**.

---

## ✨ Funcionalidades

### 📦 Gestão de Produtos e Insumos
- Cadastro de produtos com **marca**, **categoria** e **unidade de medida**
- Controle de **preços** e histórico de movimentação de estoque
- Organização hierárquica de insumos

### 📖 Receitas e Ingredientes
- Cadastro de **receitas** com modo de preparo detalhado
- Definição de ingredientes com **quantidade per capita**
- Cálculo automático do **custo por pessoa** de cada receita

### 📅 Planejamento de Produção
- Criação de **planejamentos diários** vinculados a serviços (Almoço, Coffee Break, etc.)
- Associação de receitas a cada serviço com **quantitativo de pessoas**
- Resumo consolidado de **todos os ingredientes** necessários para o dia

### 📊 Geração de Lista de Pedido (Excel)
- Planilha `.xlsx` com duas abas:
  1. **Detalhamento**: receitas, ingredientes, quantidades e custos por serviço
  2. **Resumo para Compras**: agrupamento de ingredientes com quantidades totais e custo geral
- Formatação profissional (cabeçalhos, cores, formato monetário `R$`)

### 🔐 Autenticação e Segurança
- Login com **JWT** (JSON Web Tokens)
- **BCrypt** para hash de senhas
- **Roles**: ADMIN, CHEF, NUTRICIONISTA
- Arquitetura **multi-tenant** por empresa
- Sessão **stateless** com Spring Security

### 👥 Multi-Tenancy
- Dados isolados por **empresa**
- Usuários vinculados a uma empresa
- Categorias, marcas e receitas com escopo por empresa

---

## 🛠 Stack Tecnológica

### Backend

| Tecnologia | Versão | Finalidade |
|---|---|---|
| **Java** | 21 (LTS) | Linguagem principal |
| **Spring Boot** | 3.5 | Framework de aplicação |
| **Spring Data JPA / Hibernate** | — | ORM e persistência |
| **Spring Security** | — | Autenticação e autorização |
| **Spring Validation** | — | Validação de dados de entrada |
| **PostgreSQL** | 17 | Banco de dados relacional |
| **Flyway** | — | Migração e versionamento de schema |
| **JWT (jjwt)** | 0.12.6 | Autenticação stateless |
| **Apache POI** | 5.3.0 | Geração de planilhas Excel |
| **Lombok** | — | Redução de boilerplate |
| **Maven** | — | Gerenciamento de dependências e build |
| **Docker Compose** | — | Orquestração do banco de dados |

### Frontend

| Tecnologia | Versão |
|---|---|
| **React** | 19 |
| **TypeScript** | 6.0 |
| **Vite** | 8.1 |
| **Tailwind CSS** | 4.3 |
| **React Router** | 7.18 |
| **Axios** | 1.18 |
| **Lucide React** | 1.21 (ícones) |

### Ferramentas

- **VS Code / Cursor** — desenvolvimento
- **Insomnia / Postman** — testes de API
- **Git** — versionamento

---

## 🏗 Arquitetura

O backend segue uma arquitetura em **camadas** com responsabilidades bem definidas:

```
┌─────────────────────────────────────────────────────┐
│                   Frontend (React)                   │
│              localhost:5173  →  REST API             │
└──────────────────┬──────────────────────────────────┘
                   │  HTTP (JSON)
┌──────────────────▼──────────────────────────────────┐
│              Controller Layer (REST)                 │
│   AuthController, PlanejamentoController, ...        │
├─────────────────────────────────────────────────────┤
│               Service Layer (Regras de Negócio)       │
│   PlanejamentoService, ProdutoService, ...           │
├─────────────────────────────────────────────────────┤
│              Repository Layer (JPA/Hibernate)         │
│   PlanejamentoRepository, ProdutoRepository, ...     │
├─────────────────────────────────────────────────────┤
│                  Data Layer (PostgreSQL)              │
│              Flyway migrations → Schema              │
└─────────────────────────────────────────────────────┘
```

### 🔒 Segurança

```
Request → JwtAuthFilter → SecurityConfig → Controller
             │
             ├─ 🟢 /api/auth/** → liberado
             └─ 🔒 demais → JWT obrigatório
```

- Filtro customizado `JwtAuthFilter` extrai e valida o token JWT
- Senhas hasheadas com `BCryptPasswordEncoder`
- CORS configurado para desenvolvimento

---

## 🗄 Modelagem de Dados

O banco possui **13 tabelas** relacionadas, projetadas para atender ao domínio de produção alimentícia:

```
empresa ──┬── users
          ├── categoria
          ├── marca
          ├── servico
          ├── produto ──┬── unidade_medida
          │              └── movimento_estoque
          ├── receita ──── ingrediente_receita ──── produto
          └── planejamento ─── planejamento_servico ──── planejamento_receita ──── receita
```

### Principais Entidades

| Entidade | Descrição |
|---|---|
| `empresa` | Raiz multi-tenant — isola dados de cada cliente |
| `users` | Usuários com roles (ADMIN, CHEF, NUTRICIONISTA) |
| `produto` | Insumos comprados (matéria-prima) |
| `receita` | Preparos internos com modo de preparo |
| `ingrediente_receita` | Relacionamento N:N produto ↔ receita + quantidade per capita |
| `planejamento` | Plano de produção para uma data específica |
| `movimento_estoque` | Registro de entrada/saída de produtos |

> 📄 Consulte [`MODELAGEM.md`](MODELAGEM.md) para o diagrama completo e scripts SQL.

---

## 🚀 Começando

### Pré-requisitos

- **Java 21** (JDK)
- **Maven** (ou use o wrapper `./mvnw`)
- **Docker** e **Docker Compose**
- **Node.js** 20+ (para o frontend)

### Configuração

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/akari.git
cd akari

# 2. Inicie o banco PostgreSQL com Docker
docker compose up -d

# 3. Configure as variáveis de ambiente (opcional — valores default funcionam)
cp src/main/resources/application-example.properties src/main/resources/application.properties
# Edite application.properties se necessário
```

### Execução

#### Backend

```bash
# Com Maven Wrapper
./mvnw spring-boot:run

# Ou compile e execute o JAR
./mvnw clean package -DskipTests
java -jar target/akari-0.0.1-SNAPSHOT.jar
```

O servidor iniciará em **`http://localhost:8080`**.
As migrations do Flyway serão executadas automaticamente na primeira inicialização.

#### Frontend

```bash
cd frontend
npm install
npm run dev
```

O frontend iniciará em **`http://localhost:5173`**.

> 💡 O frontend espera a API em `http://localhost:8080` (configurado via proxy do Vite).

---

## 📡 API REST

### Autenticação

```
POST /api/auth/login
Body: { "email": "...", "password": "..." }
Response: { "token": "eyJhbGci...", "email": "...", "role": "..." }
```

### Endpoints Principais

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/auth/login` | Autenticação de usuário |
| `GET` | `/api/empresas` | Listar empresas |
| `POST` | `/api/empresas` | Criar empresa |
| `GET` | `/api/categorias?empresaId={id}` | Listar categorias |
| `POST` | `/api/categorias` | Criar categoria |
| `GET` | `/api/marcas?empresaId={id}` | Listar marcas |
| `POST` | `/api/marcas` | Criar marca |
| `GET` | `/api/produtos?empresaId={id}` | Listar produtos |
| `POST` | `/api/produtos` | Criar produto |
| `GET` | `/api/servicos?empresaId={id}` | Listar serviços |
| `GET` | `/api/receitas?empresaId={id}` | Listar receitas |
| `POST` | `/api/receitas` | Criar receita |
| `GET` | `/api/planejamentos?empresaId={id}` | Listar planejamentos |
| `POST` | `/api/planejamentos` | Criar planejamento |
| `GET` | `/api/planejamentos/{id}/lista-pedido` | Lista de pedido (JSON) |
| `GET` | `/api/planejamentos/{id}/lista-pedido/excel` | Download da lista em Excel |

> ⚠️ A maioria dos endpoints exige header `Authorization: Bearer <token>`.

---

## 🖥 Frontend

O frontend é uma **SPA moderna** construída com React + TypeScript + Tailwind CSS.

```bash
cd frontend
npm run dev      # Desenvolvimento
npm run build    # Produção → saída em dist/
```

### Tecnologias do Frontend

- **Vite** — build rápido e HMR
- **React Router** — navegação SPA
- **Axios** — chamadas HTTP para API
- **Tailwind CSS** — estilização utility-first
- **Lucide** — ícones consistentes

---

## 📁 Estrutura do Projeto

```
akari/
├── pom.xml                         # Dependências Maven
├── docker-compose.yaml             # PostgreSQL 17
├── MODELAGEM.md                    # Modelagem completa do banco
├── HELP.md                         # Docs de referência (Spring Boot)
│
├── src/main/java/com/project/akari/
│   ├── AkariApplication.java       # Entry point Spring Boot
│   │
│   ├── auth/                       # Autenticação JWT
│   │   ├── AuthController.java
│   │   ├── AuthRequest/Response.java
│   │   ├── JwtUtil.java            # Geração/validação de tokens
│   │   └── JwtAuthFilter.java      # Filtro de segurança
│   │
│   ├── config/
│   │   ├── SecurityConfig.java     # Spring Security
│   │   └── DataInitializer.java    # Seeds iniciais
│   │
│   ├── categoria/                  # CRUD categorias
│   ├── empresa/                    # CRUD empresas (multi-tenant)
│   ├── marca/                      # CRUD marcas
│   ├── produto/                    # CRUD produtos
│   ├── servico/                    # CRUD serviços
│   ├── receita/                    # CRUD receitas
│   ├── ingrediente/                # Ingredientes das receitas
│   ├── planejamento/               # Core: planejamento + lista de pedido
│   │   ├── PlanejamentoController.java
│   │   ├── PlanejamentoService.java
│   │   └── ExcelExportService.java # Geração de planilhas
│   └── exception/
│       └── GlobalExceptionHandler.java
│
├── src/main/resources/
│   ├── application-example.properties
│   ├── db/migration/
│   │   └── V1__schema_inicial.sql  # Flyway migration
│   └── static/                     # Assets estáticos
│
├── frontend/
│   ├── package.json                # Dependências React
│   ├── vite.config.ts              # Configuração Vite
│   ├── tsconfig.json               # TypeScript
│   └── src/
│       ├── App.tsx                 # Componente raiz
│       ├── api.ts                  # Cliente Axios
│       ├── pages/                  # Páginas da aplicação
│       └── components/             # Componentes reutilizáveis
│
└── .gitignore
```

---

## 🗺 Roadmap

- [x] Cadastro de produtos, marcas e categorias
- [x] Cadastro de receitas com ingredientes per capita
- [x] Planejamento de produção por data
- [x] Geração de lista de pedido em Excel
- [x] Autenticação JWT
- [x] Multi-tenancy
- [ ] Dashboard com gráficos de custos
- [ ] Controle de estoque (entrada/saída completo)
- [ ] Histórico de preços de produtos
- [ ] Integração com nota fiscal eletrônica (NF-e)
- [ ] Comparação de custos entre períodos
- [ ] Testes de integração
- [ ] Documentação OpenAPI/Swagger
- [ ] Deploy com Docker multi-stage

---

## 📄 Licença

Distribuído sob a licença **MIT**. Veja `LICENSE` para mais informações.

---

<p align="center">
  Feito com ☕ e 🍽️ por <a href="https://github.com/seu-usuario">Paulo</a>
  <br>
  <sub>Akari — Iluminando sua produção alimentícia</sub>
</p>
