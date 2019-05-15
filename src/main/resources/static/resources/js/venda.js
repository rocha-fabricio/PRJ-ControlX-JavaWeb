var app = angular.module("Vender", []);

// Controller Part
app
		.controller(
				"VenderController",
				function($scope, $http, $interval, $filter) {

					$scope.enter = document.getElementById("codigo");
					$scope.enterQtd = document.getElementById("qtd");

					$scope.todosProdutos = [];

					$scope.produtosEditados = [];

					$scope.produtoForm = {
						codigoBarras : "",
						nome : "",
						preco : 0,
						qtd : 1,
						tipoUn : ""
					};

					$scope.vendaForm = {
						valor : 0,
						data : "",
						hora : "",
						produtos : []
					};
					
					// $filter('currency')($scope.vendaForm.valor);

					$scope.msg = "";

					$scope.currentTime = new Date();

					let updateTime = $interval(function() {
						$scope.currentTime = new Date();
					}, 1000);

					// Now load the data from server
					_getProdutosData();

					$scope.enter.addEventListener("keydown", function(e) {
						if (e.keyCode === 13) { // checks whether the
							// pressed
							if ($scope.produtoForm.codigoBarras != undefined) {						
								if ($scope.produtoForm.qtd == "") {
									$scope.produtoForm.qtd = 1;
								}

								$scope.addProdutoVenda();
								this.select();
								$scope.produtoForm.qtd = "";
								$scope.$digest();
							} else {
								$scope.msg = "Código de barras inválido!"
							}
						}

					});
					
					$scope.enterQtd.addEventListener("keydown", function(e) {
						if (e.keyCode === 13) { // checks whether the
							$scope.enter.focus();
						}
					});

					$scope.addProdutoVenda = function() {
						for (let i = 0; i < $scope.vendaForm.produtos.length; i++) {
							if ($scope.produtoForm.codigoBarras == $scope.vendaForm.produtos[i].codigoBarras) {
								
								let produto = $scope.produtosEditados.filter(produto => produto.codigoBarras == $scope.produtoForm.codigoBarras);
								if ((produto[0].qtd - $scope.produtoForm.qtd) < 0 || produto[0].qtd == 0){
									$scope.msg = "Quantidade inválida. Estoque disponível: " + produto[0].qtd;
									return;
								}
								
								$scope.vendaForm.produtos[i].qtd = $scope.vendaForm.produtos[i].qtd
										+ $scope.produtoForm.qtd;

								$scope.produtoForm.nome = $scope.vendaForm.produtos[i].nome;
								$scope.produtoForm.preco = $scope.vendaForm.produtos[i].preco;
								$scope.produtoForm.tipoUn = $scope.vendaForm.produtos[i].tipoUn;

								$scope.vendaForm.produtos[i].preco += ($scope.produtoForm.qtd * $scope.produtoForm.preco);

								$scope.vendaForm.valor += ($scope.produtoForm.qtd * $scope.produtoForm.preco)

								for (let y = 0; i < $scope.produtosEditados.length; i++) {
									if ($scope.produtosEditados[y].codigoBarras == $scope.produtoForm.codigoBarras) {
										$scope.produtosEditados[y].qtd = $scope.produtosEditados[y].qtd
												- $scope.produtoForm.qtd;
										break;
									}
								}
								$scope.msg = "";
								return;
							}
						}

						for (let i = 0; i < $scope.todosProdutos.length; i++) {
							if ($scope.todosProdutos[i].codigoBarras == $scope.produtoForm.codigoBarras) {
								let produtoAtualizado = JSON.parse(JSON
										.stringify($scope.todosProdutos[i]));
								
								if((produtoAtualizado.qtd - $scope.produtoForm.qtd) < 0 || produtoAtualizado.qtd == 0){ 
									$scope.msg = "Quantidade inválida. Estoque disponível: " + produtoAtualizado.qtd; 
									return; 
								}

								produtoAtualizado["qtd"] = (produtoAtualizado["qtd"])
										- ($scope.produtoForm["qtd"]);
								$scope.$apply();

								$scope.produtosEditados.push(produtoAtualizado);

								let produto = JSON.parse(JSON
										.stringify($scope.todosProdutos[i]));
								produto.qtd = $scope.produtoForm.qtd;
								delete produto.id;

								$scope.produtoForm.nome = produto.nome;
								$scope.produtoForm.preco = produto.preco;
								$scope.produtoForm.tipoUn = produto.tipoUn;
								produto.preco = (produto.qtd * produto.preco);
								$scope.vendaForm.produtos.push(produto);

								$scope.vendaForm.valor += ($scope.produtoForm.qtd * $scope.produtoForm.preco)
								$scope.msg = "";
								return;
							}
						}

						$scope.msg = "Código de barras inválido!";
					}

					// HTTP POST/PUT methods for add/edit country
					// with the help of id, we are going to find out whether it
					// is put or post
					// operation

					$scope.vender = function() {

						let method = "";
						let url = "";

						method = "POST";
						url = '/controlx-1.0/rest/venda/cadastrar';

						$http({
							method : method,
							url : url,
							data : angular.toJson($scope.vendaForm),
							headers : {
								'Content-Type' : 'application/json'
							}
						}).then(_success, _error);

						// Modificar quantidade de produtos no banco de dados
						for (let i = 0; i < $scope.produtosEditados.length; i++) {
							method = "POST";
							url = '/controlx-1.0/rest/produtos/editar';

							$http(
									{
										method : method,
										url : url,
										data : angular
												.toJson($scope.produtosEditados[i]),
										headers : {
											'Content-Type' : 'application/json'
										}
									}).then(_success, _error);
						}
						alert("Venda concluída com sucesso.")
					};

					// Remover produto da lista
					$scope.removerProduto = function(produto) {
						for (let i = 0; i < $scope.vendaForm.produtos.length; i++) {
							if (produto.codigoBarras == $scope.vendaForm.produtos[i].codigoBarras) {
								$scope.vendaForm.valor -= ($scope.vendaForm.produtos[i].qtd * $scope.vendaForm.produtos[i].preco)
								$scope.vendaForm.produtos.splice(i);

								for (let y = 0; i < $scope.produtosEditados.length; y++) {
									if (produto.codigoBarras == $scope.produtosEditados[y].codigoBarras) {
										$scope.produtosEditados.splice(y);
										$scope.enter.select();
										return;
									}
								}
							}
						}

					};

					/* Private Methods */
					// HTTP GET- get all countries collection
					function _getProdutosData() {
						$http(
								{
									method : 'GET',
									url : 'http://localhost:8080/controlx-1.0/rest/produtos/listarTodos'
								}).then(
								function successCallback(response) {
									$scope.todosProdutos = JSON.parse(JSON
											.stringify(response.data));
									// $scope.todosProdutos =
									// eval(response.data);

								}, function errorCallback(response) {
									console.log(response.statusText);
								});
					}

					function _success(response) {
						_clearFormData();
						_getProdutosData();

					}

					function _error(response) {
						console.log(response.statusText);
						_clearFormData();
						_getProdutosData();
					}

					// Clear the form
					function _clearFormData() {
						$scope.produtoForm.codigoBarras = "";
						$scope.produtoForm.nome = "";
						$scope.produtoForm.preco = "";
						$scope.produtoForm.qtd = "";
						$scope.produtoForm.tipoUn = "";
						$scope.vendaForm.valor = "";
						$scope.produtosEditados = [];
						$scope.vendaForm.produtos = [];
						$scope.todosProdutos = [];
					}
					;
				});