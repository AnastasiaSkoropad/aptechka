package com.savchenko.aptechka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cabinet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,
            length    = 255)
    private String name;

    private String iconUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name               = "cabinet_users",
               joinColumns        = @JoinColumn(name = "cabinet_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> owners = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() { createdAt = Instant.now(); }
}
