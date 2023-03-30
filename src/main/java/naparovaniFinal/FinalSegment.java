package main.java.naparovaniFinal;

import main.java.typeOfBoat.TypeBoat;

import java.lang.reflect.Type;

public class FinalSegment {

    private int id;
    private String jmenoZdroje;
    private String noteSteam;
    private TypeBoat system;
    private int kolikratOpakovat;
    private String dobaDelay;
    private String code;

    public FinalSegment(int id, String jmenoZdroje, String noteSteam, TypeBoat system, int kolikratOpakovat, String dobaDelay, String code) {
        this.id = id;
        this.jmenoZdroje = jmenoZdroje;
        this.noteSteam = noteSteam;
        this.system = system;
        this.kolikratOpakovat = kolikratOpakovat;
        this.dobaDelay = dobaDelay;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJmenoZdroje() {
        return jmenoZdroje;
    }

    public void setJmenoZdroje(String jmenoZdroje) {
        this.jmenoZdroje = jmenoZdroje;
    }

    public String getNoteSteam() {
        return noteSteam;
    }

    public void setNoteSteam(String noteSteam) {
        this.noteSteam = noteSteam;
    }

    public TypeBoat getSystem() {
        return system;
    }

    public void setSystem(TypeBoat system) {
        this.system = system;
    }

    public int getKolikratOpakovat() {
        return kolikratOpakovat;
    }

    public void setKolikratOpakovat(int kolikratOpakovat) {
        this.kolikratOpakovat = kolikratOpakovat;
    }

    public String getDobaDelay() {
        return dobaDelay;
    }

    public void setDobaDelay(String dobaDelay) {
        this.dobaDelay = dobaDelay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
