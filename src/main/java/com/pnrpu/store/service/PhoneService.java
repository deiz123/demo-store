package com.pnrpu.store.service;

import com.pnrpu.store.persistence.entity.Phone;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PhoneService {
    @Nullable
    Phone findById(Integer id);

    List<Phone> findWithOrder(String order);
}
