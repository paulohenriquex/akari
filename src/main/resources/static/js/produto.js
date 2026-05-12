document.addEventListener('DOMContentLoaded', async () => {
    try {
        const userIdInput = document.querySelector('input[name="userId"]');
        if (!userIdInput) return;

        const userId = userIdInput.value;
        const [resMarcas, resCategorias] = await Promise.all([
            fetch(`/api/marcas?userId=${userId}`),
            fetch(`/api/categorias?userId=${userId}`)
        ]);

        const marcas = await resMarcas.json();
        const categorias = await resCategorias.json();

        // Variáveis com nomes corrigidos (selectMarca / selectCategoria)
        const selectMarca = document.getElementById('marcaSelect');
        const selectCategoria = document.getElementById('categoriaSelect');

        // Limpa e preenche
        selectMarca.innerHTML = '<option value="">Selecione uma marca</option>';
        selectCategoria.innerHTML = '<option value="">Selecione uma categoria</option>';

        marcas.forEach(m => {
            selectMarca.add(new Option(m.nome, m.id));
        });

        categorias.forEach(c => {
            selectCategoria.add(new Option(c.nome, c.id));
        });

    } catch (error) {
        console.error("Erro ao carregar dados iniciais:", error);
    }
});

document.getElementById('produtoForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const msgElement = document.getElementById('mensagem');
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
        const response = await fetch('/api/produtos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const result = await response.json();
            msgElement.innerText = `Sucesso! Produto ${result.nome} cadastrado`;
            msgElement.style.color = "green";
            e.target.reset();
        } else {
            msgElement.innerText = "Erro ao cadastrar produto."
            msgElement.style.color = "red";
        }
    } catch (error) {
        msgElement.innerText = "Servidor offline ou erro de rede.";
        msgElement.style.color = "red";
    }
});