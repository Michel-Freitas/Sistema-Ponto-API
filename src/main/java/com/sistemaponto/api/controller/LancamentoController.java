package com.sistemaponto.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaponto.api.dto.LancamentoDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.service.LancamentoService;

@RestController
@RequestMapping(value = "/lancamento")
@CrossOrigin(origins = "*")
public class LancamentoController {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LancamentoDTO>>> listarPorFuncionarioId(@PathVariable(name = "funcionarioId", required = true) Long funcionarioId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		return this.lancamentoService.listarPorFuncionarioId(funcionarioId, pag, ord, dir);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDTO>> listarPorId(@PathVariable(name = "id", required = true) Long id) {
		return lancamentoService.listarPorId(id);
	}

	@PostMapping
	public ResponseEntity<Response<LancamentoDTO>> adicionarLancamento(@Valid @RequestBody LancamentoDTO novoLancamento, BindingResult result) {		
		return lancamentoService.adicionarLancamento(novoLancamento, result);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDTO>> atualizarLancamento(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody LancamentoDTO lancamentoDto, BindingResult result) {
		return lancamentoService.atualizarLancamento(id, lancamentoDto, result);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> deleteLancamento(@PathVariable(name = "id", required = true) Long id) {
		return lancamentoService.deletarLancamento(id);
	}

}
