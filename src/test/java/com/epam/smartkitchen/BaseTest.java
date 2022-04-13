package com.epam.smartkitchen;

import com.epam.smartkitchen.service.impl.TestHelper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Configuration class for unit and integration tests
 */
@ExtendWith(SpringExtension.class)
public abstract class BaseTest extends TestHelper {

}
