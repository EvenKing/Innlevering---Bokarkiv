//Bokarkiv.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Bokarkiv extends JFrame
{
  private JTextField forfatterfelt, tittelfelt, sideantallsfelt, prisfelt, fagfelt, skolefagfelt,
            klassetrinnfelt, sjangerfelt, målformfelt, språkfelt;
  private JButton regFagbok, regSkolebok, regNRoman, regURoman, visReg;
  private JTextArea utskriftsområde;
  private Bokregister register;
  private String filnavn;

  public Bokarkiv()
  {
    super( "Bokarkiv" );

    //Definerer tekstfelt og knapper
    forfatterfelt = new JTextField( 18 );
    tittelfelt = new JTextField( 18 );
    sideantallsfelt = new JTextField( 4 );
    prisfelt = new JTextField( 6 );
    fagfelt = new JTextField( 18 );
    skolefagfelt = new JTextField( 15 );
    klassetrinnfelt = new JTextField( 3 );
    sjangerfelt = new JTextField( 10 );
    målformfelt = new JTextField( 2 );
    språkfelt = new JTextField( 10 );
    regFagbok = new JButton( "Registrer fagbok" );
    regSkolebok = new JButton( "Registrer skolebok" );
    regNRoman = new JButton( "Registrer norsk roman" );
    regURoman = new JButton( "Registrer utenlandsk roman" );
    visReg = new JButton( "Vis bokregister" );
    utskriftsområde = new JTextArea( 15, 45 );
    utskriftsområde.setEditable( false );

    //Plasserer tekstfelt og knapper
    Container c = getContentPane();
    c.setLayout( new FlowLayout() );
    c.add( new JLabel( "Forfatter:" ) );
    c.add( forfatterfelt );
    c.add( new JLabel( "Tittel:" ) );
    c.add( tittelfelt );
    c.add( new JLabel( "Sideantall:" ) );
    c.add( sideantallsfelt );
    c.add( new JLabel( "Pris:" ) );
    c.add( prisfelt );
    c.add( new JLabel( "Fagområde:" ) );
    c.add( fagfelt );
    c.add( new JLabel( "Skolefag:" ) );
    c.add( skolefagfelt );
    c.add( new JLabel( "Klassetrinn:" ) );
    c.add( klassetrinnfelt );
    c.add( new JLabel( "Sjanger:" ) );
    c.add( sjangerfelt );
    c.add( new JLabel( "Målform (b = bokmål, n = nynorsk):" ) );
    c.add( målformfelt );
    c.add( new JLabel( "Språk:" ) );
    c.add( språkfelt );
    c.add( new JLabel("             "));
    c.add( regFagbok );
    c.add( regSkolebok );
    c.add( regNRoman );
    c.add( regURoman );
    c.add( visReg );
    c.add( new JScrollPane( utskriftsområde ) );

    Knappelytter lytter = new Knappelytter();
    
    //Setter effekt til hver knapp
    regFagbok.addActionListener( lytter );
    regSkolebok.addActionListener( lytter );
    regNRoman.addActionListener( lytter );
    regURoman.addActionListener( lytter );
    visReg.addActionListener( lytter );
	  filnavn = "Bok1.dta";
    lesFil();

    setSize( 550, 500 );
    setVisible( true );
  }

  //Viser feilmelding
  private void visFeilmelding( String melding )
  {
	  JOptionPane.showMessageDialog(this, melding, "Problem", JOptionPane.ERROR_MESSAGE);
  }

  //Henter tekst fra tekstfil, lager ny om det ikke finnes en
  public void lesFil()
  {
	  try( ObjectInputStream inn = new ObjectInputStream( new FileInputStream( filnavn ) ) ) //"Bok1.dta"
	  {
		  register = ( Bokregister) inn.readObject();
	  }
	  catch( FileNotFoundException fnfe )
	  {
		  visFeilmelding("Finner ikke fil. \nOppretter ny fil.");
		  register = new Bokregister();
	  }
	  catch( ClassNotFoundException cnfe )
	  {
		  visFeilmelding("Finner ikke klasse.");
		  register = new Bokregister();
	  }
	  catch( IOException e )
	  {
		  visFeilmelding("Får ikke lest datafil.");
		  register = new Bokregister();
	  }
  }

  //Skriver i tekstfil
  public void skrivTilFil()
  {
	  try( ObjectOutputStream ut = new ObjectOutputStream( new FileOutputStream( filnavn ) ) )  //"Bok1.dta"
	  {
		  ut.writeObject( register );
	  }
	  catch( NotSerializableException nse )
	  {
		  visFeilmelding( "Objektet er ikke serialisert!" );
	  }
	  catch( IOException e )
	  {
		  visFeilmelding( "Problem med utskrift til fil." );
	  }
  }

  //Funksjoner for hver type bok
  public void nyFagbok()
  {
		String forfatter = forfatterfelt.getText();
		String tittel = tittelfelt.getText();
		String fag = fagfelt.getText();
		if(forfatter.length() == 0 || tittel.length() == 0
		|| fag.length() == 0 )
		{
			visMelding("Fyll ut nødvendige tekstfelt");
			return;
		}
		try
		{
			int sideantall = Integer.parseInt(sideantallsfelt.getText());
			double pris = Double.parseDouble(prisfelt.getText());
			register.settInn(new Fagbok(forfatter, tittel, sideantall, pris, fag));
			visMelding( "Ny fagbok registrert." );
			slettFelter();
		}
		catch(NumberFormatException nfe)
		{
			visMelding("Feil i tallformat");
		}
	}


  public void nySkolebok()
  {
    String forfatter = forfatterfelt.getText();
    String tittel = tittelfelt.getText();
    String skolefag = skolefagfelt.getText();
    if ( forfatter.length() == 0 ||
         tittel.length() == 0 || skolefag.length() == 0 )
    {
      visMelding( "Fyll ut nødvendige tekstfelter!" );
      return;
    }
    try {
      int sideantall = Integer.parseInt( sideantallsfelt.getText() );
      double pris = Double.parseDouble( prisfelt.getText() );
      int trinn = Integer.parseInt( klassetrinnfelt.getText() );
      register.settInn(
          new Skolebok( forfatter, tittel, sideantall, pris, trinn, skolefag ) );
      visMelding( "Ny skolebok registrert." );
      slettFelter();
    }
    catch ( NumberFormatException e ) {
      visMelding( "Ingen registrering pga. feil i tallformat!" );
    }
  }

  public void nyNorskRoman()
  {
    String forfatter = forfatterfelt.getText();
    String tittel = tittelfelt.getText();
    String sjanger = sjangerfelt.getText();
    String målform = målformfelt.getText();
    if ( forfatter.length() == 0 || tittel.length() == 0
         || sjanger.length() == 0 || målform.length() == 0 )
    {
      visMelding( "Fyll ut nødvendige tekstfelter!" );
      return;
    }
    try {
      int sideantall = Integer.parseInt( sideantallsfelt.getText() );
      double pris = Double.parseDouble( prisfelt.getText() );
      char målkode = målform.charAt( 0 );
      if ( målkode == 'b' )
        målform = "bokmål";
      else
        målform = "nynorsk";
      register.settInn(
          new NorskRoman( forfatter, tittel, sideantall, pris, sjanger, målform ) );
      visMelding( "Ny norsk roman registrert." );
      slettFelter();
    }
    catch ( NumberFormatException e ) {
      visMelding( "Ingen registrering pga. feil i tallformat!" );
    }
  }

  public void nyUtenlandskRoman()
  {
    String forfatter = forfatterfelt.getText();
    String tittel = tittelfelt.getText();
    String sjanger = sjangerfelt.getText();
    String språk = språkfelt.getText();
    if ( forfatter.length() == 0 || tittel.length() == 0 ||
         sjanger.length() == 0 || språk.length() == 0 )
    {
      visMelding( "Fyll ut nødvendige tekstfelter!" );
      return;
    }
    try
    {
      int sideantall = Integer.parseInt( sideantallsfelt.getText() );
      double pris = Double.parseDouble( prisfelt.getText() );
      register.settInn(
          new UtenlandskRoman( forfatter, tittel, sideantall, pris, sjanger, språk ) );
      visMelding( "Ny utenlandsk roman registrert." );
      slettFelter();
    }
    catch ( NumberFormatException e ) {
      visMelding( "Ingen registrering pga. feil i tallformat!" );
    }
  }

  //Henter lista over registrerte bøker
  public void visRegister()
  {
    register.skrivListe( utskriftsområde );
  }

  private void visMelding( String melding )
  {
    JOptionPane.showMessageDialog( this, melding );
  }

  //Tømmer alle tekstfelter
  private void slettFelter()
  {
    forfatterfelt.setText( "" );
    tittelfelt.setText( "" );
    sideantallsfelt.setText( "" );
    prisfelt.setText( "" );
    fagfelt.setText( "" );
    skolefagfelt.setText( "" );
    klassetrinnfelt.setText( "" );
    sjangerfelt.setText( "" );
    målformfelt.setText( "" );
    språkfelt.setText( "" );
  }

  //Setter funksjon til å aktiveres av knapptrykk 
  private class Knappelytter implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      if ( e.getSource() == regFagbok )
        nyFagbok();
      else if ( e.getSource() == regSkolebok )
        nySkolebok();
      else if ( e.getSource() == regNRoman )
        nyNorskRoman();
      else if ( e.getSource() == regURoman )
        nyUtenlandskRoman();
      else if ( e.getSource() == visReg )
        visRegister();
    }
  }

  /*public void lesFraFil()
  {
      //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
      register.lesFraFil( filnavn );

  }

  public void skrivTilFil()
  {
      //< Skriver datafeltenes verdier til fil. >

  }*/
}
