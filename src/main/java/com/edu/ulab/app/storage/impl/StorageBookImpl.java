package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageBookImpl implements Storage <Book> {
    private static long autoIncBook = 1;
    private final Map<Long, Book> bookStorage = new HashMap<>();
    @Override
    public Book newEntity(Book book){
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
    public Book updateEntity (Book book) {
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
    public Book getEntity(Long id) {
        return null;
    }

    @Override
    public List<Book> getListEntity (Long id) {
        return bookStorage.entrySet()
                .stream()
                .filter(Objects::nonNull)
                .filter(s-> s.getValue().getUserId()==id)
                .map(Map.Entry::getValue)
                .toList();
    }
    @Override
    public void deleteEntity (Long id) {
        bookStorage.entrySet().removeIf(s-> Objects.equals(s.getValue().getUserId(), id));
    }
}
