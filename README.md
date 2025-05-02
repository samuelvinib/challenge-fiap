# Tech Challenge – Sistema de Autoatendimento para Lanchonete

## Descrição

Este projeto foi desenvolvido como parte do **Tech Challenge da Fase 01**, com o objetivo de aplicar os conhecimentos adquiridos nas disciplinas do curso em um sistema backend completo. A proposta consiste em criar uma solução de autoatendimento para uma lanchonete em expansão, otimizando o fluxo de pedidos, desde a escolha dos produtos até a entrega ao cliente, com um painel administrativo para gestão do negócio.

## Objetivo

Implementar um sistema de autoatendimento de fast food que permita:
- Realização e acompanhamento de pedidos pelos clientes;
- Gestão de produtos, categorias e clientes por administradores;
- Monitoramento da preparação e entrega dos pedidos pela cozinha.

---

## Tecnologias Utilizadas

- **Linguagem:** Java
- **Framework:** Spring Boot
- **Arquitetura:** Hexagonal
- **Documentação de API:** Swagger
- **Gerenciamento de Dependências:** Maven
- **Banco de Dados:** MySQL
- **Containers:** Docker e Docker Compose

---

## Funcionalidades

### 🧾 Cliente
- Cadastro e identificação via CPF
- Montagem de pedido personalizado com:
    - Lanche
    - Acompanhamento
    - Bebida
    - Sobremesa
- Pagamento via QR Code do Mercado Pago (fake checkout)
- Acompanhamento do status do pedido:
    - Recebido
    - Em preparação
    - Pronto
    - Finalizado

### 🛠️ Administrativo
- Gestão de produtos (CRUD)
- Gestão de categorias fixas
- Gestão de clientes
- Acompanhamento de pedidos em tempo real

---

## Instalação do Projeto

> **Pré-requisitos**: Ter o Docker instalado na máquina.

### Passo 1 - Clonar o Repositório

```bash
  git clone git@github.com:samuelvinib/challenge-fiap.git
  cd challenge-fiap
```

### Passo 2 -  Configurar e iniciar os containers

```bash
docker compose up -d --build
```

A API estará disponível em:

```bash
  http://localhost:8080
```

---

## Recursos Adicionais

- **Segurança:** A API está protegida por API Key via Spring Security.
- **Documentação:** A API está documentada via Swagger.

---

## Documentação da API

Após iniciar a aplicação, a documentação da API pode ser acessada pelo Swagger em:

```bash
  http://localhost:8080/swagger-ui/index.html
```

---