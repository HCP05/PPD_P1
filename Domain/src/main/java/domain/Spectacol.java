package domain;

import java.util.ArrayList;
import java.util.List;

public class Spectacol extends Entity<Integer>{
    private String data_spectacol;
    private String titlu;
    private Double pret_bilet;
    private Double sold;
    private Integer idSala;

    public Spectacol(Integer integer, String data_spectacol, String titlu, Double pret_bilet, Double sold, Integer idSala) {
        super(integer);
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
        this.sold = sold;
        this.idSala = idSala;
    }

    public String getData_spectacol() {
        return data_spectacol;
    }

    public void setData_spectacol(String data_spectacol) {
        this.data_spectacol = data_spectacol;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Double getPret_bilet() {
        return pret_bilet;
    }

    public void setPret_bilet(Double pret_bilet) {
        this.pret_bilet = pret_bilet;
    }

    public Double getSold() {
        return sold;
    }

    public void setSold(Double sold) {
        this.sold = sold;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }
}
