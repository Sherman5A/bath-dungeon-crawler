Coursework 2 - Bath Dungeon Crawler

Achieving separation of the implementation and input / output was one of my goals. OOP helped greatly with this, allowing
me to initialise an instance of UserInterface, providing it with a scanner, and then encapsulating the entire input and
output userLoop to a contained method. This made the Main.java file extremely simple, only requiring the instantiation of classes
and calling some methods.

Significant portions of the code were hidden in private methods, preventing access to them, and showing the user
with simplified methods. For example, the large parseHiddenMap() method was private, instead being called in the BotHandler's
lookMap(), hiding the implementation on how the bot gathers information about its environment.

Polymorphism was used at several stages. Overloading functions was used in the GameMap class to provide several
interfaces to checkTile, using it for either spawning or checking for gold.

Furthermore, OOP allowed me to reuse much of the code I implemented for the player in the bot as they had similar
features. This saved large amounts of time.