insert into adrese ( adresa, grad, drzava) values ( 'Svetozara Markovica 44', 'Bac', 'Srbija');
insert into adrese ( adresa, grad, drzava) values ( 'Svetozara Markovskog 44', 'Vidin', 'Bugarska');
insert into adrese ( adresa, grad, drzava) values ( 'Svjetozara Markovica 44', 'Prnjavor', 'Bosna i Hercegovina');
insert into adrese ( adresa, grad, drzava) values ( 'Kneza Lazara 22', 'Novi Sad', 'Srbija');

insert into administratori_klinickog_centra (id_adrese, broj_telefona, email, ime, jbo, password, prezime) values (1, '065/123-112', 'admin@gmail.com', 'Admin', '0000', 'admin', 'Admin');

insert into cenovnici (id) values (1);

insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.6, 60, 'A+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 70, 'B-');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.8, 80, '0+');
insert into zdravstveni_kartoni (visina, tezina, krvna_grupa) values (1.7, 110, '0+');

insert into tipovi_klinike (id, tip) values (1,'poliklinika');
insert into tipovi_klinike (id, tip) values (2,'hirurgija');
insert into tipovi_klinike (id, tip) values (3,'bolnica');

insert into klinike (id_adrese, naziv, opis, cenovnik_id, tip_klinike_id) values (1, 'Sent Mungo', 'bolnica', 1, 3);
insert into klinike (id_adrese, naziv, opis, cenovnik_id, tip_klinike_id) values (4, 'Poliklinika Novi Sad', 'bolnica', 1, 1);

insert into tipovi_posete (tip, id_klinike, naziv) values (1, 1,'Super specijalni pregled');
insert into tipovi_posete (tip, id_klinike,naziv) values (0, 1,'Super specijalna operacija');

insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (10.5, 1, 1);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (20, 1, 2);
/*insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (30, 1);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (40, 1);
insert into stavke_cenovnika (cena, id_cenovnik, id_tip) values (50, 1);
*/

