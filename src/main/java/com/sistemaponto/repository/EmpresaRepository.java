package com.sistemaponto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaponto.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
	
	Optional<Empresa> findByCnpj(String cnpj);
}
