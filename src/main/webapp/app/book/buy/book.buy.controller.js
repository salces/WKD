(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BuyBookController', BuyBookController);

    BuyBookController.$inject = ['book'];

    function BuyBookController(book) {
        var vm = this;
        vm.book = book;
    }
})();

