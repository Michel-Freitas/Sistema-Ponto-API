package com.sistemaponto.api.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FuncionarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private BigDecimal valorHora;
	private Float qtdHorasTrabalhadoDia;
	private Float qtdHorasAlmoco;

}
