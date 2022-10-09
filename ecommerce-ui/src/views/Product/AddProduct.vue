<template>
    <div class="container">
        <div class="row">
            <div class="col-12 text-center">
                <h3 class = "pt-3">Add new Product</h3>
            </div>
        </div>

        <div class = "row">
            <div class = "col-3"></div>
            <div class="col-md-6 px-5 px-md-0">
                <form>
                    <div class="form-group">
                        <label>Category</label>
                        <select class="form-control" v-model = "categoryId" required>
                        <option v-for = "category of categories" :key = "category.id" :value = "category.id">{{category.categoryName}}</option> 
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" v-model = "name" required>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <input type="text" class="form-control" v-model = "description" required>
                    </div>
                    <div class="form-group">
                        <label>ImageUrl</label>
                        <input type="text" class="form-control" v-model = "imageURL" requied>
                    </div>
                    <div class="form-group">
                        <label>Price</label>
                        <input type = "number" class="form-control" v-model = "price" required>
                    </div>
                    <button type = "button" class="btn btn-primary" @click = "addProduct">Submit</button> 
                </form>
            </div>
            <div class="col-3"></div>
        </div>
    </div>
</template>

<script>
var axios = require('axios')
import swal from 'sweetalert';

export default {
    data(){
        return {
            categoryId : null,
            name : null,
            description: null,
            imageUrl: null,
            price: null
        }
    },
    props:["baseUrl","categories"],
    methods:{
        async addProduct(){
            const newProduct = {
                categoryId : this.categoryId,
                name : this.name,
                description:this.description,
                imageURL: this.imageUrl,
                price: this.price
            }
            await axios({
                method: 'post',
                url: this.baseUrl+"product/add",
                data: JSON.stringify(newProduct),
                headers:{
                    'Content-Type': 'application/json'
                }
            })
            .then(()=>{
                swal({
                    text:"Product Added Successfully!!",
                    icon:"Success",
                    closeOnClickOutside: false,
                });
            })
            .catch(err=>console.log(err));
        }
    },
    mounted(){

    }
}
</script>

<style scoped>
    h4 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}
</style>