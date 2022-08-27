const path = require('path')
// const AutoImport = require("unplugin-auto-import/webpack")
// const Components = require("unplugin-auto-import/webpack")
const {ElementPlusResolver} = require("unplugin-vue-components/resolvers")
module.exports = {
  lintOnSave: false,
  configureWebpack:{
    plugins: [
      // AutoImport({
      //   resolvers: [ElementPlusResolver()],
      // }),
      // Components({
      //   resolvers: [ElementPlusResolver()],
      // }),
      require('unplugin-element-plus/webpack')({})
    ],
    // watchOptions:{
    //   poll:1000
    // }

  },
  chainWebpack: config => {
    const dir = path.resolve(__dirname, 'src/assets/svg') // icon存放路径
    config.module
      .rule('svg-sprite')
      .test(/\.svg$/) //使用条件：.svg结尾的文件
      .include.add(dir).end()   //包含icons目录
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({extract: false,symbolId:'icon-[name]'}).end() //规定需要用svg-sprite-loader这个loader，extract: false表明，不要生成其他的文件
      
    config.plugin('svg-sprite').use(require('svg-sprite-loader/plugin'), [{plainSprite: true}])
    config.module.rule('svg').exclude.add(dir)  //其他目录的.svg文件，不需要用到以上规则
  },
  
};