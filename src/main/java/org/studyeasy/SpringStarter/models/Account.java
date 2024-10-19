package org.studyeasy.SpringStarter.models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;


    
    @Email(message="Invalid Email")
    @NotEmpty(message="Email mising")
    private String email;
    @NotEmpty(message="Password mising")
    private String password;
    @NotEmpty(message="Firstname mising")
    private String firstname;
    @NotEmpty(message="Lastname mising")
    private String lastname;
    private String gender;
    @Min(value=18)
    @Max(value=99)
    private int age;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date_of_birth;

    private String photo;

    private String role;
    @OneToMany(mappedBy= "account")
    private List<Post> posts;
    
    private String token;

    private LocalDateTime password_reset_token_expiry;

    
    @ManyToMany
    @JoinTable(
        name="account_authority",
        joinColumns={@JoinColumn(name="account_id",referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="authority_id",referencedColumnName="id")}
    )
    private Set<Authority> authorities = new HashSet<>();
}
