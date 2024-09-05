package me.romo.sgstock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "publicId", nullable = false, unique = true)
    private String publicId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "money", nullable = false)
    @ColumnDefault("10000000")
    private int money;

    @Builder
    public User(String publicId, String password, int money, String auth){
        this.publicId = publicId;
        this.password = password;
        this.money = money;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return publicId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Adds money to the user's account.
     *
     * @param amount the amount to be added
     */
    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative");
        }
        this.money += amount;
    }

    /**
     * Subtracts money from the user's account.
     *
     * @param amount the amount to be subtracted
     * @throws IllegalArgumentException if the amount is negative or exceeds the current money
     */
    public void reduceMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to subtract cannot be negative");
        }
        if (amount > this.money) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.money -= amount;
    }
}
