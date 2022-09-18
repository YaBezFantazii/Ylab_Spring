package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Storage {
    private static long autoIncUser = 0;
    private static long autoIncBook = 0;
    private Map<Long, User> userStorage = new HashMap<>();
    private Map<Long, Book > bookStorage = new HashMap<>();

    public User newUser (User user){
        user.setId(autoIncUser);
        userStorage.put(autoIncUser, user);
        bookStorage.put(autoIncUser,new Book());
        autoIncUser++;
        return user;
    }

    public Book newBook (Book book){
        book.setId(autoIncBook);
        bookStorage.put(autoIncBook,book);
        autoIncBook++;
        return book;
    }

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

    public Book updateBook (Book book) {
        if (book.getId()==null){
            book.setId(autoIncBook);
            autoIncBook++;
        }
        // Если book не содержит title, autor, pageCount=0 , и книги с ее id нет в хранилище, то ничего не делаем
        if (Objects.isNull(bookStorage.get(book.getId())) &&
                   book.getPageCount()==0 && Objects.isNull(book.getTitle()) && Objects.isNull(book.getAuthor())){
            return book;
        // Удаление книги из хранилища по id, если она не содержит title, autor, pageCount=0
        } else if (book.getPageCount()==0 && Objects.isNull(book.getTitle()) && Objects.isNull(book.getAuthor())){
            bookStorage.remove(book.getId());
            return book;
        // Вставляем книгу, если ее нет в хранилище
        } else if (Objects.isNull(bookStorage.get(book.getId()))) {
            bookStorage.put(book.getId(),book);
            return book;
        }
        // Заменяем книгу
        return bookStorage.replace(book.getId(),book);
    }

    public User getUser (Long id){
        if (Objects.isNull(userStorage.get(id))){
            throw new NotFoundException("User not created");
        }
        return userStorage.get(id);
    }

    public List<Book> getBook (Long id) {
        if (Objects.isNull(bookStorage.get(id))){
            return new ArrayList<>();
        }
        return bookStorage.entrySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(s-> s.getValue().getUserId()==id)
                .map(Map.Entry::getValue)
                .toList();
    }

    public void deleteUser (Long id){
        userStorage.remove(id);
    }

    public void deleteBook (Long id) {
        bookStorage.entrySet().removeIf(s-> Objects.equals(s.getValue().getUserId(), id));
    }

}
