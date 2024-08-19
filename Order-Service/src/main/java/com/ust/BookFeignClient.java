package com.ust;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookFeignClient {
    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable("id") Long id);
}
