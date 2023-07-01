package org.example.model;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;

public class Objeto {
    @ExcelRow
    private int rowIndex;

    @ExcelCellName("Peso")
    private Double peso;

    @ExcelCellName("Valor 1")
    private Double valorUm;

    @ExcelCellName("Valor 2")
    private Double valorDois;

    @ExcelCellName("Valor 3")
    private Double valorTres;

    private Double valorTotal;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getValorUm() {
        return valorUm;
    }

    public void setValorUm(Double valorUm) {
        this.valorUm = valorUm;
    }

    public Double getValorDois() {
        return valorDois;
    }

    public void setValorDois(Double valorDois) {
        this.valorDois = valorDois;
    }

    public Double getValorTres() {
        return valorTres;
    }

    public void setValorTres(Double valorTres) {
        this.valorTres = valorTres;
    }

    public Double getValorTotal() {
        return getValorUm().doubleValue() + getValorDois().doubleValue() + getValorTres().doubleValue();
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "rowIndex=" + rowIndex +
                ", peso=" + peso +
                ", valorUm=" + valorUm +
                ", valorDois=" + valorDois +
                ", valorTres=" + valorTres +
                ", valorTotal=" + getValorTotal() +
                '}';
    }
}
