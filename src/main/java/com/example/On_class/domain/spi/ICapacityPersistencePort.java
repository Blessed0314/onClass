package com.example.On_class.domain.spi;

import com.example.On_class.domain.model.Bootcamp;
import com.example.On_class.domain.model.Capacity;

import java.util.List;

public interface ICapacityPersistencePort {
    void saveCapacity(Capacity capacity);
    List<Capacity> getAllCapacities(Integer page, Integer size, boolean orderFlag, boolean ascendingFlag);
}
