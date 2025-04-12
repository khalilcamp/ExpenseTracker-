package com.contas.model;

public class Despesas {
    private int id;
    private int usuarioId;
    private String name;
    private double valor;
    private String vencimento;
    private String status;
    private String mes;

    public Despesas(int id, int usuarioId, String name, double valor, String vencimento, String status, String mes){
        this.id = id;
        this.usuarioId = usuarioId;
        this.name = name;
        this.valor = valor;
        this.vencimento = vencimento;
        this.status = status;
        this.mes = mes;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getUsuarioId() {
        return usuarioId;
    }
    public double getValor() {
        return valor;
    }
    public String getVencimento() {
        return vencimento;
    }
    public String getStatus() {
        return status;
    }
    public String getMes() {
        return mes;
    }

    // Setters

    public void setId(){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    public void setValor(double valor){
        this.valor = valor;
    }
    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setMes(String mes) {
        this.mes = mes;
    }
}
