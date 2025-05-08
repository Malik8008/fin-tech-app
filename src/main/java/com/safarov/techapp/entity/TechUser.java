package com.safarov.techapp.entity;

import com.safarov.techapp.dto.request.AccountRequestDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_users")
public class TechUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "user_name")
    String name;
    @Column(name = "user_surname")
    String surname;
    @Column(name = "password")
    String password;
    @Column(name = "pin", unique = true)
    String pin;
    @Column(name = "role")
    String role;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    List<Account> accountList;

    public void addAccountToUser(List<AccountRequestDTO> accountRequestDTOList) {
        accountList = new ArrayList<>();
        accountRequestDTOList.forEach(accountRequestDTO -> accountList.add(
                Account.builder()
                        .balance(accountRequestDTO.getBalance())
                        .currency(accountRequestDTO.getCurrency())
                        .accountNo(accountRequestDTO.getAccountNo())
                        .isActive(accountRequestDTO.getIsActive())
                        .user(this).build()
        ));
    }
}
