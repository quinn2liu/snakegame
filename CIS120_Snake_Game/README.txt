=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 15631561
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays and Collections - I represented the grid which the snake
  traverses on a 2d array. This made it easy to mark the start and end
  position of the snake as a 2d array gives a 2-d coordinate system in the form
  of row and column representation. In addition to this coordinate system, the
  array will be used to draw the snake as well as the actual grid that it is
  traversing. I also used a LinkedList to represent the Snake as a list of
  SnakeParts. This made it easy to adjust the snake's movement and behavior.

  This is an appropriate use of the concept considering that LinkedLists are
  a great way to have a list of elements, with duplicates allowed, and also
  where the order of the elements matters. The 2D arrays was also an appropriate
  use as it's row-major order gave a coordinate system representing the playing
  space.

  2. Inheritance and Subtyping - I created a Ghost and Apple subclass of GameObj
  that allows me to use the methods, instance variables, and constructor
  of the GameObj class.

  This is an appropriate use of the concept as it allowed me to reduce the
  amount of code that I had to write. For example, instead of writing a move
  method for both Apple and GameObj, I was able to implement the same one.

  3. File I/O - I used file IO to save the state of the game to a csv file
  and then preserve that data so that the save could be re-loaded at a later
  date.

  This is an appropriate use of the concept as the file must be updated with
  each save and then read at any point later on, and must be stored when the
  game is no longer running.

  4. JUnit Testing - I used JUnit testing to test different methods and
  processes within the game individually instead of having to troubleshoot
  on my own.

  This is an appropriate use of the concept as many methods, such as
  growing the snake, or collisions with objects, can't be easily tested
  without initiating the GUI. Thus JUnit testing allowed me to
  troubleshoot any potential issues that can arise when implementing.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Apple - this class extends GameObj and specifically handles the image
  printing and initial position setting of the apple object.

  Direction - unchanged from what was given.

  GameCourt - Contains the logic for when the snake should move as well as
  determining the collision logic with the snake. This class also handles the
  spawning of the apple and ghost objects and also handles saving and loading
  different save states.

  GameObj - The framework for how Game Objects are created. The move method has
  been changed to randomly pick a new position for the apple and ghost objects
  (the snake has it's own move method).

  Ghost - Similar to apple, this class extends Game Obj and specifically handles
  the ghost image printing.

  RunSnake - This class has been essentially unchanged except for the adding
  the instructions JButton to display the instructions and also the save/load
  JButtons which save the game and then load that save, respectively.

  SnakeBody - This class stores the SnakeParts in the form of a linked list. It
  also handles adding a new SnakePart (along with assigning it its coordinates)
  and the movement of the snake.

  SnakePart - This class represents the individual parts of the snake that are
  stored in the SnakeBody linked list.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I think the biggest issue I ran into was trying to figure out the best way to
  implement it. There were a lot of different ideas I had as to how I should
  structure the code but at the end of the day I combined all of it into a
  somewhat incoherent mess. Given more time, I would definitely have wanted to
  clean everything up a little.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I would most certainly use the point class now that I have learned that it
  exists, as that would eliminate so much redundant code when I worked with 1-D
  arrays. I would also try to create a better class structure/hierarchy among the
  different classes I used as there are probably better ways to implement the
  methods that I use without having to repeat a lot of code. I would also like
  to rethink how I implemented the GameGrid class and I wish I standardized my code
  so that everything would run using the grid coordinates.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  For the apple, I used this image: "https://art.pixilart.com/5ac3131af4800a4.png"
  And for the ghost, I used this image:
  "https://static.wikia.nocookie.net/legendsofthemultiuniverse/images/9/95/92
  -Gastly.png/revision/latest/scale-to-width-down/275?cb=20190223141123"