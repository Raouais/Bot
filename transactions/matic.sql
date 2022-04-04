create table matic
(
    id                 int auto_increment
        primary key,
    nom                varchar(255) not null,
    solde              double       not null,
    walletDestinataire varchar(255) not null,
    walletExpediteur   varchar(255) not null
);

