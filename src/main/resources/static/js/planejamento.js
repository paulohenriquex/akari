document.addEventListener('DOMContentLoaded', async () => {
    const userId = document.getElementById('userId').value;
    const form = document.getElementById('planejamentoForm');
    const servicoSelect = document.getElementById('servicoSelect');
    const buscaFichaTecnica = document.getElementById('buscaFichaTecnica');
    const listaFichas = document.getElementById('listaFichas');
    const fichasSelecionadas = document.getElementById('fichasSelecionadas');
    const mensagem = document.getElementById('mensagem');
    const listaComprasResumo = document.getElementById('listaComprasResumo');
    const listaPlanejamentos = document.getElementById('listaPlanejamentos');
    let fichasDisponiveis = [];
    const fichaTecnicaIdsSelecionadas = new Set();

    const moeda = new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL'
    });

    await carregarDadosIniciais();
    await carregarPlanejamentos();

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const fichaTecnicaIds = Array.from(fichaTecnicaIdsSelecionadas);

        if (fichaTecnicaIds.length === 0) {
            mensagem.innerHTML =
                '<div class="alert alert-warning">Selecione pelo menos uma ficha técnica.</div>';
            return;
        }

        const dados = {
            data: document.getElementById('data').value,
            servicoId: parseInt(servicoSelect.value),
            fichaTecnicaIds,
            quantidadePessoas: parseInt(
                document.getElementById('quantidadePessoas').value
            ),
            userId: parseInt(userId)
        };

        try {
            const response = await fetch('/api/planejamentos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dados)
            });

            if (!response.ok) {
                const erro = await response.text();
                throw new Error(erro || 'Erro ao salvar planejamento.');
            }

            const planejamento = await response.json();
            mensagem.innerHTML =
                '<div class="alert alert-success">Planejamento salvo com sucesso.</div>';
            form.reset();
            limparSelecaoFichas();
            renderizarListaCompras(planejamento.listaDeCompras);
            await carregarPlanejamentos();
        } catch (error) {
            mensagem.innerHTML = `<div class="alert alert-danger">${escapeHtml(error.message)}</div>`;
        }
    });

    buscaFichaTecnica.addEventListener('input', () => renderizarFichas());

    async function carregarDadosIniciais() {
        try {
            const [servicosResponse, fichasResponse] = await Promise.all([
                fetch('/api/servicos'),
                fetch(`/api/fichas-tecnicas?userId=${userId}`)
            ]);

            if (!servicosResponse.ok || !fichasResponse.ok) {
                throw new Error('Falha ao carregar dados iniciais.');
            }

            const servicos = await servicosResponse.json();
            fichasDisponiveis = await fichasResponse.json();

            preencherServicos(servicos);
            renderizarFichasSelecionadas();
            renderizarFichas();
        } catch (error) {
            mensagem.innerHTML = `<div class="alert alert-danger">${escapeHtml(error.message)}</div>`;
        }
    }

    async function carregarPlanejamentos() {
        try {
            const response = await fetch(`/api/planejamentos?userId=${userId}`);

            if (!response.ok) {
                throw new Error('Falha ao carregar planejamentos.');
            }

            const planejamentos = await response.json();
            renderizarPlanejamentos(planejamentos);
        } catch (error) {
            listaPlanejamentos.innerHTML =
                '<div class="list-group-item text-danger">Erro ao carregar planejamentos.</div>';
        }
    }

    function preencherServicos(servicos) {
        servicoSelect.innerHTML =
            '<option value="">Selecione um serviço</option>';

        servicos.forEach((servico) => {
            servicoSelect.add(new Option(servico.nome, servico.id));
        });
    }

    function renderizarFichas() {
        listaFichas.innerHTML = '';
        const termo = buscaFichaTecnica.value.trim().toLowerCase();
        const fichasFiltradas = fichasDisponiveis.filter((ficha) =>
            ficha.nome.toLowerCase().includes(termo)
        );

        if (fichasDisponiveis.length === 0) {
            listaFichas.innerHTML =
                '<div class="list-group-item text-muted">Nenhuma ficha técnica cadastrada.</div>';
            return;
        }

        if (fichasFiltradas.length === 0) {
            listaFichas.innerHTML =
                '<div class="list-group-item text-muted">Nenhuma ficha técnica encontrada.</div>';
            return;
        }

        fichasFiltradas.forEach((ficha) => {
            const selecionada = fichaTecnicaIdsSelecionadas.has(ficha.id);
            const item = document.createElement('button');
            item.type = 'button';
            item.className = `list-group-item list-group-item-action d-flex justify-content-between align-items-center gap-3 ${
                selecionada ? 'active' : ''
            }`;
            item.innerHTML = `
                <span class="text-start">${escapeHtml(ficha.nome)}</span>
                <span class="badge ${
                    selecionada ? 'text-bg-light' : 'text-bg-primary'
                }">${selecionada ? 'Selecionada' : 'Adicionar'}</span>
            `;
            item.addEventListener('click', () => alternarFicha(ficha.id));
            listaFichas.appendChild(item);
        });
    }

    function alternarFicha(fichaId) {
        if (fichaTecnicaIdsSelecionadas.has(fichaId)) {
            fichaTecnicaIdsSelecionadas.delete(fichaId);
        } else {
            fichaTecnicaIdsSelecionadas.add(fichaId);
        }

        renderizarFichasSelecionadas();
        renderizarFichas();
    }

    function renderizarFichasSelecionadas() {
        fichasSelecionadas.innerHTML = '';

        if (fichaTecnicaIdsSelecionadas.size === 0) {
            fichasSelecionadas.innerHTML =
                '<span class="small text-muted">Nenhuma ficha selecionada.</span>';
            return;
        }

        fichasDisponiveis
            .filter((ficha) => fichaTecnicaIdsSelecionadas.has(ficha.id))
            .forEach((ficha) => {
                const badge = document.createElement('span');
                badge.className =
                    'badge text-bg-secondary d-inline-flex align-items-center gap-2';
                badge.innerHTML = `
                    <span>${escapeHtml(ficha.nome)}</span>
                    <button
                        type="button"
                        class="btn-close btn-close-white"
                        aria-label="Remover ${escapeHtml(ficha.nome)}"
                    ></button>
                `;
                badge
                    .querySelector('button')
                    .addEventListener('click', () => alternarFicha(ficha.id));
                fichasSelecionadas.appendChild(badge);
            });
    }

    function renderizarListaCompras(listaDeCompras) {
        if (!listaDeCompras || listaDeCompras.itens.length === 0) {
            listaComprasResumo.innerHTML =
                '<div class="text-muted">Nenhum item calculado.</div>';
            return;
        }

        const linhas = listaDeCompras.itens
            .map((item) => `
                <tr>
                    <td>${escapeHtml(item.nomeProduto)}</td>
                    <td>${formatarQuantidade(item.quantidade)} ${escapeHtml(item.medida)}</td>
                    <td class="text-end">${moeda.format(item.custoItem)}</td>
                </tr>
            `)
            .join('');

        listaComprasResumo.innerHTML = `
            <div class="table-responsive">
                <table class="table table-sm align-middle">
                    <thead>
                        <tr>
                            <th>Produto</th>
                            <th>Quantidade</th>
                            <th class="text-end">Custo</th>
                        </tr>
                    </thead>
                    <tbody>${linhas}</tbody>
                    <tfoot>
                        <tr>
                            <th colspan="2">Total</th>
                            <th class="text-end">
                                ${moeda.format(listaDeCompras.custoTotalPlanejamento)}
                            </th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        `;
    }

    function renderizarPlanejamentos(planejamentos) {
        listaPlanejamentos.innerHTML = '';

        if (planejamentos.length === 0) {
            listaPlanejamentos.innerHTML =
                '<div class="list-group-item text-muted">Nenhum planejamento cadastrado.</div>';
            return;
        }

        planejamentos.forEach((planejamento) => {
            const fichas = planejamento.fichasTecnicas
                .map((ficha) => ficha.nome)
                .join(', ');

            const item = document.createElement('div');
            item.className = 'list-group-item';
            item.innerHTML = `
                <div class="d-flex justify-content-between gap-3">
                    <div>
                        <div class="fw-bold">
                            ${formatarData(planejamento.data)} - ${escapeHtml(planejamento.servico.nome)}
                        </div>
                        <div class="small text-muted">
                            ${planejamento.quantidadePessoas} pessoas
                        </div>
                        <div class="small">${escapeHtml(fichas)}</div>
                    </div>
                    <div class="text-end fw-bold">
                        ${moeda.format(planejamento.listaDeCompras.custoTotalPlanejamento)}
                    </div>
                </div>
            `;
            item.addEventListener('click', () =>
                renderizarListaCompras(planejamento.listaDeCompras)
            );
            listaPlanejamentos.appendChild(item);
        });
    }

    function limparSelecaoFichas() {
        fichaTecnicaIdsSelecionadas.clear();
        buscaFichaTecnica.value = '';
        renderizarFichasSelecionadas();
        renderizarFichas();
    }

    function formatarQuantidade(valor) {
        return Number(valor).toLocaleString('pt-BR', {
            maximumFractionDigits: 3
        });
    }

    function formatarData(data) {
        return new Date(`${data}T00:00:00`).toLocaleDateString('pt-BR');
    }

    function escapeHtml(valor) {
        return String(valor)
            .replaceAll('&', '&amp;')
            .replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;')
            .replaceAll('"', '&quot;')
            .replaceAll("'", '&#039;');
    }
});
