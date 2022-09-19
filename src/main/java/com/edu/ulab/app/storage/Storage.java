package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;

import java.util.*;

public interface Storage {

    User newUser (User user);
    Book newBook (Book book);
    User updateUser (User user);
    Book updateBook (Book book);
    User getUser (Long id);
    List<Book> getBook (Long id);
    void deleteUser (Long id);
    void deleteBooks (Long id);

}
