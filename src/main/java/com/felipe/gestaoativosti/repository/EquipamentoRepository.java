package com.felipe.gestaoativosti.repository;

import com.felipe.gestaoativosti.domain.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    
}
