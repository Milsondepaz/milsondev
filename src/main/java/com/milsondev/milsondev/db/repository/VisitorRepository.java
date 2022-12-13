package com.milsondev.milsondev.db.repository;
import com.milsondev.milsondev.db.entities.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>  {

    @Query(value = "SELECT * FROM tb_visitor v where v.ip = ?1 and v.format_data = ?2", nativeQuery = true)
    Optional<Visitor> findCustom(String ip, String data);
}
