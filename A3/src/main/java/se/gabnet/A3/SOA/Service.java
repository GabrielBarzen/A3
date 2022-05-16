package se.gabnet.A3.SOA;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.gabnet.A3.Shared.DatabaseAccess;

import java.util.List;

@RestController
public class Service {

    @GetMapping("/books")
    public List<?> getAllBooks() {
        return new DatabaseAccess().f_get_books();
    }
}
