package com.teachme.teachme.Repository;


import com.teachme.teachme.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    boolean existsByName(String name);

    Optional<Authority> findByName(String name);

    int deleteByName(String name);
}
