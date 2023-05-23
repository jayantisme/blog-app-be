package com.springboot.utils;

public class AppConstants {
    private AppConstants() {
        throw new IllegalStateException("Utility class");
    }
    public static final String DEFAULT_PAGE_NO = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIR = "asc";
}
