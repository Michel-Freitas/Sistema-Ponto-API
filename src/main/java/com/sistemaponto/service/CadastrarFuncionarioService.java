package com.sistemaponto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.api.response.Response;

public interface CadastrarFuncionarioService {
	
	ResponseEntity<Response<CadastroFuncionarioDTO>> cadastrarFuncionario(CadastroFuncionarioDTO novoFuncionario, BindingResult result);

}
