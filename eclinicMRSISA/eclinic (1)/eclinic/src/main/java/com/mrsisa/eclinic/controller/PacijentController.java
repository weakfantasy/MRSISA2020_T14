package com.mrsisa.eclinic.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mrsisa.eclinic.dto.AKcDTO;
import com.mrsisa.eclinic.dto.KlinikaDTO;
import com.mrsisa.eclinic.dto.PacijentDTO;

import com.mrsisa.eclinic.dto.ZahtjeviZaReigstracijuDTO;
import com.mrsisa.eclinic.dto.ZdravstveniKartonDTO;
import com.mrsisa.eclinic.model.AdminKlinickogCentra;
import com.mrsisa.eclinic.model.Klinika;
import com.mrsisa.eclinic.model.Pacijent;
import com.mrsisa.eclinic.model.Prijava;
import com.mrsisa.eclinic.model.ZahtjeviZaRegistraciju;
import com.mrsisa.eclinic.model.ZdravstveniKarton;
import com.mrsisa.eclinic.repository.ZahtjeviRegRepository;
import com.mrsisa.eclinic.service.KlinikaService;
import com.mrsisa.eclinic.service.PacijentService;
import com.mrsisa.eclinic.service.ZahtjeviRegService;
import com.mrsisa.eclinic.service.ZdravKartonService;


@RestController
@RequestMapping("/homepagePacijent1")
@CrossOrigin("*")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private ZahtjeviRegService regService;
	@Autowired
	private ZdravKartonService kartonService;
	
	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasRole('PACIENT')")
	public ResponseEntity<PacijentDTO> getHomepagePacijent(@PathVariable("email") String email)
	{
		System.out.println(email);
		 Pacijent p = pacijentService.findOneByEmail(email);
		 return new ResponseEntity<>(new PacijentDTO(p,null, null), HttpStatus.OK);
	}

	@RequestMapping(value = "/profilPacijent/{email}" ,  method = RequestMethod.GET)
	@PreAuthorize("hasRole('PACIENT')")
	public ResponseEntity<PacijentDTO>  getProfilPacijent(@PathVariable("email") String email){
		System.out.println(email);
		 Pacijent p = pacijentService.findOneByEmail(email);
		 return new ResponseEntity<>(new PacijentDTO(p,null, null), HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/profilPacijent/azurirajPodatke/{email}" ,  method = RequestMethod.GET)

	@PreAuthorize("hasRole('PACIENT')")
	public ResponseEntity<PacijentDTO>  getPotatkePacijent(@PathVariable("email") String email){
		 Pacijent p = pacijentService.findOneByEmail(email);
		 return new ResponseEntity<>(new PacijentDTO(p,null, null), HttpStatus.OK);
	}	
	

	@PutMapping(value = "/profilPacijent/azurirajProfil/{email}", consumes =  "application/json")
	@PreAuthorize("hasRole('PACIENT')")
	public ResponseEntity<PacijentDTO> updatePacijentDTO(@PathVariable("email") String email,@RequestBody	 PacijentDTO pacijentDTO) {
		
		System.out.println(pacijentDTO);
		System.out.println(pacijentDTO.getJedBrojOsiguranika());
//		Pacijent pacijent = pacijentService.findOne(pacijentDTO.getJedBrojOsiguranika());
		Pacijent pacijent = pacijentService.findOneByEmail(email);
		System.out.println(pacijent);
		if (pacijent == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		pacijent.setAdresaPrebivalsta(pacijentDTO.getAdresaPrebivalista());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
		pacijent.setGrad(pacijentDTO.getGrad());
		System.out.println("kao da je setovo");

		pacijent = pacijentService.save(pacijent);
		return new ResponseEntity<>(new PacijentDTO(pacijent), HttpStatus.OK);
	}
	@PostMapping(value = "/registracija", consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<String> getRegistrationRequests(@RequestBody ZahtjeviZaReigstracijuDTO podaci)throws ParseException{
		System.out.println("usao u reg");
		System.out.println(podaci.geteAdresa());
		ZahtjeviZaRegistraciju zahtjev = new ZahtjeviZaRegistraciju();
		zahtjev.setIme(podaci.getIme());
		zahtjev.setPrezime(podaci.getPrezime());
		zahtjev.setGrad(podaci.getGrad());
		zahtjev.setBrojTelefona(podaci.getBrojTelefona());
		zahtjev.setJbo(podaci.getJbo());
		zahtjev.setLozinka(podaci.getLozinka());
		zahtjev.setAdresaPrebivalista(podaci.getAdresaPrebivalista());
		zahtjev.seteAdresa(podaci.geteAdresa());
		zahtjev.setDrzava(podaci.getDrzava());
//		zahtjev.setPrihvacen(false);
		System.out.println(zahtjev);
		
		zahtjev = regService.save(zahtjev);
		
		return new ResponseEntity<>("Zahjev je poslat.", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/kartonPacijenta/{email}" ,  method = RequestMethod.GET)
	@PreAuthorize("hasRole('PACIENT')")
	public ResponseEntity<ZdravstveniKartonDTO>  getKarton(@PathVariable("email") String email){
		System.out.println(email);
		Pacijent p = pacijentService.findOneByEmail(email);
		 ZdravstveniKarton k = p.getZdravstveniKarton();
		 ZdravstveniKartonDTO kar = new ZdravstveniKartonDTO(k);
		 return new ResponseEntity<>(kar, HttpStatus.OK);
	}	
	
}	
		
		
		
		