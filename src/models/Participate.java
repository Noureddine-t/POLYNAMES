package models;

public record Participate(
        int id,
        Role role,
        int game_id,
        int player_id
) {
}
