package com.example.on_class.domain.api.usecase;

import com.example.on_class.domain.api.IBootcampServicePort;
import com.example.on_class.domain.exception.RepeatCapacitiesInListException;
import com.example.on_class.domain.model.Bootcamp;
import com.example.on_class.domain.model.Capacity;
import com.example.on_class.domain.model.Technology;
import com.example.on_class.domain.spi.IBootcampPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BootcampUseCaseTest {
    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;
    private IBootcampServicePort bootcampServicePort;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bootcampServicePort = new BootcampUseCase(bootcampPersistencePort);
    }

    @Test
    void testSaveBootcamp() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L, "Java", "Programming Language" ));

        List<Capacity> capacities = new ArrayList<>();
        capacities.add(new Capacity(1L, "Proof2", "Fronted", technologies));

        Bootcamp bootcamp = new Bootcamp(1L, "Proof3", "bootcamp description", capacities);
        bootcampServicePort.saveBootcamp(bootcamp);

        verify(bootcampPersistencePort, times(1)).saveBootcamp(bootcamp);
    }

    @Test
    void testValidationRepeatCapacity() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L, "Java", "Programming Language"));

        List<Capacity> capacities = new ArrayList<>();
        Capacity capacity = new Capacity(1L, "Proof2", "Fronted", technologies);
        capacities.add(capacity);
        capacities.add(capacity);

        Bootcamp bootcamp = new Bootcamp(1L,"Proof3", "Bootcamp description", capacities);

        assertThrows(RepeatCapacitiesInListException.class, () -> bootcampServicePort.saveBootcamp(bootcamp));
        verify(bootcampPersistencePort, times(0)).saveBootcamp(bootcamp);
    }

    @Test
    void testGetAllBootcamps(){
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(2L,"Java","Programing Language"));
        technologies.add(new Technology(3L,"Python","Programming Language"));

        List<Capacity> capacities = new ArrayList<>();
        Capacity capacity1 = new Capacity(1L, "Proof2", "Fronted", technologies);
        Capacity capacity2 = new Capacity(2L, "Proof3", "Backend", technologies);
        capacities.add(capacity1);
        capacities.add(capacity2);

        List<Bootcamp> expectedBootcampList = new ArrayList<>();
        expectedBootcampList.add(new Bootcamp(1L, "Proof1", "Proof description", capacities));
        expectedBootcampList.add(new Bootcamp(2L, "Proof2", "Proof description", capacities));

        when(bootcampPersistencePort.getAllBootcamps(1,10,true,true)).thenReturn(expectedBootcampList);

        List<Bootcamp> actualBootcampList = bootcampServicePort.getAllBootcamps(1,10,true,true);

        assertEquals(expectedBootcampList, actualBootcampList);
    }

}