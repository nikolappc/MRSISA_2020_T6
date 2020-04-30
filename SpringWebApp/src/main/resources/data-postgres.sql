insert into administratori_klinickog_centra (id, adresa, broj_telefona, email, ime, jbo, password, prezime) values (1, 'Knockturn Alley', '065/123-112', 'admin@gmail.com', 'Admin', '0000', 'admin', 'Admin');

insert into cenovnici (id) values (1);

insert into zdravstveni_kartoni (id, visina, tezina, krvna_grupa) values (1, 1.6, 60, 'A+');
insert into zdravstveni_kartoni (id, visina, tezina, krvna_grupa) values (2, 1.7, 70, 'B-');
insert into zdravstveni_kartoni (id, visina, tezina, krvna_grupa) values (3, 1.8, 80, '0+');

insert into stavke_cenovnika (cena, id_cenovnik) values (10.5, 1);
insert into stavke_cenovnika (cena, id_cenovnik) values (20, 1);
insert into stavke_cenovnika (cena, id_cenovnik) values (30, 1);
insert into stavke_cenovnika (cena, id_cenovnik) values (40, 1);
insert into stavke_cenovnika (cena, id_cenovnik) values (50, 1);

insert into tipovi_klinike (id, tip) values (1, 'tip 1');
insert into klinike (id, adresa, naziv, opis, cenovnik_id, tip_klinike_id) values (1, 'Dijagon Aleja', 'Sent Mungo', 'bolnica', 1, 1);

insert into administratori_klinike (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (1, 'Godrics Hollow', '066/125-112', 'dumbledore@gmail.com', 'Albus', '0221', 'albus12345', 'Dumbledore', 1);

insert into lekari (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (1, 'Godrics Hollow', '066/123-113', 'lupin@gmail.com', 'Remus', '000', 'remus12345', 'Lupin', 1);
insert into lekari (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (2, 'Grimaulds Place 12', '066/123-163', 'black@gmail.com', 'Sirius', '000', 'sirius12345', 'Black', 1);

insert into godisnji_odmori (id, kraj, pocetak, id_lekara) values (1, to_timestamp('2020-03-10', 'YYYY-MM-DD'), to_timestamp('2020-03-03', 'YYYY-MM-DD'), 1);

insert into lekovi (sifra_leka, naziv_leka) values (1, 'Bromazepam');
insert into lekovi (sifra_leka, naziv_leka) values (2, 'Bensedin');
insert into lekovi (sifra_leka, naziv_leka) values (3, 'Brufen');

insert into termini (id, kraj, pocetak) values (1, to_timestamp('2020-03-20 08:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 09:00', ' YYYY-MM-DD HH:MI'));
insert into termini (id, kraj, pocetak) values (2, to_timestamp('2020-03-20 09:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 10:00', ' YYYY-MM-DD HH:MI'));
insert into termini (id, kraj, pocetak) values (3, to_timestamp('2020-03-20 08:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 10:00', ' YYYY-MM-DD HH:MI'));
insert into termini (id, kraj, pocetak) values (4, to_timestamp('2020-03-20 10:00', ' YYYY-MM-DD HH:MI'), to_timestamp('2020-03-20 11:00', ' YYYY-MM-DD HH:MI'));

insert into medicinske_sestre (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (1, 'Godrics Hollow', '064/123-114', 'evans@gmail.com', 'Lily', '000', 'lily12345', 'Evans', 1);

insert into ocene (id, vrednost, id_lekara) values (1, 10, 1);
insert into ocene (id, vrednost, id_lekara) values (2, 10, 2);
insert into ocene (id, vrednost, id_klinike) values (3, 9, 1);

insert into pacijent (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_kartona, id_klinike) values (1, 'Knockturn Alley', '065/123-111', 'hermione@gmail.com', 'Hermione', '0101', 'hermione12345', 'Granger', 1, 1);
insert into pacijent (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_kartona, id_klinike) values (2, 'Hogwarts', '065/823-115', 'harry@gmail.com', 'Harry', '000', 'harry12345', 'Potter', 2, 1);
insert into pacijent (id, adresa, broj_telefona, email, ime, jbo, password, prezime, id_kartona, id_klinike) values (3, 'Hogwarts', '065/923-115', 'ron@gmail.com', 'Ron', '000', 'ron12345', 'Weasley', 3, 1);

insert into radna_vremena (id, kraj, pocetak, id_lekara) values (1, to_timestamp('08:00', 'HH:MI'), to_timestamp('11:00', 'HH:MI'), 1);

insert into sale (id, naziv, id_klinike) values (1, 'sala 1', 1);
insert into sale (id, naziv, id_klinike) values (2, 'operaciona', 1);

insert into tipovi_posete (id, tip, id_klinike, id_stavke, naziv) values (1, 1, 1, 2,'Super specijalni pregled');
insert into tipovi_posete (id, tip, id_klinike, id_stavke,naziv) values (2, 0, 1, 1,'Super specijalna operacija');

insert into pregled (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike) values (1, 'korona test', 1, 1, 1, 1, 2, 1);
insert into pregled (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike) values (2, 'terapija', 1, 2, 1, 2, 2, 1);
insert into pregled (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike) values (3, 'previjanje', 1, 4, 1, 2, 2, 1);
insert into operacije (id, ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike) values (1, 'slijepo crijevo', 2, 3, 2, 3, 1);

insert into recepti (id, overen, id_recepta, id_pregleda) values (1, false, 1, 1);
insert into recepti (id, overen, id_recepta, id_pregleda) values (2, true, 2, 2);

insert into dijagnoze (sifra, naziv, id_kartona) values (1, 'Covid-19', 1);
insert into dijagnoze (sifra, naziv, id_kartona) values (2, 'Depresija', 2);

/*insert into stavke_cenovnika (id, cena, id_cenovnik, id_tipa_posete) values (1, 10.5, 1, 1);*/


insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (1, 2);

insert into lekari_operacije (operacija_id, lekar_id) values (1, 1);

insert into dijagnoze_pregled (dijagnoza_sifra, pregled_id) values (1, 1);
insert into dijagnoze_pregled (dijagnoza_sifra, pregled_id) values (2, 2);