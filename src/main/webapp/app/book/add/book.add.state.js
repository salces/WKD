(function() {
    'use strict';

    angular
        .module('wkdProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('book_add', {
            parent: 'app',
            url: '/book/add',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/book/add/book.add.html',
                    controller: 'BookAddController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
