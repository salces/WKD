(function() {
    'use strict';

    angular
        .module('wkdProjectApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            // 'ngTable',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.grid',
            'ui.grid.pagination',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
