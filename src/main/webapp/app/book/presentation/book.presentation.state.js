(function() {
    'use strict';

    angular
        .module('wkdProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('book_presentation', {
            parent: 'app',
            url: '/book/presentation',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/book/presentation/book.presentation.html',
                    controller: 'BookPresentationController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
