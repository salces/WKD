(function() {
    'use strict';

    angular
        .module('wkdProjectApp')
        .factory('Book', Book);

    Book.$inject = ['$resource'];

    function Book ($resource) {
        var service = $resource('api/book', {}, {
            'get': { method: 'GET', params: {}, isArray: false},
            'query': {method: 'GET', params: {}, isArray: false}
        });

        return service;
    }
})();
