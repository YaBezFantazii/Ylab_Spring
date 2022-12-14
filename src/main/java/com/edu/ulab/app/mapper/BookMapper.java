package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.web.request.create.BookRequest;
import com.edu.ulab.app.web.request.update.BookRequestUpdate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookRequestToBookDto(BookRequest bookRequest);
    BookDto bookRequestToBookDtoUpdate(BookRequestUpdate bookRequest);

    BookRequest bookDtoToBookRequest(BookDto bookDto);

    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);
}
