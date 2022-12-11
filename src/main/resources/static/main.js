window.onload = async function () {
    const response = await fetch("/api/", {
        method: "GET", headers: {"Accept": "application/json"}
    });
    if (response.ok) {
        let list = await response.json();
        for (let product of list) {
            add_product(product);
        }

        $(".remove").click(async (event) => {
            const response = await fetch(`/api/remove/${event.target.id}`, {
                method: "POST", headers: {"Accept": "application/json"}
            });
            if (response.ok) {
                window.location.href = '/';
            }
        });
        $(".update").click(async (event) => {
            await fetch(`/api/update/${event.target.id}`, {
                method: "POST", headers: {"Accept": "application/json"}
            });
        });
    }

    function add_product(product) {
        let products = document.getElementById("products");
        let child = document.createElement("div")
        child.innerHTML += `<input type="checkbox" class="update" id="${product.id}" ${product.bought ? 'checked' : ''} value="${product.bought}">`
        child.innerHTML += `${product.name} `;
        child.innerHTML += `<input type="button" class="remove" id="${product.id}" value="remove">`;
        products.appendChild(child);
    }
};