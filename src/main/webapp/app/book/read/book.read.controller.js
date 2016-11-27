(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('ReadBookController', ReadBookController);

    ReadBookController.$inject = ['book'];

    function ReadBookController(book) {
        var vm = this;
        vm.book = book;
    }


})();

