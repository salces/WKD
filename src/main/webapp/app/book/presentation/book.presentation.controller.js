(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('BookPresentationController', BookPresentationController);

    BookPresentationController.$inject = ['Book', '$scope', '$uibModal',];

    function BookPresentationController(Book, $scope, $uibModal) {
        var vm = this;

        $scope.bookPresentationGrid = {
            enableSorting: true,
            enableFiltering: true,
            paginationPageSizes: [25, 50, 75],
            paginationPageSize: 25,
            useExternalPagination: true,
            columnDefs: [
                {name: 'Author', field: 'author'},
                {name: 'Tittle', field: 'tittle'},
                {name: 'Publisher', field: 'publisher'},
                {name: 'Publication Year', field: 'publicationYear'},
                {
                    name: 'Buy',
                    cellTemplate: '<button class="btn btn-success" style="width:100%;border-radius: 0px" ng-click="grid.appScope.vm.openBuyBookModal(row.entity)" >Buy</button>',
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false
                }
            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                    getPage(newPage, pageSize);
                })
            }
        };

        vm.openBuyBookModal = function (book) {
            $scope.buyBookModalEntity = book;
            $uibModal.open({
                templateUrl: '/app/book/buy/book.buy.modal.html',
                controller: 'BuyBookController',
                controllerAs: 'vm',
                resolve: {
                    book: function getBook() {
                        return book;
                    }
                }
            });
        };

        var getPage = function (newPage, pageSize) {
            //TODO: handle count response
            Book.count().$promise.then(function (data) {
                 $scope.bookPresentationGrid.totalItems = 271400;
                // console.log(data);
                return data;
            });
            Book.query({page: newPage, size: pageSize}).$promise.then(function (data) {
                $scope.bookPresentationGrid.data = data.content;
                return data.content;
            });
        };

        getPage(0, 25);
    }

})();
