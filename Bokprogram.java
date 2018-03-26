//Bokprogram.java
//Kjørerprogrammet
import java.awt.event.*;

public class Bokprogram
{
  public static void main( String[] args )
  {
    final Bokarkiv vindu = new Bokarkiv();
    vindu.addWindowListener( new WindowAdapter()
    {
        public void windowClosing( WindowEvent e )
        {
			    vindu.skrivTilFil();
			    System.exit( 0 );
        }
	  } );
  }
}
