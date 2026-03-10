# Chess-Service 2 #

# Marco - lokal #

## Springboot ohne Docker ##

### Voraussetzungen ###
* Mindestens Java11, testen mit 
```
java --version
```

* Maven ist wird vom Skript (s.u.) automatisch  installiert.

### Repository clonen ###
```
git clone https://...@bitbucket.org/berndpb/chess-service2.git
```
## Bauen und starten ##

Alles nötige befindet im Script "marco-build-run.sh". Vorher Ausführungsrecht setzen:
```
chmod u+x marco-build-run.sh
``` 

Dann ohne Optionen aufrufen:

```
./marco-build-run.sh
``` 

Portnummer steht im Script. Dabei wir der Mavenwarpper aufgerufen, der die erforderliche Mavenversion
automatisch installiert.

Im Startscript wird ein Profil "marco" verwendet. Die Konfiguration befindet sich in:

src/main/resources/application-marco.yml

Die IP am Ende der Liste "hosts-allowed" muss ggf. geändert werden

## Starten mit Docker ##

```
docker pull berndpb/chess-service2
```

Docker-Login ist nicht erforderlich. Das Repository ist "public". Dann die Image-ID nachschauen:

```
docker images
```

Das neue Image muss dann in der Liste stehen:

```
<none>                   <none>              722d31fd05a6        20 hours ago        505MB
<none>                   <none>              79a6c96e3c1c        21 hours ago        505MB
berndpb/chess-service2   latest              ed5710c5ef93        12 days ago         503MB
``` 

Mit Namen unter Verwendung des Profils "marco" (wegen IP-Check erforderlich) starten:

```
docker run --env SPRING_PROFILES_ACTIVE=marco -t berndpb/chess-service2
```



