package com.flyhub.demo.repositories;

import com.flyhub.demo.entities.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Benjamin E Ndugga
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.username = :username")
    public Optional<AppUser> findByUserName(@Param("username") String username);
}
