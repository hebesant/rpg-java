package net.rpg.main;

public class Player extends Character {

    //numero de uprades nos tracos
    public int numAtkUpgrades, numDefUpgrades;

    //stats exclusivos do jogador
    int gold, restsLeft, pots;

    //nomes fodas dos tracos
    public String[] atkUpgrades = {"Strenght", "Power", "Might", "Godlike Strenght"};
    public String[] defUpgrades = {"Heavy Bones", "Stoneskin", "Scale Armor", "Holy Aura"};

    public Player(String name){
        super(name, 100, 0);
        this.numAtkUpgrades = 0;
        this.numDefUpgrades = 0;
        this.gold = 0;
        this.restsLeft = 0;
        this.pots = 0;
        //escolher um traco ao criar um personagem
        chooseTrait();
    }

    //metodos do joagdor
    @Override
    public int attack() {
        return (int) (Math.random() * (xp/4 + numAtkUpgrades * 3 + 3) + xp/10 + numAtkUpgrades * 2 + numDefUpgrades + 1);
    }

    @Override
    public int defend() {
        return (int) (Math.random() * (xp/4 + numDefUpgrades * 3 + 3) + xp/10 + numDefUpgrades * 2 + numAtkUpgrades + 1);
    }

    public void chooseTrait(){
        GameLogic.clearConsole();
        GameLogic.printHeading("Escolha um traço: ");
        System.out.println("(1) " + atkUpgrades[numAtkUpgrades]);
        System.out.println("(2) " + defUpgrades[numDefUpgrades]);
        //escolha do jogador
        int input = GameLogic.readInt("-> ", 2);
        GameLogic.clearConsole();
        if(input == 1){
            GameLogic.printHeading("Você escolheu " + atkUpgrades[numAtkUpgrades]);
            numAtkUpgrades++;
        }else {
            GameLogic.printHeading("Você escolheu " + defUpgrades[numDefUpgrades]);
            numDefUpgrades++;
        }
        GameLogic.anythingToContinue();
    }
}
