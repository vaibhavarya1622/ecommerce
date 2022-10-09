<template>
  <div id="app">
    <div id="nav">
      <NavbarComponent />
    </div>
    <div style="min-height: 60vh">
      <router-view v-if="products && categories"
         :baseURL="baseURL"
         :products="products"
         :categories="categories"
         @fetchData = "fetchData">
      </router-view>
    </div>
    <FooterComponent />
  </div>
</template>

<script>

const axios = require('axios');
import NavbarComponent from "./components/NavbarComponent.vue"
import FooterComponent from "./components/FooterComponent.vue"

export default {
  data() {
    return {
      baseURL : "http://localhost:8080/",
      products : null,
      categories : null
    }
  },

  components : { NavbarComponent, FooterComponent},
  methods : {
    async fetchData() {
      // fetch products
      await axios.get(this.baseURL + "product/")
              .then(res => this.products = res.data)
              .catch(err => console.log(err))

      //fetch categories
      await axios.get(this.baseURL + "category/")
              .then(res => this.categories = res.data)
              .catch(err => console.log(err))
    }
  },
  mounted() {
    this.fetchData();
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>