package pl.pwr.football.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RolaID")
    private Integer id;

    @Column(name = "RolaNazwa", unique = true, nullable = false)
    private String nazwa;

    // --- Pusty konstruktor ---
    public Role() {
    }

    // --- RĘCZNE GETTERY I SETTERY ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNazwa() { return nazwa; } // To będzie potrzebne w Security!
    public void setNazwa(String nazwa) { this.nazwa = nazwa; }
}