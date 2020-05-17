package com.pnrpu.store.service.impl;

import com.pnrpu.store.persistence.entity.Phone;
import com.pnrpu.store.persistence.repository.PhoneRepository;
import com.pnrpu.store.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public Phone findById(final Integer id) {
        return phoneRepository.findById(id).orElse(null);
    }

    @Override
    public List<Phone> findWithOrder(final String order) {
        switch (order) {
            case "modelAsc":
                return phoneRepository.findAll(Sort.by(Sort.Direction.ASC, "model"));
            case "modelDesc":
                return phoneRepository.findAll(Sort.by(Sort.Direction.DESC, "model"));
            case "memoryAsc":
                return phoneRepository.findAll(Sort.by(Sort.Direction.ASC, "memory"));
            case "memoryDesc":
                return phoneRepository.findAll(Sort.by(Sort.Direction.DESC, "memory"));
            case "priceAsc":
                return phoneRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
            case "priceDesc":
                return phoneRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
            default:
                return phoneRepository.findAll();
        }
    }
}
