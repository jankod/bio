// 'use strict';
// Base js for all pages

require('bootstrap');
require('@coreui/coreui');
//import '@coreui/coreui/js/src/index';

const $ = require('jquery');
// create global $ and jQuery variables
global.$ = global.jQuery = $;


// the bootstrap module doesn't export/return anything
//require('bootstrap');


$(function () {
    'use strict'

    // $('[data-toggle="offcanvas"]').on('click', function () {
    //     $('.offcanvas-collapse, .offcanvas-menu-open').toggleClass('open')
    // });
    //
    // $('#btnCloseOffCanvas').on('click', function () {
    //    // console.log("click");
    //     $('.offcanvas-collapse, .offcanvas-menu-open').toggleClass('open')
    // });

    console.log("dela");
});

require('./base.scss');
//require('@fortawesome/fontawesome-free/css/all.min.css');
// require('@fortawesome/fontawesome-free/js/all.js');
// or you can include specific pieces
// require('bootstrap/js/dist/tooltip');
// require('bootstrap/js/dist/popover');
// $(document).ready(function () {
//     $('[data-toggle="popover"]').popover();
// });