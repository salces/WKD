(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BuyBookController', BuyBookController);

    BuyBookController.$inject = ['Book', 'book', 'Notification', '$log'];

    function BuyBookController(Book, book, Notification, $log) {
        var vm = this;
        vm.book = book;
        vm.daysForLoan = 1;

        vm.loan = function () {
            var loanBookDTO = {
                isbn: vm.book.isbn,
                days: vm.daysForLoan
            };

            Book.loan(loanBookDTO, function success(response) {
                Notification.success('You successfully loaned book ' + vm.book.tittle
                    + '. Go to section My books to make payment.');
            }, function failure(response) {
                switch (response.status){
                    case 409:
                        Notification.warning('You already have this book');
                        break;
                    case 404:
                        Notification.error('No such book found');
                        break;
                    case 500:
                        Notification.error('Server error. Probably user not found. Contact admin');
                        break;

                }

            });
        }
    }


})();

