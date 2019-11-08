package com.example.demo.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity
@Accessors(chain = true)
public class Card {
	@Id
	String id;
	Long amount;
	Long userId;
	@Transient
	Double maxVolume;
}
