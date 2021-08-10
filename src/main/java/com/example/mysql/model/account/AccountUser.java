package com.example.mysql.model.account;

import com.example.mysql.model.book.Book;
import com.example.mysql.model.issuedBook.IssuedBook;
import com.example.mysql.model.user.User;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "User_Accounts")
public class AccountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column (unique = true)
    private User user;
    @Column (name = "alert_messages")
    private List<String> alertMessages;
    @Column(name = "issued_books")
    private List<IssuedBook> activeIssuedBooks;
    @Column (name ="reserved_books")
    List<Book> reservedBooks;
    @Column (name = "user_history")
    List<IssuedBook> historyIssuedBooks;


}
