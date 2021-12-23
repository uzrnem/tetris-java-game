import java.awt.*;
class One extends Box{
	int int0[]={2,0,2,1,2,2,2,3};
	int int1[]={0,1,1,1,2,1,3,1};
	int int2[]={1,0,1,1,1,2,1,3};
	int int3[]={0,1,1,1,2,1,3,1};
	One(){
		ch='p';
		java.net.URL url = One.class.getResource("p.png");
		img=Toolkit.getDefaultToolkit().getImage(url);
		for(int c=0;c<8;c++){
			type0[c/2][c%2]=int0[c];
			type1[c/2][c%2]=int1[c];
			type2[c/2][c%2]=int2[c];
			type3[c/2][c%2]=int3[c];
		}
		int n = new java.util.Random().nextInt(4);
		switch(n){
			case 0: type=type0; style=0; break;
			case 1: type=type1; style=1; break;
			case 2: type=type2; style=2; break;
			case 3: type=type3; style=3; break;
			default:type=type0; style=0; break;
		}
	}
}