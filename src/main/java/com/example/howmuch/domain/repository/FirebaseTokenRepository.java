package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Long> {

}
