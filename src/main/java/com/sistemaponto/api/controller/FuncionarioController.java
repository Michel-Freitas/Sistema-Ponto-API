package com.sistemaponto.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.api.dto.FuncionarioDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.service.CadastrarFuncionarioService;
import com.sistemaponto.service.FuncionarioService;

@RestController
@RequestMapping(value = "funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	
	@Autowired
	private CadastrarFuncionarioService cadastrarFuncionarioService;
	
	@Autowired
	private FuncionarioService funcionarioService;

	@PostMapping
	public ResponseEntity<Response<CadastroFuncionarioDTO>> cadastrarFuncionario(@Valid @RequestBody CadastroFuncionarioDTO novoFuncionario, BindingResult result) {
		return this.cadastrarFuncionarioService.cadastrarFuncionario(novoFuncionario, result);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDTO>> atualizarFuncionario(@PathVariable(name = "id", required = true) Long id, @RequestBody FuncionarioDTO funcionario, BindingResult result) {
		return funcionarioService.atualizarFuncionario(id, funcionario, result);
	}
}
