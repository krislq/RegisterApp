package com.krislq.register.bean;

public class Member {
    private String name;
    private String eoeId;
    private boolean register = false;

    
    public Member(){
        this(null, null);
    }
    public Member(String name,String id) {
        this.name = name;
        this.eoeId = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEoeId() {
        return eoeId;
    }

    public void setEoeId(String eoeId) {
        this.eoeId = eoeId;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

}