insert into administratori_klinike (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (1, '066/125-112', 'dumbledore@gmail.com', 'Albus', '0221', 'albus12345', 'Dumbledore', 1);

insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (2, '066/123-113', 'lupin@gmail.com', 'Remus', '000', 'remus12345', 'Lupin', 1);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (3, '066/123-163', 'black@gmail.com', 'Sirius', '000', 'sirius12345', 'Black', 1);
insert into lekari (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (3, '066/123-163', 'neekolapapic@gmail.com', 'Nikola', '000', '1', 'PPC', 1);

insert into medicinske_sestre (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_klinike) values (2, '064/123-114', 'evans@gmail.com', 'Lily', '000', 'lily12345', 'Evans', 1);

insert into godisnji_odmori (kraj, pocetak, odobren, id_lekara) values (to_timestamp('2020-03-10', 'YYYY-MM-DD'), to_timestamp('2020-03-03', 'YYYY-MM-DD'), true, 1);
insert into godisnji_odmori (kraj, pocetak, odobren, id_sestre) values (to_timestamp('2020-03-10', 'YYYY-MM-DD'), to_timestamp('2020-03-03', 'YYYY-MM-DD'), true, 1);
insert into godisnji_odmori (kraj, pocetak, odobren, id_lekara) values (to_timestamp('2020-03-10', 'YYYY-MM-DD'), to_timestamp('2020-03-03', 'YYYY-MM-DD'), false, 1);
insert into godisnji_odmori (kraj, pocetak, odobren, id_sestre) values (to_timestamp('2020-03-10', 'YYYY-MM-DD'), to_timestamp('2020-03-03', 'YYYY-MM-DD'), false, 1);

insert into lekovi (sifra_leka, naziv_leka) values (1, 'Bromazepam');
insert into lekovi (sifra_leka, naziv_leka) values (2, 'Bensedin');
insert into lekovi (sifra_leka, naziv_leka) values (3, 'Brufen');
insert into lekovi (sifra_leka, naziv_leka) values (4, 'Sirup za kašalj');
insert into lekovi (sifra_leka, naziv_leka) values (5, 'Diklofen');

insert into termini (pocetak, kraj) values (to_timestamp('2020-03-20 08:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-03-20 09:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-03-20 09:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-03-20 10:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-03-20 08:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-03-20 10:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-03-20 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-03-20 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-05-21 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-05-21 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (current_timestamp,current_timestamp + interval '10 minutes');
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-08 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-08 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-25 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-25 11:00', 'YYYY-MM-DD HH24:MI'));
insert into termini (pocetak, kraj) values (current_timestamp,current_timestamp + interval '20 minutes');
insert into termini (pocetak, kraj) values (current_timestamp,current_timestamp + interval '30 minutes');
insert into termini (pocetak, kraj) values (current_timestamp,current_timestamp + interval '60 minutes');
insert into termini (pocetak, kraj) values (to_timestamp('2020-06-26 10:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('2020-06-26 11:00', 'YYYY-MM-DD HH24:MI'));



insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona,  allowed, responded, version) values (1, '065/123-111', 'neekolapapic@gmail.com', 'nklppc', '0101', 'hermione12345', 'papic',  4, false, false, 0);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona,  allowed, responded, version) values (2, '065/123-111', 'hermione@gmail.com', 'Hermione', '0101', 'hermione12345', 'Granger', 1, true, true, 0);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona,  allowed, responded, version) values (3, '065/823-115', 'harry@gmail.com', 'Harry', '000', 'harry12345', 'Potter', 2, true, true, 0);
insert into pacijent (id_adrese, broj_telefona, email, ime, jbo, password, prezime, id_kartona,  allowed, responded, version) values (1, '065/923-115', 'ron@gmail.com', 'Ron', '000', 'ron12345', 'Weasley', 3, true, true, 0);

insert into ocene (vrednost, id_lekara, id_pacijenta) values (5, 1, 3);
insert into ocene (vrednost, id_lekara, id_pacijenta) values (3, 2, 3);
insert into ocene (vrednost, id_klinike, id_pacijenta) values (2, 1, 3);

insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 1);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 07:00', 'YYYY-MM-DD HH24:MI'), 2);
insert into radna_vremena (kraj, pocetak, id_lekara) values (to_timestamp('1000-01-01 23:00', 'YYYY-MM-DD HH24:MI'), to_timestamp('1000-01-01 15:00', 'YYYY-MM-DD HH24:MI'), 3);

insert into sale (naziv, id_klinike) values ('sala 1', 1);
insert into sale (naziv, id_klinike) values ('operaciona', 1);

insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('korona test', 1, 1, 1, 1, 2, 1, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('korona2 test', null, 5, 1, 1, 2, 1, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('terapija', 1, 2, 1, 2, 2, 1, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('previjanje', 1, 4, 1, 2, 2, 1, true, true, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Bad dream', 1, 5, 1, 2, 1, 1, true, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('Krvarenje desni', 2, 6, 1, 2, 1, 1, true, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('previjanjeee', 1, 5, 1, null, 2, 1, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('zakazani1', 1, 7, 1, 2, 2, 1, true, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('zakazani2', 1, 8, 1, 2, 2, 1, true, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('zakazani3', null, 8, 1, 2, 2, 1, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('zakazani3', 1, 12, 1, null, 2, 1, false, false, 0);
insert into pregled (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_lekara, id_klinike, potvrdjen, odradjen, version) values ('zakazani3', 1, 12, 1, null, 2, 1, false, false, 0);


insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen) values ('slijepo crijevo', 2, 3, 2, 3, 1, true, true);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen) values ('slijepo crijevo', 2, 3, 2, 2, 1, true, true);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen) values ('slijepo crijevo', 2, 3, 2, 1, 1, true, true);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen) values ('slijepo crijevo', null, 9, 2, 1, 1, true, true);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen) values ('slijepo crijevo', null, 10, 2, 1, 1, true, true);
insert into operacije (ime, id_sale, id_termina, id_tipa_posete, id_kartona, id_klinike, potvrdjen, odradjen) values ('slijepo crijevo', null, 11, 2, 1, 1, true, true);

insert into recepti (overen, id_recepta, id_pregleda) values (false, 1, 1);
insert into recepti (overen, id_recepta, id_pregleda) values (true, 2, 2);
insert into recepti (overen, id_recepta, id_pregleda) values (false, 3, 2);
insert into recepti (overen, id_recepta, id_pregleda) values (true, 4, 2);
insert into recepti (overen, id_recepta, id_pregleda) values (false, 5, 2);
insert into recepti (overen, id_recepta, id_pregleda) values (false, 5, 3);


insert into dijagnoze (sifra, naziv, id_kartona) values (1, 'Covid-19', 1);
insert into dijagnoze (sifra, naziv, id_kartona) values (2, 'Depresija', 2);
insert into dijagnoze (sifra, naziv, id_kartona) values (3, 'Bipolarni poremećaj', 1);
insert into dijagnoze (sifra, naziv, id_kartona) values (4, 'Epilepsija', 3);

/*insert into stavke_cenovnika (id, cena, id_cenovnik, id_tipa_posete) values (1, 10.5, 1, 1);*/


insert into medicinske_sestre_overeni_recepti (medicinska_sestra_id, overeni_recepti_id) values (1, 2);


insert into klinika_pacijent (id_klinike, id_pacijenta) values (1,1);
insert into klinika_pacijent (id_klinike, id_pacijenta) values (1,2);
insert into klinika_pacijent (id_klinike, id_pacijenta) values (1,3);
insert into klinika_pacijent (id_klinike, id_pacijenta) values (1,4);

insert into lekari_operacije (operacija_id, lekar_id) values (1, 1);
insert into lekari_operacije (operacija_id, lekar_id) values (2, 1);

insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (1, 1);
insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (2, 2);
insert into dijagnoze_pregled (dijagnoza_id, pregled_id) values (2, 3);

insert into lekari_specijalnosti (lekar_id, tip_id) values (1, 1);
insert into lekari_specijalnosti (lekar_id, tip_id) values (1, 2);
insert into lekari_specijalnosti (lekar_id, tip_id) values (2, 1);
insert into lekari_specijalnosti (lekar_id, tip_id) values (2, 2);