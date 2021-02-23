package com.sistemaponto.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.sistemaponto.api.assembler.EmpresaAssembler;
import com.sistemaponto.api.assembler.FuncionarioAssembler;
import com.sistemaponto.api.dto.CadastroEmpresaDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.entity.Empresa;
import com.sistemaponto.entity.Funcionario;
import com.sistemaponto.entity.enums.PerfilEnum;
import com.sistemaponto.repository.EmpresaRepository;
import com.sistemaponto.repository.FuncionarioRepository;
import com.sistemaponto.service.CadastrarEmpresaService;

@Service
public class CadastrarEmpresaServiceImpl implements CadastrarEmpresaService{
	
	private static final Logger logCadastrarEmpresaServiceImpl = LoggerFactory.getLogger(CadastrarEmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaAssembler empresaAssembler;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FuncionarioAssembler funcionarioAssembler;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public ResponseEntity<Response<CadastroEmpresaDTO>> cadastrarEmpresa(CadastroEmpresaDTO novaEmpresa, BindingResult result) {
		
		Response<CadastroEmpresaDTO> responseCadastroEmpresa = new Response<CadastroEmpresaDTO>();
		
		logCadastrarEmpresaServiceImpl.info("Cadastrando empresa: {}", novaEmpresa.toString());
		Empresa empresa = this.empresaAssembler.toEmpresa(novaEmpresa);
		logCadastrarEmpresaServiceImpl.info("Montando empresa: {}", empresa.toString());
		Funcionario funcionario = this.funcionarioAssembler.toFuncionarioEmpresa(novaEmpresa);
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		logCadastrarEmpresaServiceImpl.info("Montando funcionario: {}", funcionario.toString());
		
		validarDadosExistentes(empresa, funcionario, result);
		
		if(result.hasErrors()) {
			logCadastrarEmpresaServiceImpl.error("Erro validando dados de Cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> responseCadastroEmpresa.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(responseCadastroEmpresa);
		}
		
		empresa = this.empresaRepository.save(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioRepository.save(funcionario);
		
		responseCadastroEmpresa.setData(novaEmpresa);
		return ResponseEntity.ok(responseCadastroEmpresa);
	}
	
	public void validarDadosExistentes(Empresa empresa, Funcionario funcionario, BindingResult result) {
		this.empresaRepository.findByCnpj(empresa.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("Empresa", "Empresa já existente")));
		this.funcionarioRepository.findByCpf(funcionario.getCpf()).ifPresent(func -> result.addError(new ObjectError("Funcionário", "Funcionário já existente")));
		this.funcionarioRepository.findByEmail(funcionario.getEmail()).ifPresent(func -> result.addError(new ObjectError("Funcionário", "Email já existente")));
	}
}
