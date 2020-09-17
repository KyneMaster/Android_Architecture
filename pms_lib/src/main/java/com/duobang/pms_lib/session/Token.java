package com.duobang.pms_lib.session;

public class Token {
    /**
     * id : 1
     * iat : 1560223127
     * exp : 1561087127
     */

    private int id;
    private int iat;
    private int exp;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIat() {
        return iat;
    }

    public void setIat(int iat) {
        this.iat = iat;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
