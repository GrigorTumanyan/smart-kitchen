package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.repository.MenuRepository;
import com.epam.smartkitchen.service.MenuService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xmlunit.util.Mapper;

import static com.epam.smartkitchen.service.impl.TestHelper.menuItem;
import static com.epam.smartkitchen.service.impl.TestHelper.menuItemCreateDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MenuServiceImplTest {
    private MenuItemCreateDto menuItemCreateDto;


    private MenuRepository menuRepository = Mockito.mock(MenuRepository.class);

    @Mock
    private Mapper<MenuItem, MenuItemCreateDto> itemMenuItemCreateDtoMapper;


    @InjectMocks
    private final MenuService menuService = Mockito.spy(new MenuServiceImpl(menuRepository));


    @BeforeEach
    void setUp() {
        menuItemCreateDto = menuItemCreateDto();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addMenu() {

        when(menuRepository.save(menuItem())).thenReturn(menuItem());

        MenuItemCreateDto menuItemCreateDto = menuService.addMenu(menuItem());

        assertEquals(menuItemCreateDto, this.menuItemCreateDto);
    }
}