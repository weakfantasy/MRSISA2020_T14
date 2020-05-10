function prikaziListuKlinika(){
	console.log("nesto");
	$.ajax({
		url: "/klinika/listaKlinika",
		type: "GET",
		success: function (result) {
			localStorage.setItem("result", JSON.stringify(result));
			location.href = "listaKlinika.html" ;
		}
	,
		error: function(result) {
			alert("Something is wrong with your request.(get details)");
		}
    });	
}

function prikaziKlinike(){
	
	var retrivedData = localStorage.getItem('result');
	console.log(retrivedData);
	var retrivedJSON = JSON.parse(retrivedData);
	console.log(retrivedJSON);
	
	var elements = retrivedJSON.length;

	
	var i = 0;
	for(i; i<elements; i++){
		var text  = ` <a href ="http://localhost:8080/klinika/${retrivedJSON[i].naziv}" id = "${retrivedJSON[i].naziv}">`
		$("#table1")
		.append($("<tr>")
				.append($("<td>")
						.append($(text)
								.text(retrivedJSON[i].naziv)
									.append($("</a>"))))
				.append($("<td>")
					.text(retrivedJSON[i].grad))
				.append($("<td>")
					.text(retrivedJSON[i].ocjenaKlinike))
			);
	}
	
	$(window).click(function(e){
		var name = e.target.id;
	//	alert(e.target.id);
		$.ajax({
			url: "/klinika/" + name,
			type: "GET",
			success: function (result) {
				localStorage.setItem("klinikaPodaci", JSON.stringify(result));
				location.href = "klinika.html" ;
			}
				,	
			error: function(result) {
				toastr.error("Something is wrong with your request.(get details)");
			}
	    });	
	});

}

function ucitajPodatkeKlinike(){
	var klinikaPodaci = localStorage.getItem('klinikaPodaci');
	var klinikaPodaci = JSON.parse(klinikaPodaci);
	console.log(klinikaPodaci);
	$("#naziv").append(klinikaPodaci.naziv);
	$("#grad").append(klinikaPodaci.grad);
	$("#ocjena").append(klinikaPodaci.ocjenaKlinike);
	//TO DO iscitati iz baze sta znaci koji broj pregleda
	$("#tipKlinike").append(klinikaPodaci.tipKlinike);
	for(i in klinikaPodaci.ljekari)
		{
		//TO DO srediti izgled ocjene
		var ocjena = klinikaPodaci.ljekari[i].prosjecnaOcjena? "(" +  klinikaPodaci.ljekari[i].prosjecnaOcjena + ")" : " ";
		$("#tim-ljekara")
			.append($("<h3>")
					.append("dr " + klinikaPodaci.ljekari[i].ime + " "+ klinikaPodaci.ljekari[i].prezime + ocjena )
			.append($("</h3>")))
			.append($("<p>")
					.append(klinikaPodaci.ljekari[i].specijalizacija)
			.append($("</p>"))
					);
		console.log(klinikaPodaci.ljekari[i].ime);
		}
//	$("#telefon").append(localStorage.getItem('tel'));
//	$("#jbo").append(localStorage.getItem('jbo'));
//	$("#email").append(localStorage.getItem('email'));
}






