package symlab.ust.hk.algorithm;

import java.util.Random;




public class MiniMax {
	
	static int chess[];
	final int maxValue=1000;
	final static int minValue=-1000;
	
	
	private static void setChessBoard()
	{
		int[][] initches = {{-3, -5, -4, -2, -1, -4, -5, -3},
							{-6, -6, -6, -6, -6, -6, -6, -6},
							{0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0},
							{6, 6, 6, 6, 6, 6, 6, 6},
							{3, 5, 4, 2, 1, 4, 5, 3}
							};
		/*
		 * 
1King
2Queen
3Rook
4Bishop
5Knight
6Pawn
		 * 
		 */
		chess = new int[64];
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				chess[j*8+i]=initches[i][j];
	}
	
	
	
	public void setChessStatus(int [][] chessBoard){
		
		
		chess = new int[64];
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				chess[j*8+i]=chessBoard[i][j];
	}
	
	
	private static int[] getPossibleMove(int x, int y, int[] board)
	{
		int pos = y*8+x;
		int n = 0;
		int a[] = new int[64];
		int[][] king = {{0,-1},{0,1},
						{1,0},{-1,0},
						{1,-1},{1,1},
						{-1,1},{-1,-1}};
		
		int[][] queen = {{0,-1},{0,1},
						 {1,0},{-1,0},
						 {1,-1},{1,1},
						 {-1,1},{-1,-1}};
		
		int[][] rook = {{0,-1},{0,1},
						{1,0},{-1,0}};

		int[][] bishop = {{1,-1},{1,1},
						  {-1,1},{-1,-1}};

		int[][] knight = {{1,2},{1,-2},
						  {-1,2},{-1,-2},
						  {2,1},{2,-1},
						  {-2,1},{-2,-1}};
		
		
		switch(Math.abs(board[pos]))
		{
			case 1://King
				for (int move=0;move<4;move++)
				{
					int i=x+king[move][0],j=y+king[move][1];
					if ((i>=0)&&(i<8)&&(j>=0)&&(j<8))						
						if((board[j*8+i]==0)||(board[j*8+i]*board[pos]<0))
						{
							a[n]=j*8+i;
							n++;
						}
	
				}
			//===
				
				
				break;
				
			case 2://Queen
				for (int move=0;move<8;move++)	
					for (int i=x+queen[move][0],j=y+queen[move][1];
						(i>=0)&&(i<8)&&(j>=0)&&(j<8);
						i+=queen[move][0], j+=queen[move][1])
					{
						if(board[j*8+i]==0)
						{
							a[n]=j*8+i;
							n++;
							continue;
						}
						if (board[j*8+i]*board[pos]<0)
						{
							a[n]=j*8+i;
							n++;
						}
						break;
					}
				
				break;
			case 3://Rook
				for (int move=0;move<4;move++)	
					for (int i=x+rook[move][0],j=y+rook[move][1];
						(i>=0)&&(i<8)&&(j>=0)&&(j<8);
						i+=rook[move][0], j+=rook[move][1])
					{
						if(board[j*8+i]==0)
						{
							a[n]=j*8+i;
							n++;
							continue;
						}
						if (board[j*8+i]*board[pos]<0)
						{
							a[n]=j*8+i;
							n++;
						}
						break;
					}
				break;
			case 4://Bishop
				for (int move=0;move<4;move++)	
					for (int i=x+bishop[move][0],j=y+bishop[move][1];
						(i>=0)&&(i<8)&&(j>=0)&&(j<8);
						i+=bishop[move][0], j+=bishop[move][1])
					{
						if(board[j*8+i]==0)
						{
							a[n]=j*8+i;
							n++;
							continue;
						}
						if (board[j*8+i]*board[pos]<0)
						{
							a[n]=j*8+i;
							n++;
						}
						break;
					}
				break;
			case 5://Knight
				for (int move=0;move<8;move++)
				{
					int i=x+knight[move][0],j=y+knight[move][1];
					if ((i>=0)&&(i<8)&&(j>=0)&&(j<8))						
						if((board[j*8+i]==0)||(board[j*8+i]*board[pos]<0))
						{
							a[n]=j*8+i;
							n++;
						}
	
				}
				break;
			case 6://Pawn
				if (board[pos]<0)
				{
					
					int i=x+1,j=y;
					
					if (j==8) break;
					//System.out.println("heelo+"+x+","+y+"->"+i+","+j+" "+board[j*8+i]);
					if(board[j*8+i]==0)
					{
						a[n]=j*8+i;
						n++;
					}
					
					//add 1/2
					if( (j-1>=0)&&(board[(j-1)*8+i]*board[pos]<0))
					{
						a[n]=j*8+i-1;
						n++;
					}
					if ((j+1<8)&&(board[j*8+i+1]*board[pos]<0))
					{
						a[n]=j*8+i+1;
						n++;
					}
					
				}
				else
				{
					int i=x-1,j=y;
					if (j<0) break;
					if(board[j*8+i]==0)
					{
						a[n]=j*8+i;
						n++;
					}
					
					//add 1/2
					if( (j-1>=0)&&(board[(j-1)*8+i]*board[pos]<0))
					{
						a[n]=j*8+i-1;
						n++;
					}
					if ((j+1<8)&&(board[j*8+i+1]*board[pos]<0))
					{
						a[n]=j*8+i+1;
						n++;
					}
					
				}
					
				
				
				break;
					
		}
		int[] re = new int[n];
		
		for (int i=0;i<n;i++)
			re[i]=a[i];
		return re;
	}
	private static float[] minimax(int chess[], int depth, boolean player)
	{
		float[] value= new float[3];
		
		if (depth==0) {
						value[0]=0;
						return value;	
		}
		
		value[0] = minValue;
			for (int i=0;i<8;i++)
				for (int j=0;j<8;j++)
					
				{
					int pos = j*8+i;
					if (((chess[pos]>=0)&&(!player))||((chess[pos]<=0)&&(player))) continue;
					int move[] = getPossibleMove(i , j , chess);
					int x = chess[pos];
			//		System.out.println(i+","+j+" :"+chess[pos]);
					
					for (int k=0;k<move.length;k++)
				//	System.out.println("->"+(int)(move[k])%8+","+(int)(move[k])/8);
					
					chess[pos]=0;
					
					for (int k=0;k<move.length;k++)
					{
						int y = chess[move[k]];
						float score=0;
						switch (y)
						{
							case 1: score = 100;
							break;
							case 2: score = 10;
							break;
							case 3: score = 5.5f;
							break;
							case 4: score = 3.5f;
							break;
							case 5: score =1;
							break;
						}
						Random r = new Random();
						float ans =r.nextInt(500)+score - minimax(chess, depth-1, !player)[0]; 
						if (ans>value[0]) {
											value[0] = ans;
											value[1] = pos;
											value[2] = move[k];
						}
					}
					chess[pos] =x;
						
					
				}
		
		
		return value;
	}
	
	
	
	
	
	public static void main(String[] args) {  
	
		setChessBoard();
		
		for (int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
				System.out.print(chess[j*8+i]+" ");
			System.out.println();
		}
		
		int count=10;
		boolean swap = true;
		while (count>0){
			
		float[] step = minimax(chess,5,swap);
		System.out.println((int)(step[1])/8+","+(int)(step[1])%8+"->"+(int)(step[2])/8+","+(int)(step[2])%8);
		chess[(int)step[2]]=chess[(int)step[1]];
		chess[(int)step[1]]=0;
		swap = !swap;
		count--;
		
		
		
		}
	//	float[] step = minimax(chess,1,true);
	//	System.out.println((int)(step[1])%8+","+(int)(step[1])/8+"->"+(int)(step[2])%8+","+(int)(step[2])/8);
	
	}
} 
