package bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import bookstore.Entity.BooksEntity;

@Repository
public interface BookRepository extends JpaRepository<BooksEntity, Integer> {
    Page<BooksEntity> findBySubcategoryName(String subcategoryName, Pageable pageable);
}

