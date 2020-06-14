insert into adrese (adresa, grad, drzava) values ('Svetozara Markovića 41', 'Bač', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Svetozara Markovića 42', 'Bač', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Svetozara Markovića 43', 'Bač', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Svetozara Markovića 44', 'Bač', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Bulevar cara Lazara 75', 'Novi Sad', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Bate Brkića 8', 'Novi Sad', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Despota Stefana 7', 'Novi Sad', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Kneza Lazara 22', 'Novi Sad', 'Srbija');
insert into adrese (adresa, grad, drzava) values ('Stadiou Street 7', 'Athens', 'Greece');
insert into adrese (adresa, grad, drzava) values ('Adrianou Street 77', 'Athens', 'Greece');
insert into adrese (adresa, grad, drzava) values ('Patission Street 55', 'Athens', 'Greece');
insert into adrese (adresa, grad, drzava) values ('Dionysiou Areopagitou Street 11', 'Athens', 'Greece');
insert into adrese (adresa, grad, drzava) values ('Grimaulds place 12', 'London', 'UK');
insert into adrese (adresa, grad, drzava) values ('Privet Drive 4', 'London', 'UK');
insert into adrese (adresa, grad, drzava) values ('Ottery St Catchpole', 'London', 'UK');
insert into adrese (adresa, grad, drzava) values ('Diagon Alley', 'London', 'UK');

insert into administratori_klinickog_centra (id_adrese, broj_telefona, email, ime, jbo, password, prezime) values (1, '065/123-112', 'admin@gmail.com', 'Admin', '123345', 'admin', 'Admin');

insert into cenovnici (version) values(0);
insert into cenovnici (version) values(0);
insert into cenovnici (version) values(0);
insert into cenovnici (version) values(0);

insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.6, 60, 'A+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 70, 'B-');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.8, 80, '0+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 110, '0-');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.6, 60, 'A+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 70, 'B-');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.8, 80, 'AB+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 110, 'AB-');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.6, 60, 'A+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 70, 'B-');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.8, 80, '0+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 110, '0-');

insert into tipovi_klinike (id, tip) values (1,'Poliklinika');
insert into tipovi_klinike (id, tip) values (2,'Hirurgija');
insert into tipovi_klinike (id, tip) values (3,'Rehabilitation Clinic');
insert into tipovi_klinike (id, tip) values (4,'Wizard Clinic');

insert into klinike (id_adrese, naziv, opis, cenovnik_id, tip_klinike_id) values (1, 'Hirurgija Bač', 'Dobrodošli na našu kliniku! Nudimo povoljne operacije krajnika, slepog creva i kičme.', 1, 2);
insert into klinike (id_adrese, naziv, opis, cenovnik_id, tip_klinike_id) values (5, 'Poliklinika Novi Sad', 'Naši klijenti su naša velika porodica. Nudimo im veliku stručnost lekara u našem timu uz korišćenje najsavremenijih saznanja iz različitih oblasti medicine.', 2, 1);
insert into klinike (id_adrese, naziv, opis, cenovnik_id, tip_klinike_id) values (9, 'Health Unit Athens', 'Specijalna bolnica za rehabilitaciju.', 3, 3);
insert into klinike (id_adrese, naziv, opis, cenovnik_id, tip_klinike_id) values (13, 'Sent Mungo', 'Bolnica za magijske bolesti i povrede.', 4, 4);

insert into tipovi_posete (tip, id_klinike, naziv) values (0, 1,'Operacija kičme');
insert into tipovi_posete (tip, id_klinike, naziv) values (0, 1,'Operacija slepog creva');
insert into tipovi_posete (tip, id_klinike, naziv) values (0, 1,'Operacija krajnika');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 2,'Korona test');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 2,'Stomatološki pregled');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 2,'Ultrazvuk');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 3,'Phihoterapija');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 3,'Korona testiranje');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 3,'Oporavak od preloma');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 4,'Testiranje vida');
insert into tipovi_posete (tip, id_klinike, naziv) values (0, 4,'Specijalna operacija');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 4,'Povrede uzrokovane činima');
insert into tipovi_posete (tip, id_klinike, naziv) values (1, 4,'Trovanje napitkom');

insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (10000, 1, 1);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (5000, 1, 2);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (2000, 1, 3);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (100, 2, 4);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (500, 2, 5);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (600, 2, 6);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (300, 3, 7);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (550, 3, 8);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (400, 4, 9);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (4000, 4, 10);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (20000, 4, 11);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (1000, 4, 12);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (20000, 4, 8);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (1000, 4, 6);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (1000, 4, 13);

