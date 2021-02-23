package com.sistemaponto.service;

import org.springframework.http.ResponseEntity;

import com.sistemaponto.api.dto.EmpresaDTO;
import com.sistemaponto.api.response.Response;

public interface EmpresaService {
	
	ResponseEntity<Response<EmpresaDTO>> buscarEmpresaPorCnpj(String cnpj);

}