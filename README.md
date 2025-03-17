# Projeto Gestão de Pedidos API

Este projeto é uma API REST desenvolvida com Spring Boot para gerenciar pedidos, utilizando diversas tecnologias para garantir robustez, escalabilidade e segurança.

## Arquitetura

A arquitetura do projeto é baseada em microsserviços e utiliza as seguintes tecnologias:

* **Kafka**: Para mensagens assíncronas e comunicação entre serviços.
* **PostgreSQL**: Para persistência de dados relacionais.
* **Elasticsearch**: Para indexação e busca de dados em tempo real.
* **Kibana**: Para visualização e análise de dados do Elasticsearch.
* **Spring Boot**: Para o desenvolvimento da aplicação principal, que gerencia os pedidos.
* **Zookeeper**: para o gerenciamento do cluster do Kafka.

## Pré-requisitos

* Docker e Docker Compose instalados.

## Como executar

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/rddeveloper/gestao-pedidos.git
    cd <diretorio-do-projeto>
    ```

2.  **Construa a imagem da aplicação Spring Boot e Inicie a aplicação:**

    ```bash
    docker-compose up --build -d
    ```

3.  **Inicie os serviços com Docker Compose:**

    ```bash
    docker-compose up -d
    ```
    
4. **Acesse os serviços:**

    * **Aplicação Spring Boot:** `http://localhost:8080`
    * **Kibana:** `http://localhost:5601`
    * **Elasticsearch:** `http://localhost:9200`
    * **PostgreSQL:** Porta 5433 no host, conecte com usuário `postgres` e senha `admin`.


5. **Derrubar Container Docker Compose:**

    ```bash
    docker-compose down
    docker-compose down -v

## Configuração

A configuração da aplicação Spring Boot é feita através de variáveis de ambiente no arquivo `docker-compose.yml`:

* `DB_URL`: URL de conexão com o PostgreSQL.
* `DB_USER`: Nome de usuário do PostgreSQL.
* `DB_PASS`: Senha do PostgreSQL.
* `SPRING_ELASTICSEARCH_URIS`: URL de conexão com o Elasticsearch.
* `SPRING_KAFKA_BOOTSTRAP_SERVERS`: URL de conexão com o Kafka.

## Healthchecks

Cada serviço possui um healthcheck configurado no `docker-compose.yml` para garantir que o serviço esteja em execução:

* **Kafka:** Verifica se os tópicos Kafka estão acessíveis.
* **PostgreSQL:** Verifica se o serviço PostgreSQL está pronto para receber conexões.
* **Elasticsearch:** Verifica se o serviço Elasticsearch está acessível via HTTP.
* **Aplicação Spring Boot:** Verifica se o endpoint `/actuator/health` está acessível.

## Volumes

Os seguintes volumes são utilizados para persistência de dados:

* `postgres_data`: Dados do PostgreSQL.
* `elasticsearch_data`: Dados do Elasticsearch.

## Redes

Uma rede Docker chamada `my-network` é utilizada para permitir a comunicação entre os serviços.

## Dependências

* **Zookeeper**: Kafka depende do Zookeeper para o gerenciamento do cluster.
* **Kafka**: A aplicação Spring Boot depende do Kafka para a comunicação assíncrona.
* **PostgreSQL**: A aplicação Spring Boot depende do PostgreSQL para a persistência de dados.
* **Elasticsearch**: O Kibana depende do Elasticsearch para a visualização de dados. A aplicação SpringBoot depende do Elasticsearch para Indexação e busca de dados.
* **Kibana**: Depende do Elasticsearch para visualização de dados.
* **App**: Depende do postgres, elasticsearch e kafka.


## Tecnologias

* **Spring Boot (3.2.0)**: Framework Java para desenvolvimento rápido de aplicações Spring.
* **PostgreSQL**: Banco de dados relacional para persistência de dados.
* **Elasticsearch**: Motor de busca e análise distribuído para indexação e busca de dados.
* **Kafka**: Plataforma de streaming de eventos para comunicação assíncrona.
* **Flyway**: Ferramenta de migração de banco de dados.
* **Spring Cloud OpenFeign**: Cliente HTTP declarativo para comunicação entre serviços.
* **MapStruct**: Biblioteca para mapeamento entre objetos Java.
* **Spring Security & OAuth2 Resource Server**: Segurança da API com autenticação e autorização OAuth2.
* **Lombok**: Geração automática de código boilerplate.
* **SpringDoc OpenAPI**: Documentação automática da API (Swagger).
* **JWT (JSON Web Tokens)**: Autenticação e autorização.


## Configuração

A configuração da aplicação é realizada através de arquivos `application.properties` ou `application.yml`, variáveis de ambiente e arquivos de configuração específicos para cada tecnologia (e.g., Kafka, Elasticsearch).

## Documentação

A documentação da API é gerada automaticamente pelo SpringDoc OpenAPI e pode ser acessada através do endpoint `/swagger-ui.html`.

## Segurança

A API é protegida com autenticação e autorização OAuth2, utilizando JWT para troca segura de informações.

## Flyway: Migrações de Banco de Dados e Inicialização Automática

O Flyway é uma ferramenta essencial para gerenciar as alterações no esquema do banco de dados ao longo do tempo. Além de versionar e automatizar as migrações, este projeto foi configurado para que a API seja iniciada com as bases de dados (PostgreSQL e Elasticsearch) já criadas e atualizadas.

### Inicialização Automática das Bases de Dados

Ao iniciar a aplicação Spring Boot, o Flyway executa automaticamente as migrações pendentes no banco de dados PostgreSQL, garantindo que o esquema esteja sempre atualizado. Além disso, a aplicação também configura e inicializa o Elasticsearch, criando os índices necessários para a funcionalidade de busca e indexação.

### Como Funciona

1.  **Migrações PostgreSQL com Flyway:**
    * Os scripts SQL de migração são armazenados em `src/main/resources/db/migration`.
    * O Flyway aplica automaticamente as migrações pendentes ao banco de dados PostgreSQL durante a inicialização da aplicação.
    * Isso garante que o esquema do banco de dados esteja sempre na versão correta, sem intervenção manual.

2.  **Inicialização Elasticsearch:**
    * A aplicação Spring Boot configura e inicializa o cliente Elasticsearch.
    * Durante a inicialização, a aplicação verifica se os índices necessários existem no Elasticsearch.
    * Caso os índices não existam, a aplicação os cria automaticamente, garantindo que o Elasticsearch esteja pronto para uso.

### Benefícios

* **Consistência:** Garante que o banco de dados PostgreSQL e o Elasticsearch estejam sempre em um estado consistente e atualizado.
* **Automatização:** Elimina a necessidade de intervenção manual para criar ou atualizar as bases de dados.
* **Facilidade de Implantação:** Simplifica o processo de implantação, pois as bases de dados são inicializadas automaticamente com a aplicação.
* **Redução de Erros:** Minimiza o risco de erros humanos durante a criação ou atualização das bases de dados.
