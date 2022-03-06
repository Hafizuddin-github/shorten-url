package com.parenthexis.course;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class BaseTestCase {

    protected PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

}
