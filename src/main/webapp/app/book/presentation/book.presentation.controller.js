(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BookPresentationController', BookPresentationController);

    BookPresentationController.$inject = [];

    function BookPresentationController() {
        var vm = this;

        //TODO: remove mocked data and fetch from backend
        vm.books = [
            {
                Id: 1,
                Author: 'Jan Kochanowski',
                Tittle: 'Treny'
            },
            {
                Id: 2,
                Author: 'Adam Mickiewicz',
                Tittle: 'Pan Tadeusz'
            },
            {
                Id: 3,
                Author: 'Stanisław Lem',
                Tittle: 'Solaris'
            }
        ];

    }

})();
