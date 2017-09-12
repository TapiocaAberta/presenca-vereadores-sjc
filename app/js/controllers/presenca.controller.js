(function() {

    function presencaController($scope, presencaService, $rootScope) {
    
        $scope.showAbout = function() {
            $('#modalSobre').modal();
        };

        $scope.showHowItWorks = function() {
            $('#modalFuncionamento').modal();
        };
        $scope.carregaSessoes = function() {
            $scope.sessoes = null;
            $scope.sessao = null;
            $scope.sessoes = presencaService.sessaoParaLegislatura($scope.legislatura);
        }
        
        $scope.carregaInfoSessao = function() {
                $scope.infoSessao = presencaService.infoSessao($scope.sessao.legislature, $scope.sessao.session)
        }
        
        $scope.classeParaPresenca = function(presenca) {
            if(presenca === 'NÃO'){
                return 'falta';
            } 
            return 'presenca';
        }
        
        presencaService.loadData(function() {
            $scope.legislaturas = presencaService.todasLegislaturas();
        });
        
     
    }
    presencaController.$inject = ['$scope', 'PresencaService'];
    
    angular.module('PresencaApp').controller('PresencaController', presencaController);
}());
