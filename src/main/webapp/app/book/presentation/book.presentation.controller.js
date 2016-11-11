(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BookPresentationController', BookPresentationController);

    BookPresentationController.$inject = ['Book'];

    function BookPresentationController(Book) {
        var vm = this;
    // console.log(Book.query());
        vm.books = Book.query(function (response) {
            console.log(response.content[0]);
            console.log()
        })[0];

        console.log(vm.books);

        //TODO: remove mocked data and fetch from backend
        // vm.books = [
        //     {
        //         Id: 1,
        //         Author: 'Jan Kochanowski',
        //         Tittle: 'Treny'
        //     },
        //     {
        //         Id: 2,
        //         Author: 'Adam Mickiewicz',
        //         Tittle: 'Pan Tadeusz'
        //     },
        //     {
        //         Id: 3,
        //         Author: 'Stanisław Lem',
        //         Tittle: 'Solaris'
        //     }
        // ];
        //
        // function getBooks($http) {
        //     $http.get('http://localhost:8080/api/book?page=0&size=20')
        //         .then(function success(response) {
        //             console.log('asd');
        //             return response.data;
        //         });
        // }

    }

})();
