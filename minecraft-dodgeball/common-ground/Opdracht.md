# De Sogyo Minecraft Server

## Virtueel Trefbal met Explosieven.

Het idee is om een soort virtuele trefbalarena te bouwen in Minecraft waarbij twee teams in hun eigen gebied blijven en explosieven naar elkaar gooien. Het team waar het doel als eerst vernietigd is, verliest. Natuurlijk kun je elkaar ook afgooien, zodat je een bepaalde tijd moet wachten.

Het verschil tussen een leuk spel en een minder leuk spel zit vaak in het afstellen van parameters. Instellingen zoals afstand tussen de gebieden van de spelers, grootte van het gebied e.d. kunnen veel effect hebben. Als het gebied te klein wordt, dan kan het doelwit makkelijker worden geraakt. Als het gebied juist te groot is, dan kun je bijna niets raken. Het zoeken naar de balans is dus erg belangrijk.

De plugin wordt gebouwd door twee teams. In principe zorgt het ene team voor de aanvallende mogelijkheden en het andere team voor de defensieve acties. De battle arena zelf komt ook niet uit de lucht vallen: zorg er dus voor dat je dit onderling goed afstemt! Voor beide teams wordt een suggestie gedaan voor een eerste wapenfeit. Gebruik (in overleg) ook vooral je creativiteit!

