var app = angular.module("SimpleBank", ['ngRoute']);



app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.hashPrefix('');
}]);



app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: '/customer.html',
            controller: 'customerController'

        }).when('/edit-accounts/:customerID', {
            title: 'Edit Accounts',
            templateUrl: '/accounts.html',
            controller: 'accountController'
        }).otherwise({
            redirectTo: '/'
        });
    }
]);



app.run(['$location', '$rootScope', function ($location, $rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;
    });
}]);


