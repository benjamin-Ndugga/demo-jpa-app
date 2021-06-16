package com.flyhub.demo.repositories;

import com.flyhub.demo.entities.AppRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Benjamin E Ndugga
 */
@Repository
public interface AppRoleRepository extends CrudRepository<AppRole, Long> {

    @Query("SELECT r FROM AppRole r WHERE r.roleName = :name")
    public Optional<AppRole> findRoleByName(@Param("name") String role);

    @Query("SELECT r FROM AppRole r WHERE r.roleName IN (:names)")
    public List<AppRole> findRoleByNames(@Param("names") List<String> roleNames);

}
