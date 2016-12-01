(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('LoanBookModalController', LoanBookModalController);

    LoanBookModalController.$inject = ['book','$uibModal'];

    function LoanBookModalController(book,$uibModal) {
        var vm = this;
        vm.book = book;


        vm.changeDateFormat = function(book){
            book.startDate = book.startDate.substring(0,10) + ' ' + book.startDate.substring(11,19);
            book.endDate = book.endDate.substring(0,10) + ' ' + book.endDate.substring(11,19);;
        }

        vm.initModal = function (status) {
            vm.changeDateFormat(vm.book);
            switch (status){
                case 'Inactive':
                    vm.templatePath = '/app/book/loaned.modal/templates/book.loaned.inactive.template.html';
                    break;
                case 'Active':
                    vm.templatePath = '/app/book/loaned.modal/templates/book.loaned.active.template.html';
                    break;
                case 'End of loan':
                    vm.templatePath = '/app/book/loaned.modal/templates/book.loaned.end.of.loan.template.html';
                    break;
            }
        }

        vm.openReadModal = function () {
            console.log(vm.book);
            $uibModal.open({
                templateUrl: '/app/book/read/book.read.modal.html',
                controller: 'ReadBookController',
                controllerAs: 'vm',
                resolve: {
                    book: function getBook() {
                        return vm.book;
                    }
                }
            });
        }

        vm.initModal(book.status);

    }


})();

