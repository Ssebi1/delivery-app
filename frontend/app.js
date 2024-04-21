var express = require('express');
var store = require('store')

var app = express();
const { createProxyMiddleware } = require('http-proxy-middleware')
app.use(
  '/api',
  createProxyMiddleware({
    target: 'http://localhost:8080',
    changeOrigin: true,
  }),
);

app.set('views', 'views');
app.set('view engine', 'ejs');
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.use(express.static('./public'));

app.get('/dashboard', (req, res) => {
  // get products from backend
  fetch('http://localhost:8080/api/products')
    .then(res => res.json())
    .then(json => {
      res.render('dashboard', { title: 'Dashboard', products: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/login', (req, res) => {
  res.render('login', { title: 'Login' });
});

app.get('/register', (req, res) => {
  res.render('register', { title: 'Register' });
});

app.get('/products/add', (req, res) => {
  res.render('addProduct', { title: 'Add product' });
});

app.get('/contact', (req, res) => {
  res.render('contact', { title: 'Contact' });
});

app.get('/products/update/:id', (req, res) => {
  let product_id = req.params.id;
  // get product from backend
  fetch('http://localhost:8080/api/products/' + product_id)
    .then(res => res.json())
    .then(json => {
      res.render('updateProduct', { title: 'Update product', product: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/profile/:id', (req, res) => {
  let user_id = req.params.id;
  // get user from backend
  fetch('http://localhost:8080/api/users/' + user_id)
    .then(res => res.json())
    .then(json => {
      res.render('profile', { title: 'Profile', user: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/cart/:id', (req, res) => {
  let user_id = req.params.id;
  // get cart items from backend
  fetch('http://localhost:8080/api/cart/' + user_id)
    .then(res => res.json())
    .then(json => {
      res.render('cart', { title: 'Cart', cart: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/orders/:id', (req, res) => {
  let user_id = req.params.id;
  // get user from backend
  fetch('http://localhost:8080/api/users/' + user_id)
    .then(res => res.json())
    .then(json => {
      if (json.admin) {
        // get orders from backend
        fetch('http://localhost:8080/api/orders')
          .then(res => res.json())
          .then(json => {
            res.render('orders', { title: 'Orders', orders: json });
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        // get orders from backend
        fetch('http://localhost:8080/api/orders/' + user_id)
          .then(res => res.json())
          .then(json => {
            res.render('orders', { title: 'Orders', orders: json });
          })
          .catch(err => {
            console.log(err);
          });
      }
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/messages/:id', (req, res) => {
  let user_id = req.params.id;
  // get messages from backend
  fetch('http://localhost:8080/api/users/' + user_id)
    .then(res => res.json())
    .then(json => {
      if (json.admin) {
        // get orders from backend
        fetch('http://localhost:8080/api/messages')
          .then(res => res.json())
          .then(json => {
            res.render('messages', { title: 'Messages', messages: json });
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        // get orders from backend
        fetch('http://localhost:8080/api/messages/' + user_id)
          .then(res => res.json())
          .then(json => {
            res.render('messages', { title: 'Messages', messages: json });
          })
          .catch(err => {
            console.log(err);
          });
      }
    })
    .catch(err => {
      console.log(err);
    });
});

app.listen(8282, function () {
  console.log('Frontend started!');
})