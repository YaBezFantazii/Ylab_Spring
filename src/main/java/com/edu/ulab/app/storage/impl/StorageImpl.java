package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageImpl implements Storage  {
    private static long autoIncBook = 1;
    private static long autoIncUser = 1;
    private final Map<Long, Book> bookStorage = new HashMap<>();
    private final Map<Long, User> userStorage = new HashMap<>();

    @Override
    public User newUser(User user){
        user.setId(autoIncUser);
        userStorage.put(autoIncUser, user);
        autoIncUser++;
        return user;
    }

    @Override
    public Book newBook(Book book){
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
        if (book.getId() == 0) {
            while (Objects.nonNull(bookStorage.get(autoIncBook))){
                autoIncBook++;
            }
            book.setId(autoIncBook);
            autoIncBook++;
        }
        //Проверка, что book не содержит title, author, pageCount=0
        if (book.getPageCount() == 0 && Objects.isNull(book.getTitle()) && Objects.isNull(book.getAuthor())) {
            // Если книги с такой id нет в хранилище, то ничего не делаем.
            if (Objects.isNull(bookStorage.get(book.getId()))) {
                return null;
            }
            // Если книга с такой id есть в хранилище, и user id совпадают, то удаляем книгу из хранилища
            else if (bookStorage.get(book.getId()).getUserId() == book.getUserId()){
                bookStorage.remove(book.getId());
                return book;
            }
        } else {
            // Если книги с такой id нет в хранилище, то создаем ее.
            if (Objects.isNull(bookStorage.get(book.getId()))) {
                bookStorage.put(book.getId(), book);
                return book;
            }
            // Если книга с такой id есть в хранилище, заменяем ее на book
            else if (bookStorage.get(book.getId()).getUserId() == book.getUserId()) {
                return bookStorage.replace(book.getId(), book);
            }
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
    public List<Book> getListBook (Long id) {
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
    public void deleteBook (Long id) {
        bookStorage.entrySet().removeIf(s-> Objects.equals(s.getValue().getUserId(), id));
    }
}
