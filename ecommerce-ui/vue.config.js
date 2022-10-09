module.exports = {
  configureWebpack:{
    resolve:{
      fallback:{
        "crypto":false
      }
    }
  }
}