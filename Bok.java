import java.io.*;

//På toppen av klassehierarkiet for boktyper.
public abstract class Bok implements Serializable
{
	private static final long serialVersionUID = 567L;
	private String forfatter, tittel;
	private int sideantall;
	private double pris;
  Bok neste;
 

  public Bok()
  {
	}

	public Bok( String f, String t, int sider, double p )
  {
		forfatter = f;
		tittel = t;
		sideantall = sider;
		pris = p;
    neste = null;
	}

	public String getForfatter()
	{
		return forfatter;
	}


	public String getTittel()
  {
		return tittel;
	}

	public String toString()
  {
		String s = forfatter + "; ";
		s += tittel + "; ";
		s += sideantall + " s., ";
		s += "kr. " + pris;
		return s;
	}

	public boolean lesObjektFraFil( DataInputStream input )
	{
	    //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
	    try{
			  forfatter = input.readUTF();
			  tittel = input.readUTF();
			  sideantall = input.readInt();
			  pris = input.readDouble();
	    }
		  catch (IOException ioe)
		  {
			  System.out.println( "Finner ikke fil.");
		  }
		  return false;
	}

	public void skrivObjektTilFil( DataOutputStream output )
	{
	    //< Skriver datafeltenes verdier til fil. >
	  	try{
			  output.writeUTF(forfatter);
			  output.writeUTF(tittel);
			  output.writeInt(sideantall);
			  output.writeDouble(pris);
		  }
		  catch (IOException ioe)
		  {
			  //JOptionPane.showMessageDialog("Får ikke skrevet datafil.", JOptionPane.ERROR_MESSAGE);
			  System.out.println("Får ikke skrevet datafil.");
		  }
	}
}

class Skolebok extends Bok
{
	private static final long serialVersionUID = 234L;
	private int klassetrinn;
	private String skolefag;

	public Skolebok( String f, String t,int sider, double p, int kt, String fag )
  {
		super( f, t, sider, p );
		klassetrinn = kt;
		skolefag = fag;
	}

	public Skolebok()
	{
	}

	public String toString()
  {
		String s = super.toString();
		s += "; trinn: " + klassetrinn;
		s += ", " + skolefag;
		return s;
	}

	public boolean lesObjektFraFil( DataInputStream input )
	{
	    //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
	    try{
			  super.lesObjektFraFil(input);
			  klassetrinn = input.readInt();
			  skolefag = input.readUTF();
			  return true;
	    }
	    catch (IOException ioe)
	    {
			  //kom tilbake hit
			  return false;
	    }
	}

	public void skrivObjektTilFil( DataOutputStream output )
	{
	    //< Skriver datafeltenes verdier til fil. >
	    try{
			  output.writeUTF("Skolebok");
			  super.skrivObjektTilFil(output);
			  output.writeInt(klassetrinn);
			  output.writeUTF(skolefag);
		 }
		 catch (IOException ioe)
		 {
			//JOptionPane.showMessageDialog("Får ikke skrevet datafil.", JOptionPane.ERROR_MESSAGE);
			System.out.println("Får ikke skrevet datafil.");
		 }
	}
}

class Fagbok extends Bok
{
	private String fagområde;

	public Fagbok( String f, String t, int sider, double p, String omr )
  {
		super( f, t, sider, p );
		fagområde = omr;
	}
	public Fagbok()
	{
	}

	public String toString()
  {
		String s = super.toString();
		s += "; " + fagområde;
		return s;
	}

	public boolean lesObjektFraFil( DataInputStream input )
	{
	  //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
	  try{
			super.lesObjektFraFil(input);
			fagområde = input.readUTF();
			return true;
		}
		catch (IOException ioe)
		{
			//kom tilbake hit
			return false;
		}
	}

