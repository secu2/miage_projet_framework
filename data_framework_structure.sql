CREATE TABLE "fichier" ("id_fichier" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL );
CREATE TABLE "groupes" ("id_groupe" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "nom_groupe" VARCHAR DEFAULT noname);
CREATE TABLE "permissions_groupes" ("id_groupe" INTEGER, "flag" VARCHAR, "value" INTEGER);
CREATE TABLE "permissions_utilisateurs" ("id_utilisateur" INTEGER, "flag" VARCHAR, "value" INTEGER);
CREATE TABLE "utilisateurs" ("id_utilisateur" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "nom_utilisateur" VARCHAR , "mot_de_passe" VARCHAR);
