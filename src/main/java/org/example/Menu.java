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
        System.out.println("   *** welcome to lightcity ***");
        System.out.println("**********************************");
        System.out.println("*     continue game    [1]       *");
        System.out.println("*     start game       [2]       *");
        System.out.println("*     join server      [3]       *");
        System.out.println("*     exit             [4]       *");
        System.out.println("**********************************");
        System.out.println("enter your command : ");
    }

    public static User loginMenu() {
//       get user info : username, password
        System.out.println("enter your username :");
        String usname = scanner.nextLine();
        System.out.println("enter your password :");
        String psname = scanner.nextLine();
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
