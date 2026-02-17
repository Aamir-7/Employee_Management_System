package com.employee.management.service;

import com.employee.management.entity.RefreshToken;
import com.employee.management.repository.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    @Transactional
    public RefreshToken createToken(UUID employeeId){

        refreshTokenRepo.deleteByEmployeeId(employeeId);

        RefreshToken token=new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setEmployeeId(employeeId);
        token.setExpiryDate(LocalDateTime.now().plusDays(7));

        return refreshTokenRepo.save(token);
    }

    public RefreshToken validateToken(String token) {

        RefreshToken refreshToken = refreshTokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }
}
