package com.sistemaponto.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaponto.api.dto.CadastroEmpresaDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.service.CadastrarEmpresaService;

@RestController
@RequestMapping(value = "empresa")
@CrossOrigin(origins = "*")
public class EmpresaController {
	
	@Autowired
	private CadastrarEmpresaService cadastrarEmpresaService;
	
	@PostMapping
	public ResponseEntity<Response<CadastroEmpresaDTO>> cadastrarEmpresa(@Valid @RequestBody CadastroEmpresaDTO novaEmpresa, BindingResult result) {
		return cadastrarEmpresaService.cadastrarEmpresa(novaEmpresa, result);
	}

}
