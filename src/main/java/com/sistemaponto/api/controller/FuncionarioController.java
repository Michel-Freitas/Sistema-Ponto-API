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

import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.service.CadastrarFuncionarioService;

@RestController
@RequestMapping(value = "funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	
	@Autowired
	private CadastrarFuncionarioService cadastrarFuncionarioService;

	@PostMapping
	public ResponseEntity<Response<CadastroFuncionarioDTO>> cadastrarFuncionario(@Valid @RequestBody CadastroFuncionarioDTO novoFuncionario, BindingResult result) {
		return this.cadastrarFuncionarioService.cadastrarFuncionario(novoFuncionario, result);
	}
}
