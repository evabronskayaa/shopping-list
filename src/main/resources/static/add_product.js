window.onload = async function () {
    let elem = document.getElementById("add");
    elem.onclick = async function () {
        await fetch("/api/add", {
            method: "POST",
            headers: {"Accept": "application/json", "Content-Type": "application/json"},
            body: JSON.stringify({
                "name": document.getElementById("name").value
            })
        });
    }
};