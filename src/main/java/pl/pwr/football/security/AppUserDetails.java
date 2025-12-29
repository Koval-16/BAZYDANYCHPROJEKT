package pl.pwr.football.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {

    private final Integer id;
    private final String imie;
    private final String nazwisko;

    public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                          Integer id, String imie, String nazwisko) {
        super(username, password, authorities);
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Integer getId() { return id; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
}