package com.nandaliyan.mylibapi.constant;

public class AppPath {
    
    public final static String ADMIN_PATH = "/admin";
    public final static String MEMBER_PATH = "/members";
    public final static String BOOK_PATH = "/books";
    public final static String GENRE_PATH = "/genres";
    public final static String AUTHOR_PATH = "/authors";
    public final static String PUBLISHER_PATH = "/publishers";
    public final static String BORROWING_RECORD_PATH = "/borrowing-records";
    public final static String SEARCH = "/search";

    public final static String REGISTER_PATH = "/register";
    public final static String LOGIN_PATH = "/login";
    public final static String BORROW_PATH = "/borrow";
    public final static String RETURN_PATH = "/return";

    public final static String GET_BY_ID = "/{id}";
    public final static String GET_BY_NAME = "/{name}";
    public final static String GET_BY_URL_NAME = "/{urlName}";
    public final static String UPDATE_BY_ID = "/{id}";
    public final static String DELETE_BY_ID = "/{id}";

    public final static String GET_ALL_BOOKS = "/all-books";

    public final static String REGISTER_ADMIN_PATH = ADMIN_PATH + REGISTER_PATH;

}
