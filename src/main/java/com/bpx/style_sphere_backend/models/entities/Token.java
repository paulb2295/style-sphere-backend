package com.bpx.style_sphere_backend.models.entities;

import com.bpx.style_sphere_backend.enums.TokenType;
import jakarta.persistence.*;


@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, columnDefinition = "TEXT")
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public Token(Long id, User user, boolean expired, boolean revoked, TokenType tokenType, String token) {
        this.id = id;
        this.user = user;
        this.expired = expired;
        this.revoked = revoked;
        this.tokenType = tokenType;
        this.token = token;
    }

    public Token() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Builder {
        private Long id;
        private String token;
        private TokenType tokenType = TokenType.BEARER;
        private boolean revoked;
        private boolean expired;
        private User user;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder tokenType(TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder revoked(boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public Builder expired(boolean expired) {
            this.expired = expired;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Token build() {
            Token token = new Token();
            token.id = this.id;
            token.token = this.token;
            token.tokenType = this.tokenType;
            token.revoked = this.revoked;
            token.expired = this.expired;
            token.user = this.user;
            return token;
        }
    }
}
