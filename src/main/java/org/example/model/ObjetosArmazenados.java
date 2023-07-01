package org.example.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class ObjetosArmazenados {

    Set<Nodo> nodos = new LinkedHashSet<>();

    Set<Objeto> objetoList = new LinkedHashSet<>();

    private Double valorTotal = 0.0;
    private Double pesoAtingido = 0.0;

    public ObjetosArmazenados() {
    }

    public void addNodo(Nodo nodo){
        nodos.add(nodo);
    }

    public void addObjeto(Objeto obj){
        objetoList.add(obj);
    }

    public void addPeso(Double peso){
        this.pesoAtingido += peso;
    }

    public void addValor(Double valor){
        this.valorTotal += valor;
    }

    public Set<Nodo> getNodos() {
        return nodos;
    }


    public Double getPesoAtingido() {
        return pesoAtingido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public Set<Objeto> getObjetoList() {
        return objetoList;
    }
}
