function addQuantity(id, quantity) {
    var form = document.createElement("form");
    form.action = "/ITP4511_Assignment/wishList.jsp";
    form.method = "get";
    form.hidden = true;
    form.innerHTML += "<input type='hidden' name='id' id='id' value='" + id + "' />";
    form.innerHTML += "<input type='hidden' name='quantity' id='quantity' value='" + quantity + "' />";
    document.body.appendChild(form);
    form.submit();
}