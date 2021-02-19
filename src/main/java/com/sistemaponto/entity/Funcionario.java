package com.sistemaponto.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sistemaponto.entity.enums.PerfilEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "funcionario")
public class Funcionario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String cpf;

    @Column(name = "valor_hora", nullable = true)
    private BigDecimal valorHora;

    @Column(name = "qtd_horas_trabalhadas", nullable = true)
    private Float qtdHorasTrabalhadoDia;

    @Column(name = "qtd_horas_almoco", nullable = true)
    private Float qtdHorasAlmoco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilEnum perfil;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao" ,nullable = false)
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao" ,nullable = false)
    private Date dataAtualizacao;

    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa empresa;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;

    @Transient
    public Optional<BigDecimal> getValorHoraOpt(){
        return Optional.ofNullable(this.valorHora);
    }

    @Transient
	public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
		return Optional.ofNullable(this.qtdHorasTrabalhadoDia);
	}

    @PreUpdate
    public void preUpdate(){
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist(){
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
}
