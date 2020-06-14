# MRSISA_2020_T6

## Članovi tima:
Nikola Papić SW4-2017
Dušan Stojančević SW11-2017
Vera Kovačević SW19-2017

## Pokretanje projekta

Za pokretanje ovog projekta na računaru je potrebno imati sledeće programe:
* Apache Maven
* Java JRE 8 ili noviju verziju
* Node.js


Nakon preuzimanja projekta sa github-a potrebno je instalirati npm (Node package manager) u folderu "vueapp" u korenu projekta sledećom komandom:
```bash
npm install
```
Nakon toga je potrebno pokrenuti maven, instalirati sve potrebne bibilioteke i kreirati jar. To se obavlja komandom:
```bash
mvn -DskipTests clean dependency:list install
```
I na kraju da bi se pokrenuo server potrebno je pokrenuti kreirani jar pomocu jave:
```bash
java -jar SpringWebApp\target\SpringWebApp-1.0.jar
```
