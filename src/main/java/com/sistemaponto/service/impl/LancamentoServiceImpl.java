package com.sistemaponto.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.sistemaponto.api.assembler.LancamentoAssembler;
import com.sistemaponto.api.dto.LancamentoDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.entity.Funcionario;
import com.sistemaponto.entity.Lancamento;
import com.sistemaponto.repository.FuncionarioRepository;
import com.sistemaponto.repository.LancamentoRepository;
import com.sistemaponto.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{
	
	private static final Logger logLancamentoServiceImpl = LoggerFactory.getLogger(LancamentoServiceImpl.class);
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoAssembler lancamentoAssembler;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Value("${paginacao_qtd_por_pagina}")
	private int qtdPorPagina;
	
	public ResponseEntity<Response<Page<LancamentoDTO>>> listarPorFuncionarioId(Long id, int pag, String ord, String dir) {
		Response<Page<LancamentoDTO>> responsePageLancamentoDto = new Response<Page<LancamentoDTO>>();
		PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		
		Page<Lancamento> resultPagLancamento = this.lancamentoRepository.findByFuncionarioId(id, pageRequest);
		Page<LancamentoDTO> lancamentoDto = resultPagLancamento.map(lanc -> this.lancamentoAssembler.toLancamentoDto(lanc));
		
		responsePageLancamentoDto.setData(lancamentoDto);
		return ResponseEntity.ok(responsePageLancamentoDto);
	}
	
	public ResponseEntity<Response<LancamentoDTO>> listarPorId(Long id) {
		Response<LancamentoDTO> responseLancamentoDto = new Response<LancamentoDTO>();
		
		Optional<Lancamento> resultLancamento = this.lancamentoRepository.findById(id);
		
		if(resultLancamento.isEmpty()) {
			logLancamentoServiceImpl.error("Lançamento não encontrado para o ID {}", id);
			responseLancamentoDto.getErros().add("Lançamento não encontrado para o ID " + id);
			return ResponseEntity.badRequest().body(responseLancamentoDto);
		}
		
		LancamentoDTO lancamentoDto =  this.lancamentoAssembler.toLancamentoDto(resultLancamento.get());
		
		responseLancamentoDto.setData(lancamentoDto);
		
		return ResponseEntity.ok(responseLancamentoDto);		
	}
	
	
	public ResponseEntity<Response<LancamentoDTO>> adicionarLancamento(LancamentoDTO novoLancamento, BindingResult result) {
		
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		
		Lancamento lancamento = this.lancamentoAssembler.toLancamento(novoLancamento);
		System.out.println(lancamento.toString());
		validarDadosLancamento(lancamento, result);
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoRepository.save(lancamento);
		response.setData(novoLancamento);
		return ResponseEntity.ok(response);
		
	}
	
	public ResponseEntity<Response<LancamentoDTO>> atualizarLancamento(Long id, LancamentoDTO updateLancamento, BindingResult result) {
		Response<LancamentoDTO> response = new Response<LancamentoDTO>();
		
		Lancamento lancamento = this.lancamentoAssembler.toLancamento(updateLancamento);
		
		lancamento.setId(id);
		validarDadosLancamento(lancamento, result);
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		lancamento = this.lancamentoRepository.save(lancamento);
		response.setData(updateLancamento);
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<Response<String>> deletarLancamento(Long id) {
		
		Response<String> response = new Response<String>();
		Optional<Lancamento> lancamento = this.lancamentoRepository.findById(id);

		if (!lancamento.isPresent()) {
			response.getErros().add("Erro ao remover lançamento. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.lancamentoRepository.deleteById(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	
	
	
	
	
	
	
	
	public void validarDadosLancamento(Lancamento lancamento, BindingResult result) {
		if(lancamento.getFuncionario().getId() == null) {
			result.addError(new ObjectError("Funcionario", "Funcionário não informado"));
			return;
		}
		
		Optional<Funcionario> funcionario = this.funcionarioRepository.findById(lancamento.getFuncionario().getId());
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
		}
	}
	
	
	
}






