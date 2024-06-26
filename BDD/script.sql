CREATE DATABASE IF NOT EXISTS poly_names;
USE poly_names;

CREATE TABLE GAME (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    UNIQUE (code),
    score INT DEFAULT 0
);


CREATE TABLE PLAYER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    game_id INT,
    FOREIGN KEY (game_id) REFERENCES GAME(id)
);


CREATE TABLE WORD (
    id INT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(255) NOT NULL
);

CREATE TABLE PARTICIPATE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(255) NOT NULL,
    player_id INT,
    game_id INT,
    FOREIGN KEY (player_id) REFERENCES PLAYER(id),
    FOREIGN KEY (game_id) REFERENCES GAME(id)
);

CREATE TABLE INCLUDE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    color VARCHAR(255) NOT NULL,
    game_id INT,
    word_id INT,
    FOREIGN KEY (game_id) REFERENCES GAME(id),
    FOREIGN KEY (word_id) REFERENCES WORD(id)
);

USE poly_names;

INSERT INTO WORD (label) VALUES 
('AFRIQUE'), ('AIGUILLE'), ('AILE'), ('AIR'), ('ALIEN'), ('ALLEMAGNE'), ('ALPES'), ('AMOUR'), ('AMPOULE'), 
('AMÉRIQUE'), ('ANGE'), ('ANGLETERRE'), ('ANNEAU'), ('APPAREIL'), ('ARAIGNÉE'), ('ARC'), ('ARGENT'), 
('ASILE'), ('ASTÉRIX'), ('ATLANTIQUE'), ('ATOUT'), ('AUSTRALIE'), ('AVION'), ('AVOCAT'), ('BAGUETTE'), 
('BAIE'), ('BALANCE'), ('BALEINE'), ('BALLE'), ('BALLON'), ('BANANE'), ('BANC'), ('BANDE'), ('BANQUE'), 
('BAR'), ('BARBE'), ('BASE'), ('BATEAU'), ('BERLIN'), ('BIÈRE'), ('BLÉ'), ('BOMBE'), ('BON'), ('BOTTE'), 
('BOUCHE'), ('BOUCHON'), ('BOUGIE'), ('BOULET'), ('BOURSE'), ('BOUTEILLE'), ('BOUTON'), ('BOÎTE'), 
('BRANCHE'), ('BRETELLE'), ('BRIQUE'), ('BUREAU'), ('BUT'), ('BÂTON'), ('BÊTE'), ('BÛCHE'), ('BɶUF'), 
('CABINET'), ('CADRE'), ('CAFARD'), ('CAFÉ'), ('CAMEMBERT'), ('CAMPAGNE'), ('CANADA'), ('CANARD'), 
('CANNE'), ('CANON'), ('CARREAU'), ('CARRIÈRE'), ('CARTE'), ('CARTON'), ('CARTOUCHE'), ('CASINO'), 
('CEINTURE'), ('CELLULE'), ('CENTRE'), ('CERCLE'), ('CHAMP'), ('CHAMPAGNE'), ('CHANCE'), ('CHAPEAU'), 
('CHARGE'), ('CHARME'), ('CHASSE'), ('CHAT'), ('CHAUSSON'), ('CHAÎNE'), ('CHEF'), ('CHEMISE'), ('CHEVAL'), 
('CHEVALIER'), ('CHIEN'), ('CHINE'), ('CHOCOLAT'), ('CHOU'), ('CHÂTEAU'), ('CINÉMA'), ('CIRQUE'), ('CITROUILLE'), 
('CLASSE'), ('CLUB'), ('CLÉ'), ('COCHON'), ('CODE'), ('COL'), ('COLLE'), ('COMMERCE'), ('COQ'), ('CORDE'), 
('CORNE'), ('COTON'), ('COUPE'), ('COURANT'), ('COURONNE'), ('COURSE'), ('COURT'), ('COUTEAU'), 
('COUVERTURE'), ('CRITIQUE'), ('CROCHET'), ('CUISINE'), ('CYCLE'), ('CɶUR'), ('DANSE'), ('DINOSAURE'), 
('DOCTEUR'), ('DON'), ('MEUNIER'), ('DRAGON'), ('DROIT'), ('DROITE'), ('EAU'), ('ENCEINTE'), ('ENSEMBLE'), ('ENTRÉE'), 
('ESPACE'), ('ESPAGNE'), ('ESPION'), ('ESPRIT'), ('ESSENCE'), ('EUROPE'), ('FACTEUR'), ('FANTÔME'), 
('FARCE'), ('FER'), ('FERME'), ('FEU'), ('FEUILLE'), ('FIGURE'), ('FILET'), ('FIN'), ('FLÛTE'), ('FORMULE'), 
('FORT'), ('FORÊT'), ('FOU'), ('FOYER'), ('FRAISE'), ('FRANÇAIS'), ('FRONT'), ('FUITE'), ('GARDE'), 
('GAUCHE'), ('GEL'), ('GLACE'), ('GORGE'), ('GRAIN'), ('GRENADE'), ('GRUE'), ('GRÈCE'), ('GUERRE'), 
('GUIDE'), ('GÉANT'), ('GÉNIE'), ('HERBE'), ('HIMALAYA'), ('HISTOIRE'), ('HIVER'), ('HOLLYWOOD'), ('HÉROS'), 
('HÔPITAL'), ('HÔTEL'), ('INDIEN'), ('IRIS'), ('JET'), ('JEU'), ('JOUR'), ('JOURNAL'), ('JUMELLES'), 
('JUNGLE'), ('KANGOUROU'), ('KIWI'), ('LAIT'), ('LANGUE'), ('LASER'), ('LENTILLE'), ('LETTRE'), ('LICORNE'), 
('LIEN'), ('LIGNE'), ('LION'), ('LIQUIDE'), ('LIT'), ('LIVRE'), ('LONDRES'), ('LOUCHE'), ('LUMIÈRE'), 
('LUNE'), ('LUNETTES'), ('LUXE'), ('MACHINE'), ('MAGIE'), ('MAIN'), ('MAJEUR'), ('MALADIE'), ('MANCHE'), 
('MANÈGE'), ('MARCHE'), ('MARIN'), ('MARQUE'), ('MARRON'), ('MARS'), ('MAÎTRESSE'), ('MEMBRE'), 
('MENU'), ('MEUBLE'), ('MICROSCOPE'), ('MIEL'), ('MILLIONAIRE'), ('MINE'), ('MINEUR'), ('MODE'), 
('MOLIÈRE'), ('MORT'), ('MOUCHE'), ('MOULE'), ('MOUSSE'), ('MOUSTACHE'), ('MÉMOIRE'), ('NAIN'), ('NAPOLÉON'), 
('NEIGE'), ('NEW-YORK'), ('NINJA'), ('NOIR'), ('NOTE'), ('NOËL'), ('NUIT'), ('NUMÉRO'), ('NɶUD'), ('OISEAU'), 
('OPÉRA'), ('OPÉRATION'), ('OR'), ('ORANGE'), ('ORDRE'), ('PAGE'), ('PAILLE'), ('PALAIS'), ('PALME'), 
('PAPIER'), ('PARACHUTE'), ('PARIS'), ('PARTIE'), ('PASSE'), ('PATRON'), ('PENDULE'), ('PENSÉE'), 
('PERLE'), ('PESTE'), ('PHARE'), ('PHYSIQUE'), ('PIANO'), ('PIED'), ('PIGEON'), ('PILE'), ('PILOTE'), 
('PINGOUIN'), ('PIRATE'), ('PIÈCE'), ('PLACE'), ('PLAGE'), ('PLAN'), ('PLANCHE'), ('PLANTE'), ('PLAT'), 
('PLATEAU'), ('PLUME'), ('POINT'), ('POIRE'), ('POISON'), ('POISSON'), ('POLICE'), ('POMPE'), ('PORTABLE'), 
('POSTE'), ('POUCE'), ('POÊLE'), ('PRINCESSE'), ('PRISE'), ('PRÊT'), ('PYRAMIDE'), ('PÉTROLE'), ('PÊCHE'), 
('PÔLE'), ('QUARTIER'), ('QUEUE'), ('RADIO'), ('RAIE'), ('RAME'), ('RAT'), ('RAYON'), ('RECETTE'), 
('REINE'), ('RELIGIEUSE'), ('REMISE'), ('REQUIN'), ('RESTAURANT'), ('ROBE'), ('ROBOT'), ('ROI'), 
('ROME'), ('RONDE'), ('ROSE'), ('ROUGE'), ('ROULEAU'), ('ROULETTE'), ('RUSSIE'), ('RÈGLE'), ('RÉSISTANCE'), 
('RÉVOLUTION'), ('SARDINE'), ('SATELLITE'), ('SCHTROUMPF'), ('SCIENCE'), ('SCÈNE'), ('SENS'), ('SEPT'), 
('SERPENT'), ('SIRÈNE'), ('SIÈGE'), ('SOL'), ('SOLDAT'), ('SOLEIL'), ('SOLUTION'), ('SOMME'), ('SORCIÈRE'), 
('SORTIE'), ('SOURIS'), ('TABLE'), ('TABLEAU'), ('TALON'), ('TAMBOUR'), ('TEMPLE'), ('TEMPS'), ('TENNIS'), 
('TERRE'), ('TIMBRE'), ('TITRE'), ('TOILE'), ('TOKYO'), ('TOUR'), ('TRAIT'), ('TROU'), ('TRÉSOR'), ('TUBE'), 
('TUILE'), ('TÊTE'), ('UNIFORME'), ('VAGUE'), ('VAISSEAU'), ('VAMPIRE'), ('VASE'), ('VENT'), ('VERRE'), 
('VERT'), ('VIE'), ('VIN'), ('VISAGE'), ('VISION'), ('VOILE'), ('VOITURE'), ('VOL'), ('VOLEUR'), 
('VOLUME'), ('ZÉRO'), ('ÉCHELLE'), ('ÉCLAIR'), ('ÉCOLE'), ('ÉGALITÉ'), ('ÉGYPTE'), ('ÉPONGE'), 
('ÉTOILE'), ('ÉTUDE'), ('ŒIL'), ('ŒUF');
