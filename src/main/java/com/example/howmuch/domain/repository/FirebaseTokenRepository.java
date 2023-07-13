package com.example.howmuch.domain.repository;

import com.example.howmuch.domain.entity.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FirebaseTokenRepository extends JpaRepository<FirebaseToken, Long> {

}
