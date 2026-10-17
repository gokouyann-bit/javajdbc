package tech.synelia;

import java.sql.Time;
import java.util.Date;

public class Emploi_du_temps {
    private String matiere;
    private Date jour;
    private Time heure;
    private int id;



    public String getMatiere(){
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Date getJour(){
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public Time getHeure(){
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }


    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
