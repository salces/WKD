(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BuyBookController', BuyBookController);

    BuyBookController.$inject = ['book', '$http', '$log'];

    function BuyBookController(book, $http, $log) {
        var vm = this;
        vm.book = book;
        vm.loan = function () {
            var loanBookDTO = {
                ISBN: book.ISBN,
                userID: 'pawel',
                days: 30
            };

            $http
                .post('http://localhost:8080/api/book/loan', loanBookDTO)
                .then(function success(response) {
                    $log.log(response)
                }, function failure(response) {
                    $log.log(response)
                });
            $log.log("sie dzieje");
        }
    }


})();

