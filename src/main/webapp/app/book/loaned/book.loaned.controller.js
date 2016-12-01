(function () {
    'use strict';

    angular
        .module('wkdProjectApp')
        .controller('LoanedBookController', LoanedBookController);

    LoanedBookController.$inject = ['Book', '$scope', '$uibModal','$log'];

    function LoanedBookController(Book, $scope, $uibModal, $log) {
        var vm = this;

        $scope.loanedBookGrid = {
            enableSorting: true,
            enableFiltering: true,
            columnDefs: [
                {name: 'Author', field: 'book.author'},
                {name: 'Tittle', field: 'book.tittle'},
                {name: 'Publisher', field: 'book.publisher'},
                {name: 'Publication Year', field: 'book.publicationYear'},
                {name: 'Status', field: 'status'},
                {
                    name: 'Action',
                    cellTemplate: '<button class="btn btn-success" style="width:100%;border-radius: 0px" ng-click="grid.appScope.vm.openLoanedBookModal(row.entity)" >Action</button>',
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false
                }
            ]
        };

        vm.openLoanedBookModal = function (book) {
            $log.log('Loaned modal');
            $uibModal.open({
                templateUrl: '/app/book/loaned.modal/book.loaned.modal.html',
                controller: 'LoanBookModalController',
                controllerAs: 'vm',
                resolve: {
                    book: function getBook() {
                        return book;
                    }
                }
            });
        };

        var getData= function () {
            Book.loaned().$promise.then(function (response) {
                $log.log(response);
                for(var i = 0; i < response.length; i++){
                    if(response[i].paid == false){
                        response[i].status = 'Inactive';
                    } else if(response[i].paid == true && response[i].active == true){
                        response[i].status = 'Active'
                    } else if(response[i].paid == true && response[i].active == false){
                        response[i].status = 'End of loan'
                    }
                }
                $scope.loanedBookGrid.data = response;
                return response;
            }, function (response) {
                $log.log(response)
            });
        };

        getData();
    }

})();
