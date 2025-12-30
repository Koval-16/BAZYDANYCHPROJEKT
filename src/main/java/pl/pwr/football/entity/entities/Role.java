package pl.pwr.football.entity.entities;

import jakarta.persistence.*;

/*
1 - Administrator
2 - Sedzia
3 - Trener
4 - Pilkarz
*/

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RolaID")
    private Integer id;

    @Column(name = "RolaNazwa", unique = true, nullable = false)
    private String name;

    public Role() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}