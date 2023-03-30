package main.java.naparovaniSource;

public class Naparovani {

    private int idNaparovani;
    private String nameNamarovani;
    private String autorNaparovani;
    private String noteNaparovani;
    private String code;

    public Naparovani(int idNaparovani, String nameNamarovani, String autorNaparovani, String noteNaparovani, String code) {
        this.idNaparovani = idNaparovani;
        this.nameNamarovani = nameNamarovani;
        this.autorNaparovani = autorNaparovani;
        this.noteNaparovani = noteNaparovani;
        this.code = code;
    }

    public int getIdNaparovani() {
        return idNaparovani;
    }

    public void setIdNaparovani(int idNaparovani) {
        this.idNaparovani = idNaparovani;
    }

    public String getNameNamarovani() {
        return nameNamarovani;
    }

    public void setNameNamarovani(String nameNamarovani) {
        this.nameNamarovani = nameNamarovani;
    }

    public String getAutorNaparovani() {
        return autorNaparovani;
    }

    public void setAutorNaparovani(String autorNaparovani) {
        this.autorNaparovani = autorNaparovani;
    }

    public String getNoteNaparovani() {
        return noteNaparovani;
    }

    public void setNoteNaparovani(String noteNaparovani) {
        this.noteNaparovani = noteNaparovani;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
