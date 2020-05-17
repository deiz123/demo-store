function sort(value) {
    window.location.href = "?sort=" + value;
}

function updateCity() {
    const city = $('#cityControl').val();
    $.cookie('city', city);
    $('#cityHolder').text(city);
    $('#changeCityModal').modal('hide');
}

function addToShoppingCart(phoneId) {
    $.ajax({
        type: "POST",
        url: "/cart/add",
        data: JSON.stringify({
            phoneId: phoneId
        }),
        success: function () {
            updateCartSize();
        },
        contentType: 'application/json'
    });
}

function updateCartSize() {
    $.post("/cart/size").done(function (data) {
        $('#cartHolder').text("Корзина (" + data + ")");
    });
}

function updateOrInitCity() {
    const cityCookie = $.cookie('city');
    if (cityCookie == null) {
        $('#changeCityModal').modal();
    } else {
        $('#cityControl').val(cityCookie);
        $('#cityHolder').text(cityCookie);
    }
}

function addToCartAndUpdate(phoneId) {
    $.ajax({
        type: "POST",
        url: "/cart/add",
        data: JSON.stringify({
            phoneId: phoneId
        }),
        success: function () {
            location.reload();
        },
        contentType: 'application/json'
    });
}

function removeFromCartAndUpdate(phoneId) {
    $.ajax({
        type: "POST",
        url: "/cart/remove",
        data: JSON.stringify({
            phoneId: phoneId
        }),
        success: function () {
            location.reload();
        },
        contentType: 'application/json'
    });
}

$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has("sort")) {
        $('#sortControl').val(urlParams.get("sort"))
    }

    updateOrInitCity();
    updateCartSize();
});
