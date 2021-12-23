import java.awt.*;
class Box{
	public char ch;
	public Image img;
	int style=0;
	public int type[][]= new int[4][2];
	public int type0[][]= new int[4][2];
	public int type1[][]= new int[4][2];
	public int type2[][]= new int[4][2];
	public int type3[][]= new int[4][2];
	public void change(){
		style=style+1;
		switch(style){
			case 0: type=type0; style=0; break;
			case 1: type=type1; style=1; break;
			case 2: type=type2; style=2; break;
			case 3: type=type3; style=3; break;
			default:type=type0; style=0; break;
		}
	}
	public int[][] newBox(){
		int i=style+1;
		switch(i){
			case 0: return type0;
			case 1: return type1;
			case 2: return type2;
			case 3: return type3;
			default: return type0;
		}
	}
	public static Box factoryMethod(int n){
		switch(n){
			case 0: return new Zero();
			case 1: return new One();
			case 2: return new Two();
			case 3: return new Three();
			case 4: return new Four();
			case 5: return new Five();
			case 6: return new Six();
			default: return new Zero();
		}
	}
}