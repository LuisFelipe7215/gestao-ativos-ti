package com.felipe.gestaoativosti.repository;

import com.felipe.gestaoativosti.domain.Equipamento;
import com.felipe.gestaoativosti.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Page<Equipamento> findByStatus(Status status, Pageable pageable);
}
