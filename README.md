# supportMe

A project consisting of the members:
Gabriel Modin Bärzén,
Isak Holmqvist,
Marcus Juninger,
Nicholas Narvell,
Ahmad Toron

Projektet är baserat runt en idé om teknisk support för dina nära och kära. Programmet ska fungera som en central för användare som inte är tekniskt lagda och kan behöva hjälp med sin teknik hemma. Den andra typen av användare är de som är tekniskt lagda som kan skapa och dela skräddarsydda felsökningsguider. Programmet kommer att ha två olika vyer, en för att skapa guider och en för att visa guiderna på ett enkelt och tydligt sätt. Målgruppen för vårt program är personer som ofta får hjälpa personer i sin omgivning med teknisk support och ger de en möjlighet att skapa tydliga guider. 

# Hämta projekt Till intellij
0. Kontrollera att ```java --version``` åtminstånde ger ```15.0.1```

1. Hämta projektet från github genom kommandot ```git clone https://github.com/GabrielModin/supportMe``` eller via github-desktop.

2. Öppna projektet som ligger under sökvägen {download dir}/supportMe/Code/ProjectMain i intellij

![image](https://user-images.githubusercontent.com/71310727/117422219-a4e12e80-af1f-11eb-959f-ac41870c0066.png)

3. Öppna fliken maven till höger i intellij

![image](https://user-images.githubusercontent.com/71310727/117422331-c6421a80-af1f-11eb-9c16-d53d1561028e.png)

5. Kör "Generate Sources and Update Folders For All Projects"

![image](https://user-images.githubusercontent.com/71310727/117422442-e4a81600-af1f-11eb-8d53-b388c67ab0ca.png)

4. Kör Shared/Lifecycle/install

![image](https://user-images.githubusercontent.com/71310727/117422518-f8537c80-af1f-11eb-8f4c-b71070f21c6c.png)

5. placera filen pwd.txt som ligger i canvas under Server/src/main/resources/pwd.txt
Filen innhåller information som server behöver för att koppla till databas.

![image](https://user-images.githubusercontent.com/71310727/117422663-2a64de80-af20-11eb-8acc-bebd3688ba1f.png)

Server :
1. starta servern genom att högerklicka på klassen "Server.java" och sedan klicka "run Server.main()"

![image](https://user-images.githubusercontent.com/71310727/117423740-559bfd80-af21-11eb-83b5-aad2e1d36db1.png)

Client :
1. Öppna fliken maven till höger i intellij

2. kör Client/Plugins/javafx:compile

![image](https://user-images.githubusercontent.com/71310727/117423841-6c425480-af21-11eb-8bfa-bb22375666bd.png)

3. kör Client/Plugins/javafx:run

![image](https://user-images.githubusercontent.com/71310727/117423855-706e7200-af21-11eb-8671-d59738069163.png)

