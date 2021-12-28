package domain;

public class Vanzare extends Entity<Integer>{
    private Integer ID_Spectacol;
    private String dataVanzare;
    private Integer bilete_vandute;
    private Double suma;

    public Vanzare(Integer integer, Integer ID_Spectacol, String dataVanzare, Integer bilete_vandute, Double suma) {
        super(integer);
        this.ID_Spectacol = ID_Spectacol;
        this.dataVanzare = dataVanzare;
        this.bilete_vandute = bilete_vandute;
        this.suma = suma;
    }

    public Integer getID_Spectacol() {
        return ID_Spectacol;
    }

    public void setID_Spectacol(Integer ID_Spectacol) {
        this.ID_Spectacol = ID_Spectacol;
    }

    public String getDataVanzare() {
        return dataVanzare;
    }

    public void setDataVanzare(String dataVanzare) {
        this.dataVanzare = dataVanzare;
    }

    public Integer getBilete_vandute() {
        return bilete_vandute;
    }

    public void setBilete_vandute(Integer bilete_vandute) {
        this.bilete_vandute = bilete_vandute;
    }

    public Double getSuma() {
        return suma;
    }

    public void setSuma(Double suma) {
        this.suma = suma;
    }
}
