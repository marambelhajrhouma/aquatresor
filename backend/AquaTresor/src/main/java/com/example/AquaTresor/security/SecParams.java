package com.example.AquaTresor.security;

public interface SecParams {
    long EXP_TIME = 10 * 24 * 60 * 60 * 1000; // 10 jours en millisecondes
    String SECRET = "hanabelhadj27@gmail.com"; // Clé secrète pour signer le JWT
    String PREFIX = "Bearer ";
}