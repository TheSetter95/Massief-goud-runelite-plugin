# Massief Goud RuneLite-plugin

Meldt automatisch item-drops aan de Massief Goud-website, zodat verzamel-vakjes op het
Ganzebord vanzelf bijgehouden worden per team.

## Belangrijk om vooraf te weten

Dit is een **fundamenteel ander traject** dan de website of de Discord-bot:
- Geen `npm install` en `npm run dev` — dit is **Java**, met een eigen ontwikkelomgeving
- Je kan de plugin niet zomaar "aanzetten" zoals een gewone RuneLite-plugin — om 'm te
  **testen** heb je IntelliJ IDEA nodig, en om 'm **openbaar te verspreiden** naar je
  hele community moet hij eerst goedgekeurd worden via de officiële RuneLite Plugin Hub
  (een reviewproces, geen instant-publicatie)
- Reken op een stevigere leercurve dan de rest van dit project tot nu toe

## Stap 1 — Ontwikkelomgeving opzetten

1. Installeer **IntelliJ IDEA** (Community edition is gratis): https://www.jetbrains.com/idea/download
2. Installeer de **Lombok-plugin** in IntelliJ: `File` -> `Settings` -> `Plugins` -> zoek
   "Lombok" -> installeren -> IntelliJ herstarten
3. Zorg dat je een **Jagex-account** hebt gekoppeld — de RuneLite-ontwikkelclient
   vereist tegenwoordig een geldig Jagex-account om in te loggen. Zie de officiële
   RuneLite-documentatie ("Jagex account setup") als je dit nog niet eerder hebt gedaan.

## Stap 2 — Het project openen

1. Pak deze map (`massief-goud-plugin`) ergens uit
2. Open IntelliJ IDEA -> `Open` -> kies de `massief-goud-plugin`-map
3. IntelliJ herkent het als een Gradle-project en downloadt automatisch de benodigde
   RuneLite-bibliotheken (dit kan de eerste keer een paar minuten duren)

## Stap 3 — Testen (lokale dev-client)

1. Open het bestand `MassiefGoudPlugin.java`
2. Zoek in het **Gradle-paneel** (meestal rechts in IntelliJ) naar de `run`-taak, of
   maak een Run Configuration aan die `net.runelite.client.RuneLite` als hoofdklasse
   gebruikt
3. Start deze — dit opent een **eigen, aparte** RuneLite-client (los van je normale
   RuneLite) met jouw plugin er al in geladen
4. Log in met je OSRS-account in deze dev-client
5. Ga naar de plugin-instellingen (het moersleutel-icoontje) -> zoek "Massief Goud" ->
   vul je **plugin-sleutel** in (die haal je van je profielpagina op de website)

## Stap 4 — Testen of het werkt

1. Zet op de website een Ganzebord-event op **actief**, met een team dat op een
   "Verzamel item(s)"-vakje staat
2. Zorg dat je zelf lid bent van dat team (via je Discord-koppeling)
3. Dood in-game iets dat het benodigde item droppen kan (of gebruik een makkelijk te
   testen NPC/item als proef)
4. Check op de website of de teller omhoog gaat

## Wat gebeurt er technisch?

- De plugin luistert naar het `LootReceived`-event van RuneLite (drops van killed
  NPC's/spelers — geen grondvondsten of trades, dat is bewust zo)
- Voor elk gedropt item stuurt hij een klein berichtje naar
  `https://massiefgoud.vercel.app/api/plugin/report-drop`, met je plugin-sleutel als
  bewijs wie je bent
- De server (die je al gebouwd hebt) zoekt zelf uit bij welk team je hoort, of dat team
  op een vakje staat dat dit item nodig heeft, en telt het zo nodig mee

## Stap 5 — Verspreiden naar je hele community

Dit is de grote vervolgstap, met twee opties:

### Optie A — Officiële Plugin Hub (aanbevolen, maar met wachttijd)
1. Zet dit project in een eigen GitHub-repository
2. Volg de instructies op https://github.com/runelite/plugin-hub om een Pull Request
   te openen naar hun repository
3. Je plugin wordt gereviewd (op veiligheid en naleving van de spelregels — geen
   automatisering van input, alleen data lezen en melden, wat dit plugin al doet)
4. Na goedkeuring kan **iedereen** de plugin gewoon vinden en installeren via de
   Plugin Hub in RuneLite zelf — geen gedoe meer met IntelliJ voor je community

### Optie B — Handmatig delen (sneller, maar omslachtiger per lid)
Elk lid zou dan zelf dit hele traject (IntelliJ, Gradle, dev-client) moeten doorlopen —
dat is voor een hele community onrealistisch. Ik raad dit alleen aan voor je eigen
testen, niet als eindoplossing.

## Samengevat: volgende stappen

1. Test dit eerst zelf via de dev-client (Stap 1-4)
2. Werkt het naar wens? Dan pakken we samen **Optie A** op — ik help je met de
   GitHub-repository en de Plugin Hub-aanvraag zodra je zover bent
