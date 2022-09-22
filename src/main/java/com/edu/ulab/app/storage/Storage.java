package com.edu.ulab.app.storage;

import java.util.*;
import com.edu.ulab.app.entity.*;

public interface Storage {
    User newUser (User user);
    Book newBook (Book book);
    User updateUser (User user);
    Book updateBook (Book book);
    User getUser (Long id);
    List<Book> getListBook (Long id);
    void deleteUser (Long id);
    void deleteBook (Long id);
}
