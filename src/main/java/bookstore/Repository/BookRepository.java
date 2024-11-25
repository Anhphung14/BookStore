package bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import bookstore.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findBySubcategoryName(String subcategoryName, Pageable pageable);
}

