package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final Storage<Book> storage;

    private final BookMapper bookMapper;
@Autowired
    public BookServiceImpl( BookMapper bookMapper, Storage<Book> storage) {
        this.storage = storage;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        if (Objects.isNull(bookDto)){
            throw new NotFoundException("BookDto is null");
        }
        Book book = bookMapper.bookDtoToBook(bookDto);
        return bookMapper.bookToBookDto(storage.newEntity(book));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (Objects.isNull(bookDto)){
            throw new NotFoundException("BookDto is null");
        }
        Book book = bookMapper.bookDtoToBook(bookDto);
        return bookMapper.bookToBookDto(storage.updateEntity(book));
    }

    @Override
    public List<BookDto> getBookById(Long id) {
        if (Objects.isNull(id)){
            throw new NotFoundException("Long is null");
        }
        return storage.getListEntity(id).stream().map(bookMapper::bookToBookDto).toList();
    }

    @Override
    public void deleteBookById(Long id) {
        if (Objects.isNull(id)){
            throw new NotFoundException("Long is null");
        }
        //storage.deleteBooks(id);
        storage.deleteEntity(id);
    }
}
