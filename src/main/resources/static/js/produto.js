document.addEventListener('DOMContentLoaded', async () => {
    try {
        const selectMarca = document.getElementById('marcaSelect');
        const selectCategoria = document.getElementById('categoriaSelect');
        const produtoForm = document.getElementById('produtoForm');
        const mensagemDiv = document.getElementById('mensagem');

        const userIdInput = document.querySelector('input[name="userId"]');
        if (!userIdInput) return;

        const userId = userIdInput.value;
        const [resMarcas, resCategorias] = await Promise.all([
            fetch(`/api/marcas?userId=${userId}`),
            fetch(`/api/categorias?userId=${userId}`)
        ]);

        const marcas = await resMarcas.json();
        const categorias = await resCategorias.json();

        // Limpa e preenche
        selectMarca.innerHTML = '<option value="">Selecione uma marca</option>';
        selectCategoria.innerHTML = '<option value="">Selecione uma categoria</option>';

        marcas.forEach(m => {
            selectMarca.add(new Option(m.nome, m.id));
        });

        categorias.forEach(c => {
            selectCategoria.add(new Option(c.nome, c.id));
        });

        // --- Lógica para o formulário de produto ---
        produtoForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const formData = new FormData(e.target);
            const data = {
                nome: formData.get('nome'),
                preco: parseFloat(formData.get('preco')),
                medida: formData.get('medida'),
                marcaId: parseInt(formData.get('marcaId')),
                categoriaId: parseInt(formData.get('categoriaId')),
                userId: parseInt(formData.get('userId'))
            };

            try {
                const response = await fetch('/api/produtos/criar', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });

                if (response.ok) {
                    const result = await response.json();
                    mensagemDiv.innerHTML = `<div class="alert alert-success">Sucesso! Produto ${result.nome} cadastrado.</div>`;
                    e.target.reset();
                } else {
                    const erro = await response.text();
                    mensagemDiv.innerHTML = `<div class="alert alert-danger">Erro ao cadastrar produto: ${erro}</div>`;
                }
            } catch (error) {
                mensagemDiv.innerHTML = `<div class="alert alert-danger">Servidor offline ou erro de rede.</div>`;
            }
        });

    } catch (error) {
        console.error("Erro ao carregar dados iniciais:", error);
        document.getElementById('mensagem').innerHTML = `<div class="alert alert-danger">Falha ao carregar marcas e categorias.</div>`;
    }

    // --- Lógica para criação "on-the-fly" ---
    const modalMarca = new bootstrap.Modal(document.getElementById('modalNovaMarca'));
    const modalCategoria = new bootstrap.Modal(document.getElementById('modalNovaCategoria'));

    document.getElementById('btnSalvarNovaMarca').addEventListener('click', () => {
        criarNovaEntidade({
            nome: document.getElementById('inputNomeNovaMarca').value,
            endpoint: '/api/marcas',
            selectElement: document.getElementById('marcaSelect'),
            modal: modalMarca,
            mensagemElement: document.getElementById('mensagemModalMarca'),
            nomeEntidade: 'Marca'
        });
    });

    document.getElementById('btnSalvarNovaCategoria').addEventListener('click', () => {
        criarNovaEntidade({
            nome: document.getElementById('inputNomeNovaCategoria').value,
            endpoint: '/api/categorias',
            selectElement: document.getElementById('categoriaSelect'),
            modal: modalCategoria,
            mensagemElement: document.getElementById('mensagemModalCategoria'),
            nomeEntidade: 'Categoria'
        });
    });
});

/**
 * Função genérica para criar uma nova entidade (Marca ou Categoria) a partir de um modal.
 * @param {object} config - Objeto de configuração.
 */
async function criarNovaEntidade(config) {
    const { nome, endpoint, selectElement, modal, mensagemElement, nomeEntidade } = config;
    const userId = document.querySelector('input[name="userId"]').value;

    if (!nome.trim()) {
        mensagemElement.innerHTML = `<div class="alert alert-warning">O nome não pode ser vazio.</div>`;
        return;
    }

    try {
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nome, userId: parseInt(userId) })
        });

        if (response.ok) {
            const novaEntidade = await response.json();
            const novaOpcao = new Option(novaEntidade.nome, novaEntidade.id, true, true);
            selectElement.add(novaOpcao);
            modal.hide();
            document.getElementById(`inputNomeNova${nomeEntidade}`).value = ''; // Limpa o input do modal
        } else {
            mensagemElement.innerHTML = `<div class="alert alert-danger">Erro ao salvar.</div>`;
        }
    } catch (error) {
        mensagemElement.innerHTML = `<div class="alert alert-danger">Erro de conexão.</div>`;
    }
}