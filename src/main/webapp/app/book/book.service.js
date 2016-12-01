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
            'loan' : {method: 'POST', params: {action: 'loan'}},
            'add' : {method : 'POST', params: {}},
            'edit' : {method : 'PUT'},
            'loaned' : {method : 'GET', params : {action : 'loaned'}, isArray: true},
            'inactives' : {method : 'GET', params : {action : 'inactive'}, isArray: true},
            'activate' : {method: 'POST', params : {action : 'activate'}, isArray: false},
            'remove' : {method : 'DELETE', isArray : false}
        });

        return service;
    }
})();
