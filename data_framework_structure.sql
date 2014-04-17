CREATE TABLE "fichier" ("id_fichier" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL );
CREATE TABLE "utilisateurs" ("id_utilisateur" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "nom_utilisateur" VARCHAR  NOT NULL  UNIQUE , "mot_de_passe" VARCHAR);
CREATE TABLE "groupes" ("id_groupe" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "nom_groupe" VARCHAR  NOT NULL  UNIQUE  DEFAULT noname);
CREATE TABLE "apartenance_groupe" ("id_groupe" INTEGER, "id_utilisateur" INTEGER;

