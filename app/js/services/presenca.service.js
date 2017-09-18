(function() {

    function presencaService($http) {
        legislaturas = [];
        dadosSessoes = [];
        sessoes = {};
        faltasVereadores = [];
        faltasSessoes = [];
        
        function loadData(success) {
            $http.get('./data/sessionAttendance.json').then(function(response) {
                sessoes = response.data.sessionAttendance;
                var contagemFaltasVereadores = {}, contagemFaltasSessoes = {};
                for(var i =0; i < sessoes.length; i++) {
                    var sessao = sessoes[i];
                    var legislatura = sessao.legislature;
                    var chaveSessao = sessao.session + ' ( ' + sessao.date + ' )';
                    if($.inArray(legislatura, legislaturas) === -1) legislaturas.push(legislatura);
                    if(!sessoes[legislatura]){
                        sessoes[legislatura] = [];
                    }
                    sessoes[legislatura].push(sessao);
                    contagemFaltasSessoes[chaveSessao] = 0;
                    $.each(sessao.attendance, function(k, v) {
                        if(!contagemFaltasVereadores[k]) {
                            contagemFaltasVereadores[k] = 0;
                        }
                        if(v === 'NÃO') {
                            contagemFaltasVereadores[k] += 1;
                            contagemFaltasSessoes[chaveSessao] +=1;
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
                
                $.each(contagemFaltasVereadores, function(k, v){
                    faltasVereadores.push({"vereador": k, "faltas": v});
                });
                faltasVereadores.sort(function(a, b){
                    return  b.faltas - a.faltas;
                });
                
                $.each(contagemFaltasSessoes, function(k, v){
                    faltasSessoes.push({"sessao": k, "faltas": v});
                });
                faltasSessoes.sort(function(a, b){
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
            return faltasVereadores;
        }
        
        function faltasEmSessoes() {
            return faltasSessoes;
        }
        
        return {
            loadData: loadData,
            todasLegislaturas: todasLegislaturas,
            sessaoParaLegislatura: sessaoParaLegislatura,
            infoSessao: infoSessao,
            faltas: faltas,
            faltasEmSessoes: faltasEmSessoes
        };
    }

    presencaService.$inject = ['$http'];
    angular.module('PresencaApp').factory('PresencaService', presencaService);

}());
