package com.milsondev.milsondev.db.repository;

import com.milsondev.milsondev.db.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {


}
