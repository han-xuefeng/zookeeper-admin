const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port:8081,
    proxy:{
      '/api':{
        target: 'http://127.0.0.1:8082',
        changeOrigin: true,
        secure:false,
        // rewrite:path=>path.replace(/^\/path/,'')
        pathRewrite:{
          '^/api':'',
        }
      }
    }
  }
})