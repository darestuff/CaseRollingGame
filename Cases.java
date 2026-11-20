/* This project is a text-based case opening simulator, like that of Counter 
 * Strike 2 but every weapon skin is unique and there are various things 
 * and methods that affect the price of the skin shown at the end of the run
 */

import javax.sound.sampled.*;
import java.io.File;
public class Cases {
    private int cases;
    private boolean isGold;
    private double rollNumber;
    private double priceMultiplier;
    private double wearRating;  
    private String statTrak; // if randomNumber > 0.9
    private String itemRolled;

    // random number >0.91 >0.79 >0.58 >0.41
    private String[] wearRatings = {
        "FACTORY NEW!", "Minimal Wear", "Field-Tested", 
        "Well-Worn", "Battle Scarred"
    };
    
    // last else statement
    private String[] blues = {
        "AK-47 | Dustwalker", "P90 | Scrap Metal", "MAC-10 | Folk",
        "MP7 | Iron Bark", "Galil-AR | Sandstorm", "P90 | Drylands",
        "M249 | Employment", "SCAR-20 | Gray Matter", "FAMAS | Orange Juice",
        "Glock-18 | Night Shift", "Five-SeveN | Moss Edge",
        "USP-S | Pine Needle", "Tec-9 | Alleyway",
        "AK-47 | Steel Vein", "Galil-AR | Deez",
        "MP9 | Circuit Breaker", "Galil-AR | Static Pulse",
        "Nova | Venom Fang", "XM1014 | Crossfire",
        "R8 Revolver | Brass Coil", "Tec-9 | Blueprint",
        "P250 | 187um"
    };

    // purple-teir weapon if randomNumber > 0.62
    private String[] purples = {
        "AWP | Venom Strike", "AK-47 | Crimson Trail",
        "M4A4 | Hornet Nest", "Desert Eagle | Blackout",
        "P90 | Mech Assault", "MP7 | Phantom Grid",
        "UMP-45 | Lunar Echo", "Galil-AR | Glitch Core",
        "FAMAS | Athena's Wrath", "SG 553 | Sven",
        "P250 | Revenant", "MP9 | Solar Flare",
        "Nova | Relic Hunter", "Tec-9 | Pulsefire",
        "AUG | Overdrive", "R8 Revolver | Firewall"
    };

    // pink-teir weapon if randomNumber >= 0.77
    private String[] pinks = {
        "M4A4 | Obsidian Bloom", "Glock-18 | Sunset Rush",
        "P250 | Polar Storm", "M4A1-S | Ohnepixel Phantom",
        "Desert Eagle | Obzidian", "AK-47 | Neon Eclipse",
        "AK-47 | Tidal Fury", "AWP | Russian",
        "MAC-10 | Hex Curse", "AUG | Crimson Lotus",
        "FAMAS | Iron Cage", "P90 | Frozen",
        "M4A4 | Voidwalker"
    };

    // red-teir weapon if randomNumber >= 0.89
    private String[] reds = {
        "AK-47 | Emberfall", "AWP | Eternal Dragon",
        "M4A4 | Hellfire", "AWP | Ambush",
        "AK-47 | Scorching Star", "AWP | Nightshade",
        "M4A1-S | Royal Guard", "Desert Eagle | Silver Phantom",
        "Desert Eagle | Wildfire", "Glock-18 | Leviathan",
        "AK-47 | Project X"
    };
    
