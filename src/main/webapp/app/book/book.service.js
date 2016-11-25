(function() {
    'use strict';

    angular
        .module('wkdProjectApp')
        .factory('Book', Book);

    Book.$inject = ['$resource'];

    function Book ($resource) {

        var service = $resource('api/book/:action', {}, {
            'get': { method: 'GET', params: {}, isArray: false},
            'query': {method: 'GET', params: {}, isArray: false},
            'count': {method: 'GET', params: {action: 'count'}, isArray: false},
            'loan' : {method: 'POST', params: {action: 'loan'}}
        });

        return service;
    }
})();
