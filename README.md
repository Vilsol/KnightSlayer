# KnightSlayer

This is a solution to the game "Dragons of Mugloar" http://www.dragonsofmugloar.com/ using a very simple weight based network, which takes the knight, and adjusts weights depending on the weather.

Currently can achieve >= 100% accuracy.

From multiple iterations of the code, I think I have completely reverse engineered the game. Now that I have fully realized how the game works, there is a lot of dead code, that is no longer necessary.

## How to win the game

Each of the knight stats have a direct opposite for the dragon
Knight Attack = Dragon Scale Thickness
Knight Armor = Dragon Claw Sharpness
Knight Agility = Dragon Wing Strength
Knight Endurance = Dragon Fire Breath

It is as simple as checking the weather:

- If it's storming, you have two options, but the idea is the same. You count it as a fight, but it is your choice to send or not to send a dragon
- If it is foggy, just send a dragon with the same stats as the knight, don't know if the stats even matter, but you seem to win every time
- If it is raining, set the scale thickness to 5, claw sharpness to 10 (to cut through the boat) and the wing strength to 5. Leave the fire breath at 0, because it is useless in the rain.
- If it is dry, simply set all stats to 5, and that will make the dragon "balanced" or "zen".
- If it is normal weather, make the dragon the same as the knight, find the highest stat of the knight, and increase the weight of that stat in the dragon. If there are two identical highest stats, take the first one, like it is listed above.

That is seriously it, really simple game if you find out all the rules.

If you count that you win storms by not even sending a dragon, but still count that the knight is dumb enough to go to a battle, then you can get percentages higher than 100%.