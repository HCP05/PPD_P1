package objectProtocol;

import domain.TicketDTO;
import domain.Vanzare;

import java.util.List;

public class VanzareResponse implements Response {
    private Vanzare vanzare;

    public VanzareResponse(Vanzare vanzare) {
        this.vanzare = vanzare;
    }

    public Vanzare getVanzare() {
        return vanzare;
    }

    public void setVanzare(Vanzare vanzare) {
        this.vanzare = vanzare;
    }
}
