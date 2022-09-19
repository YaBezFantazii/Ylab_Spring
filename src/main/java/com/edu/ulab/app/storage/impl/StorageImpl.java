package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageImpl implements Storage {
    private static long autoIncUser = 0;
    private static long autoIncBook = 0;
    private final Map<Long, User> userStorage = new HashMap<>();
    private final Map<Long, Book> bookStorage = new HashMap<>();
    @Override
    public User newUser (User user){
        user.setId(autoIncUser);
        userStorage.put(autoIncUser, user);
        autoIncUser++;
        return user;
    }
    @Override
    public Book newBook (Book book){
        // Проверка, есть ли уже такое id в хранилище
        while (Objects.nonNull(bookStorage.get(autoIncBook))){
            autoIncBook++;
        }
        book.setId(autoIncBook);
        bookStorage.put(autoIncBook,book);
        autoIncBook++;
        return book;
    }
    @Override
    public User updateUser (User user){
        if (user.getId()==null){
            return newUser(user);
        }
        if (userStorage.get(user.getId())==null){
            throw new NotFoundException("User not created");
        }
        // Полностью заменяем пользователя
        userStorage.remove(user.getId());
        userStorage.put(user.getId(),user);
        return user;
    }
    @Override
    public Book updateBook (Book book) {
        if (book.getId() == null) {
            book.setId(autoIncBook);
            autoIncBook++;
        }
        // Вставляем книгу, если ее нет в хранилище
        if (Objects.isNull(bookStorage.get(book.getId()))) {
            bookStorage.put(book.getId(), book);
            return book;
        }
        //Проверка, что id пользователя переданной книги, и id пользователя книги в хранилище совпадают
        if (bookStorage.get(book.getId()).getUserId() == book.getUserId()) {
            // Удаление книги из хранилища по id, если она не содержит title, author, pageCount=0
            if (book.getPageCount() == 0 && Objects.isNull(book.getTitle()) && Objects.isNull(book.getAuthor())) {
                bookStorage.remove(book.getId());
                return book;
            }
            return bookStorage.replace(book.getId(), book);
        }
        return null;
    }

    @Override
    public User getUser (Long id){
        if (Objects.isNull(userStorage.get(id))){
            throw new NotFoundException("User not created");
        }
        return userStorage.get(id);
    }
    @Override
    public List<Book> getBook (Long id) {
        return bookStorage.entrySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(s-> s.getValue().getUserId()==id)
                .map(Map.Entry::getValue)
                .toList();
    }
    @Override
    public void deleteUser (Long id){
        userStorage.remove(id);
    }
    @Override
    public void deleteBooks (Long id) {
        bookStorage.entrySet().removeIf(s-> Objects.equals(s.getValue().getUserId(), id));
    }
}
