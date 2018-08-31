var app = angular.module("SimpleBank", ['ngRoute']);





app.controller('customerController', function($scope, $http){

    var url = "api/customers";

    $http.get(url).then(function (response) {
        $scope.customers = response.data;
    });

    $scope.postfunction = function(){

        var url = "api/postcustomer";

        var config = {
            headers : {
                'Accept': 'text/plain'
            }
        }
        var customer = {
            name: ($scope.lastname +  " " + $scope.firstname + " " + ($scope.patronymic!=null ? $scope.patronymic : "")).trim(),
            address: $scope.address

        };


        $http.post(url, customer, config).then(function (response) {
            $scope.postResultMessage = response.data;
            $scope.customers.push(customer);
        }, function error(response) {
            $scope.postResultMessage = "Error with status: " +  response.statusText;
        });

        $scope.firstname = "";
        $scope.lastname = "";
        $scope.patronymic = "";
        $scope.address = "";

    }

});



app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/', {
            title: 'Customers',
            templateUrl: '/customer.html'

        }).otherwise({
            redirectTo: '/'
        });
    }
]);

app.run(['$location', '$rootScope', function($location, $rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;
    });
}]);