	public void skrivObjektTilFil( DataOutputStream output )
	{
	  //< Skriver datafeltenes verdier til fil. >
	  try{
			output.writeUTF("Fagbok");
	    super.skrivObjektTilFil(output);
	    output.writeUTF(fagområde);
		}
		catch (IOException ioe)
		{
			//kom tilbake hit
			//JOptionPane.showMessageDialog("Får ikke skrevet datafil.", JOptionPane.ERROR_MESSAGE);
			System.out.println("Får ikke skrevet datafil.");
		}
	}
}





//Klassen har som oppgave å definere det som er felles for de
//forskjellige typene av romaner. Skal ikke instansieres.
abstract class Roman extends Bok
{
	protected String sjanger;

	protected Roman( String f, String t, int sider, double p, String s )
  {
		super( f, t, sider, p );
		sjanger = s;
	}

	public Roman()
	{
	}

	public String toString()
  {
		String s = super.toString();
		s += ". Sjanger: " + sjanger;
		return s;
	}

	public boolean lesObjektFraFil( DataInputStream input )
	{
	    //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
	    try{
			super.lesObjektFraFil(input);
			sjanger = input.readUTF();
			return true;
		}
		catch (IOException ioe)
		{
			//kom tilbake hit
			return false;
		}
	}

	public void skrivObjektTilFil( DataOutputStream output )
	{
	    //< Skriver datafeltenes verdier til fil. >
	    try{
			  super.skrivObjektTilFil(output);
	    	output.writeUTF(sjanger);
		  }
		  catch (IOException ioe)
		  {
			  //kom tilbake hit
			  //JOptionPane.showMessageDialog("Får ikke skrevet datafil.", JOptionPane.ERROR_MESSAGE);
			  System.out.println("Får ikke skrevet datafil.");
		  }

	}
}


class NorskRoman extends Roman
{
	private String målform;

	public NorskRoman( String f, String t,  int s, double p, String sj, String m )
  {
		super( f,t, s, p, sj );
		målform = m;
	}

	public NorskRoman()
	{
	}

	public String toString()
  {
		String s = super.toString();
		s += ". " + målform;
		return s;
	}

	public boolean lesObjektFraFil( DataInputStream input )
	{
	  //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
	  try{
			super.lesObjektFraFil(input);
			målform = input.readUTF();
			return true;
		}
		catch (IOException ioe)
		{
			//kom tilbake hit
			return false;
		}
	}

	public void skrivObjektTilFil( DataOutputStream output )
	{
	  //< Skriver datafeltenes verdier til fil. >
	  try{
			output.writeUTF("NorskRoman");
			super.skrivObjektTilFil(output);
			output.writeUTF(målform);
		}
		catch(IOException ioe)
		{
			//kom tilbake hit
			//JOptionPane.showMessageDialog("Får ikke skrevet datafil.", JOptionPane.ERROR_MESSAGE);
			System.out.println("Får ikke skrevet datafil.");
		}
	}
}


class UtenlandskRoman extends Roman
{
	private String språk;

	public UtenlandskRoman( String f, String t,  int s, double p, String sj, String sp )
  {
		super( f, t, s, p, sj );
		språk = sp;
	}

	public UtenlandskRoman()
	{
	}

	public String toString()
	{
		String s = super.toString();
		s += ". " + språk;
		return s;
	}

	public boolean lesObjektFraFil( DataInputStream input )
	{
	  //< Leser verdier fra fil og lagrer dem i de tilhørende datafeltene. >
	  try{
			super.lesObjektFraFil(input);
			språk = input.readUTF();
			return true;
		}
		catch(IOException ioe)
		{
			//kom tilbake hit
			return false;
		}
	}

	public void skrivObjektTilFil( DataOutputStream output )
	{
	  //< Skriver datafeltenes verdier til fil. >
	  try{
			output.writeUTF("UtelandskRoman");
	    	super.skrivObjektTilFil(output);
	    	output.writeUTF(språk);
		}
		catch(IOException ioe)
		{
			//kom tilbake hit
			//JOptionPane.showMessageDialog("Får ikke skrevet datafil.", JOptionPane.ERROR_MESSAGE);
			System.out.println("Får ikke skrevet datafil.");
		}
	}
}
