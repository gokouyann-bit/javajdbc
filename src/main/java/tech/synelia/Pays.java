package tech.synelia;

import java.util.ArrayList;
import java.util.List;

public class Pays {
    private Integer id;
    private String nom;
    private Double superficie;
    private Long nombreHabitant;
    private List<String> capitales = new ArrayList<>();

    public Pays(Integer id, String nom, Double superficie, Long nombreHabitant) {
        this.id = id;
        this.nom = nom;
        this.superficie = superficie;
        this.nombreHabitant = nombreHabitant;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Double getSuperficie() { return superficie; }
    public void setSuperficie(Double superficie) { this.superficie = superficie; }

    public Long getNombreHabitant() { return nombreHabitant; }
    public void setNombreHabitant(Long nombreHabitant) { this.nombreHabitant = nombreHabitant; }

    // Renvoie la vraie liste (pas une copie), indispensable pour que le DAO puisse y ajouter
    public List<String> getCapitales() { return capitales; }
    public void setCapitales(List<String> capitales) { this.capitales = capitales; }

    @Override
    public String toString() {
        return "Pays{id=" + id + ", nom='" + nom + "', capitales=" + capitales + "}";
    }
}