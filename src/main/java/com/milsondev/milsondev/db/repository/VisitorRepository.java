package com.milsondev.milsondev.db.repository;
import com.milsondev.milsondev.db.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>  {
}
