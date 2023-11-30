package com.intern.OnlineBookStore.util;

import com.intern.OnlineBookStore.dto.BookDto;
import com.intern.OnlineBookStore.dto.UserDto;
import com.intern.OnlineBookStore.model.Book;
import com.intern.OnlineBookStore.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomMapper {

    public static UserDto mapUserDto(User user){

        return new UserDto(user.getUserId(),user.getUsername(), user.getEmail(), user.getRole());
    }

    public static List<UserDto> mapUserDtos(List<User> users) {

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(CustomMapper.mapUserDto(user));
        }
        return userDtos;
    }

    public static List<BookDto> mapBookDto(List<Book> books) {

        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(new BookDto(book.getBookId(),book.getTitle(), book.getGenre(),book.getAuthor(),book.getPrice(),book.isAvailability()));
        }
        return bookDtos;
    }
}
