import java.util.Scanner;

public class Main {

    public static final short MAX_PTS_VIE = 100;
    public static final short PTS_BOUCLIER = 25;
    public static final short MAX_ATTAQUE_ENNEMI = 5;
    public static final short MAX_VIE_ENNEMI = 30;
    public static final short MAX_ATTAQUE_JOUEUR = 5;
    public static final short REGENARATION_BOUCLIER_PAR_TOUR = 10;

    public static String nomPersonnage;
    public static short ptsDeVie;
    public static short ptsBouclier;
    public static short nbEnnemisTues;
    public static boolean bouclierActif = true;

    public static void main(String[] args)
    {
        //TODO exercice 11
    }

    public static void initPersonnage(){
        //Afficher le message de saisi
        //afficher("Saisir le nom de votre personnage");
        System.out.println("Saisir le nom de votre personnage");
        //Lire la saisie utilisateur dans ma variable nomPersonnage
        //lire(nomPersonnage);
        Scanner scanner = new Scanner(System.in);
        nomPersonnage = scanner.nextLine();
        //Afficher le message c'est parti !
        System.out.println("OK " + Util.color(nomPersonnage, Color.GREEN) + " ! C'est parti !");
        //Affecter la variable ptsDeVie
        ptsDeVie = MAX_PTS_VIE;
        //Affecter la variable ptsBouclier
        ptsBouclier = bouclierActif ? PTS_BOUCLIER : 0;
        scanner.close();
    }

    public static boolean hasard(double pourcentage){
        //pourcentage < résultat du chiffre random => true
        //sinon faux
        return pourcentage < Math.random();
    }

    public static short nombreAuHasard(short nombre){
        return (short) Math.round(Math.random() * nombre);
    }

    public static short attaqueJoueur(short ptsVieEnnemi){
        //Déterminer la force de l'attaque du joueur
        short forceAttaque = nombreAuHasard(MAX_ATTAQUE_JOUEUR);
        //Retrancher les points de l'attaque sur les points de vie de l'ennemi
        ptsVieEnnemi -= forceAttaque;
        //Afficher les caractéristiques de l'attaque
        System.out.println(Util.color(nomPersonnage, Color.GREEN)
                + " attaque l'" + Util.color("ennemi", Color.YELLOW) + " ! Il lui fait perdre "
                + Util.color(forceAttaque, Color.PURPLE) + " points de dommages");
        //Retourner le nombre de points de vie de l'ennemi après l'attaque
        return ptsVieEnnemi;
    }

    public static void afficherPersonnage(){
        System.out.print(Util.color(nomPersonnage, Color.GREEN) + " (" + Util.color(ptsDeVie, Color.RED));
        if(bouclierActif) {
            System.out.print(" " + Util.color(ptsBouclier, Color.BLUE));
        }
        System.out.print(")");
    }

    public static void attaqueEnnemi(){
        //Déterminer la force de l'attaque
        short dommages = nombreAuHasard(MAX_ATTAQUE_ENNEMI);
        //Affiche le début de la description de l'attaque
        System.out.print("L'" + Util.color("ennemi", Color.YELLOW) + " attaque " + Util.color(nomPersonnage, Color.GREEN) + " ! ");
        System.out.print("Il lui fait " + dommages + " points de dommages ! ");
        //Le bouclier absorbe les dégats en premier si il est actif et qu'il n'est pas vide
        if(ptsBouclier > 0){
            if(ptsBouclier >= dommages){
                ptsBouclier -= dommages;
                System.out.print("Le bouclier perd " + Util.color(dommages, Color.BLUE) + " points. ");
                dommages = 0;
            } else {
                dommages -= ptsBouclier;
                System.out.print("Le bouclier perd " + Util.color(ptsBouclier, Color.BLUE) + " points. ");
                ptsBouclier = 0;
            }
        }

        //Les points de vie du joueur absorbent le reste
       if(dommages > 0){
           ptsDeVie -= dommages;//ptsDeVie = (short) (ptsDeVie - dommages)
            System.out.print(Util.color(nomPersonnage, Color.GREEN) + " perd " + Util.color(dommages, Color.RED) + " points de vie ! ");
       }
    }

    public static short[] initEnnemis(){
        System.out.println("Combien souhaitez-vous combattre d'ennemis ?");
        //Récupère le nombre d'ennemis saisis par l'utilisateur
        Scanner scanner = new Scanner(System.in);
        int nbEnnemis = scanner.nextInt();
        System.out.println("Génération des ennemis...");
        //Déclarer et Initialiser le tableau qui va contenir les points de vie de tous mes ennemis
        short[] ennemis = new short[nbEnnemis];
        for(int i = 0; i < nbEnnemis; i++){
            //Remplir la case i avec un nombre au hasard entre 0 et la vie max des ennemis
            ennemis[i] = nombreAuHasard(MAX_VIE_ENNEMI);
            //Affichage de l'ennemi
            System.out.println("Ennemi numéro " + (i + 1) + " : " + Util.color(ennemis[i], Color.PURPLE));
        }
        return ennemis;
    }

    public static short attaque(short ennemi, boolean joueurAttaque){
        //Vérifier si l'un des deux combattants est mort => Si oui, on ne fait aucune attaque
        if(ptsDeVie <= 0 || ennemi <= 0){
            return ennemi;
        }

        //On va faire attaquer le joueur si c'est à lui d'attaquer
        if(joueurAttaque){
            ennemi = attaqueJoueur(ennemi);
        }
        //Sinon, on fait attaquer l'ennemi
        else {
            attaqueEnnemi();
        }

        //On renvoie le nombre de points de l'ennemi
        return ennemi;
    }

}