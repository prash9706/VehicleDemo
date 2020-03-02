var vehicleApp = angular.module('vehicleApp', ['ui.bootstrap']);
vehicleApp.controller('vehicleController', function($scope, $http, $uibModal) {
	$scope.vehicleList = [];
	$scope.showModal = true;
	$scope.getAllVehicles = function(){
		$http({
		    method : "GET",
		      url : "/get"
		  }).then(function mySuccess(response) {
			  $scope.vehicleList = response.data;
		  }, function myError(response) {
		  });
	}
	$scope.createVehicle = function(id, model, make, year){
		var payload = {
	    	  "id": id,
	    	  "model": model,
	    	  "make": make,
	    	  "year":year
		};
		$http({
		    method : "POST",
		      url : "/create",
		      params: payload,
		  }).then(function mySuccess(response) {
			  $scope.createMsg = response.data;
			  $scope.getAllVehicles();
		  }, function myError(response) {
		  });
	}
	$scope.deleteVehicle = function(id){
		var payload = {
	    	  "id": id,
		};
		$http({
		    method : "POST",
		      url : "/delete",
		      params: payload
		  }).then(function mySuccess(response) {
			  $scope.deleteMsg = response.data;
			  $scope.getAllVehicles();
		  }, function myError(response) {
		  });
	}
	$scope.updateVehicle = function(vehicle){
		var modalInstance =  $uibModal.open({
		      templateUrl: "updatePopUp.html",
		      controller: "updateController",
		      resolve: {
		    	  popUpData: function () {
		    		  var vehicledata={
		    			getAllVehicles:$scope.getAllVehicles,
		    			vehicleToUpdate:vehicle,
		    		  }
		    		  return vehicledata;
		          }
		        }
		 });
		modalInstance.result.then(function(response){
			 $scope.getAllVehicles();
	    });
		
	}
});
vehicleApp.controller('updateController', function($scope, $http,$uibModalInstance, popUpData) {
	  $scope.vehicleToUpdate = popUpData.vehicleToUpdate;
	  $scope.getAllVehicles = popUpData.getAllVehicles;
	  $scope.ok = function(){
	    $uibModalInstance.close("Ok");
	  }
	  
	  $scope.updateVehicle =function(model, make, year){
		  var payload = {
		    	  "id": $scope.vehicleToUpdate.id,
		    	  "model": model,
		    	  "make": make,
		    	  "year":year
			};
			$http({
			    method : "POST",
			      url : "/update",
			      params: payload
			  }).then(function mySuccess(response) {
				  $scope.updateMsg = response.data;
				  $scope.getAllVehicles();
			  }, function myError(response) {
			  });
	  }
	  
	  $scope.cancel = function(){
	    $uibModalInstance.dismiss();
	  } 
	  
});