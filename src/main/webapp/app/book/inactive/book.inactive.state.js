(function() {
    'use strict';

    angular
        .module('wkdProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('inactive_book_loaned', {
            parent: 'app',
            url: '/book/loaned/inactive',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            views: {
                'content@': {
                    templateUrl: '/app/book/inactive/book.inactive.html',
                    controller: 'InactiveBookLoanController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