Voor het ontwikkelen van een plugin maken we gebruik van [Spigot](https://www.spigotmc.org/wiki/about-spigot/). Spigot biedt een leesbare API voor Minecraft-objecten. Voor Minecraft 1.16.4 heeft Spigot een [corresponderende versie uitgebracht](https://www.spigotmc.org/threads/spigot-bungeecord-1-16-2-1-16-3-1-16-4.457579/).

In principe wil je dat de teams zo onafhankelijk mogelijk kunnen ontwikkelen. Spreek dan ook een gedeelde interface af. De teams kunnen dan tegen deze interface aan ontwikkelen. Aangezien Minecraft een actieve wereld is waarin dingen gebeuren, zijn de events (later meer) een goed beginpunt van de interface.

Nog wat belangrijke punten:
- De stappen zijn vooral thematisch verdeeld over de teams. Wij weten van te voren niet hoe makkelijk of moeilijk een punt te implementeren is. Houd daarom ook contact met elkaar en ondersteun elkaar waar nodig.
- Wees vooral creatief. Je hoeft de opdracht niet tot op de letter te volgen. Als je een oplossing weet die makkelijker en leuker is, ben je vrij om deze te implementeren. Ook als je een bepaalde feature voor elkaar wil krijgen, kun je kijken naar alternatieve manieren om grofweg hetzelfde te bereiken. Het zal niet de eerste keer zijn dat in een game iets gefaket wordt om de implementatie makkelijker of überhaupt mogelijk te maken.

## Opbouw

_Squid Squad en Team Octo joint effort_

Om de minigame te kunnen spelen, zullen we deze allereerst moeten opzetten. Dit onderdeel bevat het in het leven roepen van de arena en de mogelijkheid om deze te joinen.

1. Maak een commando `create_arena`. Dit commando maakt twee gebieden op een bepaalde afstand van elkaar. Maak bijvoorbeeld een twee torens op een bepaalde afstand van elkaar. Deze hoeven nog niet gekoppeld te zijn aan een score.

   Tips Spigot:
   1. Commando’s moet je in het plugin.yml registreren, anders weet Spigot niet dat het commando bestaat. Een voorbeeldcommando is te zien in    plugin.yml in het template.
   2. Vergeet ook niet je commando te koppelen aan een Executor. Zie hiervoor lijn 25 in `MyPlugin.java` in het template.
   3. Minecraft is vector based.  Voor een basis in vectorprogrammeren kun je terecht bij [de tutorial van Spigot](https://www.spigotmc.org/wiki/vector-programming-for-beginners/).
   4. Spigot is grotendeels event-driven. Hiervoor gebruikt het `Listener`s en `Event` types en `@EventHandler`-annotaties. Er is in het template als voorbeeld al een `Listener` met een `@EventHandler` geïmplementeerd. Voor meer info over de Event API lees [de wiki](https://www.spigotmc.org/wiki/using-the-event-api/). Voor meer info over alle mogelijke events kan je kijken in de [JavaDocs](https://hub.spigotmc.org/javadocs/spigot/) onder de `org.bukkit.event` package en alle child packages hiervan.

2. Maak een command `join_arena`. De speler die dit commando uitvoert, wordt ingedeeld in een van de twee teams en naar de betreffende arena geteleporteerd.

   Spigot API Tips:
   1. Zoek in de JavaDocs naar de `Player`-klasse, deze heeft enkele handige methodes om bovenstaande uit te voeren.

3. Deelnemers mogen hun eigen gebied niet verlaten. Teleporteer ze terug zodra dat gebeurt. Als een deelnemer moet respawnen, moet dit ook in hun eigen gebied zijn. Kijk of je dit met een tijdstraf van enkele seconden voor elkaar kan krijgen.

   Spigot API Tips:
   1. Zoek in de JavaDocs naar de `Player`-klasse, deze heeft enkele handige methodes om bovenstaande uit te voeren.
   2. Opnieuw Events zijn belangrijk. Als je wat het gebied van een team is dan kan je aan de hand van event bepalen wanneer een Player deze    verlaat.

4. Zorg ervoor dat geminede of vernietigde "strategic" resources (benodigd voor bijvoorbeeld het gooien van TNT) na een bepaalde tijd respawnen. 

5. Research en/of crafting. Kijk of het mogelijk is om research (permanente upgrades) of crafting (consumable upgrades) in te bouwen. Te denken valt aan een 'bananenbom' (een ontploffing die een aantal kleinere bommen spawnt) of een 'reflecterend schild'. 

## Aanval

_Squid squad_

1. Maak een commando waarmee je een blok TNT kunt spawnen. Dit blok krijgt een specifieke richting en snelheid. Zorg dat dit blok via een boog naar de andere kant van het veld gaat; hiervoor moet het blok dus geen te hoge snelheid hebben. Voor de leukigheid kun je een kleine random offset meegeven zodat je je schoten 'scattert'.

   Spigot API Tips:
   1. Spigot/Minecraft maakt verschil tussen een Block en een Entity. In dit geval wil je een actief stuk TNT spawnen. Als hint het spawn commando:
   ```java
   Location loc = …; // bepaal waar de entity gespawnd moet worden.
   World world = ...; // Verkrijg op een of andere manier de World
   TNTPrimed tnt = (TNTPrimed) world.spawn(loc, TNTPrimed.class);
   ```
   2. Zoek TNTPrimed op in de Javadocs. Je wil waarschijnlijk kijken naar de methodes `setVelocity` en `setYield`.

2. Simuleer windsnelheid. Hierdoor zal je gegooide bom een curve krijgen.

3. Zorg dat het gooien of afschieten van een blok TNT resources kost. De benodigde resources kunnen in de arena geplaatst worden.

4. Zorg dat er monsters spawnen zodra iemand een verdediging opwerpt. Het monster moet dan de verdediging opblazen. Let wel dat deze het doel niet mag beschadigen.


## Verdediging

_Team Octo_

1. Maak een doel in beide gebieden. Het doel kan iets van een kubus van vier bij vier blokken zijn. Het doel moet duidelijk te onderscheiden zijn en te vernietigen (een niet al te grote hardheid). Hou bij van elk team bij hoeveel er nog over zijn als score en geef aan wanneer een team verloren heeft.

   Tips Spigot:
   1. Wanneer je blocks genereert, houd dan hun Location bij in een Map of List. Bij het afsluiten van de server of bij het beëindigen van een  ronde, kun je de blocks netjes opruimen.
   
   Algemene Tips:
   1. Obsidian en Bedrock zijn twee blok-soorten die bijna onverwoestbaar resp. niet te beschadigen zijn.
   2. Je kan proberen een ronde toren te maken, maar vergeet niet dat dat lastig is in een vierkante wereld :)

2. Bouw muren om het doel heen. Hoe dik en stevig je de muur maakt, zal bepalen hoelang een potje duurt. Zorg dus dat je dit makkelijk kan blijven tweaken.

3. Zorg dat je een groot 'schild' kan spawnen. Dit zal dan inkomende projectielen blokkeren. Als er een blok van het schild kapot gaat, vernietig dan het hele schild.

4. Het maken van het schild of het afvuren van afweergeschut moet resources kosten. De benodigde resources kunnen in de arena geplaatst worden.

5. Een andere manier om een projectiel onschadelijk te maken is door het uit de lucht te schieten. Maak een apparaat dat bijvoorbeeld reageert op projectielen die jouw terrein op dreigen te komen. Het apparaat heeft ook hiervoor resources nodig. Je kunt een bepaald aantal (5-10) schoten vooraf inladen.


## Teamindeling

**The Squid Squad**
_Raiders_
- Christelle Klein - Scrum Master
- Sabine Glotzbach - Technical Lead
- Glenn Mulder - OPS engineer
- Ruben Timmermans - Developer/tester
- Rosa Luirink - Developer/tester 
- Jeroen Vester - Developer/tester

**Team Octo**
_Defenders_
- Silke Schönecker - Scrum Master
- Michel van der Hoorn - Technical Lead
- Wicher Otten - OPS engineer
- Bartjan de Vries - Developer/tester
- Niels van der Horst - Developer/tester

### Chapter: Operations (Infrastructuur)

De operations ondersteunen het team in het deploybaar maken van de plugins en operationele zaken omtrent de infrastructuur. Hoe kunnen we de server herstarten of resetten?

### Chapter: Technical Lead (architecture/design)

De technical lead zal zich bezighouden met het doorhakken van knopen tijdens technische discussies (maar hoeft deze niet per se zelf te bedenken). De technical lead zal hun squadgenoten er ook op aanspreken als de afspraken niet nageleefd worden. Daarnaast zullen zij tussen de squads de integratie tussen de projecten bemiddelen.

### Chapter: Testen

Stiekem gaan jullie een best complex stuk software bouwen. Hoe ga je dit geautomatiseerd en vooraf testen? Hiermee voorkom je veel downtime doordat je een versie moet deployen die misschien niet goed werkt en zorg je ervoor dat je binnen je team veel efficiënter kan gaan werken.

### Chapter: Scrum master

De scrum masters zorgen ervoor dat het proces binnen het team soepel loopt. Dit betekent dat communicatiemomentjes (zoals stand-ups) en reflectiemomentjes (zoals retrospectives) ingepland worden. Daarnaast zorgen de scrum masters op een functioneel niveau voor overzicht waar het team (en het andere team) mee bezig is. Hierdoor kunnen ze zorgen dat het werk op een goede manier tussen de teams verdeeld wordt.


## Resources:
- [Using the Event API](https://www.spigotmc.org/wiki/using-the-event-api/)
- [Eclipse: Debug your Plugin](https://www.spigotmc.org/wiki/eclipse-debug-your-plugin/)
- [Creating a Config File](https://www.spigotmc.org/wiki/creating-a-config-file/)
- [Working with Configuration Files](https://www.spigotmc.org/wiki/config-files/)
- [Creating a blank Spigot plugin in IntelliJIDEA](https://www.spigotmc.org/wiki/creating-a-blank-spigot-plugin-in-intellijidea/)
- [Creating a blank Spigot plugin in IntelliJ](https://www.spigotmc.org/wiki/creating-a-blank-spigot-plugin-in-intellij/)
- [Creating a blank Spigot plugin in Eclipse](https://www.spigotmc.org/wiki/creating-a-blank-spigot-plugin-in-eclipse/)
- [Vector Programming for Beginners](https://www.spigotmc.org/wiki/vector-programming-for-beginners/)
- [Creating a Simple Command](https://www.spigotmc.org/wiki/create-a-simple-command/)
- [Overview (Spigot-API 1.16.4-R0.1-SNAPSHOT API)](https://hub.spigotmc.org/javadocs/spigot/)

