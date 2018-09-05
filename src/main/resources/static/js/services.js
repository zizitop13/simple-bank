angular.module("SimpleBank").factory("services", ['$http', function ($http) {

    var serivceBase = 'api/';

    var obj = {};

    obj.getCustomers = function () {
        return $http.get(serivceBase + 'customers');
    };

    obj.getCustomerAccounts = function (customerID) {
        return $http.get(serivceBase + 'customerAccounts/' + customerID);
    };

    obj.getCustomer = function (customerID) {
        return $http.get(serivceBase + 'customer/' + customerID);
    };


    obj.getTransactions = function () {
        return $http.get(serivceBase + 'transactions');
    };

    obj.insertAccount = function (account, customerID) {
        return $http.post(serivceBase + 'postaccount/' + customerID, account);
    };


    obj.insertCustomer = function (customer) {

        return $http.post(serivceBase + 'postcustomer', customer);
    };

    obj.transfer = function (senderId, recipientId, sum) {

        var config = {
            headers: {
                'Accept': 'text/plain'
            }
        };

        return $http.post(serivceBase + 'transfer/' + senderId + "/" + recipientId + "/" + sum, config);

    };

    obj.transactionFilter = function(table, from, to) {
        var result = [];

        if(from==null && to==null)
            return table;

        table.forEach(function (transaction) {

            var dat = new Date(transaction.date);

            var datFrom = new Date(from);

            var datTo = new Date(to);

            if(to!=null)
                datTo.setDate(datTo.getDate() + 1);

            if(from!=null && to!=null && dat >= datFrom && dat <= datTo)
                result.push(transaction);

            if(from==null && dat <= datTo)
                result.push(transaction);

            if(to==null && dat >= datFrom)
                result.push(transaction);


        });

        return result;
    };

    return obj;
}]);

