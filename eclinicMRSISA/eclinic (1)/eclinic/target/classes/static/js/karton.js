var email = localStorage.getItem('email');
var token = localStorage.getItem('token');
console.log(token);

function kartonPacijenta()
{
	
	$.ajax({
		url: "/homepagePacijent1/kartonPacijenta/"+email,
		type: "GET",

		headers: { "Authorization": "Bearer " + token},
		success: function (result) {
			console.log(result);
			localStorage.setItem("alergije", result.alergije);
			localStorage.setItem("dioptrija", result.dioptrija);
			localStorage.setItem("krvnaGrupa", result.krvnaGrupa);
			localStorage.setItem("tezina", result.tezinaKg);
	
			localStorage.setItem("visina", result.visinaCm);
		
			location.href = "zdravstveniKarton.html";
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(get details)");
		}
    });	
}

function popuniKarton()
{
	console.log("popunjavanje kartona");
	$("#alergija").append(localStorage.getItem("alergije"));
	$("#dioptrija").append(localStorage.getItem('dioptrija'));
	$("#krvnaGrupa").append(localStorage.getItem('krvnaGrupa'));

	$("#tezina").append(localStorage.getItem('tezina'));
	$("#visina").append(localStorage.getItem('visina'));

}

//function istorijaOperacija()
//{
//	$.ajax({
//		url: "/homepagePacijent1/istorijaOperacija/"+email,
//		type: "GET",
//
//		headers: { "Authorization": "Bearer " + token},
//		success: function (result) {
//			console.log(result);
////			localStorage.setItem("alergije", result.alergije);
////			localStorage.setItem("dioptrija", result.dioptrija);
////			localStorage.setItem("krvnaGrupa", result.krvnaGrupa);
////			localStorage.setItem("tezina", result.tezinaKg);
////	
////			localStorage.setItem("visina", result.visinaCm);
//		
//			location.href = "istorijaOperacija.html";
//		},
//		error: function(result) {
//			toastr.error("Something is wrong with your request.(get details)");
//		}
//    });	
//}

function istorijaPregleda()
{
	
	$.ajax({
		url: "/homepagePacijent1/istorijaPregleda/"+email,
		type: "GET",
		headers: { "Authorization": "Bearer " + token},
		success: function (result) {
			console.log(result);
			localStorage.setItem('preglediPacijenta',  JSON.stringify(result));
//			localStorage.setItem("alergije", result.alergije);
//			localStorage.setItem("dioptrija", result.dioptrija);
//			localStorage.setItem("krvnaGrupa", result.krvnaGrupa);
//			localStorage.setItem("tezina", result.tezinaKg);
//	
//			localStorage.setItem("visina", result.visinaCm);
		
			location.href = "listaPregledaPacijenta.html";
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(get details)");
		}
    });	
}

function ucitajPodatkePregledaPacijenta(){
	var podaciPregledaPacijenta = localStorage.getItem('preglediPacijenta');
	var podaciPregledaPacijenta = JSON.parse(podaciPregledaPacijenta);
	console.log(podaciPregledaPacijenta);
	
	for( i in podaciPregledaPacijenta){
		$("#table2")
		.append($("<tr>")
				.append($("<td>")
								.text(podaciPregledaPacijenta[i].tipPregledaDTO.specijalizacija))
//									
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].ljekarDTO.klinika))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].ocjenaKlinike))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].adresaKlinike))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].grad))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].datum))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].vrijemePocetka))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].status))
				.append($("<td>")
					.text(podaciPregledaPacijenta[i].tipPregledaDTO.cijena + "€"))
			);
	}
	
}