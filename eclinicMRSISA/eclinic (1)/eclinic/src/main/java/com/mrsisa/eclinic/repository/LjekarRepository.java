package com.mrsisa.eclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mrsisa.eclinic.model.Klinika;
import com.mrsisa.eclinic.model.Ljekar;

public interface LjekarRepository extends JpaRepository<Ljekar, Long>{
	//List<Ljekar> findAllById(Long id);
	}
