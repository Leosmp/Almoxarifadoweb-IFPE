/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.model.classes;

public class Produto {
    
    private Integer codigo;
    private String nome;
    private String marca;
    private String categoria;
    private String descricao;    

    public Produto() {
	}    

	public Produto(Integer codigo, String nome, String marca, String categoria, String descricao) {
		this.codigo = codigo;
		this.nome = nome;
		this.marca = marca;
		this.categoria = categoria;
		this.descricao = descricao;
	}

	public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	@Override
	public String toString() {
		return "Produto [codigo=" + codigo + ", nome=" + nome + ", marca=" + marca + ", categoria=" + categoria
				+ ", descricao=" + descricao + "]";
	}    
    
}
