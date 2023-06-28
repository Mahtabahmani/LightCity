package org.example;

import org.example.models.User;

import java.util.Scanner;

public class Menu {
    private static Game game = new Game();
    private static Scanner scanner = new Scanner(System.in);
    public static void showMenu(){
       mainMenu();
       String next = scanner.next();
       if (next.equals("1")) {
           game.continueGame(loginMenu());
       }else if(next.equals("2")){
           game.startGame(signUpMenu());
       }else if (next.equals("3")){
           joinServer();
       }else if (next.equals("4"))
           System.exit(0);
    }
    public static void mainMenu(){
        System.out.println("1-Continue\n2-Start New Game\n3-Join Server\n4-Exit");
    }

    public static User loginMenu(){
//       get user info : username, password

        System.out.println("Enter Username :");
        String use = scanner.next();
        System.out.println("Enter Password :");
        String pass = scanner.next();
        User user = new User(use,pass);
        return user;
    }
    public static User signUpMenu(){
        Database database = new Database();
        System.out.println("Enter Username :");
        String use = scanner.next();
        while (true){
            if(!database.checkUsername(use))
                break;
            System.out.println("This username is already taken\nEnter a new username :");
            use = scanner.next();
        }
        System.out.println("Enter Password :");
        String pass = scanner.next();
        User user = new User(use,pass);
        database.createTables(user);
        return user;
    }

    private static void joinServer(){
        System.out.print("Enter Server Ip Address :");
        String ip = scanner.next();
        System.out.print("Enter Server Port :");
        int port = scanner.nextInt();
        game.joinServer(ip,port);
    }
    public static void userMenu(){
        System.out.println("1- Go to\n2- Process Location\n3- Dashboard\n4- Life\n5- Exit");
    }

    public static int[] getCoordination(){
        System.out.println("Enter Coordination of the location : (for example  1,2)");
        String answer = scanner.nextLine().trim();
        String[] temp = new String[2];
        temp=answer.split(",");
        int[] coordination = new int[2];
        coordination[0] = Integer.parseInt(temp[0]);
        coordination[1] = Integer.parseInt(temp[1]);
        return coordination;
    }
    public static void main(String[] args) {
            showMenu();
    }
}
