# Projeto Akari

## Visão Geral

O **Akari** é um sistema voltado para pequenos restaurantes e serviços de alimentação.

O objetivo é permitir que cada empresa gerencie:

* Usuários da equipe;
* Produtos e ingredientes;
* Marcas;
* Categorias;
* Receitas;
* Planejamentos de produção;
* Serviços oferecidos.

Cada empresa possui seus próprios dados e os compartilha entre todos os usuários vinculados a ela.

---

## Regra de Negócio

A empresa é a proprietária dos dados do sistema.

Isso significa que produtos, receitas, categorias, marcas, planejamentos e demais registros não pertencem a um usuário específico, mas sim à empresa.

### Exemplo

Empresa A:

* João
* Maria
* Pedro

Todos os usuários da Empresa A conseguem visualizar e gerenciar:

* Produtos
* Marcas
* Categorias
* Receitas
* Ingredientes das receitas
* Serviços
* Planejamentos

Já a Empresa B possui seus próprios dados, totalmente isolados da Empresa A.

---

## Principais Entidades

### Empresa

Representa o cliente do sistema.

Responsável por agrupar todos os dados relacionados ao restaurante.

### Usuário

Usuários que acessam o sistema.

Atributos principais:

* Email
* Senha
* Perfil (Role)

Cada usuário pertence a uma empresa.

### Marca

Utilizada para organizar produtos por fabricante ou fornecedor.

### Categoria

Agrupa produtos semelhantes.

Exemplos:

* Carnes
* Bebidas
* Hortifruti
* Laticínios

### Produto

Representa ingredientes ou itens comprados pela empresa.

Atributos:

* Nome
* Marca
* Categoria
* Preço
* Unidade de medida

### Receita

Cadastro de preparações culinárias.

Atributos:

* Nome
* Modo de preparo

### Ingrediente da Receita

Relaciona produtos às receitas.

Atributos:

* Produto
* Receita
* Per capita (quantidade utilizada)

### Serviço

Representa um tipo de atendimento ou evento.

Exemplos:

* Almoço
* Jantar
* Coffee Break
* Evento Corporativo

### Planejamento

Utilizado para planejar a produção.

Atributos:

* Serviço
* Quantidade de pessoas
* Data

### Planejamento x Receita

Tabela responsável por relacionar quais receitas serão produzidas em determinado planejamento.

---

## Modelo Conceitual

Empresa
├── Usuários
├── Produtos
├── Marcas
├── Categorias
├── Receitas
├── Serviços
└── Planejamentos

Receita
└── Ingredientes da Receita
└── Produto

Planejamento
└── Receitas

---

## Melhoria Proposta para o Banco

Atualmente várias tabelas possuem a coluna `users_id`.

Como os dados pertencem à empresa, o ideal seria substituir essa referência por `empresa_id`.

Exemplo:

```sql
CREATE TABLE empresa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    empresa_id INT REFERENCES empresa(id),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL
);
```

Da mesma forma, as tabelas:

* marca
* categoria
* produto
* receita
* ingrediente_receita
* servico
* planejamento
* planejamento_receita

deveriam possuir `empresa_id` em vez de `users_id`.

---

## Benefícios da Abordagem

* Multiempresa (multi-tenant).
* Compartilhamento de dados entre usuários da mesma empresa.
* Isolamento completo dos dados entre empresas.
* Menor duplicação de receitas e produtos.
* Escalabilidade para atender múltiplos restaurantes.
* Modelo de permissões mais simples.
