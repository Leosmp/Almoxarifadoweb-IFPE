/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.model.classes;

public class ItemEntrada {
    
    private int codigo;
    private Produto produto;
    private int quantidade;
    private LoteEntrada loteEntrada;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

	public LoteEntrada getLoteEntrada() {
		return loteEntrada;
	}

	public void setLoteEntrada(LoteEntrada loteEntrada) {
		this.loteEntrada = loteEntrada;
	}

	@Override
	public String toString() {
		return "ItemEntrada [codigo=" + codigo + ", produto=" + produto + ", quantidade=" + quantidade
				+ ", loteEntrada=" + loteEntrada + "]";
	}
	
	
    
}
