package com.sistemaponto.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.sistemaponto.api.assembler.EmpresaAssembler;
import com.sistemaponto.api.assembler.FuncionarioAssembler;
import com.sistemaponto.api.dto.CadastroFuncionarioDTO;
import com.sistemaponto.api.response.Response;
import com.sistemaponto.entity.Empresa;
import com.sistemaponto.entity.Funcionario;
import com.sistemaponto.entity.enums.PerfilEnum;
import com.sistemaponto.repository.EmpresaRepository;
import com.sistemaponto.repository.FuncionarioRepository;
import com.sistemaponto.service.CadastrarFuncionarioService;

@Service
public class CadastrarFuncionarioServiceImpl implements CadastrarFuncionarioService{
	
	private static final Logger logCadastrarFuncionarioServiceImpl = LoggerFactory.getLogger(CadastrarFuncionarioServiceImpl.class);
	
	@Autowired
	private FuncionarioAssembler funcionarioAssembler;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaAssembler empresaAssembler;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public ResponseEntity<Response<CadastroFuncionarioDTO>> cadastrarFuncionario(CadastroFuncionarioDTO novoFuncionario, BindingResult result) {
		
		Response<CadastroFuncionarioDTO> responseCadastroFuncionario = new Response<CadastroFuncionarioDTO>();
		
		Funcionario funcionario = this.funcionarioAssembler.toFuncionario(novoFuncionario);	
		Empresa empresaFuncionario = this.empresaAssembler.toEmpresaFuncionario(novoFuncionario);
		
		validarDadosExistentes(empresaFuncionario, funcionario, result);
		
		if(result.hasErrors()) {
			logCadastrarFuncionarioServiceImpl.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(erro -> responseCadastroFuncionario.getErros().add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(responseCadastroFuncionario);
		}
		
		Optional<Empresa> empresa = this.empresaRepository.findByCnpj(empresaFuncionario.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		this.funcionarioRepository.save(funcionario);
		
		responseCadastroFuncionario.setData(novoFuncionario);
		return ResponseEntity.ok(responseCadastroFuncionario);
	}
	
	public void validarDadosExistentes(Empresa novaEmpresa, Funcionario novoFuncionario, BindingResult result) {
		Optional<Empresa> empresa = this.empresaRepository.findByCnpj(novaEmpresa.getCnpj());
		if(!empresa.isPresent()) {
			result.addError(new ObjectError("Empresa", "Empresa não cadastrada"));
		}
		
		this.funcionarioRepository.findByCpf(novoFuncionario.getCpf()).ifPresent(func -> result.addError(new ObjectError("Funcionário", "CPF já cadastrado")));
		this.funcionarioRepository.findByEmail(novoFuncionario.getEmail()).ifPresent(func -> result.addError(new ObjectError("Funcionário", "Email já cadastrado.")));
	}
}
