package ru.javaops.startup.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static ru.javaops.startup.common.util.Util.getEffectiveClass;

/**
 * id is the same as User.id (not autogenerate)
 */
@Entity
@Table(name = "user_data")
@NoArgsConstructor
@Getter
@Setter
public class UserData {
    @Id
    @Column(name = "user_id")
    @NotNull
    private int userId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userId")
    private Set<Contact> contacts = new HashSet<>();

    public UserData(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserData: " + userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getEffectiveClass(this) != getEffectiveClass(o)) return false;
        UserData userData = (UserData) o;
        return userId == userData.userId;
    }

    @Override
    public int hashCode() {
        return userId;
    }
}
