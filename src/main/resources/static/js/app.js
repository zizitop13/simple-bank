var app = angular.module("SimpleBank", ['ngRoute', 'ui.bootstrap']);



app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.hashPrefix('');
}]);



app.config(['$routeProvider',
    function ($routeProvider) {

        $routeProvider.when('/', {
            templateUrl: '/html/home.html',

        }).when('/customer/', {
            templateUrl: '/html/customer.html',
            controller: 'СustomerController'

        }).when('/edit-accounts/:customerID', {
            templateUrl: '/html/accounts.html',
            controller: 'AccountController'

        }).when('/transfer/:id/:customerID', {
            templateUrl: '/html/transfer.html',
            controller: 'TransferController'


        }).when('/transactions/', {
            templateUrl: '/html/transactions.html',
            controller: 'TransactionController'


        }).otherwise({
            templateUrl: '/html/error.html'
        });
    }
]);

//
//
// app.run(['$location', '$rootScope', function ($location, $rootScope) {
//     $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
//         $rootScope.title = current.$$route.title;
//     });
// }]);


