(function() {

    function presencaController($scope, presencaService, $rootScope) {

        $scope.showAbout = function() {
            $('#modalSobre').modal();
        };

        $scope.showHowItWorks = function() {
            $('#modalFuncionamento').modal();
        };
        $scope.legislaturas = ["1", "2"];
     
    }
    presencaController.$inject = ['$scope', 'PresencaService'];
    angular.module('PresencaApp').controller('PresencaController', presencaController);
}());
