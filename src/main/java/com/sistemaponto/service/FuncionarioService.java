package com.sistemaponto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.sistemaponto.api.dto.FuncionarioDTO;
import com.sistemaponto.api.response.Response;

public interface FuncionarioService {
	
	ResponseEntity<Response<FuncionarioDTO>> atualizarFuncionario(Long id, FuncionarioDTO funcionarioDto, BindingResult result);

}
