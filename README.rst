=====
GEO REVERSE
=====

Requisisti
============

* IDE Java
* Jdk 1.5+

Utilizo
=======
	Per il momento il progetto è in fase di Sviluppo. E' più per utilizzo personale, che di condivisione.
	
	Prende come input, dei files CSV, ogni riga ha il formato : [CODICE INTERNO]||[INDIRIZZO]
	
	Esempio
	003036321||Via Don Milani #, 61025, MONTELABBATE (PESARO-URBINO), Italia
	
	Dopo essere elaborato l'applicazione torna due file
	
	- UUID.OK : Alla riga viene aggiunto la LON e LAT 
	   003036321||Via Don Milani #, 61025, MONTELABBATE (PESARO-URBINO), Italia ||43.8474503||12.7842333
	- UUID.KO
	
Compilazione
==============
	Per la compilazione, opzione :
	- export Jar solo il sorgente src/main/java ed il MANIFEST.MF da src/main/resources/META-INF
	
Installazione
==============
	la struttura esta al interno della cartella App
	
	+ Root
		+conf
			geo.properties
		+data
			+in
				[Files da input]
			+process
				[Files processati OK,KO]
		+lib
			commons-io-1.3.2.jar
			hamcrest-core-1.1.jar
			json-simple-1.1.1.jar
			junit-4.10.jar
			log4j-1.2.17.jar
		+log
		geoReverse.jar
		log4j.properties	
	
	