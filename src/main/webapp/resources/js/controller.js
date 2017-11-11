'use strict';

myapp.controller('LoginController', function ($rootScope, $scope, AuthSharedService) {
    $scope.rememberMe = true;
    $scope.login = function () {
        $rootScope.authenticationError = false;
        if ($scope.loginForm.$valid) {
        	AuthSharedService.login(
                    $scope.username,
                    $scope.password,
                    $scope.rememberMe
                );
        }
    }
})
.controller('HomeController', function ($scope, HomeService) {
//    $scope.technos = HomeService.query(function(result, responseHeaders){
//    	console.log('Total users received', result.length);
//    }, function(httpResponse) {
//    	 console.log('Error while fetching users list');
//    });
	HomeService.query(function(result, responseHeaders){
		$scope.technos = result;
    	console.log('Total items received', result.length);
    }, function(httpResponse) {
    	 console.log('Error while fetching items list');
    });
})
.controller('UsersController', function ($scope, $log, UsersService) {
	//$scope.users = UsersService.getAll();

	UsersService.get(function(result, responseHeaders){
		//var users = result;
		//var userArray = [];
//		for (var i = 0; i < users.user.length; i++) {
//			var obj = users.user[i];
//			userArray.push(obj);
//		}
		console.log("Result", result);
		console.log("User", result.user);
		$scope.userLists = result.user;
    	console.log('Total users received', $scope.userLists.length);
    }, function(httpResponse) {
    	 console.log('Error while fetching users list');
    });
})
.controller('RegisterController', function ($scope, $log, UserRegisterService) {
	$scope.names = [
		    { id:1, type:'USER'},
		    { id:2, type:'ADMIN'}
		];
	$scope.submitForm = function() {
		if ($scope.userForm.$valid) {
				UserRegisterService.register(
			        $scope.username,
			        $scope.password,
			        $scope.email,
			        $scope.selectedRole
				);
		    }
	};
})
.controller('LogoutController', function (AuthSharedService) {
        AuthSharedService.logout();
	//console.log("logout");
 })
.controller('ApiDocController', function ($scope) {
    // init form
    $scope.isLoading = false;
    $scope.url = $scope.swaggerUrl = 'v2/api-docs';
    // error management
    $scope.myErrorHandler = function (data, status) {
        console.log('failed to load swagger: ' + status + '   ' + data);
    };

    $scope.infos = false;
})
.controller('MealController', function ($scope, $filter, MealService) {
	
		var self = this;
		self.meal = new MealService();
		
		$scope.meals = [];
		
		self.fetchAllMeals = function() {
			MealService.get(function(result, responseHeaders) {
				self.meals = result.mealResource;
			}, function(httpResponse) {
				console.log('Error while fetching list of meals.');
			});	
		};
		
		self.fetchAllMeals();
		
		self.saveMeal = function() {
			if (self.meal.rid == null) {
				console.log('inputDate', self.meal.inputDate);
				console.log('description', self.meal.description);
				console.log('calories', self.meal.calories);
				MealService.save({date: self.meal.inputDate, description: self.meal.description, calories: self.meal.calories}, function() {
					self.fetchAllMeals();
				});
//				self.meal.$save(function() {
//					self.fetchAllMeals();
//				});
			} else {
				MealService.update({id: self.meal.rid}, {date: self.meal.inputDate, description: self.meal.description, calories: self.meal.calories}, 
						function(result, responseHeaders) {
					self.fetchAllMeals();
				}, function(httpResponse) {
					console.log('Error');
				});
			}	
			 self.reset();
		};
		
		self.deleteMeal = function(id) {
			var meal = MealService.get({id: id}, function() {
				console.log('deletingMeal', meal);
				MealService.delete({id: id},function() {
					console.log('Deleting meal with id: ', id);
					self.fetchAllMeals();
				});
			});
		};
		
		self.edit = function(id) {
			console.log(id);
			for(var i = 0; i < self.meals.length; i++) {
				if (self.meals[i].rid === id) {
					self.meal = angular.copy(self.meals[i]);
					break;
				}
			}
		};
		
		self.remove = function(id) {
			console.log(id);
			if (self.meal.rid === id) {
				self.reset();
			}
			self.deleteMeal(id);
		};
		
		self.reset = function(){
			self.meal = new MealService();
            $scope.mealForm.$setPristine(); //reset Form
        };
		
//		$scope.saveMeal = function() {
//		//var _date = $filter('date')(new Date(), $scope.inputDate); 
//		//$scope.inputDate = $filter('date')(new Date(), 'MM/dd/yyyy');
//		console.log($scope.inputDate);
//		//console.log($scope.description);
//		MealService.save({date: $scope.inputDate, description: 'desc', calories: '1'}, function(result, responseHeaders){
//	    	console.log('Response', result);
////	    	MealService.get(function(result, responseHeaders){
////		    	console.log('MealList', result);
////		    	console.log('Meal', result.mealResource);
////	    	});
//	    	var mealList = MealService.get();
//	    	console.log('MealList', mealList);
//	    	console.log('Meal', mealList.mealResource);
//
//	    }, function(httpResponse) {
//	    	 console.log('Error');
//	    });
//		
//	}
 })
.controller('TokensController', function($scope, UsersService, TokensService, $q) {
	var browsers = ["Firefox", 'Chrome', 'Trident']
	
	$q.all([
	    UsersService.get().$promise,
	    TokensService.getAll().$promise
	]).then(function(data) {
		var usersRes = data[0];
		var users = usersRes.user;
		var tokens = data[1];
		
		console.log(users);
		
		tokens.forEach(function (token) {
            users.forEach(function (user) {
                if (token.username === user.username) {
                    token.username = user.username;
                    browsers.forEach(function (browser) {
                        if (token.userAgent.indexOf(browser) > -1) {
                            token.browser = browser;
                        }
                    });
                }
            });
        });
		$scope.tokens = tokens;
		console.log($scope.tokens);
	});
})
.controller('ErrorController', function ($scope, $routeParams) {
    console.log($routeParams.code); 
	$scope.code = $routeParams.code;
      
      switch ($scope.code) {
      	case "403" : 
      		$scope.message = "Oops! you have come to unauthorised page.";
      	case "404" :
            $scope.message = "Page not found."
            break;
        default:
            $scope.code = 500;
      }
 });