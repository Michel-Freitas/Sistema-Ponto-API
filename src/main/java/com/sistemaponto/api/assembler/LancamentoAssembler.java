package com.sistemaponto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistemaponto.api.dto.LancamentoDTO;
import com.sistemaponto.entity.Lancamento;

@Component
public class LancamentoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public LancamentoDTO toLancamentoDto(Lancamento lancamento) {
		return modelMapper.map(lancamento, LancamentoDTO.class);
	}
	
	public Lancamento toLancamento(LancamentoDTO novoLancamento) {
		return modelMapper.map(novoLancamento, Lancamento.class);
	}
}