insert into administratori_klinike (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (2, '066/254-655', 'pavlovic@gmail.com', 'Žika', '0225', 'zika12345', 'Pavlović', 1);
insert into administratori_klinike (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (6, '066/125-112', 'tanasijevic@gmail.com', 'Andro', '0227', 'andro12345', 'Tanasijević', 2);
insert into administratori_klinike (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (10, '066/335-222', 'mosby@gmail.com', 'Ted', '0220', 'ted12345', 'Mosby', 3);
insert into administratori_klinike (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (14, '066/647-273', 'dumbledore@gmail.com', 'Albus', '0221', 'albus12345', 'Dumbledore', 4);
insert into administratori_klinike (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (14, '066/256-676', 'mcgonnagal@gmail.com', 'Minerva', '0222', 'minerva12345', 'McGonnagal', 4);

insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (1, '066/122-255', 'pavlovic1@gmail.com', 'Mišo', '37958', 'miso12345', 'Pavlović', 1, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (2, '066/255-785', 'todorovic@gmail.com', 'Milan', '848930', 'milan12345', 'Todorović', 1, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (3, '066/123-111', 'petrovic@gmail.com', 'Nataša', '047362', 'natasa12345', 'Petrović', 1, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (5, '066/123-113', 'pletikosic@gmail.com', 'Stanko', '278400', 'stanko12345', 'Pletikosić', 2, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (6, '066/123-685', 'golubic@gmail.com', 'Mustafa', '293746', 'mustafa12345', 'Golubić', 2, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (7, '066/123-065', 'miric@gmail.com', 'Alimpije', '088883', 'alimpije12345', 'Mirić', 2, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (9, '066/123-664', 'eriksen@gmail.com', 'Marshall', '383737', 'marshall12345', 'Eriksen', 3, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (10, '066/123-232', 'aldrin@gmail.com', 'Lily', '283300', 'lily12345', 'Aldrin', 3, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (13, '066/123-344', 'potterj@gmail.com', 'James', '193736', 'james12345', 'Potter', 4, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (14, '066/123-233', 'pettigrew@gmail.com', 'Peter', '493726', 'peter12345', 'Pettigrew', 4, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (15, '066/123-566', 'lupin@gmail.com', 'Remus', '402735', 'remus12345', 'Lupin', 4, false,0);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put, version) values (16, '066/123-344', 'black@gmail.com', 'Sirius', '483777', 'sirius12345', 'Black', 4, false,0);

insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put) values (3, '064/123-897', 'nikolajevic@gmail.com', 'Snežana', '395653', 'snezana12345', 'Nikolajević', 1,false);
insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put) values (4, '064/123-232', 'petrovicolga@gmail.com', 'Olga', '330472', 'olga12345', 'Petrović', 1,false);
insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put) values (7, '064/123-114', 'orsic@gmail.com', 'Marija', '407353', 'marija12345', 'Oršić', 2,false);
insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put) values (8, '064/123-332', 'davidovic@gmail.com', 'Maja', '053638', 'maja12345', 'Davidović', 2,false);
insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put) values (12, '064/123-344', 'eriksen1@gmail.com', 'Marvin', '393930', 'marvin12345', 'Eriksen', 3,false);
insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike, prvi_put) values (16, '064/123-212', 'evans@gmail.com', 'Lily', '474300', 'lily12345', 'Evans', 4,false);

insert into godisnji_odmori (kraj, pocetak, odobren, id_lekara) values (to_timestamp('2020-07-10', 'YYYY-MM-DD'), to_timestamp('2020-07-03', 'YYYY-MM-DD'), true, 1);
insert into godisnji_odmori (kraj, pocetak, odobren, id_sestre) values (to_timestamp('2020-07-10', 'YYYY-MM-DD'), to_timestamp('2020-07-03', 'YYYY-MM-DD'), true, 1);
insert into godisnji_odmori (kraj, pocetak, odobren, id_lekara) values (to_timestamp('2020-08-10', 'YYYY-MM-DD'), to_timestamp('2020-08-03', 'YYYY-MM-DD'), false, 1);
insert into godisnji_odmori (kraj, pocetak, odobren, id_sestre) values (to_timestamp('2020-08-10', 'YYYY-MM-DD'), to_timestamp('2020-08-03', 'YYYY-MM-DD'), false, 1);

insert into lekovi (sifra_leka, naziv_leka) values (1, 'Bromazepam');
insert into lekovi (sifra_leka, naziv_leka) values (2, 'Bensedin');
insert into lekovi (sifra_leka, naziv_leka) values (3, 'Brufen');
insert into lekovi (sifra_leka, naziv_leka) values (4, 'Sirup za kašalj');
insert into lekovi (sifra_leka, naziv_leka) values (5, 'Diklofen');
insert into lekovi (sifra_leka, naziv_leka) values (6, 'Napitak za rast kose');
insert into lekovi (sifra_leka, naziv_leka) values (7, 'Propolis');
insert into lekovi (sifra_leka, naziv_leka) values (8, 'Analgin');
insert into lekovi (sifra_leka, naziv_leka) values (9, 'Paracetamol');
insert into lekovi (sifra_leka, naziv_leka) values (10, 'Konjska mast');

insert into termini (pocetak, kraj) values (to_timestamp('2020-05-20 08:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-05-20 08:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-05-20 08:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-05-20 09:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-13 09:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-13 10:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-13 13:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-13 13:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-04 08:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-04 08:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-04 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-04 10:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-04 10:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-04 11:00', 'YYYY-MM-DD HH24:MI'));

insert into termini (pocetak, kraj) values (current_timestamp,current_timestamp + interval '30 minutes');
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-15 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-15 10:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-22 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-22 10:30', 'YYYY-MM-DD HH24:MI'));

insert into termini (pocetak, kraj) values (to_timestamp('2020-06-25 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-25 10:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-25 11:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-25 11:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-27 12:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-27 12:30', 'YYYY-MM-DD HH24:MI'));

insert into termini (pocetak, kraj) values (to_timestamp('2020-06-21 10:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-21 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-21 11:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-21 12:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-22 14:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-22 14:30', 'YYYY-MM-DD HH24:MI'));

insert into termini (pocetak, kraj) values (to_timestamp('2020-06-02 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-02 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-03 11:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-03 12:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-05 14:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-05 14:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-11 10:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-11 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-19 11:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-19 12:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-19 14:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-19 14:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-07-05 14:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-07-05 14:30', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-07-11 10:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-07-11 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-07-19 11:30', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-07-19 12:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-07-19 14:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-07-19 14:30', 'YYYY-MM-DD HH24:MI'));

insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (1, '065/123-111', 'neekolapapic@gmail.com', 'Nklppc', '384265', 'nikola12345', 'Papic',  1, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (2, '065/123-111', 'rodoljub@gmail.com', 'Rodoljub', '037462', 'rodoljub12345', 'Petrovic', 2, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (3, '065/823-115', 'borko@gmail.com', 'Borko', '263864', 'borko12345', 'Petrovic', 3, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (5, '065/923-115', 'tina@gmail.com', 'Tina', '474649', 'tina12345', 'Sibinovic', 4, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (6, '065/123-111', 'nenad@gmail.com', 'Nenad', '946404', 'nenad12345', 'Bešlic',  5, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (7, '065/123-111', 'milos@gmail.com', 'Milos', '496353', 'milos12345', 'Gazivoda', 6, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (9, '065/823-115', 'barney@gmail.com', 'Barney', '393035', 'barney12345', 'Stinson', 7, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (10, '065/923-115', 'robin@gmail.com', 'Robin', '204753', 'robin12345', 'Scherbatsky', 8, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (11, '065/123-111', 'tracy@gmail.com', 'Tracy', '392634', 'tracy12345', 'Mosby',  9, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (13, '065/123-111', 'hermione@gmail.com', 'Hermione', '403753', 'hermione12345', 'Granger', 10, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (14, '065/823-115', 'harry@gmail.com', 'Harry', '304624', 'harry12345', 'Potter', 11, true, true);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona, allowed, responded) values (15, '065/923-115', 'verakovacevic98@gmail.com', 'Ron', '304736', 'ron12345', 'Weasley', 12, true, true);

insert into ocene (vrednost, id_lekara, id_pacijenta) values (5, 11, 11);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (3, 12, 11);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (4, 11, 12);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (5, 12, 12);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (3, 11, 10);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (4.5, 12, 10);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (5, 12, 9);
insert into ocene (vrednost, id_klinike, id_pacijenta) values (3, 4, 10);
insert into ocene (vrednost, id_klinike, id_pacijenta) values (4, 4, 11);
insert into ocene (vrednost, id_klinike, id_pacijenta) values (5, 4, 12);
insert into ocene (vrednost, id_klinike, id_pacijenta) values (5, 4, 9);

insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 1);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 2);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 23:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), 3);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 4);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 5);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 23:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), 6);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 7);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 8);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 23:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), 9);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 10);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 11);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 23:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), 12);

insert into sale (naziv, id_klinike) values ('12', 1);
insert into sale (naziv, id_klinike) values ('Operaciona 222', 1);
insert into sale (naziv, id_klinike) values ('Sala 11', 2);
insert into sale (naziv, id_klinike) values ('Sala 12', 2);
insert into sale (naziv, id_klinike) values ('Teretana', 3);
insert into sale (naziv, id_klinike) values ('Sala 3', 3);
insert into sale (naziv, id_klinike) values ('111', 4);
insert into sale (naziv, id_klinike) values ('112', 4);
insert into sale (naziv, id_klinike) values ('113', 4);
insert into sale (naziv, id_klinike) values ('114', 4);

insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 1.', 7, 1, 10, 9, 12, 4, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 2', 7, 5, 11, 10, 11, 4, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 3', 8, 3, 12, 10, 12, 4, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 4', 8, 4, 10, 11, 11, 4, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 5', 8, 2, 11, 11, 12, 4, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 6', 8, 6, 12, 12, 11, 4, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Prošao pregled 7', 9, 7, 10, 12, 12, 4, true, true, 0);

insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', 9, 8, 11, 11, 12, 4, true, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', 9, 9, 11, 11, 11, 4, true, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', 8, 10, 10, 12, 12, 4, true, false, 0);

insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', 9, 11, 11, null, 12, 4, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', 9, 12, 11, null, 11, 4, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', 8, 13, 10, null, 12, 4, false, false, 0);

insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', null, 14, 11, 11, 12, 4, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', null, 15, 11, 12, 11, 4, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('', null, 16, 10, 11, 12, 4, false, false, 0);

insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen, version) values ('Prošla operacija 1', 10, 17, 10, 3, 4, true, true,0);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen, version) values ('Prošla operacija 2', 10, 18, 4, 2, 4, true, true,0);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen, version) values ('Prošla operacija 3', 10, 19, 4, 1, 4, true, true,0);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen, version) values ('Prošla operacija 4', 10, 20, 10, 1, 4, true, true,0);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen, version) values ('', null, 21, 11, 10, 4, true, true,0);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen, version) values ('', null, 22, 11, 11, 4, true, true,0);

