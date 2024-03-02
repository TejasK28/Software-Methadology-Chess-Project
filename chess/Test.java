package chess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;
// test class for Chess class
// Chess.play(String move) method will execute the move and return the result
// Chess.start() method will reset the board
// I want to test a sequence of moves and print the board after each move
// ReturnPlay Messages:
//	enum Message {ILLEGAL_MOVE, DRAW, 
//   RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
//   CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
//   STALEMATE};
// write a test sequence for testing above messages
public class Test {
    
    public static void main(String[] args) {
        // given a text file with a sequence of moves,
        // read the file and execute the moves
        // save output to a file
        // compare the output with expected output
        System.out.println(new File(".").getAbsolutePath());
        System.out.println("args[0]: " + args[0]);
        // open a text file
        try (BufferedReader br = new BufferedReader(new FileReader("./chess/" + args[0]));
                BufferedWriter bw = new BufferedWriter(new FileWriter("./chess/output.txt"))) {
            String move;
            // reset the board
            Chess.start();
            while ((move = br.readLine()) != null) {
                // write whose turn it is
                bw.write("Turn: " + Chess.whosPlaying);
                bw.newLine();

                // execute the move
                ReturnPlay result = Chess.play(move);
                
                // print the board
                // chess.printBoard();
                PlayChess.printBoard(result.piecesOnBoard);
                
                // print the result
                System.out.println("ReturnPlay.message: " + result.message);
                

                // write the result to the output file
                bw.write("Move: " + move + ", ReturnPlay.message: " + result.message);
                bw.newLine();
                bw.write("BOARD");
                bw.newLine();
                // write the board to the output file
                bw.write(Test.printBoard(result.piecesOnBoard));
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String printBoard(ArrayList<ReturnPiece> pieces) {
        StringBuilder sb = new StringBuilder();
        String[][] board = PlayChess.makeBlankBoard();
        if (pieces != null) {
            PlayChess.printPiecesOnBoard(pieces, board);
        }
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                sb.append(board[r][c]).append(" ");
            }
            sb.append(8 - r).append("\n");
        }
        sb.append(" a  b  c  d  e  f  g  h");
        return sb.toString();
    }
}
