(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('InactiveBookLoanController', InactiveBookLoanController);

    InactiveBookLoanController.$inject = ['Book', '$scope', 'Notification','$log'];

    function InactiveBookLoanController(Book, $scope, Notification, $log) {
        var vm = this;

        $scope.inactiveBookLoanGrid = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                {name: 'Author', field: 'book.author'},
                {name: 'Tittle', field: 'book.tittle'},
                {name: 'Publisher', field: 'book.publisher'},
                {name: 'Publication Year', field: 'book.publicationYear'},
                {name: 'User', field: 'user'},
                {name: 'Book loan ID', field: 'bookLoanID'},
                {
                    name: 'Activate',
                    cellTemplate: '<button class="btn btn-success" style="width:100%;border-radius: 0px" ng-click="grid.appScope.vm.activate(row.entity)" >Activate</button>',
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false
                }
            ]
        };

        var getData= function () {
            Book.inactives().$promise.then(function (response) {
                $log.log(response);
                $scope.inactiveBookLoanGrid.data = response;
                return response;
            }, function (response) {
                $log.log(response)
            });
        };

        vm.activate = function (book) {
            $log.log('Book activated');
            Book.activate(book.bookLoanID).$promise.then(function (response) {
                $log.log(response);
                Notification.success('Book has been activated');
                var index = $scope.inactiveBookLoanGrid.data.indexOf(book);
                $scope.inactiveBookLoanGrid.data.splice(index, 1);
            });

        }

        getData();
    }

})();
