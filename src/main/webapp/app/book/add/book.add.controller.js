(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BookAddController', BookAddController);

    BookAddController.$inject = ['Book','Notification'];

    function BookAddController(Book,Notification) {
        var vm = this;
        vm.add = function () {
            var book = {
                isbn : vm.isbn,
                author: vm.author,
                tittle : vm.tittle,
                publicationYear : vm.publicationYear,
                publisher : vm.publisher,
                smallImageUrl: vm.smallImageUrl,
                mediumImageUrl : vm.mediumImageUrl,
                largeImageUrl : vm.largeImageUrl
            };
            Book.add(book).$promise.then(function (response) {
                Notification.success('You successfully added a book');
                vm.clearBook();
            },function (response) {
                Notification.error('Something went wrong during adding this book');
            });
        }

        vm.clearBook = function(){
            vm.isbn = '';
            vm.author = '';
            vm.tittle = '';
            vm.publicationYear = 0;
            vm.publisher = '';
            vm.smallImageUrl = '';
            vm.mediumImageUrl = '';
            vm.largeImageUrl = '';
        }
        
        vm.edit = function (book) {
            
        }
    }

})();
