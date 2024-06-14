/**
 * Service permettant d'affecter un rôle à un joueur avec l'API
 */
export class RoleService {
    /**
     * Participate in a game
     * @param {string} username
     * @param {string} gameCode
     * @param {string} role
     * @returns {Promise<string>}
     */
    static async participate(username, gameCode, role) {
        const response = await fetch(`http://127.0.0.1:8080/api/participate/${username}/${gameCode}/${role}`, {method: 'POST'});
        if (response.status === 200) {
            const data = await response.json();
            return data['role'];
        } else {
            throw new Error('Failed to associate role to player');
        }
    }
}