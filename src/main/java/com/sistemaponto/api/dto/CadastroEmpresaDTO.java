package com.sistemaponto.api.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CadastroEmpresaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Nome não pode ser vazio.")
    @Length(min = 3, max = 80, message = "Nome deve conter entre 3 e 80")
    private String nome;

    @NotEmpty(message = "Email não pode ser vazio.")
    @Length(min = 10, max = 150, message = "Email deve conter entre 10 e 150 caracteres.")
    @Email(message = "Email Inválido!")
    private String email;

    @NotEmpty(message = "Senha não pode ser vazia.")
    private String senha;

    @NotEmpty(message = "CPF não pode ser vazio.")
    @CPF(message = "CPF Inválido!")
    private String cpf;

    @NotEmpty(message = "CNPJ não pode ser vazio.")
    @CNPJ(message = "CPF Inválido!")
    private String cnpj;

    @NotEmpty(message = "Razão Social não pode ser vazio.")
    @Length(min = 5, max = 150, message = "Razão Social deve conter entre 5 e 150 caracteres.")
    private String razaoSocial;
}
