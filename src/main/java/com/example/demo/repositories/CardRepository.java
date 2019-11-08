package com.example.demo.repositories;

import com.example.demo.beans.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, String> {
}
