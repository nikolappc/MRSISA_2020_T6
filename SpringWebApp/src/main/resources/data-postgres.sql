insert into administratori_klinickog_centra (id, adresa, broj_telefona, email, ime, jbo, password, prezime) values (0, 'Knockturn Alley', '065/123-112', 'admin@gmail.com', 'Admin', '0000', 'admin', 'Admin');

insert into cenovnici (id) values (0);

insert into zdravstveni_kartoni (id) values (0);
insert into zdravstveni_kartoni (id) values (1);
insert into zdravstveni_kartoni (id) values (2);

insert into tipovi_klinike (id, tip) values (0, 'tip 1');
insert into klinike (id, adresa, naziv, opis, cenovnik_id, tip_klinike_id) values (0, 'Dijagon Aleja', 'Sent Mungo', 'bolnica', 0, 0);

insert into administratori_klinike (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (0, 'Godrics Hollow', '066/125-112', 'dumbledore@gmail.com', 'Albus', '0221', 'albus12345', 'Dumbledore', 0);

insert into lekari (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (0, 'Godrics Hollow', '066/123-113', 'lupin@gmail.com', 'Remus', '000', 'remus12345', 'Lupin', 0);
insert into lekari (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (1, 'Grimaulds Place 12', '066/123-163', 'black@gmail.com', 'Sirius', '000', 'sirius12345', 'Black', 0);

insert into godisnji_odmori (id, kraj, pocetak, id_lekara) values (0, to_timestamp('2020-03-10', 'YYYY-MM-DD'), to_timestamp('2020-03-03', 'YYYY-MM-DD'), 0);

insert into lekovi (sifra_leka) values (0);
insert into lekovi (sifra_leka) values (1);
insert into lekovi (sifra_leka) values (2);

insert into termini (id, kraj, pocetak) values (0, to_timestamp('2020-03-20 08:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 09:00', ' YYYY-MM-DD HH:MI'));
insert into termini (id, kraj, pocetak) values (1, to_timestamp('2020-03-20 09:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 10:00', ' YYYY-MM-DD HH:MI'));
insert into termini (id, kraj, pocetak) values (2, to_timestamp('2020-03-20 08:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 10:00', ' YYYY-MM-DD HH:MI'));

insert into medicinske_sestre (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (0, 'Godrics Hollow', '064/123-114', 'evans@gmail.com', 'Lily', '000', 'lily12345', 'Evans', 0);

insert into ocene (id, vrednost, id_lekara) values (0, 10, 0);
insert into ocene (id, vrednost, id_lekara) values (1, 10, 1);
insert into ocene (id, vrednost, id_klinike) values (2, 9, 0);

insert into pacijent (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_kartona, id_klinike) values (0, 'Knockturn Alley', '065/123-111', 'hermione@gmail.com', 'Hermione', '0101', 'hermione12345', 'Granger', 0, 0);
insert into pacijent (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_kartona, id_klinike) values (1, 'Hogwarts', '065/823-115', 'harry@gmail.com', 'Harry', '000', 'harry12345', 'Potter', 1, 0);
insert into pacijent (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_kartona, id_klinike) values (2, 'Hogwarts', '065/923-115', 'ron@gmail.com', 'Ron', '000', 'ron12345', 'Weasley', 2, 0);

insert into radna_vremena (id, kraj, pocetak, id_lekara) values (0, to_timestamp('08:00', 'HH:MI'), to_timestamp('11:00', 'HH:MI'), 0);

insert into sale (id, naziv, id_klinike) values (0, 'sala 1', 0);
insert into sale (id, naziv, id_klinike) values (1, 'operaciona', 0);

insert into tipovi_posete (id, tip, id_klinike) values (0, 1, 0);

insert into pregled (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike) values (0, 'korona test', 0, 0, 0, 0, 1, 0);
insert into pregled (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike) values (1, 'terapija', 0, 1, 0, 1, 1, 0);
insert into operacije (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike) values (0, 'slijepo crijevo', 1, 2, 0, 2, 0);

insert into recepti (id, overen, id_recepta, id_pregleda) values (0, false, 0, 0);
insert into recepti (id, overen, id_recepta, id_pregleda) values (1, true, 1, 1);

insert into dijagnoze (sifra, naziv, id_pregleda, id_kartona) values (0, 'Covid-19', 0, 0);
insert into dijagnoze (sifra, naziv, id_pregleda, id_kartona) values (1, 'Depresija', 1, 1);

insert into stavke_cenovnika (id, cena, id_cenovnik, id_tipa_posete) values (0, 10.5, 0, 0);

insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (0, 1);

insert into lekari_operacije (operacija_id, lekar_id) values (0, 0);

