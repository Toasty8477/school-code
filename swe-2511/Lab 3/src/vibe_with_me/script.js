/*
* Class: SWE2511 - Layout Managers
* Vibecoded Website
* Name(s): Colin Glynn and Alex Horton
* Class Section: 111
*/

const cartItems = document.getElementById("cart-items");
const cartTotal = document.getElementById("cart-total");
let total = 0;

document.querySelectorAll(".add-to-cart").forEach(button => {
    button.addEventListener("click", () => {
        const product = button.parentElement;
        const name = product.querySelector("h3").textContent;
        const price = parseFloat(product.querySelector("p").textContent.replace(/[^0-9.]/g, ""));

        const li = document.createElement("li");
        li.textContent = `${name} - $${price.toFixed(2)}`;
        cartItems.appendChild(li);

        total += price;
        cartTotal.textContent = total.toFixed(2);
    });
});

// Search + Filter
document.getElementById("search").addEventListener("input", e => {
    const query = e.target.value.toLowerCase();
    document.querySelectorAll(".product").forEach(prod => {
        const name = prod.querySelector("h3").textContent.toLowerCase();
        prod.style.display = name.includes(query) ? "block" : "none";
    });
});

document.getElementById("filter").addEventListener("change", e => {
    const filter = e.target.value;
    document.querySelectorAll(".product").forEach(prod => {
        prod.style.display = filter === "all" || prod.dataset.category === filter ? "block" : "none";
    });
});
