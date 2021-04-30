# supportMe

A project consisting of the members:
Gabriel Modin Bärzén,
Isak Holmqvist,
Marcus Juninger,
Nicholas Narvell,
Ahmad Toron

Projektet är baserat runt en idé om teknisk support för dina nära och kära. Programmet ska fungera som en central för användare som inte är tekniskt lagda och kan behöva hjälp med sin teknik hemma. Den andra typen av användare är de som är tekniskt lagda som kan skapa och dela skräddarsydda felsökningsguider. Programmet kommer att ha två olika vyer, en för att skapa guider och en för att visa guiderna på ett enkelt och tydligt sätt. Målgruppen för vårt program är personer som ofta får hjälpa personer i sin omgivning med teknisk support och ger de en möjlighet att skapa tydliga guider. 

# Hämta projekt Till intellij
0. Förutsättningar:
java --version ger version java 15 eller högre

1. Kör kommandot ```git clone https://github.com/GabrielModin/supportMe``` där du vill lägga projektet

![image](https://user-images.githubusercontent.com/71310727/114859549-3232d680-9deb-11eb-8cae-f4b58ead3204.png)

2. Starta projektet som ligger under /Code/ProjectMain

![image](https://user-images.githubusercontent.com/71310727/114859647-55f61c80-9deb-11eb-9324-0a21f5733a23.png)

![image](https://user-images.githubusercontent.com/71310727/114859730-702ffa80-9deb-11eb-9ae3-4c7be272b08c.png)

3. Öppna maven configuration

![image](https://user-images.githubusercontent.com/71310727/114859845-8e95f600-9deb-11eb-9fb3-21ff5eba149e.png)

4. Installera Shared

![image](https://user-images.githubusercontent.com/71310727/114859990-be44fe00-9deb-11eb-932e-e210057c13b6.png)

Nu är klient samt servern redo att köra.

# Client instruktioner

1. Compilera & kör genom javafx fliken under mave configuration

![image](https://user-images.githubusercontent.com/71310727/114860221-02380300-9dec-11eb-8983-8500dcf1cd78.png)

# Server instruktioner

1. Starta servern genom att starta main Server klassen.

# Databas

För att databas koppling ska fungera krävs det att pwd.txt placeras under filvägen 
src/main/resources/org/supportmeinc/pwd.txt

Filen kommer finnas under retrospekt katalogen i canvas.

# Felsökning

Projektfiler dyker inte upp i intellij

![image](https://user-images.githubusercontent.com/71310727/114860718-a7eb7200-9dec-11eb-9721-38f6291c55c0.png)

1. Ladda om maven projekt

![image](https://user-images.githubusercontent.com/71310727/114860809-c05b8c80-9dec-11eb-9210-8c99603c8c54.png)

2. Trust project

![image](https://user-images.githubusercontent.com/71310727/114860857-cc474e80-9dec-11eb-86c0-c09d16a35e8b.png)

