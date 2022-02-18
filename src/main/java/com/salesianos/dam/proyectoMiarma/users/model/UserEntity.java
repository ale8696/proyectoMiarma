package com.salesianos.dam.proyectoMiarma.users.model;

import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.Post;
import com.salesianos.dam.proyectoMiarma.validation.anotations.Unique;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class UserEntity implements UserDetails {


    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Unique
    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;

    @Unique
    private String nick;

    private String password;

    private String avatar;

    private String fullName;

    private boolean privacity;

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    @Builder.Default
    @OneToMany
    private List<Follow> following = new ArrayList<>();;

    @Builder.Default
    @OneToMany
    private List<Follow> followers = new ArrayList<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    // Methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return nick;
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

}
