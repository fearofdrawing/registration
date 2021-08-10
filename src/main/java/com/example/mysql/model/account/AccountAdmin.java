package com.example.mysql.model.account;

import com.example.mysql.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "Admin_Accounts")
public class AccountAdmin{
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long Id;
    @Column(unique = true)
    private User user;
    private List<String> alertMessages;
}
