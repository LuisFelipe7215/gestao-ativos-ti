package com.felipe.gestaoativosti.service;

import com.felipe.gestaoativosti.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipamentoService {
    private final EquipamentoRepository repository;

}
