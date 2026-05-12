/**
 * Capitaliza a primeira letra de uma string.
 * Exemplo: 'marca' vira 'Marca'.
 * @param {string} str A string para capitalizar.
 * @returns {string} A string com a primeira letra maiúscula.
 */
function capitalize(str) {
    if (!str) return ''; // Se a string for vazia, retorna vazio.
    return str.charAt(0).toUpperCase() + str.slice(1); // Pega a primeira letra, a torna maiúscula e junta com o resto da string.
}

/**
 * Esta é a função principal. Ela serve como um "molde" para criar páginas
 * que gerenciam listas de coisas (como marcas, categorias, etc.).
 * Você passa um objeto de configuração `config` para dizer a ela o que gerenciar.
 *
 * @param {object} config - O objeto com as informações específicas da página.
 * @param {string} config.entityName - O nome da entidade no singular (ex: 'marca').
 * @param {string} config.entityNamePlural - O nome da entidade no plural, usado na URL da API (ex: 'marcas').
 * @param {string} config.formId - O ID do formulário no HTML (ex: 'marcaForm').
 * @param {string} config.inputNameId - O ID do campo de texto para o nome (ex: 'marcaNome').
 * @param {string} config.messageDivId - O ID da área que mostra mensagens de sucesso/erro (ex: 'marcaMensagem').
 * @param {string} config.listId - O ID da lista `<ul>` que vai mostrar os itens (ex: 'listaMarcas').
 */
function setupEntityManagement(config) {
    // Desestruturação: uma forma fácil de pegar os valores de dentro do objeto 'config'.
    const {
        entityName,
        entityNamePlural,
        formId,
        inputNameId,
        messageDivId,
        listId,
    } = config;

    // 'DOMContentLoaded' é um evento que dispara quando o HTML da página foi completamente carregado.
    // Isso garante que nosso código só vai rodar quando todos os elementos (botões, formulários, etc.) já existirem.
    document.addEventListener('DOMContentLoaded', () => {
        // --- 1. PEGAR OS ELEMENTOS HTML ---
        // O código precisa "encontrar" os elementos no HTML para poder interagir com eles.

        // Pega o ID do usuário que está "escondido" na página.
        const userIdInput = document.getElementById('userId');
        if (!userIdInput) {
            console.error('Elemento com id "userId" não encontrado.');
            return; // Para a execução se não encontrar.
        }
        const userId = userIdInput.value; // Pega o valor (ex: "1")

        // Pega os outros elementos da página usando os IDs que recebemos na configuração.
        const form = document.getElementById(formId);
        const inputName = document.getElementById(inputNameId);
        const messageDiv = document.getElementById(messageDivId);
        const entityList = document.getElementById(listId);
        const apiEndpoint = `/api/${entityNamePlural}`; // Monta a URL da API, ex: "/api/marcas"

        // Verificação de segurança: se algum elemento não for encontrado, avisa no console.
        if (!form || !inputName || !messageDiv || !entityList) {
            console.error('Um ou mais IDs de elementos não foram encontrados no DOM.', { formId, inputNameId, messageDivId, listId });
            return;
        }

        // --- 2. FUNÇÃO PARA CARREGAR E EXIBIR A LISTA ---
        // Esta função busca os dados no servidor (backend) e os exibe na lista `<ul>`.
        async function loadEntities() {
            try {
                // `fetch` faz uma requisição para o servidor. `await` espera a resposta chegar.
                // A URL fica algo como: /api/marcas?userId=1
                const response = await fetch(`${apiEndpoint}?userId=${userId}`);
                if (!response.ok) {
                    // Se a resposta não for de sucesso (ex: erro 404 ou 500), lança um erro.
                    throw new Error('Falha ao carregar os dados.');
                }
                const entities = await response.json(); // Converte a resposta do servidor (JSON) para um objeto JavaScript.
                entityList.innerHTML = ''; // Limpa a lista antes de adicionar os novos itens.

                if (entities.length === 0) {
                    // Se a lista de entidades estiver vazia, mostra uma mensagem.
                    entityList.innerHTML = `<li class="list-group-item">Nenhuma ${entityName} cadastrada.</li>`;
                } else {
                    // Para cada item na lista...
                    entities.forEach(entity => {
                        const li = document.createElement('li'); // Cria um elemento <li>
                        li.className = 'list-group-item';        // Adiciona a classe do Bootstrap para estilização.
                        li.textContent = entity.nome;            // Coloca o nome da entidade dentro do <li>.
                        entityList.appendChild(li);              // Adiciona o <li> na lista `<ul>`.
                    });
                }
            } catch (error) {
                // Se qualquer coisa no bloco `try` der errado, o código pula para cá.
                entityList.innerHTML = `<li class="list-group-item text-danger">Erro ao carregar ${entityNamePlural}.</li>`;
                console.error(`Erro ao carregar ${entityNamePlural}:`, error);
            }
        }

        // --- 3. FUNÇÃO PARA QUANDO O FORMULÁRIO É ENVIADO ---
        // 'submit' é o evento que acontece quando o usuário clica no botão "Salvar" do formulário.
        form.addEventListener('submit', async (e) => {
            e.preventDefault(); // Impede que a página recarregue, que é o comportamento padrão de um formulário.

            const nome = inputName.value.trim(); // Pega o valor do campo de texto e remove espaços em branco do início e fim.
            if (!nome) {
                // Se o nome estiver vazio, mostra um aviso e não faz mais nada.
                messageDiv.innerHTML = `<div class="alert alert-warning">O nome não pode estar em branco.</div>`;
                return;
            }

            try {
                // Envia os dados para o servidor para criar uma nova entidade.
                const response = await fetch(apiEndpoint, {
                    method: 'POST', // O método POST é usado para criar novos dados.
                    headers: { 'Content-Type': 'application/json' }, // Avisa ao servidor que estamos enviando dados em formato JSON.
                    body: JSON.stringify({ nome, userId: parseInt(userId) }) // Converte o objeto JavaScript para uma string JSON.
                });

                if (response.ok) {
                    // Se a criação foi um sucesso...
                    const newEntity = await response.json(); // Pega os dados da entidade recém-criada.
                    // Mostra uma mensagem de sucesso.
                    messageDiv.innerHTML = `<div class="alert alert-success">${capitalize(entityName)} "${newEntity.nome}" cadastrada com sucesso!</div>`;
                    form.reset(); // Limpa o formulário.
                    await loadEntities(); // Recarrega a lista para mostrar o novo item.
                } else {
                    // Se o servidor retornou um erro...
                    const errorText = await response.text();
                    messageDiv.innerHTML = `<div class="alert alert-danger">Erro ao cadastrar ${entityName}: ${errorText}</div>`;
                }
            } catch (error) {
                // Se houve um erro de conexão (ex: servidor offline).
                messageDiv.innerHTML = `<div class="alert alert-danger">Erro de conexão: ${error.message}</div>`;
            }
        });

        // --- 4. CARGA INICIAL ---
        // Chama a função para carregar a lista assim que a página termina de carregar.
        loadEntities();
    });
}