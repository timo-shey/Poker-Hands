# Problem #54 from Project Euler - Poker Hands

This is a solution that attempts to solve Problem #54 from Project Euler, determining how many hands Player 1 wins. It involves analyzing one-thousand random hands dealt to two players.

These hands are read from a text file (Poker.txt). Each line of the file contains ten cards (separated by a single space): the first five are Player 1's cards, and the last five are Player 2's cards.

## How the Solution Works (At a High Level)

This problem was solved using Object-Oriented Programming (OOP) style to ensure separation of concerns. The main classes and their functionalities are:

- **Card Class:** Represents a playing card with rank and suit attributes. Ranks are assigned based on characters representing card values ("A" for Ace, "K" for King, etc.), and suits are assigned based on characters representing suits ("D" for Diamond, "C" for Club, etc.).

- **Hand Class:** Represents a hand of five cards. It contains an array of Card objects and provides a `getScore()` method to calculate the score of the hand using the HandService interface. The constructor creates a Hand object from an array of strings representing Card values (rank and suit).

- **Suit Enum:** Represents the suits present in a deck of cards(Spades, Hearts, Clubs, Diamonds). 

- **Hand Service Interface:** An abstraction of the service used to calculate the score of a hand, with a `getScore()` method that takes an array of Card objects.

- **Hand Service Implementation Class:** Implements the HandService interface, following poker rules to determine the score of a hand.

- **Poker Class:** Manages game logic by reading the poker.txt file containing poker hands, splitting them into individual hands, and determining the winner based on their scores.

- **Main Class:** The entry point of the program. It creates an instance of the Poker class, counts the number of wins for Player 1, and prints the result.

## What You Like and Do Not Like About Your Solution

**Like:**
- The code is well-structured, following Object-Oriented Programming(OOP) principles with clear separation of concerns.
- The use of interface and implementation allows for easy extension and customization of the hand scoring logic.
- Custom error handling was introduced for cases where the input file doesn't have the expected number of cards.

**Do Not Like:**
- Given that I am still in the process of fully grasping Java Streams API, I would have loved to include comments in the code to
  serve as a guide for myself or anyone that might come in contact with this code to have a full understanding of what every method does
  and how the program runs as a whole. Upon a careful review of the Code section of the Engineering Note, it was not stated that
  comments in code were acceptable, so I decided to leave them out.

## New Technologies or Approaches Used

The Java Stream API library was used for data processing, along with Comparator for sorting cards. While not entirely new, this project helped improve my understanding of Java Streams API.

