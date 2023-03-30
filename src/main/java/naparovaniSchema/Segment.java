package main.java.naparovaniSchema;

import main.java.typeOfSegment.Typ;

public class Segment {

    private int id;
    private Typ typ;
    private int delka;
    private int procento;

    public Segment(int id, Typ typ, int delka, int procento) {
        this.id = id;
        this.typ = typ;
        this.delka = delka;
        this.procento = procento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public int getDelka() {
        return delka;
    }

    public void setDelka(int delka) {
        this.delka = delka;
    }

    public int getProcento() {
        return procento;
    }

    public void setProcento(int procento) {
        this.procento = procento;
    }
}
