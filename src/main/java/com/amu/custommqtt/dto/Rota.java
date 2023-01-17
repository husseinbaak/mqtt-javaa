package com.amu.custommqtt.dto;

public class Rota {
    private String[][] rota;
    private int baslangic;

    public Rota() {
    }

    public Rota(String[][] rota, int baslangic) {
        this.rota = rota;
        this.baslangic = baslangic;
    }

    public String[][] getRota() {
        return rota;
    }

    public void setRota(String[][] rota) {
        this.rota = rota;
    }

    public int getBaslangic() {
        return baslangic;
    }

    public void setBaslangic(int baslangic) {
        this.baslangic = baslangic;
    }
}
