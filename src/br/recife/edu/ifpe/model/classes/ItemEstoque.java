/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.recife.edu.ifpe.model.classes;


public class ItemEstoque {
    
    private int codigo;
    private Produto produto;
    private int quantidade;
    
    public ItemEstoque() {
	}

    public ItemEstoque(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

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

	public void adicionar(int quantidade) {
        this.quantidade += quantidade;
    }
    
    public void subtrair(int quantidade) {
        this.quantidade -= quantidade;
    }
    
    
}
