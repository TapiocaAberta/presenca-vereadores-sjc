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
                $("#gradeSessao").fadeOut(50).fadeIn(500);
                $scope.infoSessao = presencaService.infoSessao($scope.sessao.legislature, $scope.sessao.session);
                // vamos picar os dados pra criar uma grid
                $scope.presencas = [];
                var i = 0, j = 0;
                $scope.totalPorLinha = 6;
                $.each($scope.infoSessao.attendance, function(k, v) {
                    if(i % $scope.totalPorLinha === 0) {
                        j++;
                    }
                    if(!$scope.presencas[j]) {
                        $scope.presencas[j] = {};
                    }
                    $scope.presencas[j][k] = v;
                    i++;
                })
        }
        
        presencaService.loadData(function() {
            $scope.legislaturas = presencaService.todasLegislaturas();
            //carrega dados iniciais
            $scope.legislatura = $scope.legislaturas[$scope.legislaturas.length - 1];
            $scope.carregaSessoes();
            $scope.sessao = $scope.sessoes[$scope.sessoes.length - 1];
            $scope.carregaInfoSessao();
            $scope.faltas = presencaService.faltas();
            $scope.faltasSessoes = presencaService.faltasEmSessoes();
        });
        
        $scope.fotoVereador = function(nome) {
            return img = './images/' + nome.replace(/ /g,"_").toLowerCase() + '.jpg'; 
        }
    }
    presencaController.$inject = ['$scope', 'PresencaService'];
    
    angular.module('PresencaApp').controller('PresencaController', presencaController);
}());
