package com.example.demo.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
public class User {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	String firstName;
	String lastName;
	String middleName;
	String email;
	@Transient
	List<Card> registeredCards;
	String password;
}
