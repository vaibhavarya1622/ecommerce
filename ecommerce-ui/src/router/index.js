import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/HomeView.vue'
import AddCategory from "../views/Category/AddCategory.vue";
import CategoryView from "../views/Category/CategoryView.vue";
import EditCategory from "../views/Category/EditCategory.vue";
import AddProduct from "../views/Product/AddProduct.vue";
import ProductView from "../views/Product/ProductView.vue";
import EditProduct from "../views/Product/EditProduct.vue";
import ShowDetails from "../views/Product/ShowDetails.vue";
import SignUp from "../views/SignUp.vue";
import SignIn from "../views/SignIn.vue";
import WishList from "../views/Product/WishList.vue";
import CartView from "../views/Cart/CartView.vue";
import success from "../views/payment/success.vue";
import failed from "../views/payment/failed.vue";
import CheckOut from "../views/checkout/Checkout.vue"

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/admin/category/add",
    name: "AddCategory",
    component: AddCategory,
  },
  {
    path: "/admin/category",
    name: "AdminCategory",
    component: CategoryView,
  },
  {
    path: "/admin/category/:id",
    name: "EditCategory",
    component: EditCategory
  },
  {
    path:"/admin/product/add",
    name:"AddProduct",
    component:AddProduct,
  },
  {
    path:"/admin/product",
    name:"AdminProduct",
    component:ProductView
  },
  {
    path: "/admin/product/:id",
    name: "EditProduct",
    component: EditProduct
  },
  {
    path:'/product/show/:id',
    name: "ShowDetails",
    component: ShowDetails
  },
  {
    path:'/signup',
    name:'SignUp',
    component:SignUp
  },
  {
    path:'/signin',
    name:'SignIn',
    component:SignIn
  },
  {
    path:'/wishlist',
    name:'WishList',
    component:WishList
  },
  {
    path:'/cart',
    name:'CartView',
    component:CartView
  },
  {
    path:'/payment/success',
    name:'PaymentSuccess',
    component:success
  },
  {
    path:'/payment/failed',
    name:'FailedPayment',
    component: failed
  },
  {
    path:'/checkout',
    name:'Checkout',
    component:CheckOut
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
