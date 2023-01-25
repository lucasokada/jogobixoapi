package com.exercicios.jogobixo.infraestrutura.database.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ResultadoDiaJpaRepository extends JpaRepository<ResultadoDiaEntity, LocalDate> {

}
