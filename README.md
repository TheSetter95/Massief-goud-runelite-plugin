# Massief Goud RuneLite-plugin

Meldt automatisch item-drops aan de Massief Goud-App, zodat verzamel-vakjes bij verschillende events van 
MassiefGoud afgetikt worden
## Belangrijk om vooraf te weten

- De plugin luistert naar het `LootReceived`-event van RuneLite (drops van killed
  NPC's/spelers — geen grondvondsten of trades, dat is bewust zo)
- Voor elk gedropt item stuurt hij een klein berichtje naar
  `https://massiefgoud.vercel.app/api/plugin/report-drop`, met je plugin-sleutel als
  bewijs wie je bent

