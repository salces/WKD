(function() {
    'use strict';

    angular
        .module('wkdProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('book_loaned', {
            parent: 'app',
            url: '/book/loaned',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/book/loaned/book.loaned.html',
                    controller: 'LoanedBookController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
