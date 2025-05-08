package com.safarov.techapp.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "balance")
    BigDecimal balance;
    @Column(name = "currency")
    Currency currency;
    @Column(name = "account_status")
    Boolean isActive;
    @Column(name = "account_number",unique = true)
    Integer accountNo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    TechUser user;
}
