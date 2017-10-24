package com.game.battle;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Battleship {



	public static void main(String[] args) {

		int[][] board = new int[5][5];

		int[][] ships = new int[3][2];

		int[] shoot = new int[2];

		int attempts=0,

				shotHit=0;



		initBoard(board);

		initShips(ships);

		int ammo = Integer.parseInt(args[0]);

		int seeds = Integer.parseInt(args[1]); 

		int boat;      

		boat = 0;

		File file = new File(args[2]);

		if (ammo < 0) {

			System.out.println("ERROR - AMMO NOT GREATER THAN ZERO");

		}

		if (seeds < 0) {

			System.out.println("ERROR - SEED LESS THAN ZERO");

		}

		if (!args[2].contains(".txt")) {

			System.out.println("ERROR - FILE DOES NOT END WITH .TXT"); }

		Scanner sc = null;

		try{

			sc = new Scanner(file);

		} catch (IOException e) {

			System.out.println("ERROR - FILE DOES NOT EXIST");

			System.exit(0);

		}

		String line = sc.nextLine();

		if (line.contains(" ")) {

			String[] parts = line.split(", ");

			String rowsize = (parts[0]);

			String colsize = (parts[1]);

			int rows = Integer.parseInt(rowsize);

			int cols = Integer.parseInt(colsize);

			char[][] myArray = new char[rows][cols];   



			for(int k = 0; k < cols; k++) {

				System.out.print("=");

			}

			for(int i = 0; i < rows; i++) {

				System.out.println("");

				for(int j = 0; j < cols; j++) {

					System.out.print("*");

				}

			}

			System.out.println("");

			for(int k = 0; k < cols; k++) {

				System.out.print("=");

			}



			while(sc.hasNextLine()) {

				line = sc.nextLine();



				//System.out.println(line);

				if (line.length() != cols) {

					System.out.println("ERROR - COL MISMATCH");

					System.exit(0); }



				if (!line.matches("[=*B]+")) {

					System.out.println("ERROR - UNKNOWN SYMBOL");

					System.exit(0); }  



				if (line.indexOf("B") != -1) {

					boat = ++boat;

				}

			}  

			if (boat < 1 ) {

				System.out.println("ERROR - NO BOAT");

				System.exit(0); }



		}



		System.out.println();



		do{

			showBoard(board);

			shoot(shoot);

			attempts++;



			if(hit(shoot,ships)){

				hint(shoot,ships,attempts);

				shotHit++;

			}               

			else

				hint(shoot,ships,attempts);



			changeboard(shoot,ships,board);





		}while(shotHit!=3);



		System.out.println("\n\n\nBattleship Java game finished! You hit 3 ships in "+attempts+" attempts");

		showBoard(board);

	}



	public static void initBoard(int[][] board){

		for(int row=0 ; row < 5 ; row++ )

			for(int column=0 ; column < 5 ; column++ )

				board[row][column]=-1;

	}



	public static void showBoard(int[][] board){

		System.out.println("\t1 \t2 \t3 \t4 \t5");

		System.out.println();



		for(int row=0 ; row < 5 ; row++ ){

			System.out.print((row+1)+"");

			for(int column=0 ; column < 5 ; column++ ){

				if(board[row][column]==-1){

					System.out.print("\t"+"~");

				}else if(board[row][column]==0){

					System.out.print("\t"+"*");

				}else if(board[row][column]==1){

					System.out.print("\t"+"X");

				}



			}

			System.out.println();

		}



	}



	public static void initShips(int[][] ships){

		Random random = new Random();



		for(int ship=0 ; ship < 3 ; ship++){

			ships[ship][0]=random.nextInt(5);

			ships[ship][1]=random.nextInt(5);



			//let's check if that shot was already tried

			//if it was, just finish the do...while when a new pair was randomly selected

			for(int last=0 ; last < ship ; last++){

				if( (ships[ship][0] == ships[last][0])&&(ships[ship][1] == ships[last][1]) )

					do{

						ships[ship][0]=random.nextInt(5);

						ships[ship][1]=random.nextInt(5);

					}while( (ships[ship][0] == ships[last][0])&&(ships[ship][1] == ships[last][1]) );

			}



		}

	}



	public static void shoot(int[] shoot){

		Scanner input = new Scanner(System.in);



		System.out.print("Row: ");

		shoot[0] = input.nextInt();

		shoot[0]--;



		System.out.print("Column: ");

		shoot[1] = input.nextInt();

		shoot[1]--;



	}



	public static boolean hit(int[] shoot, int[][] ships){



		for(int ship=0 ; ship<ships.length ; ship++){

			if( shoot[0]==ships[ship][0] && shoot[1]==ships[ship][1]){

				System.out.printf("You hit a ship located in (%d,%d)\n",shoot[0]+1,shoot[1]+1);

				return true;

			}

		}

		return false;

	}



	public static void hint(int[] shoot, int[][] ships, int attempt){

		int row=0,

				column=0;



		for(int line=0 ; line < ships.length ; line++){

			if(ships[line][0]==shoot[0])

				row++;

			if(ships[line][1]==shoot[1])

				column++;

		}



		System.out.printf("\nHint %d: \nRow %d -> %d ships\n" +

                              "Column %d -> %d ships\n",attempt,shoot[0]+1,row,shoot[1]+1,column);

	}



	public static void changeboard(int[] shoot, int[][] ships, int[][] board){

		if(hit(shoot,ships))

			board[shoot[0]][shoot[1]]=1;

		else

			board[shoot[0]][shoot[1]]=0;

	}

}
