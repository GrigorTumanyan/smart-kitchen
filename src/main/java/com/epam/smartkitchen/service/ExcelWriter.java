package com.epam.smartkitchen.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface ExcelWriter {
    HttpServletResponse write(List<?> data, HttpServletResponse response /* String outputPath */);
}

