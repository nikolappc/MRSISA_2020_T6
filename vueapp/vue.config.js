const path = require("path");

module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    proxy: 'https://mrsisa-tim06.herokuapp.com/'
  },
  outputDir: '../SpringWebApp/src/main/resources/static',
  publicPath:""
}
