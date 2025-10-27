package org.vvspickles.userservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vvspickles.userservice.Models.Token;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {


    Optional<Token> findByValueAndDeletedAtAndExpiryAtGreaterThan(String tokenValue, Boolean isDeleted, Date expiryDate);

//    @Query("SELECT t FROM Token t WHERE t.value = :value AND t.deletedAt = :deletedAt AND t.expiryAt > :expiryAt")
//    Optional<Token> findValidToken(
//            @Param("value") String value,
//            @Param("deletedAt") boolean deletedAt,
//            @Param("expiryAt") Date expiryAt
//    );

    Optional<Token> findByValue(String value);
    Optional<Token> findById(int id);
    Optional<Token> findByValueAndDeletedAt(String token, boolean isDeleted);
}
