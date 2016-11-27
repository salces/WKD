(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('EditBookController', EditBookController);

    EditBookController.$inject = ['Book', 'book', 'Notification', '$log'];

    function EditBookController(Book, book, Notification, $log) {
        var vm = this;
        vm.book = book;
        $log.log(vm.book);

        vm.edit = function () {
            Book.edit(book).$promise.then(function (response) {
                    $log.log(response)
                    Notification.success('You successfully edited this book')
                }, function (response) {
                    $log.log(response)
                    Notification.error('Error during edition');
                }
            )
        };
    }


})();

