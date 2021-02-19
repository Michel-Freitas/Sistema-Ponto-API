package com.sistemaponto.api.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmpresaDTO {
	
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private List<FuncionarioDTO> funcionarios;

}
