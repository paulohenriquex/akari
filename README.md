# Akari - Sistema de Gestão para Negócios de Alimentação

O Akari é uma plataforma de gerenciamento completa (Backend e Frontend integrados) projetada especificamente para atender às demandas do setor de alimentação (restaurantes, buffets, cozinhas industriais, confeitarias e afins). O foco principal do sistema é o controle preciso de insumos, a engenharia de receitas (fichas técnicas), a precificação baseada em custos dinâmicos e o planejamento operacional diário.

## Arquitetura e Padrões de Projeto
O ecossistema foi estruturado seguindo as melhores práticas recomendadas para o ecossistema Spring Boot:

Camada de Apresentação (REST Controllers): Endpoints bem definidos que expõem os serviços da plataforma e gerenciam as requisições HTTP da interface visual.
Camada de Regras de Negócio (Services): Centraliza a lógica de validação de dados, integridade operacional e coordena o comportamento das transações através da anotação @Transactional.
Camada de Persistência (Repositories): Camada abstrata alimentada pelo Spring Data JPA para realizar operações seguras e automáticas no banco de dados.
Isolamento de Dados por DTOs: Uso extensivo de Java Records (RequestDTO/ResponseDTO) para assegurar que as entidades de banco de dados fiquem protegidas contra alterações indesejadas externas, além de otimizar a carga de dados na API.

## Funcionalidades Principais e Entidades
1. Sistema Multi-utilizador (Isolamento por Usuário)
O sistema opera sob a premissa de que cada estabelecimento possui seus próprios dados sigilosos. Por isso, as entidades críticas (como Produto, FichaTecnica, Marca, Categoria, Endereco e Planejamento) possuem um mapeamento direto com um usuário administrador (User):
Consultas e filtros em banco exigem o parâmetro de quem está logado (userId), inviabilizando o vazamento ou a visualização cruzada de dados.
2. Gestão de Insumos (Produtos, Marcas e Categorias)
Produtos: Cadastro detalhado das matérias-primas com tratamento de unidade de medida (ex: kg, un, l) e controle estrito de preços via BigDecimal para afastar discrepâncias financeiras em cálculos acumulados.
Marcas e Categorias: Agrupamento lógico que apoia os inventários e a tomada de decisão no estoque.
3. O Core: Ficha Técnica e Engenharia de Ingredientes
As receitas e modos de preparo são controlados de forma minuciosa por meio de uma associação Many-to-Many customizada:
Ficha Técnica: Agrega o nome do prato principal e unifica o Modo de Preparo estruturado textualmente.
Ingrediente: Atua como a tabela de ligação inteligente enriquecida. Ele relaciona cada insumo base (Produto) à respectiva receita e adiciona o valor Per Capita (quantidade exata necessária para a confecção de uma porção).
Custo Atualizado Dinamicamente: Caso o valor de mercado de um produto seja alterado, o sistema consegue recalcular instantaneamente o custo consolidado de qualquer prato associado a ele, sem que o usuário precise reeditar a receita.
4. Fornecedores e Logística de Compras
Mapeamento dos parceiros estratégicos (Fornecedor), controlando dados essenciais de comunicação corporativa, CNPJ e integração direta @OneToOne com os dados de localização (Endereco).
5. Planejamento Operacional e Escalonamento
Serviços: Cadastro de rotinas ou eventos específicos (ex: Almoço Executivo, Buffet de Casamento, Menu Degustação).
Planejamento: Liga os serviços cadastrados às fichas técnicas que serão preparadas em determinada data, definindo o quantitativo estipulado para a produção. Esta estrutura permite consolidar com antecedência as demandas exatas de compra necessárias no estoque.

## Tecnologias Utilizadas
Backend:
Java 17+
Spring Boot 3.x
Spring Data JPA
Lombok (Produtividade no encapsulamento de classes)
Jakarta Persistence / Hibernate
Frontend:
HTML5 nativo e CSS3 (Arquivos estáticos hospedados e servidos pela estrutura /static do servidor Spring).
Bootstrap 5 (Garante a fluidez de layout e responsividade para dispositivos móveis na cozinha).
Vanilla JavaScript (Uso síncrono e assíncrono do ecossistema fetch para envio/leitura de payloads JSON, isolado de bibliotecas robustas de terceiros e otimizado para injeção dinâmica de elementos no DOM, como linhas incrementais de ingredientes).

## Roadmap de Próximas Implementações
[ ] Módulo Importador de NF-e (XML): Desenvolvimento do serviço NfeService para processar arquivos XML de Notas Fiscais Eletrônicas de entrada. Essa funcionalidade irá automatizar instantaneamente o registro e a atualização de preços de custo dos insumos cadastrados a partir do CNPJ dos fornecedores.

[ ] Handler de Exceções Centralizado: Configuração de um @RestControllerAdvice corporativo para padronizar as falhas (como ResourceNotFoundException) e devolver códigos e mensagens amigáveis ao usuário final.

[ ] Segurança Avançada (Spring Security): Implementação de autenticação via JWT ou gerenciamento de sessão, eliminando a obrigatoriedade de se passar explicitamente parâmetros de userId nas URLs de requisição do frontend.