    // gold-teir weapon if randomNumber >= 0.98
    private String[] golds = {
        "Karambit", "Butterfly Knife", "Gut Knife", "M9 Bayonet",
        "Falchion Knife", "Skeleton Knife", "Flip Knife",
        "Huntsman Knife", "Shadow Daggers",
    };
    // generates the random item in the case that is presented after the
    // animation, deals with all the random chances in here
    public String rollCase() {
        isGold = false;
        priceMultiplier = 1.0;
        rollNumber = Math.random();
        String wear = checkWear();
        double statTrakNum = Math.random();
       if(statTrakNum > 0.9){
           statTrak = "StatTrak";
       } else {
           statTrak = "";
       }
       if (rollNumber >= 0.98) {
            itemRolled = golds[(int)(Math.random() * golds.length)];
            isGold = true;

            // Apply special knife finishes and price modifiers
            String special = goldSpecial(itemRolled, isGold);

            return "GOLD! " + statTrak + " " + special + " " + itemRolled + " | " + wear;
       }
       else if (rollNumber >= 0.89) {
            itemRolled = reds[(int)(Math.random() * reds.length)];
            return "Red! " + statTrak + " " + itemRolled + " | " + wear;
        } 
        else if (rollNumber >= 0.77) {
            itemRolled = pinks[(int)(Math.random() * pinks.length)];
            return "Pink " + statTrak + " " + itemRolled + " | " + wear;
        } 
        else if (rollNumber >= 0.62) {
        itemRolled = purples[(int)(Math.random() * purples.length)];
        return "Purple " + statTrak + " " + itemRolled + " | " + wear;
        } 
        else {
        itemRolled = blues[(int)(Math.random() * blues.length)];
        return "Blue " + statTrak + " " + itemRolled + " | " + wear;
        }
    }
    // this is to check the condition that the rolled weapon is in
    // the higher the rating, the more expensive it is, therefore
    // price multiplier goes up
    public String checkWear(){
        String wear = "";
        wearRating = Math.random();
        if(wearRating > 0.91){
            wear = "FACTORY NEW!";
            priceMultiplier = 1.79;
        } else if(wearRating > 0.85){
            wear = "Minimal Wear";
            priceMultiplier = 1.33;
        } else if(wearRating > 0.77){
            wear = "Field Tested";
            priceMultiplier = 1.02;
        } else if(wearRating > 0.6){
            wear = "Well Worn";
            priceMultiplier = 0.86;
        } else {
            wear = "Battle Scarred";
            priceMultiplier = 0.77;
        }
        return wear;
    }
    // this one sets up checkPrice, its easier to make a method instead of
    // writing a ton of nested if statements
    public boolean contains(String item, String[] arr){
    for(String s : arr){
        if(s.equals(item)){
            return true;
        }
    }
    return false;
    }  
    // this is the one that generates the BASE price of the item rolled, 
    // this price is multiplied by the priceMultiplier double that gets
    // changed a lot throughout the program, giving a large variety of 
    // different possible prices
    public double checkPrice(String itemRolled, String wear){
        double price = 0.0;
        if(contains(itemRolled, golds)){
            price = 79.12;
        } else if(contains(itemRolled, reds)){
            price = 32.65;
        } else if(contains(itemRolled, pinks)){
            price = 14.14;
        } else if(contains(itemRolled, purples)){
            price = 5.56;
        } else {
            price = 0.49;
        }
        price *= priceMultiplier;
        if(statTrak.equals("StatTrak")){
            price *= 1.25;
        }
        return price;
    }
    // if its a gold, the price can change drastically just like in the real
    // game, a 'karambit bluegem 387' in real life is worth well over a
    // million USD, the owner rejected an offer for 1.5Mil a lil while ago
    public String goldSpecial(String itemRolled, boolean isGold){
        if(!isGold){
            return "";
        }
        double specialPotential = Math.random();
        String specialEffect = "";
        if(specialPotential > 0.96){
            priceMultiplier += 7.82;
            specialEffect = "Gamma Doppler";
        } else if (specialPotential > 0.92){
            priceMultiplier += 6.14;
            specialEffect = "Doppler";
        } else if(specialPotential > 0.84){
            priceMultiplier += 4.32;
            specialEffect = "Fade";
        } else if (specialPotential > 0.75){
            priceMultiplier += 2.4;
            specialEffect = "Autotronic";
        } else if (specialPotential > 0.56){
            priceMultiplier += 1.8;
            specialEffect = "Damascus Steel";
        } else if (specialPotential > 0.31){
            priceMultiplier += 1.42;
            specialEffect = "Ultraviolet";
        }
        if(itemRolled.equals("Shadow Daggers")){
            priceMultiplier -= 0.3;
        } else if(itemRolled.equals("Karambit")){
            priceMultiplier *= 2.5;
        } else if(itemRolled.equals("Butterfly Knife")){
            priceMultiplier *= 1.5;
        }
        return specialEffect;
    }
    // this is the method used in the animation at the roling animation
    // that js spams out random colors that dont mean anything
    public String getRandomRarity(){
        double r = Math.random();

        if (r >= 0.98) {return "GOLD";}
        else if (r >= 0.89){ return "RED";}
        else if (r >= 0.77) {return "PINK";}
        else if (r >= 0.62) {return "PURPLE";}
        else {
            return "BLUE";
        }
    }
    //this is when u run it and it shows a bunch of random colors
    public void rollingAnimation(String finalRarity){
    try {
        playSound("CaseSound.wav");
        System.out.println("Processing..");
        Thread.sleep(1500);
        for (int i = 0; i < 25; i++) {
            String r = getRandomRarity();
            System.out.println(r + " ");
            Thread.sleep(60);
        }
        for (int i = 0; i < 11; i++) {
            String r = getRandomRarity();
            System.out.println(r + " ");
            Thread.sleep(130);
        }
        for (int i = 0; i < 7; i++) {
            String r = getRandomRarity();
            System.out.println(r + " ");
            Thread.sleep(250);
        }
        for (int i = 0; i < 2; i++) {
            String r = getRandomRarity();
            System.out.println(r + " ");
            Thread.sleep(400);
        }
        for (int i = 0; i < 3; i++) {
            String r = getRandomRarity();
            System.out.print(".");
            Thread.sleep(170);
        }
        System.out.println("");
        Thread.sleep(400);

        System.out.print(finalRarity);
        System.out.println("");

    } catch (InterruptedException e) {}
    }
    // gets the item that is rolled before its shown to the user
    public String getItemRolled() {
        return itemRolled;
    }
    //this ones for the sound effect that plays when u run it
    public void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f);

            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
        }
    public static void main(String[] args){
        Cases c = new Cases();
        System.out.println("Hello! This is a text-based case opening project");
        System.out.println("heavily inspired by Counter-Strike 2 Cases!");
        String result = c.rollCase();

        /* this one is just for the rolling animation that 
           shows a bunch of random colors when u run it (while the
           sound effect is playing) */
        String rarity = c.getRandomRarity();
        c.rollingAnimation(rarity);

        System.out.println(result);

        double price = c.checkPrice(c.getItemRolled(), "");
        // makes price double digits instead of sum like $0.512312
        System.out.println("Price: $" + Math.round(price * 100.0) / 100.0);
    }
}