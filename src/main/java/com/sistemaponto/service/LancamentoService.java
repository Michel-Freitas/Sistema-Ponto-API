package com.sistemaponto.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.sistemaponto.api.dto.LancamentoDTO;
import com.sistemaponto.api.response.Response;

public interface LancamentoService {
	
	ResponseEntity<Response<Page<LancamentoDTO>>> listarPorFuncionarioId(Long id, int pag, String ord, String dir);
	
	ResponseEntity<Response<LancamentoDTO>> listarPorId(Long id);
	
	ResponseEntity<Response<LancamentoDTO>> adicionarLancamento(LancamentoDTO novoLancamento, BindingResult result);
	
	ResponseEntity<Response<LancamentoDTO>> atualizarLancamento(Long id, LancamentoDTO updateLancamento, BindingResult result);
	
	ResponseEntity<Response<String>> deletarLancamento(Long id);

}
