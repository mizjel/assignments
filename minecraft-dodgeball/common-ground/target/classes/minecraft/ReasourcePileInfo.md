# Readme voor de ResourceChest
Owner: Silke Schönecker

De ResourceChest / -Pile wordt aangemaakt in de arena, nadat de targets geplaatst zijn, en heeft een locatie nodig, in dit geval een punt achter de targets. <br />
De Chest wordt gemaakt door een block air naar het type chest te veranderen en zijn inventory op te vullen.

Oorspronkelijk is de inhoud 1 DIAMOND_CHESTPLATE, 1 DIAMOND_BOOTS en een random aantal items van ARROW, GLASS_PANE, IRON_BLOCK, SPECTRAL_ARROW. Voor een verschil in beschikbaarheid staan sommige materialen vaker in de materials-list (dit zou netter kunnen).

In MyPlugin staat een runnable, die om de zoveel tijd aan de arena doorgeeft de chests op te vullen. Bij het opvullen is er een beperkte kans (0.2) om weer een diamond-armour te spawnen. De rest van de available space in de chest wordt bijgevuld met random materialen (hierbij wordt NIET gekeken naar de types die er nog in zitten). <br />
Mocht een chest verdwijnen, zou er bij het refillResources-en in de Arena ook een nieuwe chest aangemaakt moeten worden, deze heeft dan weer de oorspronkelijke inhoud.

De ResourceChest wordt getest in ResourcePileTest.

Advancements: Het max aantal items en de timer zouden zo aangepast kunnen/moeten worden, dat het spel leuk blijft. Bovendien kun je, zoals het nu is, je chest kapot slaan en met absolute zekerheid beide diamond armours krijgen.
