(function() {

    function presencaService($http) {
        return {}
    }

    presencaService.$inject = ['$http'];
    angular.module('PresencaApp').factory('PresencaService', presencaService);

}());
