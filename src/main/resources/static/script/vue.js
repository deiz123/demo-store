

Vue.component('phone-sort', {
    data: function () {
        return {
            sortControl: ""
        }
    },
    template:
       '<div class="form-group">' +
           '<select class="form-control" v-model="sortControl" @change="$root.getPhones(sortControl)">' +
               '<option value="" disabled selected>Выберете способ сортировки</option>' +
               '<option value="modelAsc">По модели (по возрастанию)</option>' +
               '<option value="modelDesc">По модели (по убыванию)</option>' +
               '<option value="memoryAsc">По объему памяти (по возрастанию)</option>' +
               '<option value="memoryDesc">По объему памяти (по убыванию)</option>' +
               '<option value="priceAsc">По цене (по возрастанию)</option>' +
               '<option value="priceDesc">По цене (по убыванию)</option>' +
           '</select>' +
       '</div>'
});

Vue.component('phone-row', {
    props: ['phone'],
    template:
        `<tr>
            <td>{{ phone.model }}</td>
            <td>{{ phone.memory }}</td>
            <td>{{ phone.price }}</td>
            <td>
                <button type="button" class="btn btn-info" @click="$root.addToCart(phone.id)">Добавить в корзину</button>
            </td>
        </tr>`
});

Vue.component('phone-list', {
    template:
        '<table class="table table-bordered products-table">' +
            '<thead>' +
                '<tr>' +
                    '<th scope="col">Модель</th>' +
                    '<th scope="col">Память (GB)</th>' +
                    '<th scope="col">Цена ($)</th>' +
                    '<th scope="col"></th>' +
                '</tr>' +
            '</thead>' +
            '<tbody>' +
                '<phone-row v-for="phone in $root.phones" :key="phone.id" :phone="phone" />' +
            '</tbody>' +
        '</table>'
});


Vue.component('shopping-cart-info', {
   template:
    `<div class="cart-info">
        <h3>Общая сумма заказа: {{ $root.calcCartSum() }} ($)</h3>
        <button type="button" class="btn btn-primary" @click="$root.createOrder()">Оформить заказ</button>
      </div>`
});

Vue.component('shopping-cart-row', {
    props: ['item'],
    template:
        `<tr>
            <td>{{ item.model }}</td>
            <td>{{ item.price }}</td>
            <td>{{ item.count }}</td>
            <td>
              <button type="button" class="btn btn-success" @click="$root.addToCart(item.phoneId)">+</button>
              <button type="button" class="btn btn-danger" @click="$root.removeFromCart(item.phoneId)">-</button>
            </td>
          </tr>`
});

Vue.component('shopping-cart-list', {
    template:
        `<div>
            <table class="table table-bordered products-table">
            <thead>
              <tr>
                <th scope="col">Модель</th>
                <th scope="col">Цена ($)</th>
                <th scope="col">Количество</th>
                <th scope="col">Действия</th>
              </tr>
            </thead>
            <tbody>
                <shopping-cart-row v-for="item in $root.cartItems" :key="item.phoneId" :item="item" />
            </tbody>
          </table>
        </div>`,
    created: function () {
        this.$root.getShoppingCartItems()
    }
});

Vue.component('city-modal', {
    data: function () {
        return {
            cityControl: this.$root.city
        }
    },
   template:
   `<div class="modal fade" id="changeCityModal" tabindex="-1" role="dialog" aria-labelledby="changeCityModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="changeCityModalLabel">Выберете город</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <select class="form-control" id="cityControl" v-model="cityControl">
                  <option value="" disabled selected>Выберете город</option>
                  <option value="Moscow">Moscow</option>
                  <option value="Saint-Petersburg">Saint-Petersburg</option>
                  <option value="Perm">Perm</option>
                </select>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
              <button type="button" class="btn btn-info" data-dismiss="modal" @click="$root.updateCityCooke(cityControl)">Обновить</button>
            </div>
          </div>
        </div>
      </div>`
});


Vue.component('navigation', {
    template:
        `<div>
            <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <router-link name="" class="nav-link" to="/">Каталог</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/cart" id="cartHolder">Корзина ({{ $root.calcCartSize() }})</router-link>
                    </li>
                </ul>
                <span class="navbar-brand mb-0" id="cityHolder"> {{ $root.city }} </span>
                <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#changeCityModal">Изменить город</button>
            </nav>
        </div>`
});
const Catalog = Vue.component('catalog', {
    template:
        `<div>
            <navigation />
            <main role="main" class="container">
                <div class="main-title">
                    <h1>Список товаров</h1>
                </div>
                <phone-sort />
                <phone-list />
            </main>
        </div>`
});
const Cart = Vue.component('cart', {
    template:
        `<div>
            <navigation />
            <main role="main" class="container">
                <div class="main-title">
                    <h1>Корзина</h1>
                </div>
                <shopping-cart-list />
                <shopping-cart-info />
            </main>
        </div>`
});

const routes = [
    { path: '/', component: Catalog },
    { path: '/cart', component: Cart }
];

const router = new VueRouter({
    routes,
    base: 'spa'
});

new Vue({
    router,
    template:
        `<div>
            <city-modal/>
            <router-view/>
        </div>`,
    el: '#app',
    data: {
        phones: [],
        cartItems: [],
        city: "",
    },
    methods: {
        getPhones: function (sort) {
            Vue.resource('/catalog/list?sort=' + sort).get().then(result =>
                result.json().then(data =>
                    this.phones = data
                )
            )
        },
        getShoppingCartItems: function () {
            Vue.resource('/cart/items').get().then(result =>
                result.json().then(data =>
                    this.cartItems = data
                )
            )
        },
        addToCart: function (phoneId) {
            Vue.resource('/cart/add').save({phoneId: phoneId}).then(res => {
                this.getShoppingCartItems()
            });
        },
        removeFromCart: function (phoneId) {
            Vue.resource('/cart/remove').save({phoneId: phoneId}).then(res => {
                this.getShoppingCartItems()
            });
        },
        createOrder: function () {
            Vue.resource('/order/create').save().then(res => {
                alert("Заказ оформлен!");
                this.getShoppingCartItems();
                this.$router.push("/");
            });
        },
        calcCartSize: function () {
            return this.cartItems.reduce((acc, item) => {
                acc += item.count;
                return acc;
            }, 0)
        },
        calcCartSum: function () {
            return this.cartItems.reduce((acc, item) => {
                acc += item.price * item.count;
                return acc;
            }, 0)
        },
        updateCityCooke: function (city) {
            Cookies.set('city', city);
            this.city = city;
        }
    },
    created: function () {
        const cityCookie = Cookies.get('city');
        if (!cityCookie) {
            this.$nextTick(function () {
                $('#changeCityModal').modal();
            });
        } else {
            this.city = cityCookie
        }
        this.getPhones("")
    }
});