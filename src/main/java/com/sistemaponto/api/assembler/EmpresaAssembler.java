package com.sistemaponto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistemaponto.api.dto.CadastroEmpresaDTO;
import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.entity.Empresa;

@Component
public class EmpresaAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Empresa toEmpresa(CadastroEmpresaDTO novaEmpresa) {
		return modelMapper.map(novaEmpresa, Empresa.class);		
	}
	
	public Empresa toEmpresaFuncionario(CadastroFuncionarioDTO novoFuncionario) {
		return modelMapper.map(novoFuncionario, Empresa.class);
	}
}
