<%@	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap-social.css" />

<script type="text/javascript" src="js/jquery.js"></script>
<link rel="icon" type="imagem/png" href="imgs/faviicon.png" />
<title>Ofertas Ouro Branco</title>

<style>
html, body {
	margin: 0;
	padding: 0;
	height: 100%;
}

#container {
	min-height: 100%;
	position: relative;
}

#header {
	background: #ff0;
	padding: 10px;
}

#body {
	padding: 10px;
	padding-bottom: 220px; /* Height of the footer */
}

#footer {
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 170px; /* Height of the footer */
	background-color: #6e9fbb;
}

.buttonLike {
	background: url(imgs/like.png) no-repeat;
	cursor: pointer;
	border: none;
	width: 30px;
	height: 30px;
	margin-right: 10px;
}

.buttonLike:hover {
	background: url(imgs/likeHover.png) no-repeat;
}

.buttonDislike {
	background: url(imgs/dislike.png) no-repeat;
	cursor: pointer;
	border: none;
	width: 30px;
	height: 30px;
}

.buttonDislike:hover {
	background: url(imgs/dislikeHover.png) no-repeat;
}

.navbar {
	background-color: #b9d9eb;
}

.copyright {
	color: rgba(255, 255, 255, 0.7);
	margin-top: 2%;
}

.social-icons a {
	font-size: 1.7em;
	color: rgba(255, 255, 255, 0.7);
}

.social-icons a:last-child {
	margin-right: 0;
}

.social-icons a:hover {
	color: rgb(255, 255, 255);
}

.logo {
	max-width: 100%;
	max-height: 100%;
}

.portrait {
	float: left;
	height: 62px;
	width: 250px;
}

.container-form {
	margin-top: 30px;
}

.centerV {
	margin-top: 7px;
}

.bg-modal {
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	position: fixed;
	background-color: rgba(0, 0, 0, 0.7);
	display: none;
	justify-content: center;
	align-items: center;
	z-index: 2000;
}

.modal-content {
	width: 500px;
	height: 330px;
	background-color: white;
	border-radius: 10px;
	padding: 20px;
	position: relative;
	text-align: center;
}

.modal-input {
	width: 70%;
	display: block;
	margin: 15px auto;
	border-radius: 6px;
}

.close {
	position: absolute;
	top: 0;
	right: 14px;
	font-size: 42px;
	transform: rotate(45deg);
	cursor: pointer;
}

.center-things {
	text-align: center;
}

.form-position {
	width: 60%;
	float: left;
}
</style>

<script>
	function like(id) {
		$.ajax({
			type : 'POST',
			url : 'like',
			data : {
				"id" : id
			},
			statusCode : {
				200 : function() {
					alert("Obrigado por ajudar a comunidade Ouro Branquense!");
				},
				202 : function() {
					alert("Você já votou nessa opção!");
				}
			}
		});
	}

	function dislike(id) {
		$.ajax({
			type : 'POST',
			url : 'dislike',
			data : {
				"id" : id
			},
			statusCode : {
				200 : function() {
					alert("Obrigado por ajudar a comunidade Ouro Branquense!");
				},
				202 : function() {
					alert("Você já votou nessa opção!");
				}
			}
		});
	}

	function openModal() {
		$("#modalBg").fadeIn('fast');
		document.querySelector('.bg-modal').style.display = 'flex';
	}

	function closeModal() {
		$("#modalBg").fadeOut('fast');
		//document.querySelector('.bg-modal').style.display = 'none';
	}

	
	$(document).ready(function() {
			$("#enviar").submit(
				function(event) {
					event.preventDefault();
						var formData = {
								'nome' : $('input[name=nome]').val(),
								'preco' : $('input[name=preco]').val().replace(/[,]/,"."),
								'quantidade' : $('input[name=quantidade]').val(),
								'mercado' : $('input[name=mercado]').val(),
								};

			$.ajax({
					type : 'POST',
					url : 'sendoferta',
					data : formData,
					statusCode : {
									200 : function() {
											alert("Oferta Enviada. Obrigado por ajudar a comunidade Ouro Branquense!");
										}
								}
					});
			$('input[name=nome]').val("");
			$('input[name=preco]').val("");
			$('input[name=quantidade]').val("");
			$('input[name=mercado]').val("");
			closeModal();
			});
	});
