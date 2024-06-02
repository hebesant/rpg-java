package net.rpg.main;
import java.util.Scanner;

public class GameLogic {
    static Scanner scanner = new Scanner(System.in);
    static Player player;
    public static boolean isRunning;
    public static String[] encounters = {"Battle", "Battle", "Battle", "Rest", "Rest"};
    public static String[] enemies = {"Ogre", "Ogre", "Goblin", "Goblin", "Stone Elemental"};
    public static int place = 0, act = 1;
    public static String[] places = {"Everlasting Mountains", "Haunted Lanlines", "Castle of the Evil Emperor", "Throne Room"};

    //pegar input do console
    public static int readInt(String prompt, int userChoices){
        int input;
        do{
            System.out.println(prompt);
            try{
                input =  Integer.parseInt(scanner.next());
            }catch(Exception e){
                input = -1;
                System.out.println("Por favor digite um número inteiro!");
            }
        }while(input < 1 || input > userChoices);

        return input;
    }

    //simular limpeza do console
    public static void clearConsole(){
        for(int i = 0; i < 100; i++){
            System.out.println();
        }
    }

    //printar o texto lento
    public static void printSlowly(String text) {
        int delay = 15;
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //printar um separador
    public static void printSeparator(int n){
        for(int i = 0; i < n; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    //printar título
    public static void printHeading(String title){
        printSeparator(30);
        System.out.println(title);
        printSeparator(30);
    }

    public static void anythingToContinue(){
        System.out.println("\nDigite qualquer coisa para continuar...");
        scanner.next();
    }

    //metodo para iniciar o jogo
    public static void startGame(){
        boolean nameSet = false;
        String name;
        clearConsole();
        printSeparator(40);
        printSeparator(30);
        System.out.println("ERA DO IMPERADOR DO MAL");
        System.out.println("RPG FEITO POR");
        printSeparator(30);
        printSeparator(40);
        anythingToContinue();

        Story.printintro();

        //escolhendo nome
        do {
            clearConsole();
            printHeading("Qual é o seu nome?");
            name = scanner.next();
            clearConsole();
            printHeading("Seu nome é " + name + ".\nEstá correto?");
            System.out.println("(1) Sim!");
            System.out.println("(2) Não, eu quero mudar meu nome.");
            int input = readInt("-> ", 2);
            if (input == 1) {
                nameSet = true;
            }
        }while (!nameSet) ;

        Story.firsAct();
        //criando jogador
        player = new Player(name);



        //setando pra true pro jogo continuar
        isRunning = true;

        //loop do jogo
        gameLoop();
    }

    public static void checkAct(){
        if(player.xp >= 10 && act == 1){
            act = 2;
            place = 1;
            Story.firsAct();
            player.chooseTrait();
            //Story.secondActintro();
            //valores ao inimigos
            enemies[0] = "Evil Mercenary";
            enemies[1] = "Goblin";
            enemies[2] = "Wolvepack";
            enemies[3] = "Henchman of the Evil Emperor";
            enemies[4] = "Scary Stranger";
            //novos valores para encontros
            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Rest";
            encounters[4] = "Shop";


        }else if(player.xp >= 50 &&  act == 2){
            act = 3;
            place = 2;
            //Story.printSecontAct();
            player.chooseTrait();
            //Story.thirdActIntro;
            enemies[0] = "Evil Mercenary";
            enemies[1] = "Evil Mercenary";
            enemies[2] = "Henchman of the Evil Emperor";
            enemies[3] = "Henchman of the Evil Emperor";
            enemies[4] = "Henchman of the Evil Emperor";
            //novos valores para encontros
            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Battle";
            encounters[4] = "Shop";
            //curar o jogador
            player.hp = player.maxHP;
        }else if(player.xp >= 100 && act  == 3){
            act = 4;
            place = 3;
            //Story.thirdAct;
            player.chooseTrait();
            //Story.fourthActIntro
            player.hp = player.maxHP;
            //ultima batalha
            //finalBattle();
        }
    }

    //encontro aleatorio
    public static void randomEncounter(){
        //numero aleatorio entre 0 e tamanho do array de encontros
        int encounter = (int) (Math.random()* encounters.length);
        if(encounters[encounter].equals("Battle")){
            randomBattle();
        } else if(encounters[encounter].equals("Rest")){
            takeRest();
        } else{
            shop();
        }

    }
    //avancar na historia
    public static void continueJourney(){

        checkAct();

        if(act != 4){
            randomEncounter();

        }

    }

    //informações do personagem
    public static void characterInfo(){
        clearConsole();
        printHeading("INFORMAÇÕES DO PERSONAGEM");
        System.out.println(player.name);
        System.out.println("HP: " + player.hp + "/" + player.maxHP);
        printSeparator(20);

        System.out.println("XP: " + player.xp);
        System.out.println("Ouro: " + player.gold);
        System.out.println("# de poções: " + player.pots);


        printSeparator(20);

        //exibindo tracos
        if(player.numAtkUpgrades > 0){
            System.out.println("Traço ofensivos: " + player.atkUpgrades[player.numAtkUpgrades]);
           printSeparator(20);
        }
        if (player.numAtkUpgrades > 0){
            System.out.println("Traços defensivos: " + player.defUpgrades[player.numDefUpgrades]);
            printSeparator(20);
        }

        anythingToContinue();

    }

    //loja/encontrar tesouro
    public static void shop(){
        clearConsole();
        printHeading("Você encontrou um estranho misterioso.\nEle está te oferecendo algo.");
        int price = (int) (Math.random()* (10 + player.pots * 3) + 10 + player.pots);
        System.out.println("- Magic Potion: " + price + " ouro");
        printSeparator(20);
        System.out.println("Você deseja comprar uma?");
        System.out.println("(1) Sim!");
        System.out.println("(2) Não, obrigado.");
        int input = readInt("-> ", 2);
        if(input == 1){
            clearConsole();
            if(player.gold >= price){
                printHeading("Você comprou uma poção mágica por " + price + " ouro!");
                player.pots++;
                player.gold -= price;
            }else{
                printHeading("Você não tem ouro o suficiente para comprar isso...");
            }
            anythingToContinue();
        }
    }

    public static void takeRest(){
        clearConsole();
        if(player.restsLeft >= 1){
            printHeading("Você deseja descansar? (" + player.restsLeft + " descansos restantes).");
            System.out.println("(1) Sim");
            System.out.println("(2) Não agora");
            int input = readInt("-> ", 2);
            if(input == 1){
                clearConsole();
                if(player.hp < player.maxHP){
                    int hpRestored = (int) (Math.random() * (player.xp/4 + 1) + 10);
                    player.hp += hpRestored;
                    if(player.hp > player.maxHP){
                        player.hp = player.maxHP;
                    }
                    System.out.println("Você descansou e recuperou " + hpRestored + " de HP!");
                    System.out.println("Seu Hp agora é " + player.hp + "/" + player.maxHP);
                    player.restsLeft--;

                }else{
                    System.out.println("Seu HP está cheio! Você não precisa descansar agora!");
                }
                anythingToContinue();
            }
        }
    }

    //batalha aleatoria
    public static void randomBattle(){
        clearConsole();
        printHeading("Você encontrou uma criatura maligna. Terá que enfrentá-la!");
        anythingToContinue();
        //criando inimigo aleatorio
        battle(new Enemy(enemies[(int) (Math.random() * enemies.length)],player.xp));
    }

    //método de batalha
    public static void battle(Enemy enemy){
        while (true){
            clearConsole();
            printHeading(enemy.name + "\nHP: " + enemy.hp + "/" + enemy.maxHP);
            printHeading(player.name + "\nHP " + player.hp + "/" + player.maxHP);
            System.out.println("Escolha uma opção: ");
            printSeparator(20);
            System.out.println("(1) Lutar");
            System.out.println("(2) Usar poção");
            System.out.println("(3) Fugir");
            int input = readInt("->", 3);
            if(input == 1){
                //LUTAR
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();
                if(dmgTook < 0){
                    dmg -= dmgTook/2;
                    dmgTook = 0;
                }
                if(dmg < 0){
                    dmg = 0;
                }

                player.hp -= dmgTook;
                enemy.hp -= dmg;

                clearConsole();
                printHeading("BATALHA");
                System.out.println("Você causou " + dmg + " de dano ao " + enemy.name + ".");
                printSeparator(15);
                System.out.println("O " + enemy.name + " causou " + dmgTook + " de dano a você.");
                anythingToContinue();
                if(player.hp <= 0){
                    playerDied(); //finalizar o jogo
                    break;
                }else if(enemy.hp <= 0){
                    clearConsole();
                    printHeading("Você derrotou o " + enemy.name + "!");
                    player.xp += enemy.xp;
                    System.out.println("Você aquiriu " + enemy.xp + " de XP!");
                    //drop aleatório
                    boolean addRest = (Math.random() * 5 + 1 <= 2.25);
                    int goldEarned = (int) (Math.random() * enemy.xp);
                    if(addRest){
                        player.restsLeft++;
                        System.out.println("Você ganhou um descanso a mais!");
                    }
                    if(goldEarned > 0){
                        player.gold += goldEarned;
                        System.out.println("Você adquiriu " + goldEarned + " de ouro!");
                    }
                    anythingToContinue();
                    break;

                }

            }else if(input == 2){
                clearConsole();
                if(player.pots >= 1 && player.hp < player.maxHP){
                    printHeading("Você quer beber uma poção? (" + player.pots + " restantes)");
                    System.out.println("(1) Sim");
                    System.out.println("(2) Não, talvez depois");
                    input = readInt("-> ", 2);
                    if(input == 1){
                        player.hp = player.maxHP;
                        clearConsole();
                        printHeading("Você bebeu uma poção mágica. Ela restaurou o seu HP para " + player.maxHP);
                        anythingToContinue();
                    }

                }else{
                    printHeading("Você não possue poções ou seu HP está no máximo.");
                    anythingToContinue();
                }
            }else{
                clearConsole();
                //35% de chance de escapar
                if(Math.random()*10 + 1 <= 3.5){
                    printHeading("Você escapou do " + enemy.name + "!");
                    anythingToContinue();
                    break;
                }else{
                    if(act!=4){
                        printHeading("Você não conseguiu escapar!");
                        //dano levado por não escapar
                        int dmgTook = enemy.attack();
                        System.out.println("Na tentativa de fuga, o inimigo causou 0" + dmgTook + " de dano!");
                        anythingToContinue();
                        if(player.hp <= 0){
                            playerDied();
                        }
                    }else{
                        System.out.println("VOCÊ NÃO PODE FUGIR DESTA BATALHA!");
                        anythingToContinue();
                    }
                }
            }
        }
    }

    //exibir o meny
    public static void printMenu(){
        clearConsole();
        printHeading(places[place]);
        System.out.println("Escolha uma opção: ");
        printSeparator(20);
        System.out.println("(1) Continuar jornada");
        System.out.println("(2) Informações do personagem");
        System.out.println("(3) Sair do jogo");
    }

    //batalha final
    public static void finalBattle(){
        //criando o bichao
        battle(new Enemy("O PICA", 300));
        //Story.printEnd(Player);
        isRunning = false;
    }

    //checar se foi de base
    public static void playerDied(){
        clearConsole();
        printHeading("You died...");
        printHeading("Você adquiriu " + player.xp + " ao longo de sua jornada. Tente conseguir mais da próxima vez.");
        System.out.println("Obrigado por jogar o jogo! Até a próxima!");
        isRunning = false;
    }

    public static void gameLoop(){
        while(isRunning){
            printMenu();
            int input  = readInt("-> ", 3);
            if(input == 1){
                continueJourney();
            } else if (input == 2) {
                characterInfo();
            } else{
                isRunning = false;
            }
        }
    }
}
