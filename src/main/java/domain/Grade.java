package domain;

import repository.HasID;

public class Grade implements HasID<String> {
    private String id;
    private String idStudent;
    private String idTema;
    private double nota;
    private int weekGiven;

    /**
     * Class Constructor
     * @param id - id-ul notei
     * @param idStudent - id-ul studentului
     * @param idTema - id-ul temei
     * @param gradeValue - valoarea notei
     */
    public Grade(String id, String idStudent, String idTema, double gradeValue, int weekGiven) {
        this.id = id;
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.nota = gradeValue;
        this.weekGiven = weekGiven;
    }

    /**
     * @return id-ul studentului
     */
    public String getIdStudent() {
        return idStudent;
    }

    /**
     * @return id-ul temei
     */
    public String getIdTema() {
        return idTema;
    }

    /**
     * @return valoarea notei
     */
    public double getNota() {
        return nota;
    }

    /**
     * Modifica valoarea unei note
     * @param nota - noua valoarea a notei
     */
    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getWeekGiven() {
        return weekGiven;
    }

    @Override
    public String toString() {
        return idStudent + "," + idTema + "," + nota + "," + weekGiven;
    }

    /**
     * @return id-ul notei
     */
    @Override
    public String getID() {
        return this.id;
    }

    /**
     * modifica id-ul notei
     * @param id - noul id
     */
    @Override
    public void setID(String id) {
        this.id = id;
    }

    /**
     * modifica id-ul studentului
     * @param idStudent - noul id
     */
    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    /**
     * modifica id-ul temei
     * @param idTema - noul id
     */
    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }
}
