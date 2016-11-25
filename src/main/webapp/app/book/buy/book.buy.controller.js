(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BuyBookController', BuyBookController);

    BuyBookController.$inject = ['Book', 'book', '$log'];

    function BuyBookController(Book, book, $log) {
        var vm = this;
        vm.book = book;

        vm.loan = function () {
            var loanBookDTO = {
                isbn: vm.book.isbn,
                days: 30
            };
            Book.loan(loanBookDTO, function success(response) {
                $log.log('success');
                $log.log(response);
            }, function failure(response) {
                $log.log('failure');
                $log.log(response);
            });
        }

        //     $http
        //         .post('http://localhost:8080/api/book/loan', loanBookDTO)
        //         .then(function success(response) {
        //             $log.log(response)
        //         }, function failure(response) {
        //             $log.log(response)
        //         });
        //     $log.log("sie dzieje");
        // }
    }


})();

