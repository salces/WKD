(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BuyBookController', BuyBookController);

    BuyBookController.$inject = ['Book', 'book', 'Notification', '$uibModal', '$scope'];

    function BuyBookController(Book, book, Notification, $uibModal, $scope) {
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

        vm.openEditModal = function () {
            $uibModal.open({
                templateUrl: '/app/book/edit/book.edit.modal.html',
                controller: 'EditBookController',
                controllerAs: 'vm',
                resolve: {
                    book: function getBook() {
                        return vm.book;
                    }
                }
            });
        }

        vm.remove = function(){
            console.log(vm.book);
            Book.remove({action: vm.book.isbn}).$promise.then(function (response) {
                Notification.success('This book has been deleted');
            });
            // var index = $scope.bookPresentationGrid.data.indexOf(vm.book);
            // $scope.bookPresentationGrid.data.slice(index,1);
        }

    }


})();

