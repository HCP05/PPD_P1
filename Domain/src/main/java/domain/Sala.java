package domain;

public class Sala extends Entity<Integer>{
    Integer nr_locuri;

    public Sala(Integer id,Integer nr_locuri) {
        super(id);
        this.nr_locuri = nr_locuri;
    }

    public Integer getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(Integer nr_locuri) {
        this.nr_locuri = nr_locuri;
    }
}