</script>
</head>
<body>
	<div id="container">
		<nav class="navbar navbar-light">
			<div class="col-md-12">
				<div class="col-md-2">
					<a class="portrait" href="/ofertasob"> <img
						src="imgs/logoTransparente.png" class="logo" alt="">
					</a>
				</div>
				<div class="row" style="margin-top: 10px">
					<h3 class="col-md-10 mx-auto">Ofertas Ouro Branco - Pesquise
						ofertas e economize!</h3>
				</div>
			</div>
		</nav>
		<div id="body">
			<div class="container-form">
				<div class="container mx-auto col-md-8">
					<div class="row">
						<form class="container" action="search" method="post">
							<input class="form-control form-position" style="float: left"
								type="search" name="query"
								placeholder="Insira as palavras-chave do produto" autofocus
								required> <input class="btn btn-outline-success"
								type="submit" value="Pesquisar">
						</form>
					</div>
				</div>
			</div>
			<div class="border mx-auto col-md-8" style="padding: 10px">
				<div class="alert alert-success text-center rounded">
					<h3 class="alert-heading">Pesquise ofertas no botão acima!</h3>
				</div>
				<div class="alert alert-info rounded center-things">
					<h6>Viu alguma oferta e gostaria de compartilhar ela com a
						comunidade?! Nos envie!</h6>
					<input class="btn btn-outline-info" style="display: inline-block"
						type="submit" value="Enviar Oferta" onClick="openModal()">

				</div>
				<table class="table table-condensed table-hover">
					<thead>

					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		<footer id="footer" class="footer fixed-bottom">
			<div class="container mx-auto" style="margin-top: 10px">
				<div class="row">
					<div class="social-icons mx-auto">
						<a href="https://www.facebook.com/tulio.moreira.2" target="_blank"
							class="col-md-1"><i class="fa fa-facebook"></i></a> <a
							href="https://www.linkedin.com/in/tulio-moreira77"
							target="_blank" class="col-md-1"><i class="fa fa-linkedin"></i></a>
						<a href="https://github.com/tuliomoreira77" target="_blank"
							class="col-md-1"><i class="fa fa-github"></i></a> <a href="#"
							target="_blank" class="col-md-1"><i class="fa fa-envelope"></i></a>
					</div>
				</div>
				<div class="row">
					<div class="footer-copyright mx-auto text-center py-3">
						© 2019 Copyright: <b>Automática - Software e Automação</b> <br>Este
						site não possui nenhuma ligação com os estabelecimentos.<br>Todas
						as informações são retiradas dos panfletos disponíveis aos
						consumidores nos supermercados.
					</div>
				</div>
			</div>
		</footer>
	</div>
	<div id="modalBg" class="bg-modal">
		<div id="modalCt" class="modal-content">
			<div class="close" onClick="closeModal()">+</div>
			<div>
				<form action="" id="enviar">
					<h5>Digite as informações da oferta:</h5>
					<input class="form-control modal-input" type="text"
						placeholder="Nome do Produto" name="nome" required> <input
						class="form-control modal-input" type="text" placeholder="Preço"
						name="preco" required> <input
						class="form-control modal-input" type="text"
						placeholder="Quantidade (Se não souber digite 'nulo')"
						name="quantidade" required> <input
						class="form-control modal-input" type="text"
						placeholder="Supermercado" name="mercado" required> <input
						class="btn btn-outline-info" type="submit" value="Enviar">
				</form>
			</div>
		</div>
	</div>
</body>
</html>