var app = angular.module("SimpleBank", ['ngRoute']);



app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.hashPrefix('');
}]);



app.config(['$routeProvider',
    function ($routeProvider) {

        $routeProvider.when('/', {
            templateUrl: '/html/customer.html',
            controller: 'СustomerController'

        }).when('/edit-accounts/:customerID', {
            title: 'Edit Accounts',
            templateUrl: '/html/accounts.html',
            controller: 'AccountController'

        }).when('/transfer/:id/:customerID', {
            title: 'Edit Accounts',
            templateUrl: '/html/transfer.html',
            controller: 'TransferController'


        }).otherwise({
            redirectTo: '/'
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


