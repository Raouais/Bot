create table clients
(
    id            int auto_increment
        primary key,
    prenom        varchar(255) not null,
    nom           varchar(255) not null,
    idTelegram    double       not null,
    solde         double       not null,
    walletAdresse varchar(255) not null
);

