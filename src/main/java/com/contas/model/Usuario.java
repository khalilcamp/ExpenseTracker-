package com.contas.model;

public class Usuario {
    private int id;
    private String name;
    private String email;
    private String senhaHash;
    public Usuario(int id, String name, String email, String senhaHash){
        this.id = id;
        this.name = name;
        this.email = email;
        this.senhaHash = senhaHash;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getSenhaHash(){
        return senhaHash;
    }

    // Setters

    public void setId(){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSenhaHash(String senhaHash){
        this.senhaHash = senhaHash;
    }
}
