angular.module("SimpleBank")


    .controller('HeaderController', function ($scope, $location) {

        $scope.isActive = function (viewLocation) {
            var active = (viewLocation === $location.url());
            return active;
        };
    })


    .controller('СustomerController', function ($scope, services) {

        services.getCustomers().then(function (response) {
            $scope.customers = response.data;
        });

        var prepareCustomerName = function (lastname, firstname, patronymic) {
            return (lastname + " " + firstname + " " + (patronymic != null ? patronymic : "")).trim()
        };

        $scope.postfunction = function () {

            if ($scope.customerForm.$invalid) {
                $scope.postResultMessage = "Фамилия и Имя клиента должны быть заполнены!";
                return;
            }

            var customer = {
                name: prepareCustomerName($scope.customer.lastname, $scope.customer.firstname, $scope.customer.patronymic),
                address: $scope.customer.address
            };

            services.insertCustomer(customer).then(function (response) {
                if (response.data != null)
                    $scope.postResultMessage = 'Клиент добавлен!';

                $scope.customer.firstname = "";
                $scope.customer.lastname = "";
                $scope.customer.patronymic = "";
                $scope.customer.address = "";

                $scope.customers.push(response.data);


            }, function error(response) {
                $scope.postResultMessage = "Ошибка! Статус ошибки: " + response.status + " " + response.statusText;
            });

        }

    })


    .controller('AccountController', function ($scope, $routeParams, services) {


        $scope.model = {
            customerID: $routeParams.customerID
        }

        services.getCustomerAccounts($routeParams.customerID).then(function (response) {
            $scope.accounts = response.data;
        });

        $scope.postfunction = function () {

            if ($scope.account.$invalid) {
                $scope.postResultMessage = "Сумма должна быть заполнена по шаблону 0.00!";
                return;
            }

            var account = {
                sum: $scope.sum
            };

            services.insertAccount(account, $routeParams.customerID).then(function (response) {

                if (response.data.id != null) {
                    $scope.postResultMessage = 'Счет добавлен!';
                    $scope.accounts.push(response.data);
                    $scope.sum = "";
                }

            }, function error(response) {
                $scope.postResultMessage = "Ошибка! Статус ошибки: " + response.statusText + " " + response.status;
            });
        }
    })

    .controller('TransferController', function ($scope, $routeParams, services) {


        var findCustomerAccounts = function (id) {
            services.getCustomerAccounts(id).then(function (response) {

                $scope.accounts = response.data;

                response.data.forEach(function (account) {

                    if (account.id.toString() == $routeParams.id)
                        $scope.model.currentAccount = account;
                });

            });
        };


        $scope.model = {

            accountID: $routeParams.id,

            customerID: $routeParams.customerID,

        };

        findCustomerAccounts($routeParams.customerID);

        var findCustomers = function () {
            services.getCustomers().then(function (response) {

                $scope.customers = response.data;

                response.data.forEach(function (customer) {

                    if (customer.id.toString() == $routeParams.customerID)
                        $scope.model.currentCustomer = customer;
                });

            });
        };

        findCustomers();


        $scope.selectedCustomerRow = null;

        $scope.selectedAccountRow = null;

        $scope.rowCustomerHighilited = function (idSelected) {

            $scope.selectedCustomerRow = idSelected;

            findCustomerAccounts($scope.selectedCustomerRow);

        };


        $scope.rowAccountHighilited = function (idSelected) {
            $scope.selectedAccountRow = idSelected;
        };


        $scope.checked = function (chkselct) {

            $scope.selectedCustomerRow = null;

            $scope.selectedAccountRow = null;

            if (chkselct !== null && chkselct == true)
                findCustomerAccounts($routeParams.customerID);
        };


        $scope.transferFunction = function () {


            if ($scope.transfer.$invalid) {
                $scope.postResultMessage = "Сумма должна быть заполнена по шаблону 0.00!";
                return;
            }

            if ($scope.selectedAccountRow == null) {
                $scope.postResultMessage = 'Необходимо выбрать счет для перевода!';
                return;
            }

            services.transfer($routeParams.id, $scope.selectedAccountRow.id.toString(), $scope.transfered.sum)

                .then(function (response) {

                    if (response.data !== null) {
                        $scope.postResultMessage = 'Перевод осуществлен!';
                        $scope.transfered.sum = "";
                        $scope.model.currentAccount.sum = response.data.sumAfterSender;
                        $scope.selectedAccountRow.sum = response.data.sumAfterRecipient;
                    }

                }, function error(response) {
                    $scope.postResultMessage = "Ошибка! Статус ошибки: " + response.statusText + " " + response.status;
                });

        };

    })

    .controller('TransactionController', function ($scope, services) {

        $scope.transactions = [];

        $scope.transactions_buffer = [];

        var findTransaction = function () {
            services.getTransactions().then(function (response) {

                response.data.forEach(function (bankTransaction) {

                    bankTransaction.accounts.forEach(function (account) {

                        $scope.transactions_buffer.push({

                            date: bankTransaction.date,

                            name: account.accountOwner.name,

                            discription: bankTransaction.discription
                                + (bankTransaction.senderId == account.id ? " Снятие денежных средств "
                                    : " Поступление денежных средств ") + "в размере: " + bankTransaction.transferdSum

                                + ". Доступно :" + (bankTransaction.senderId == account.id ? bankTransaction.sumAfterSender : bankTransaction.sumAfterRecipient)

                        })


                    })


                });

            });
        };

        findTransaction();


        $scope.transactions = services.transactionFilter($scope.transactions_buffer, $scope.dtfrom, $scope.dtto);


        $scope.filterFunction = function(){
            $scope.transactions = services.transactionFilter($scope.transactions_buffer, $scope.dtfrom, $scope.dtto);
        }


        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.open2 = function () {
            $scope.popup2.opened = true;
        };


        $scope.popup1 = {
            opened: false
        };

        $scope.popup2 = {
            opened: false
        };


    });



