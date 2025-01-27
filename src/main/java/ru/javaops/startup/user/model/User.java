package ru.javaops.startup.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.startup.common.HasIdAndEmail;
import ru.javaops.startup.common.mapper.Default;
import ru.javaops.startup.common.model.TimestampEntity;
import ru.javaops.startup.common.validation.NoHtml;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimestampEntity implements HasIdAndEmail, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 64)
    @NoHtml   // https://stackoverflow.com/questions/17480809
    private String email;

    @Column(name = "first_name", nullable = true)
    @NotBlank
    @Size(max = 32)
    @NoHtml
    private String firstName;

    @Column(name = "last_name", nullable = true)
    @Size(max = 32)
    @NoHtml
    private String lastName;

    @Column(name = "avatar_url", nullable = true)
    @Size(max = 256)
    @NoHtml
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = EnumSet.noneOf(Role.class);

    public User(User u) {
        this(u.id, u.email, u.firstName, u.lastName, u.avatarUrl, u.startpoint, u.endpoint, u.roles);
    }

    @Default
    public User(Integer id, String email, String firstName, String lastName, String avatarUrl, Role... roles) {
        this(id, email, firstName, lastName, avatarUrl, new Date(), null, Arrays.asList(roles));
    }

    public User(Integer id, String email, String firstName, String lastName, String avatarUrl, Date startpoint, Date endpoint, Collection<Role> roles) {
        super(id, startpoint, endpoint);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarUrl = avatarUrl;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    @Override
    public String toString() {
        return "User:" + id + '[' + email + ']';
    }
}