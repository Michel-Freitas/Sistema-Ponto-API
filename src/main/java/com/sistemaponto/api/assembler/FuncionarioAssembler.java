package com.sistemaponto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistemaponto.api.dto.CadastroEmpresaDTO;
import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.entity.Funcionario;

@Component
public class FuncionarioAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Funcionario toFuncionario(CadastroFuncionarioDTO novoFuncionario) {
		return modelMapper.map(novoFuncionario, Funcionario.class);
	}
	
	public Funcionario toFuncionarioEmpresa(CadastroEmpresaDTO novaEmpresa) {
		return modelMapper.map(novaEmpresa, Funcionario.class);
	}
	
}
