# Contributing :

1. **AmirMmd LotfiPour** :  database authentication and city builder ...
2. **ALi Nosrati** : menu and character modifier ...

# Growing In Lightcity Project

This project is a city-building game that includes various features and systems. The objective is to understand the game
logic and design it accordingly.

## Getting Started

To get started with the project, you need to fork it

### Prerequisites

The project contains an initial structure and some implemented systems. Each team member is responsible for implementing
a specific system.

## User Interface

The user interface section includes tasks such as user registration, login, and character creation. You should also
examine the game loop and implement each system and feature as you encounter them.

The project structure is highly flexible, allowing for modifications and additions in any direction desired. The game
logic remains constant, but you can add or direct branches as needed.

## City Structure

The city structure is partially implemented, and certain algorithms, such as property creation upon the municipality's
creation, are missing. You should implement these algorithms based on the desired city structure. For example, you can
create 20 lands with dimensions of 40x80 using a simple loop to generate the properties.

Pay attention to the existing algorithms, such as the stock market's capital increase and decrease. Think about how you
can create a menu for interacting with this section or how you can display charts and detailed information about
investments. Feel free to be creative and make any necessary changes, even to the initial structure, by informing the
exercise solver.

No system is devoid of flexibility. Embrace creativity and progress each topic one by one.

## Structure Overview

The following is an overview of the initial structure and implemented classes:

1. **Game**: The Game class contains information about cities and the overall game structure.
2. **City**: The City class contains a list of characters, a bank system, a municipality, and the root character.
3. **Municipality**: The Municipality class generates properties based on a specific algorithm (you need to devise this
   algorithm). It also contains a list of properties and property operations such as buying and selling.
4. **Character**: The Character class includes user information, a bank account, a life system, a job (can be null), a
   list of owned properties, and the character's current position property.
5. **User**: The User class includes the username and password.
6. **Job**: The Job class includes the job title, income, and industry ID.
7. **Employee**: The Employee class is generated when a character joins an industry. It includes the username, base
   salary, level, bank account, and industry.
8. **Manager**: [No further information provided]
9. **Bank**: The Bank class includes a list of accounts, a manager (root), and the bank's turnover.
10. **BankTurnOver**: The BankTurnOver class includes income and outcome.
11. **BankAccount**: The BankAccount class represents the owner (a character), password, money, and the last change
    date.
12. **Property**: The Property class includes an array of scales, an array of coordinates, and the owner.
13. **StockMarket**: The StockMarket system works with the character's money and generates a profit or loss per second
    within the range of -0.03 to +0.03.
14. **Food & Liquid**: Includes water and food options.
15. **Industry**: The Industry class includes the title, income, and a list of employees.

And other interfaces...

## Contributing

Feel free to contribute to the project by suggesting improvements or implementing new features.
``
