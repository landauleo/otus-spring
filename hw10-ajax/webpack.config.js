const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path');

module.exports = {
    entry: './src/main/resources/templates/index.html', // Путь к вашей HTML-странице
    mode: 'development',
    output: {
        filename: 'bundle.js', // Имя выходного файла, хотя он не будет фактически использоваться
        path: path.resolve(__dirname, 'dist'),
    },
    module: {
        rules: [
            {
                test: /\.html$/, // Применить загрузчик для файлов с расширением .html
                use: 'html-loader', // Использовать загрузчик html-loader
            },
        ],
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // Целевой URL, куда будут проксироваться запросы
                secure: false,
                changeOrigin: true,
            },
        },
    },
};