insert into recepti (overen, id_recepta, id_pregleda, version) values (true, 1, 1,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (true, 2, 2,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (true, 3, 2,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (true, 4, 2,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (false, 5, 3,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (false, 6, 4,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (false, 7, 5,0);
insert into recepti (overen, id_recepta, id_pregleda, version) values (false, 8, 7,0);

insert into dijagnoze (sifra, naziv, id_kartona) values (1, 'Covid-19', 9);
insert into dijagnoze (sifra, naziv, id_kartona) values (2, 'Depresija', 10);
insert into dijagnoze (sifra, naziv, id_kartona) values (3, 'Bipolarni poremećaj', 10);
insert into dijagnoze (sifra, naziv, id_kartona) values (4, 'Epilepsija', 11);
insert into dijagnoze (sifra, naziv, id_kartona) values (5, 'Covid-19', 11);

insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (5, 1);
insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (5, 2);
insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (6, 3);
insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (6, 4);

insert into lekari_operacije (lekar_id, operacija_id) values (9, 1);
insert into lekari_operacije (lekar_id, operacija_id) values (10, 1);
insert into lekari_operacije (lekar_id, operacija_id) values (9, 2);
insert into lekari_operacije (lekar_id, operacija_id) values (9, 3);
insert into lekari_operacije (lekar_id, operacija_id) values (9, 4);

insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (1, 1);
insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (2, 2);
insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (3, 3);
insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (4, 4);
insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (5, 4);

insert into lekari_specijalnosti (lekar_id, tip_id) values (1, 1);
insert into lekari_specijalnosti (lekar_id, tip_id) values (2, 2);
insert into lekari_specijalnosti (lekar_id, tip_id) values (3, 3);
insert into lekari_specijalnosti (lekar_id, tip_id) values (4, 4);
insert into lekari_specijalnosti (lekar_id, tip_id) values (5, 5);
insert into lekari_specijalnosti (lekar_id, tip_id) values (6, 6);
insert into lekari_specijalnosti (lekar_id, tip_id) values (7, 7);
insert into lekari_specijalnosti (lekar_id, tip_id) values (8, 8);
insert into lekari_specijalnosti (lekar_id, tip_id) values (8, 9);
insert into lekari_specijalnosti (lekar_id, tip_id) values (2, 1);
insert into lekari_specijalnosti (lekar_id, tip_id) values (9, 10);
insert into lekari_specijalnosti (lekar_id, tip_id) values (10, 11);
insert into lekari_specijalnosti (lekar_id, tip_id) values (11, 12);
insert into lekari_specijalnosti (lekar_id, tip_id) values (12, 13);
insert into lekari_specijalnosti (lekar_id, tip_id) values (11, 13);
insert into lekari_specijalnosti (lekar_id, tip_id) values (12, 12);
insert into lekari_specijalnosti (lekar_id, tip_id) values (11, 11);
insert into lekari_specijalnosti (lekar_id, tip_id) values (12, 8);
insert into lekari_specijalnosti (lekar_id, tip_id) values (11, 6);