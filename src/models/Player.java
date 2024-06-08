package models;

public record Player(
        int player_id,
        String username,
        String role,
        String password,
        int game_id
) {}
