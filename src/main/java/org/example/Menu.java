package org.example;

import org.example.models.User;

import javax.crypto.spec.PSource;
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
            game.startGame(signupMenu());
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
        Scanner myobj = new Scanner(System.in);
        System.out.println("enter your username :");
        String usname = myobj.nextLine();
        System.out.println("enter your password :");
        String psname = myobj.nextLine();
        User tempUser = new User(usname, psname);
        if (Database.loginGame(tempUser) != null) {
            return tempUser;
        } else {
            System.out.println("1. go back to login page");
            System.out.println("2. go to signUp page");
            System.out.println("0. exit");
            int num = myobj.nextInt();
            switch (num) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    signupMenu();
                    break;
                case 0:
                    System.exit(0);
            }
        }
        return null;

    }

    public static User signupMenu() {
        Scanner myobj = new Scanner(System.in);
        System.out.println("enter your username :");
        String usname = myobj.nextLine();
        System.out.println("enter your password :");
        String psname = myobj.nextLine();
        User signUpUser = new User(usname, psname);
        if (Database.registerGame(signUpUser) != null)
            return signUpUser;
        else {
            System.out.println("1. go back to login page");
            System.out.println("0. exit");
            int num = myobj.nextInt();
            switch (num) {
                case 1:
                    loginMenu();
                    break;
                case 0:
                    System.exit(0);
            }
        }
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
