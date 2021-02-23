package com.sistemaponto.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sistemaponto.api.assembler.EmpresaAssembler;
import com.sistemaponto.api.dto.EmpresaDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.entity.Empresa;
import com.sistemaponto.repository.EmpresaRepository;
import com.sistemaponto.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{
	
	private static final Logger logEmpresaServiceImpl = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaAssembler empresaAssembler;
	
	public ResponseEntity<Response<EmpresaDTO>> buscarEmpresaPorCnpj(String cnpj) {
		
		Response<EmpresaDTO> responseEmpresaDTO = new Response<EmpresaDTO>();		
		Optional<Empresa> empresa = empresaRepository.findByCnpj(cnpj);
		
		if(empresa.isEmpty()) {
			logEmpresaServiceImpl.error("Empresa não encontrada para o CNPJ {}", cnpj);
			responseEmpresaDTO.getErros().add("Empresa não encontrada para o CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(responseEmpresaDTO);
		}
		
		EmpresaDTO empresaDto = empresaAssembler.toEmpresaDTO(empresa.get());
		responseEmpresaDTO.setData(empresaDto);
		return ResponseEntity.ok(responseEmpresaDTO);
		
	}
	
}