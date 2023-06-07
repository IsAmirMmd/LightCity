package org.example;

import org.example.models.User;

import java.util.Scanner;

public class Menu {
    private static Game game = new Game();

    private static Database database = new Database();
    private static Scanner scanner = new Scanner(System.in);

    public static void showMenu() {
        mainMenu();
        String next = scanner.next();
        if (next.equals("1")) {
            game.continueGame(loginMenu());
        } else if (next.equals("2")) {
            game.startGame(loginMenu());
        } else if (next.equals("3")) {
            joinServer();
        } else if (next.equals("4"))
            System.exit(0);
    }

    public static void mainMenu() {
        System.out.println("welcome");
    }

    public static User loginMenu() {
//       get user info : username, password
        return null;
    }

    private static void joinServer() {
        System.out.print("Enter Server Ip Address :");
        String ip = scanner.next();
        System.out.print("Enter Server Port :");
        int port = scanner.nextInt();
        game.joinServer(ip, port);
    }

    public static void main(String[] args) {
        showMenu();
    }
}
