package com.sistemaponto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.sistemaponto.api.dto.CadastroEmpresaDTO;
import com.sistemaponto.api.response.Response;

public interface CadastrarEmpresaService {


	ResponseEntity<Response<CadastroEmpresaDTO>> cadastrarEmpresa(CadastroEmpresaDTO novaEmpresa, BindingResult result);
}
