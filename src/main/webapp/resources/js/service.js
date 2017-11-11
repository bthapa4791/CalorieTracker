'use strict';

myapp.service('Session', function () {
    this.create = function (data) {
        this.id = data.id;
        this.username = data.username;
        this.login = true;
        this.email = data.email;
        this.userRoles = [];
        angular.forEach(data.authorities, function (value, key) {
            this.push(value.name);
        }, this.userRoles);
    };
    this.invalidate = function () {
        this.id = null;
        this.login = false;
        this.username = null;
        this.email = null;
        this.userRoles = null;
    };
    return this;
});

myapp.service('AuthSharedService', function ($rootScope, $http, $resource, authService, Session) {
    return {
        login: function (userName, password, rememberMe) {
        	console.log(userName);
            var config = {
                ignoreAuthModule: 'ignoreAuthModule',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            };
            $http.post('authenticate', $.param({
                username: userName,
                password: password,
                rememberme: rememberMe
            }), config)
                .success(function (data, status, headers, config) {
                	console.log(data);
                    authService.loginConfirmed(data);
                })
                .error(function (data, status, headers, config) {
                    $rootScope.authenticationError = true;
                    Session.invalidate();
                });
        },
        getAccount: function () {
            $rootScope.loadingAccount = true;
            $http.get('security/account')
                .then(function (response) {
                    authService.loginConfirmed(response.data);
                });
        },
        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!Session.login &&
                Session.userRoles.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                	isAuthorized = true;
                }
            });
            return isAuthorized;
        },
        logout: function () {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            $http.get('logout');
            Session.invalidate();
            authService.loginCancelled();
        }
    };
});

myapp.service('UserRegisterService', function ($rootScope, $http, $resource, $location, authService, AuthSharedService, Session) {
    return {
        register: function (username, password, email, selectedRole) {
        	console.log(selectedRole);
        	var data = {
        		'username': username,
                'password': password,
                'email': email,
                'authorities': [{"id": selectedRole}]
            };
            var config = {
                headers: {'Content-Type': 'application/json'}
            };
            $http.post('user', data, config)
                .success(function (data, status, headers, config) {
                    console.log(data);
                    var rememberMe = true;
                    AuthSharedService.login(
                            username,
                            password,
                            rememberMe
                        );
                })
                .error(function (data, status, headers, config) {
                    console.log('error');
                });
        }
    };
});

//myapp.service('HomeService', function ($log, $resource) {
//    return {
//        getTechno: function () {
//            var userResource = $resource('resources/json/techno.json', [{}], {
//                query: {method: 'GET', params: {}, isArray: true}
//            });
//            return userResource.query();
//        }
//    }
//});

myapp.factory('HomeService', ['$resource',  function($resource) {
	return $resource('resources/json/techno.json',
			{},
			{
				update: {
					method: 'PUT'
				}
			});
}]);

//myapp.service('UsersService', function ($log, $resource) {
//    return {
//        getAll: function () {
//            var userResource = $resource('users', {}, {
//                query: {method: 'GET', params: {}, isArray: true}
//            });
//            return userResource.get();
//        }
//    }
//});

myapp.factory('UsersService', ['$resource',  function($resource) {
	return $resource('users',
			{},
			{
				update: {
					method: 'PUT'
				}
			});
}]);

myapp.factory('MealService', ['$resource', function($resource) {
	return $resource('meal/:id', 
			{id: '@id'},
			{
				update: {
					method: 'PUT'
				}
			});
}]);

myapp.service('TokensService', function ($log, $resource) {
    return {
        getAll: function () {
            var tokensResource = $resource('security/tokens', {}, {
                query: {method: 'GET', params: {}, isArray: true}
            });
            return tokensResource.query();
        }
    }
});