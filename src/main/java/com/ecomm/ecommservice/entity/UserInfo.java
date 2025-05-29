package com.ecomm.ecommservice.entity;

import com.ecomm.ecommservice.util.encryption.EncryptField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    @Convert(converter = EncryptField.class)
    private String password;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
