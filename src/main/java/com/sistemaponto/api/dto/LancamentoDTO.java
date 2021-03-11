package com.sistemaponto.api.dto;

import java.util.Date;

import lombok.Data;

@Data
public class LancamentoDTO {

	private Long id;
	private Date data;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;
}
