document.addEventListener('DOMContentLoaded', async () => {
    const userId = document.getElementById('userId').value;
    const btnAdicionar = document.getElementById('btnAdicionarIngrediente');
    const tabelaCorpo = document.querySelector('#tabelaIngredientes tbody');
    let produtosDisponiveis = [];

    // CORREÇÃO 1: Ativar o botão IMEDIATAMENTE (antes de qualquer fetch)
    btnAdicionar.addEventListener('click', adicionarLinhaIngrediente);

    // CORREÇÃO 2: Iniciar a primeira linha vazia já no carregamento
    adicionarLinhaIngrediente();

    // Buscar produtos do backend
    try {
        // CORREÇÃO: Adicionado /listar e o parâmetro ?userId=
        const response = await fetch(`/api/produtos/listar?userId=${userId}`);

        if (response.ok) {
            produtosDisponiveis = await response.json();
            // Atualiza a primeira linha
            const selectInicial = document.querySelector('.produto-select');
            if (selectInicial) atualizarSelect(selectInicial);
        } else {
            console.error("Erro na API de produtos:", response.status);
        }
    } catch (err) {
        console.error("Erro ao buscar produtos:", err);
    }

    function atualizarSelect(select) {
        const valorAtual = select.value;
        select.innerHTML = '<option value="">Selecione um produto...</option>' +
            produtosDisponiveis.map(p => `<option value="${p.id}">${p.nome}</option>`).join('');
        select.value = valorAtual;
    }

    function adicionarLinhaIngrediente() {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <select class="form-select produto-select" required>
                    <option value="">Selecione um produto...</option>
                    ${produtosDisponiveis.map(p => `<option value="${p.id}">${p.nome}</option>`).join('')}
                </select>
            </td>
            <td>
                <input type="number" step="0.001" class="form-control percapita-input" placeholder="0.000" required>
            </td>
            <td>
                <button type="button" class="btn btn-outline-danger btn-sm btn-remover">×</button>
            </td>
        `;

        tr.querySelector('.btn-remover').addEventListener('click', () => tr.remove());
        tabelaCorpo.appendChild(tr);
    }

    // Envio do Formulário
    document.getElementById('fichaForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        const ingredientes = [];
        document.querySelectorAll('#tabelaIngredientes tbody tr').forEach(linha => {
            const produtoId = linha.querySelector('.produto-select').value;
            const percapita = linha.querySelector('.percapita-input').value;

            if (produtoId && percapita) {
                ingredientes.push({
                    produtoId: parseInt(produtoId),
                    percapita: parseFloat(percapita)
                });
            }
        });

        const dados = {
            nome: document.getElementById('nomeFicha').value,
            modoDePreparo: document.getElementById('modoDePreparo').value,
            userId: parseInt(userId),
            ingredientes: ingredientes
        };

        try {
            // URL agora coincide com o @RequestMapping do Controller
            const res = await fetch('/api/fichas-tecnicas', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            const msgDiv = document.getElementById('mensagem');
            if (res.ok) {
                msgDiv.innerHTML = `<div class="alert alert-success">Receita salva com sucesso!</div>`;
                e.target.reset();
                tabelaCorpo.innerHTML = '';
                adicionarLinhaIngrediente();
            } else {
                msgDiv.innerHTML = `<div class="alert alert-danger">Erro ao salvar a receita. Verifique o console.</div>`;
            }
        } catch (err) {
            console.error("Erro na conexão:", err);
        }
    });
});