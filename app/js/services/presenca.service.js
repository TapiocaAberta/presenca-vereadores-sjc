(function() {

    function presencaService($http) {
        legislaturas = [];
        dadosSessoes = [];
        sessoes = {};
        listaFaltas = [];
        
        function loadData(success) {
            $http.get('./data/sessionAttendance.json').then(function(response) {
                sessoes = response.data.sessionAttendance;
                var contagemFaltas = {};
                for(var i =0; i < sessoes.length; i++) {
                    var sessao = sessoes[i];
                    var legislatura = sessao.legislature;
                    if($.inArray(legislatura, legislaturas) === -1) legislaturas.push(legislatura);
                    if(!sessoes[legislatura]){
                        sessoes[legislatura] = [];
                    }
                    sessoes[legislatura].push(sessao);
                    
                    $.each(sessao.attendance, function(k, v) {
                        if(!contagemFaltas[k]) {
                            contagemFaltas[k] = 0;
                        }
                        if(v === 'NÃO') {
                            contagemFaltas[k] += 1;
                        }
                    });
                    
                }
                // TODO: sort sessions correctly
                for(var i = 0; i < legislaturas.length; i++) {
                    sessoes[legislaturas[i]].sort(function(a, b){
                        if(a.date < b.date) return -1;
                        if(a.date > b.date) return 1;
                        return 0;
                    });
                }
                $.each(contagemFaltas, function(k, v){
                    listaFaltas.push({"vereador": k, "faltas": v});
                });
                listaFaltas.sort(function(a, b){
                    return  b.faltas - a.faltas;
                });
                success();
            });
        }
        
        function todasLegislaturas() {
            return legislaturas;
        }
        
        function sessaoParaLegislatura(l) {
            return sessoes[l];
        }
        
        function infoSessao(legislatura, sessao) {
            var infoSessao = null;
            for(var i =0; i < sessoes.length; i++) {
                var s = sessoes[i];
                if(s.legislature === legislatura && s.session === sessao) {
                    infoSessao = s;
                }
             }
            return infoSessao;
        }
        
        function faltas() {
            return listaFaltas;
        }
        
        return {
            loadData: loadData,
            todasLegislaturas: todasLegislaturas,
            sessaoParaLegislatura: sessaoParaLegislatura,
            infoSessao: infoSessao,
            faltas: faltas
        };
    }

    presencaService.$inject = ['$http'];
    angular.module('PresencaApp').factory('PresencaService', presencaService);

}());