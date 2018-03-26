//Bokregister.java
import javax.swing.JTextArea;
import java.io.*;

public class Bokregister implements Serializable
{
	private static final long serialVersionUID = 678L;
  private Bok første;

  //registrerer et bokobjekt
  public void settInnForrest( Bok ny )  // sortert
  { 
    if(ny == null)
    {
      return;
    }
    ny.neste = første;
    første = ny;
  }
  public void settInn( Bok ny )
	{
    if( ny == null )
    {
      return;
    }
    
    if( første == null ) // tom liste:
    {
      første = ny;
      return;
    }
					// objektet skal inn forrest:
    if( ( ny.getForfatter().compareToIgnoreCase( første.getForfatter() ) == 0 &&
          ny.getTittel().compareToIgnoreCase( første.getTittel() ) < 0 )
		 || ( ny.getForfatter().compareToIgnoreCase( første.getForfatter() ) < 0 ) )
    {
	    settInnForrest( ny );
      return;
    }

    Bok løper = første;
    while( løper.neste != null )
    {
      if( ( ny.getForfatter().compareToIgnoreCase(løper.neste.getForfatter() ) == 0 &&
            ny.getTittel().compareToIgnoreCase(løper.neste.getTittel() ) < 0 )
       || ( ny.getForfatter().compareToIgnoreCase(løper.neste.getForfatter() ) < 0 ) )
      {
        ny.neste = løper.neste;
        løper.neste = ny;
		    return;
      }
      else
      {
        løper = løper.neste;
      }
    }
   // setter inn boka sist i lista.
    løper.neste = ny;
  }
  //utskrift av innhold i bokliste
  public void skrivListe( JTextArea bøker )
  {
    if ( første == null )
    {
      bøker.append( "Ingen bøker registrert." );
    }
    else
    {
      bøker.setText( "" );
      Bok løper = første;
      while ( løper != null )
      {
        bøker.append( løper.toString() + "\n" );
        løper = løper.neste;
      }
    }
  }

  public void lesFraFil( String filnavn )
  {
      //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
      try( DataInputStream fil = new DataInputStream( new FileInputStream( filnavn ) ) )
      {
		    while( true )
		    {
			    String boktype = fil.readUTF();

			    if( boktype.equals("Skolebok") )
			    {
				    Skolebok skolebok = new Skolebok();
				    if( skolebok.lesObjektFraFil( fil ) )
            {
				  	  settInn(skolebok);
            }
			    }
			    else if( boktype.equals("Fagbok") )
			    {
				    Fagbok fagbok = new Fagbok();
				    if( fagbok.lesObjektFraFil( fil ) )
				  	{
              settInn(fagbok);
            }
			    }
			    else if( boktype.equals("NorskRoman") )
			    {
				    NorskRoman norsk = new NorskRoman();
				    if( norsk.lesObjektFraFil( fil ) )
				  	{
              settInn(norsk);
            }
			    }
			    else if( boktype.equals("UtelandskRoman") )
			    {
				    UtenlandskRoman utenlandsk = new UtenlandskRoman();
				    if( utenlandsk.lesObjektFraFil( fil ) )
				  	{
              settInn(utenlandsk);
            }
			    }
		    }
	  }
	  catch( FileNotFoundException exc )
	  {
		  System.out.println("Finner ikke datafil.");
	  }
	  catch(EOFException exc)
	  {
		  System.out.println("Filen er ferdiglastet");
	  }
	  catch (IOException ioe)
	  {
		  System.out.println("Får ikke skrevet datafil.");
	  }
  }

  public void skrivTilFil( String filnavn )
  {
      //< Skriver datafeltenes verdier til fil. >
      try( DataOutputStream fil = new DataOutputStream( new FileOutputStream( filnavn ) ) )
      {
		    Bok løper = første;
		    while( løper != null )
		    {
			    løper.skrivObjektTilFil( fil );
			    løper = løper.neste;
		    }
	    }
	    catch (IOException ioe)
	    {
		    System.out.equals( "Fikk ikke skrevet datafil." );
	    }
    }
}


