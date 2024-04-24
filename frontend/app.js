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

app.get('/', (req, res) => {
   res.redirect('/dashboard')
});

app.get('/dashboard', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }

    page_no = req.query.page;
  if (page_no === undefined) {
    page_no = 0;
  }

  sort_by = req.query.sort_by;
  if (sort_by === undefined) {
      sort_by='name'
  }

  sort_order = req.query.sort_order;
    if (sort_order === undefined) {
        sort_order='asc'
    }

    // get products from backend
  fetch(
      'http://localhost:8080/api/products?page=' + page_no + '&sortBy=' + sort_by + '&sortOrder=' + sort_order,
      {headers: {"Authorization": "Bearer " + token}})
    .then(res => res.json())
    .then(json => {
      res.render('dashboard', { title: 'Dashboard', products: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/login', (req, res) => {
    token = store.get('token');
    if (token !== undefined) {
        res.redirect('/dashboard')
    }
  res.render('login', { title: 'Login' });
});

app.get('/login/save/:token', (req, res) => {
    token = req.params.token;
    store.set('token', token);
    res.redirect('/dashboard')
});

app.get('/logout', (req, res) => {
    store.remove('token');
    res.redirect('/login')
});

app.get('/register', (req, res) => {
    token = store.get('token');
    if (token !== undefined) {
        res.redirect('/dashboard')
    }
  res.render('register', { title: 'Register' });
});

app.get('/products/add', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  res.render('addProduct', { title: 'Add product' });
});

app.get('/contact', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  res.render('contact', { title: 'Contact' });
});

app.get('/products/update/:id', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  let product_id = req.params.id;
  // get product from backend
  fetch('http://localhost:8080/api/products/' + product_id,
      {headers: {"Authorization": "Bearer " + token, "Origin": "http://localhost:8282"}})
    .then(res => res.json())
    .then(json => {
        console.log(json)
      res.render('updateProduct', { title: 'Update product', product: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/profile/:id', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  let user_id = req.params.id;
  // get user from backend
  fetch('http://localhost:8080/api/users/' + user_id,
      {headers: {"Authorization": "Bearer " + token}})
    .then(res => res.json())
    .then(json => {
      res.render('profile', { title: 'Profile', user: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/cart/:id', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  let user_id = req.params.id;
  // get cart items from backend
  fetch('http://localhost:8080/api/cart/' + user_id,
      {headers: {"Authorization": "Bearer " + token}})
    .then(res => res.json())
    .then(json => {
      res.render('cart', { title: 'Cart', cart: json });
    })
    .catch(err => {
      console.log(err);
    });
});

app.get('/orders/:id', (req, res) => {
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  let user_id = req.params.id;
  // get user from backend
  fetch('http://localhost:8080/api/users/' + user_id,
      {headers: {"Authorization": "Bearer " + token}})
    .then(res => res.json())
    .then(json => {
      if (json.admin) {
        // get orders from backend
        fetch('http://localhost:8080/api/orders/admin',
            {headers: {"Authorization": "Bearer " + token}})
          .then(res => res.json())
          .then(json => {
            res.render('orders', { title: 'Orders', orders: json });
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        // get orders from backend
        fetch('http://localhost:8080/api/orders/' + user_id,
            {headers: {"Authorization": "Bearer " + token}})
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
    token = store.get('token');
    if (token === undefined) {
        res.redirect('/login')
    }
  let user_id = req.params.id;
  // get messages from backend
  fetch('http://localhost:8080/api/users/' + user_id,
      {headers: {"Authorization": "Bearer " + token}})
    .then(res => res.json())
    .then(json => {
      if (json.admin) {
        // get orders from backend
        fetch('http://localhost:8080/api/messages/admin',
            {headers: {"Authorization": "Bearer " + token}})
          .then(res => res.json())
          .then(json => {
            res.render('messages', { title: 'Messages', messages: json });
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        // get orders from backend
        fetch('http://localhost:8080/api/messages/' + user_id,
            {headers: {"Authorization": "Bearer " + token}})
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