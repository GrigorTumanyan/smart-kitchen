package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.mapper.WarehouseMapper;
import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.models.Warehouse;
import com.epam.smartkitchen.repository.WarehouseRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.WarehouseService;
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

import static com.epam.smartkitchen.service.impl.TestHelper.optionalWarehouse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WarehouseServiceImplTest {

    Warehouse warehouse = new Warehouse("halo",12.4,null,null,null);
    WarehouseDto warehouseDto = new WarehouseDto("halo",12.4,null,null,null,false);
    private final WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
    Warehouse warehouseForDeletedCase;




    @Mock
    private Mapper<Warehouse, WarehouseDto> itemWarehouseDtoMapper;


    @InjectMocks
    private final WarehouseService warehouseService = Mockito.spy(new WarehouseServiceImpl(warehouseRepository));


    @BeforeEach
    void setUp() {
        warehouseForDeletedCase = new Warehouse(null,null,null,null,null);
        warehouseForDeletedCase.setDeleted(true);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void addItem() {
        when(warehouseRepository.save(any())).thenReturn(warehouse);
        Response<ErrorResponse, WarehouseDto> warehouseDtoResponse = warehouseService.addItem(warehouseDto);
        Warehouse warehouse = WarehouseMapper.warehouseDtoToWarehouse(warehouseDtoResponse.getSuccessObject());
        assertEquals(this.warehouse,warehouse);


    }

    @Test
    void updateItem() {
        when(warehouseRepository.findById(any())).thenReturn(optionalWarehouse());

        when(warehouseRepository.save(any())).thenReturn(warehouse);

        Response<ErrorResponse, WarehouseDto> warehouseDtoResponse = warehouseService.updateItem("8", new WarehouseDto());

         assertEquals(warehouseDto , warehouseDtoResponse.getSuccessObject());
    }

    @Test
    void deleteItemById() {
        when(warehouseRepository.findById(any())).thenReturn(optionalWarehouse());

        when(warehouseRepository.save(optionalWarehouse().get())).thenReturn(warehouseForDeletedCase);

     Response<ErrorResponse, WarehouseDto> warehouseDto= warehouseService.deleteItemById("7");

      assertEquals(true,warehouseDto.getSuccessObject().getDeleted());
    }


    @Test
    void decreaseProductCountInWarehouse() {

    }

    @Test
    void getAll() {

    }



}
