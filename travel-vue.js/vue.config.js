const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    lintOnSave: false,
    devServer: {
        port: 8088,
        proxy: {
            '/travel': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                // pathRewrite: {'^/travel': ''}
            }
        }
    }
})
