// const { defineConfig } = require('@vue/cli-service')
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin')


module.exports = {
  transpileDependencies: true,
  devServer: {
    host: '127.0.0.1',
    port: 8080,
    open: true,
  },
  configureWebpack: {
    plugins: [
      new MonacoWebpackPlugin({
        languages: ['json', 'javascript'], 
        features: ['!gotoSymbol'] // 禁用不需要的功能以减少打包体积
      })
    ]
  }
};


