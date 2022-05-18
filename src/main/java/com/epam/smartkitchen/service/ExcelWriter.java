package com.epam.smartkitchen.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface ExcelWriter {
    void write(List<?> data, HttpServletResponse response );
}

