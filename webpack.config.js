let Encore = require('@symfony/webpack-encore');
let dirSrc = "./src/main/resources/static/src";
let dirDist = "./src/main/resources/static/dist";

Encore
    // directory where compiled assets will be stored
    .setOutputPath(dirDist)
    // public path used by the web server to access the output path
    //.setPublicPath('/static/build/')
    .setPublicPath('/dist')
    // only needed for CDN's or sub-directory deploy
    //.setManifestKeyPrefix('build/')

    /*
     * ENTRY CONFIG
     *
     * Add 1 entry for each "page" of your app
     * (including one that's included on every page - e.g. "app")
     *
     * Each entry will result in one JavaScript file (e.g. base.js)
     * and one CSS file (e.g. app.css) if you JavaScript imports CSS.
     */
    .addEntry('base', dirSrc + '/base.js')



  //  .addEntry('fonts/font-awesome', './node_modules/font-awesome/css/fontawesome.css')
//    .addEntry('fonts/fontawesome-webfont.eot', './node_modules/font-awesome/fonts/fontawesome-webfont.eot')

//    .addStyleEntry('header', main_dir + '/css/header.scss')

    // will require an extra script tag for runtime.js
    // but, you probably want this, unless you're building a single-page app
    .enableSingleRuntimeChunk()

    //.disableFontsLoader()
   // .disableSingleRuntimeChunk()
   // .disableImagesLoader()

    .cleanupOutputBeforeBuild()
    .enableSourceMaps(!Encore.isProduction())
    // enables hashed filenames (e.g. app.abc123.css)
   // .enableVersioning(Encore.isProduction())

    .enableSassLoader()

    .autoProvidejQuery()
;

module.exports = Encore.getWebpackConfig();