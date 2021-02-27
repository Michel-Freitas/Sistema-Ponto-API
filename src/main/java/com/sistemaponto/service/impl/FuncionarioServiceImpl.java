package com.sistemaponto.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.sistemaponto.api.assembler.FuncionarioAssembler;
import com.sistemaponto.api.dto.FuncionarioDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.entity.Funcionario;
import com.sistemaponto.repository.FuncionarioRepository;
import com.sistemaponto.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{
	
	private static final Logger logFuncionarioServiceImpl = LoggerFactory.getLogger(FuncionarioServiceImpl.class);
	
	@Autowired
	private FuncionarioAssembler funcionarioAssembler;

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public ResponseEntity<Response<FuncionarioDTO>> atualizarFuncionario(Long id, FuncionarioDTO funcionarioDto, BindingResult result) {
		
		Response<FuncionarioDTO> responseFuncionarioDto = new Response<FuncionarioDTO>();
		
		Optional<Funcionario> resultFuncionario = funcionarioRepository.findById(id);
		
		if(resultFuncionario.isEmpty()) {
			result.addError(new ObjectError("Funcionario", "Funcionário não encontrado."));
			result.getAllErrors().forEach(erro -> responseFuncionarioDto.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(responseFuncionarioDto);
		}
		
		validarDadosAtualizacaoFuncionario(resultFuncionario.get(), funcionarioDto, result);
		
		if(result.hasErrors()) {
			logFuncionarioServiceImpl.error("Erro validando dados de atualização do funcionário {}", result.getAllErrors());
			result.getAllErrors().forEach(erro -> responseFuncionarioDto.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(responseFuncionarioDto);
		}
		
		Funcionario funcionario = this.funcionarioAssembler.toFuncionario(resultFuncionario.get(), funcionarioDto);
		
		this.funcionarioRepository.save(funcionario);
		responseFuncionarioDto.setData(funcionarioDto);
		return ResponseEntity.ok(responseFuncionarioDto);
		
	}
	
	public void validarDadosAtualizacaoFuncionario(Funcionario funcionario, FuncionarioDTO funcionarioDto, BindingResult result) {
		
		if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			funcionarioRepository.findByEmail(funcionarioDto.getEmail()).ifPresent(func -> result.addError(new ObjectError("Funcionario", "Email já cadastrado")));
		}
		
	}
	
}
