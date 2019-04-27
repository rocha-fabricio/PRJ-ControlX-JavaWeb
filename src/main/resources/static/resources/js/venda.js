var app = angular.module("Vender", []);
         
            //Controller Part
            app.controller("VenderController", function($scope, $http) {
         
               
                $scope.proutos = [];
                $scope.produtosVenda = [],
                $scope.produtoForm = {
                	id : "",
                    codigoBarras : "",
                    nome : "",
                    preco : "",
                    qtd : "",
                    tipoUn : "",
                };
                $scope.vendaForm = {
                	data : "",
                	hora : "",
                	total : ""
                }
                
                $scope.addProdutoVenda = function() {
                	
                	
                	
                	for(produto in $scope.produtosVenda){
                		if(produto.codigoBarras === $scope.produtoForm.codigoBarras){
                        	for(p in $scope.produtos){
                        		if(p.codigoBarras === $scope.produtoForm.codigoBarras){
                        			p.qtd = p.qtd-produtoForm.qtd;
                        		}
                        	}
                			produto.qtd = produto.qtd + produtoForm.qtd;
                			return true;
                		}
                	}
                	
                }
         
                //Now load the data from server
                _getProdutosData();
         
                //HTTP POST/PUT methods for add/edit country 
                // with the help of id, we are going to find out whether it is put or post operation
                
                $scope.vender = function() {
         
                    var method = "";
                    var url = "";
                    if ($scope.produtoForm.id == -1) {
                        //Id is absent in form data, it is create new country operation
                        method = "POST";
                        url = '/AngularjsSpringRestExample/countries';
                    } else {
                        //Id is present in form data, it is edit country operation
                        method = "PUT";
                        url = '/AngularjsSpringRestExample/countries';
                    }
         
                    $http({
                        method : method,
                        url : url,
                        data : angular.toJson($scope.countryForm),
                        headers : {
                            'Content-Type' : 'application/json'
                        }
                    }).then( _success, _error );
                };
         
                //Remover produto da lista
                $scope.removerProduto = function(produto) {
                	
                };
         
                /* Private Methods */
                //HTTP GET- get all countries collection
                function _getProdutosData() {
                    $http({
                        method : 'GET',
                        url : 'http://localhost:8080/rest/produtos/listarTodos'
                    }).then(function successCallback(response) {
                        $scope.produtos = response.data;
                    }, function errorCallback(response) {
                        console.log(response.statusText);
                    });
                }
         
                function _success(response) {
                    _getProdutosData();
                    _clearFormData()
                }
         
                function _error(response) {
                    console.log(response.statusText);
                }
         
                //Clear the form
                function _clearFormData() {
                    $scope.produtoForm.codigoBarras = "";
                    $scope.produtoForm.nome = "";
                    $scope.produtoForm.preco = "";
                    $scope.produtoForm.qtd = "";
                    $scope.produtoForm.tipoUn = "";
                    
                    $scope.vendaForm.data = "";
                    $scope.vendaForm.hora = "";
                    $scope.vendaForm.total = "";
                };
            });