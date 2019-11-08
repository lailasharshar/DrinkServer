package com.example.demo.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Accessors(chain = true)
public class Machine {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	String name;
	String location;
	Double volume;
	Double remaining;
}
