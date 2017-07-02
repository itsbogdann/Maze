import java.util.*;
import java.io.File;
import java.io.*;
import java.awt.*;

class Character {
	private String nume;
	private int pozX = 0;
	private int pozY = 0;
	
	public String getNume(){
		return this.nume;
	}
	
	public int[] getPozitie() {
		
		int[] pozitie = new int[2];
		pozitie[0] = this.pozX;
		pozitie[1] = this.pozY;
		return pozitie;
	}
	
	Character(String nm, int pX, int pY){
		this.nume = nm;
		this.pozX = pX;
		this.pozY = pY;
	}
	
}

class CityMap{
	private char[][] harta = new char[50][50];
	private int[] pozR = new int[2];
	private int[] pozJ = new int[2];
	private int n,m;
	
	public CityMap(){
		
		
		try{
			Scanner input = new Scanner(new FileReader("/Users/itsbogdann/Desktop/harta1.txt"));
			
			
			n = Integer.parseInt(input.next());
			m = Integer.parseInt(input.next());
			
			int k=1;
			
			input.nextLine();
			System.out.println(m);
			while(input.hasNextLine()){
				String tk = input.nextLine();
				for (int i=1; i<=m; i++){
				
						harta[k][i] = tk.charAt(i-1);
						
					if (tk.charAt(i-1) == 'R')
					{
						pozR[0] = k;
						pozR[1] = i;
					}
					if (tk.charAt(i-1) == 'J')
					{
						pozJ[0] = k;
						pozJ[1] = i;
					}
				}
				
			k+=1;
			}
			
		
		} catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public int[] getPozRomeo(){
		return pozR;
	}
	
	public int[] getPozJulieta(){
		return pozJ;
	}
	
	public char[][] getHarta()
	{
		return this.harta;
	}
	
	public void afisareHarta(){
		for (int i=1;i<=n;i++){
			for (int j=1;j<=m;j++){
				System.out.print(harta[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
}

class Game{
	
	static private char [][] map;
	static private Character romeo;
	static private Character julieta;
	static private CityMap citymap;
	static int timp = 0;
	static int intalniri = 0;
	
	public Game(){
		
		this.citymap = new CityMap();
		this.map = citymap.getHarta();
		
		int [] pozitieJ = new int[2];
		int [] pozitieR = new int[2];
		
		pozitieJ = citymap.getPozJulieta();
		pozitieR = citymap.getPozRomeo();
		
		this.romeo = new Character("Romeo",pozitieR[0],pozitieR[1]);
		this.julieta = new Character("Julieta",pozitieJ[0],pozitieJ[1]);

	}

	public static void play(int rX,int rY, int jX, int jY){
		
		citymap.afisareHarta();
		System.out.println();
		System.out.println();
	
		map[rX][rY] = 'R';
		map[jX][jY] = 'J';
		int r = 0;
		
		int[] nextR = new int[2];
		int[] nextJ = new int[2];
			nextR[0] = -1; nextR[1] = -2; nextJ[0] = -3; nextJ[1] = -4;
		
		//verifica ce locuri sunt libere langa ele
			//Romeo
			if (map[rX][rY-1] == ' ') { nextR[0] = rX; nextR[1] = rY-1; r = 1; }
			else if (map[rX][rY+1] == ' ') { nextR[0] = rX; nextR[1] = rY+1; }
			else if (map[rX-1][rY] == ' ') { nextR[0] = rX-1; nextR[1] = rY; }
			else if (map[rX+1][rY] == ' ') { nextR[0] = rX+1; nextR[1] = rY; }
			else {System.out.println("DA1");}
			//System.out.println(r);
		
			//Julieta
			if (map[jX][jY-1] == ' ') { nextJ[0] = jX; nextJ[1] = jY-1; }
			else if (map[jX][jY+1] == ' ') { nextJ[0] = jX; nextJ[1] = jY+1; }
			else if (map[jX-1][jY] == ' ') { nextJ[0] = jX-1; nextJ[1] = jY; }
			else if (map[jX+1][jY] == ' ') { nextJ[0] = jX+1; nextJ[1] = jY; }
			else {System.out.println("DA2");}
			
		//daca e spatiu liber unde sa se mute amandoi
			if (nextR[0]>0 && nextR[1]>0 && nextJ[0]>0 && nextJ[1]>0) {
				timp+=1;
				play(nextR[0], nextR[1], nextJ[0], nextJ[1]);
			}
		//daca se intalnesc
			if (nextR[0] == nextJ[0] && nextR[1] == nextJ[1]){
				System.out.println("S-au intalnit! Coordonate:"+nextR[0]+" "+nextR[1]);
				intalniri+=1;
				System.out.println("Timp:" + timp);
				
				//scriere in fisier
				try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("/Users/itsbogdann/Desktop/maze.out"), "utf-8"))){
					writer.write(timp + " " + nextR[0] + " " + nextR[1]);
				} catch(Exception e) {
				}
				
				
			}

			
	}
	
	public static void main(String[] args) {
		
		Game letsPlay = new Game();
		
		int [] r = romeo.getPozitie();
		int [] j = julieta.getPozitie();
		
		letsPlay.play(r[0],r[1],j[0],j[1]);
		
		
		
	}
}