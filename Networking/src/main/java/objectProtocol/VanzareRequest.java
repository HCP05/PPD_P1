package objectProtocol;

import domain.TicketDTO;
import domain.Vanzare;

import java.util.List;

public class VanzareRequest implements Request {
    private Vanzare vanzare;

    public VanzareRequest(Vanzare vanzare) {
        this.vanzare = vanzare;
    }

    public Vanzare getVanzare() {
        return vanzare;
    }

    public void setVanzare(Vanzare vanzare) {
        this.vanzare = vanzare;
    }
}
