package models;

public record Participate(
        int id,
        String role,
        int game_id,
        int player_id
){}
