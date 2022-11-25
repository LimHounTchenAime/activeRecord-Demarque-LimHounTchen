package activeRecord;

public class RealisateurAbsentException extends Exception{
    public RealisateurAbsentException(){
        super("realisateur absent de la table personne");
    }
}
