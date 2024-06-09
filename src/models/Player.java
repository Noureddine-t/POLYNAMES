package models;

public record Player(
        int id,
        String username,
        String password,
        int game_id
) {}
