const webpack = require('webpack')
const isDevelopment = process.env.NODE_ENV !== 'production'

module.exports = {
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        process: 'process/browser',
 }),
    ],
    resolve:{
      "fallback":{
        "tls": false,
        "net": false,
        "path": false,
        "zlib": false,
        "url": require.resolve('url'),
        "fs": require.resolve('fs'),
        "assert": require.resolve('assert'),
        "crypto": require.resolve('crypto-browserify'),
        "http": require.resolve('stream-http'),
        "https": require.resolve('https-browserify'),
        "os": require.resolve('os-browserify/browser'),
        "buffer": require.resolve('buffer'),
        "stream": require.resolve('stream-browserify'),
      }
    }
  }
}