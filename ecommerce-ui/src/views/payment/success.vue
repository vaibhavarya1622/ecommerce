<template>
    <div class="alert alert-success" role="alert" id="message">
        Payment Successful
    </div>
</template>

<script>
import axios from 'axios';
export default{
    name:'PaymentSuccess',
    props:["baseURL"],
    data(){
        return{
            token:null,
            sessionId:null
        }
    },
    methods:{
        saveOrder(){
            axios.post(`${this.baseURL}order/add/?token=${this.token}&sessionId=${this.sessionId}`)
            .then(()=>{
                // document.getElementById("message").innerHTML = "order placed";
                this.$router.push({name:'OrderHistory'})
            })
            .catch((error)=>console.log(error));
        }
    },
    mounted(){
        this.token = localStorage.getItem('token');
        this.sessionId = localStorage.getItem('sessionId');
        this.saveOrder();
    }
}
</script>