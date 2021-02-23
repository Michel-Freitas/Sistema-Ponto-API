package com.sistemaponto.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sistemaponto.api.dto.CadastroEmpresaDTO;
import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.api.dto.EmpresaDTO;
import com.sistemaponto.api.dto.FuncionarioDTO;
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
	
	public EmpresaDTO toEmpresaDTO(Empresa empresa) {
		
		EmpresaDTO empresaDto = modelMapper.map(empresa, EmpresaDTO.class);
		
		for(int i = 0; i > empresa.getFuncionarios().size(); i++) {
			FuncionarioDTO funcionarioDto = modelMapper.map(empresa.getFuncionarios().get(i), FuncionarioDTO.class);
			empresaDto.getFuncionarios().add(funcionarioDto);
		}
		
		return empresaDto;
	}
}
