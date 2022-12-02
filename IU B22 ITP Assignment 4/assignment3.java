import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This is the Main Class that contains all variables and method.
 */
public class Main {

    /**
     * declaring the class Board.
     */
    private static Board chessBoard = new Board(0);


    /**
     * initializing variable n (square chess board).
     */
    private static int n = 0;
    /**
     * initializing variable m (pieces on the board).
     */
    private static int m = 0;
    /**
     * initializing variable max.
     */
    final static int MAX = 1005000;
    /**
     * initializing onX.
     */
    private static int[] onX = new int[MAX];
    /**
     * initializing onY.
     */
    private static int[] onY = new int[MAX];
    /**
     * initializing the Variable name.
     */

    private static String[] name = new String[MAX];
    /**
     * initializing the Variable col.
     */

    private static String[] col = new String[MAX];
    /**
     * initializing the Variable inp.
     */

    private static String inp;
    /**
     * initializing the Variable input.
     */
    private static String[] input = new String[MAX];
    /**
     * initializing the Variable ind.
     */
    private static int ind = 0;

    static void read() throws IOException {

        BufferedReader r = new BufferedReader(new FileReader("input.txt"));
        while ((inp = r.readLine()) != null) {
            input[ind] = inp;
            ind++;
        }
        r.close();
    }

    /**
     * main method that contains all other methods inside it.
     *
     * @param args Each string inside the array is a command line argument.
     */
    public static void main(final String[] args) throws IOException,
            InvalidNumberOfPiecesException, InvalidPieceNameException,
            InvalidPieceColorException, InvalidPiecePositionException,
            InvalidGivenKingsException {
        try {
            read();
            int r = 0;
            final int bm1 = 3;
            final int bm1000 = 1000;
            for (int i = 0; i < ind; i++) {
                if (i == 0) {
                    isInteger(input[i], 0);
                    n = Integer.parseInt(input[i]);
                    if (n < bm1) {
                        throw new InvalidBoardSizeException();
                    }
                    if (n > bm1000) {
                        throw new InvalidBoardSizeException();
                    }
                } else if (i == 1) {
                    isInteger(input[i], 1);
                    m = Integer.parseInt(input[i]);
                    if (m < 2) {
                        throw new InvalidNumberOfPiecesException();
                    }
                    if (m > n * n) {
                        throw new InvalidNumberOfPiecesException();
                    }
                } else if (i > 1) {
                    String s = input[i];
                    int j = 0;
                    String l = "";
                    while (!(s.charAt(j) == ' ')) {
                        l += s.charAt(j);
                        j++;
                    }
                    name[r] = l;
                    boolean ok = (Objects.equals(name[r], "Pawn"))
                            | (Objects.equals(name[r], "King"))
                            | Objects.equals(name[r], "Queen");
                    ok |= (Objects.equals(name[r], "Rook"))
                            | (Objects.equals(name[r], "Bishop"))
                            | (Objects.equals(name[r], "Knight"));

                    if (!ok) {
                        throw new InvalidPieceNameException();
                    }
                    l = "";
                    j++;
                    while (!(s.charAt(j) == ' ')) {
                        l += s.charAt(j);
                        j++;
                    }
                    col[r] = l;
                    l = "";
                    j++;
                    boolean ok1 = (Objects.equals(col[r], "White")) | (Objects.equals(col[r], "Black"));
                    if (!ok1) {
                        throw new InvalidPieceColorException();
                    }
                    while (!(s.charAt(j) == ' ')) {
                        l += s.charAt(j);
                        j++;
                    }
                    isInteger(l, 2);
                    onY[r] = Integer.parseInt(l);
                    if (onY[r] < 1 || onY[r] > n) {
                        throw new InvalidPiecePositionException();
                    }
                    j++;
                    l = "";
                    while (j < s.length()) {
                        l += s.charAt(j);
                        j++;
                    }
                    isInteger(l, 2);
                    onX[r] = Integer.parseInt(l);
                    if (onX[r] < 1 || onX[r] > n) {
                        throw new InvalidPiecePositionException();
                    }
                    int q = onX[r];
                    onX[r] = n;
                    onX[r] -= q;
                    onX[r] += 1;
                    r++;
                }
            }
            chessBoard = new Board(n);
            if (ind - 2 != m) {
                throw new InvalidNumberOfPiecesException();
            }
            for (int i = 0; i < m; i++) {
                PiecePosition x = new PiecePosition(onX[i], onY[i]);
                int j = i + 1;
                while (j < m) {
                    PiecePosition y = new PiecePosition(onX[j], onY[j]);
                    boolean ok = Objects.equals(x.toString(), y.toString());
                    if (ok) {
                        throw new InvalidPiecePositionException();
                    }
                    j++;
                }
            }
            for (int i = 0; i < m; i++) {
                if (Objects.equals(name[i], "King")) {
                    int j = i + 1;
                    while (j < m) {
                        boolean ok = Objects.equals(col[i], col[j]);
                        boolean ok1 = Objects.equals(name[i], name[j]);
                        if (ok && ok1) {
                            throw new InvalidGivenKingsException();
                        }
                        j++;
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                ChessPiece add = new Queen(new PiecePosition(onX[i], onY[i]), PieceColor.parse(col[i]));
                chessBoard.addPiece(add);
            }
            if (n == -1) {
                throw new InvalidInputException();
            }
            BufferedWriter wr = new BufferedWriter(new FileWriter("output.txt"));
            for (int i = 0; i < m; i++) {
                PiecePosition position = new PiecePosition(onX[i], onY[i]);
                ChessPiece piece = chessBoard.getPiece(position);
                String answer1 = String.valueOf(chessBoard.getPiecePossibleMovesCount(piece, name[i]));
                String answer2 = String.valueOf(chessBoard.getPiecePossibleCapturesCount(piece, name[i]));
                wr.write(answer1 + " " + answer2);
                wr.write("\n");

            }
            wr.close();
        } catch (InvalidBoardSizeException ex) {
            write(ex.getMessage());
        } catch (InvalidGivenKingsException ex) {
            write(ex.getMessage());
        } catch (InvalidPieceColorException ex) {
            write(ex.getMessage());
        } catch (InvalidNumberOfPiecesException ex) {
            write(ex.getMessage());
        } catch (InvalidPieceNameException ex) {
            write(ex.getMessage());
        } catch (InvalidPiecePositionException ex) {

            write(ex.getMessage());

        } catch (InvalidInputException ex) {
            write(ex.getMessage());
        }

    }

    static void write(String s) throws IOException {
        BufferedWriter w = new BufferedWriter(new FileWriter("output.txt"));
        w.write(s);
        w.close();
    }

    static void isInteger(final String s, int p) throws InvalidBoardSizeException,
            InvalidNumberOfPiecesException,
            InvalidPiecePositionException {
        final int bm3 = 1;
        final int bm4 = 3;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                if (p == 0) {
                    throw new InvalidBoardSizeException();
                } else if (p == bm3) {
                    throw new InvalidNumberOfPiecesException();
                } else if (p == bm4) {
                    throw new InvalidPiecePositionException();
                }
            }
        }
    }

}

class Bishop extends ChessPiece implements BishopMovement {
    public Bishop(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }


    @Override
    public int getDiagonalMovesCount(PiecePosition position,
                                     PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();
        int a = x + 1;
        int b = y + 1;
        int possibleMoves = 0;
        while (exist(a, b, boardSize, positions)) {
            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a += 1;
            b += 1;
        }
        a = x - 1;
        b = y + 1;
        while (exist(a, b, boardSize, positions)) {

            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a -= 1;
            b += 1;

        }
        a = x - 1;
        b = y - 1;
        while (exist(a, b, boardSize, positions)) {

            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            b -= 1;
            a -= 1;

        }

        a = x + 1;
        b = y - 1;
        while (exist(a, b, boardSize, positions)) {

            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a += 1;
            b -= 1;

        }
        return possibleMoves;
    }

    @Override
    public int getDiagonalCapturesCount(PiecePosition position,
                                        PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();
        int a = x + 1;
        int b = y + 1;
        int possibleCaptures = 0;
        while (exist(a, b, boardSize, positions)) {
            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a += 1;
            b += 1;
        }
        a = x - 1;
        b = y + 1;
        while (exist(a, b, boardSize, positions)) {

            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a -= 1;
            b += 1;

        }
        a = x - 1;
        b = y - 1;
        while (exist(a, b, boardSize, positions)) {

            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            b -= 1;
            a -= 1;

        }

        a = x + 1;
        b = y - 1;
        while (exist(a, b, boardSize, positions)) {

            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a += 1;
            b -= 1;

        }
        return possibleCaptures;
    }
}

interface BishopMovement {
    public int getDiagonalMovesCount(PiecePosition position,
                                     PieceColor color, Map<String,
            ChessPiece> positions, int boardSize);

    public int getDiagonalCapturesCount(PiecePosition position,
                                        PieceColor color, Map<String,
            ChessPiece> positions, int boardSize);
}

class Board {
    /**
     * declaring positionsToPieces.
     */
    private Map<String, ChessPiece> positionsToPieces =
            new HashMap<String, ChessPiece>();
    /**
     * initializing the variable size.
     */
    private int size;

    /**
     * this function is to know the board size.
     *
     * @param boardSize parameter from type int represent the board size.
     */
    public Board(final int boardSize) {
        this.size = boardSize;
    }

    /**
     * @param piece parameter from type ChessPiece represent the type of the piece.
     * @param name  parameter from type String represent the name.
     * @return the possible count moves. type integer
     */
    public int getPiecePossibleMovesCount(
            final ChessPiece piece, final String name) {
        ChessPiece a = new Queen(piece.getPosition(), piece.getColor());
        int answer = a.getMovesCount(positionsToPieces, size, name);
        return answer;
    }

    /**
     * @param piece parameter from type ChessPiece represent the type of the piece.
     * @param name  parameter from type String represent the name.
     * @return the piece possible capture count type integer.
     */
    public int getPiecePossibleCapturesCount(
            final ChessPiece piece, final String name) {
        ChessPiece a = new Queen(piece.getPosition(), piece.getColor());
        int answer = a.getCaptureCount(positionsToPieces, size, name);
        return answer;
    }

    /**
     * This function is used to add a new piece.
     *
     * @param piece a type ChessPiece parameter.
     */
    public void addPiece(final ChessPiece piece) {
        PiecePosition pi = piece.getPosition();
        String name = pi.toString();
        positionsToPieces.put(name, piece);
    }

    /**
     * @param position a type PiecePosition parameter.
     * @return the piece position.
     */
    public ChessPiece getPiece(final PiecePosition position) {
        String s = position.toString();
        return positionsToPieces.get(s);
    }
}

abstract class ChessPiece {
    /**
     * creating PiecePosition class.
     */
    protected PiecePosition position;
    /**
     * creating PieceColor class.
     */
    protected PieceColor color;

    /**
     * @param pieceColor    a type PieceColor parameter that represent the color of the piece.
     * @param piecePosition a type piecePosition parameter that represent the position of the piece.
     */
    public ChessPiece(
            final PiecePosition piecePosition, final PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }

    boolean exist(final int x, final int y, final int n, final Map<String, ChessPiece> positions) {
        if (x < 1 || x > n || y < 1 || y > n) {
            return false;
        }
        PiecePosition p = new PiecePosition(x, y);

        if (positions.containsKey(p.toString())) {
            ChessPiece a = positions.get(p.toString());
            PieceColor currentColor = a.getColor();
            //System.out.println(color+" "+currentColor);
            //System.out.println((color == currentColor));
            if (color == currentColor) {
                return false;
            }
        }
        return true;
    }

    boolean sameColor(final int i, final int j, final Map<String, ChessPiece> positions) {
        PiecePosition p = new PiecePosition(i, j);

        if (positions.containsKey(p.toString())) {
            ChessPiece a = positions.get(p.toString());
            PieceColor currentColor = a.getColor();
            if (color == currentColor) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }


    /**
     * @return the position of the piece.
     */
    public PiecePosition getPosition() {
        return position;
    }

    /**
     * @return the color of the piece.
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * @param boardSize a parameter that represent the board size.
     * @param name      a parameter that represent the name of the piece.
     * @param positions a parameter that represent the position of the piece.
     * @return the possible moves that the piece can do.
     */
    public int getMovesCount(final Map<String, ChessPiece> positions, final int boardSize, final String name) {

        if (Objects.equals(name, "Queen") || Objects.equals(name, "Bishop") || Objects.equals(name, "Rook")) {
            int possibleMoves = 0;
            if (Objects.equals(name, "Queen")) {
                Queen queen = new Queen(position, color);
                possibleMoves = queen.getOrthogonalMovesCount(position, color,
                        positions, boardSize) + queen.getDiagonalMovesCount(position, color, positions, boardSize);

            } else if (Objects.equals(name, "Rook")) {
                Rook rook = new Rook(position, color);
                possibleMoves = rook.getOrthogonalMovesCount(
                        position, color, positions, boardSize);
            } else if (Objects.equals(name, "Bishop")) {
                Bishop bishop = new Bishop(position, color);
                possibleMoves = bishop.getDiagonalMovesCount(
                        position, color, positions, boardSize);
            }
            return possibleMoves;
        } else {
            int onX = position.getX();
            int onY = position.getY();
            int possibleMoves = 0;
            if (Objects.equals(name, "Pawn")) {
                if (PieceColor.BLACK == color) {
                    if (exist(onX + 1, onY, boardSize, positions)) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {

                    if (exist(onX - 1, onY, boardSize, positions)) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            } else if (Objects.equals(name, "King")) {
                int[] dx;
                dx = new int[]{1, 1, 1, -1, -1, -1, 0, 0};
                int[] dy;
                dy = new int[]{1, 0, -1, 1, 0, -1, 1, -1};

                final int bm5 = 8;
                for (int i = 0; i < bm5; i++) {
                    int x = onX + dx[i];
                    int y = onY + dy[i];

                    if (exist(x, y, boardSize, positions)) {
                        possibleMoves++;
                    }
                }
            } else if (Objects.equals(name, "Knight")) {
                final int bm6 = -2;
                int[] dx;
                dx = new int[]{2, 2, bm6, bm6, 1, -1, 1, -1};
                int[] dy;
                dy = new int[]{1, -1, 1, -1, 2, 2, bm6, bm6};
                final int bm9 = 8;
                for (int i = 0; i < bm9; i++) {
                    int x = onX + dx[i];
                    int y = onY + dy[i];

                    if (exist(x, y, boardSize, positions)) {
                        possibleMoves++;
                    }
                }
            }
            return possibleMoves;
        }

    }

    public int getCaptureCount(final Map<String, ChessPiece> positions,
                               final int boardSize, final String name) {
        if (Objects.equals(name, "Queen") || Objects.equals(name, "Bishop") || Objects.equals(name, "Rook")) {
            int possibleCaptures = 0;
            if (Objects.equals(name, "Queen")) {
                Queen queen = new Queen(position, color);
                possibleCaptures = queen.getOrthogonalCapturesCount(position, color,
                        positions, boardSize) + queen.getDiagonalCapturesCount(position, color, positions, boardSize);

            } else if (Objects.equals(name, "Rook")) {
                Rook rook = new Rook(position, color);
                possibleCaptures = rook.getOrthogonalCapturesCount(
                        position, color, positions, boardSize);
            } else if (Objects.equals(name, "Bishop")) {
                Bishop bishop = new Bishop(position, color);
                possibleCaptures = bishop.getDiagonalCapturesCount(
                        position, color, positions, boardSize);
            }
            return possibleCaptures;
        } else {
            int onX = position.getX();
            int onY = position.getY();
            int possbileCaptures = 0;
            if (Objects.equals(name, "Pawn")) {
                if (PieceColor.BLACK == color) {

                    if ((exist(onX + 1, onY + 1, boardSize, positions)
                            && !sameColor(onX + 1, onY + 1, positions))
                            && exist(onX + 1, onY - 1, boardSize, positions)
                            && !sameColor(onX + 1, onY - 1, positions)) {
                        possbileCaptures += 2;
                    } else if ((exist(onX + 1, onY + 1, boardSize, positions)
                            && !sameColor(onX + 1, onY + 1, positions))
                            || (exist(onX + 1, onY - 1, boardSize, positions)
                            && !sameColor(onX + 1, onY - 1, positions))) {
                        possbileCaptures += 1;
                    }
                } else {
                    if ((exist(onX - 1, onY + 1, boardSize, positions)
                            && !sameColor(onX - 1, onY + 1, positions))
                            && exist(onX - 1, onY - 1, boardSize, positions)
                            && !sameColor(onX - 1, onY - 1, positions)) {
                        possbileCaptures += 2;
                    } else if ((exist(onX - 1, onY + 1, boardSize, positions)
                            && !sameColor(onX - 1, onY + 1, positions))
                            && exist(onX - 1, onY - 1, boardSize, positions)
                            && !sameColor(onX - 1, onY - 1, positions)) {
                        possbileCaptures += 1;
                    }

                }
            } else if (Objects.equals(name, "King")) {
                int[] dx;
                dx = new int[]{1, 1, 1, -1, -1, -1, 0, 0};
                int[] dy;
                dy = new int[]{1, 0, -1, 1, 0, -1, 1, -1};
                final int bm10 = 8;
                for (int i = 0; i < bm10; i++) {
                    int x = onX + dx[i];
                    int y = onY + dy[i];

                    if (exist(x, y, boardSize, positions) && !sameColor(x, y, positions)) {
                        possbileCaptures++;
                    }
                }
            } else if (Objects.equals(name, "Knight")) {
                final int bm7 = -2;
                int[] dx;
                dx = new int[]{2, 2, bm7, bm7, 1, -1, 1, -1};
                int[] dy;
                dy = new int[]{1, -1, 1, -1, 2, 2, bm7, bm7};
                final int bm11 = 8;
                for (int i = 0; i < bm11; i++) {
                    int x = onX + dx[i];
                    int y = onY + dy[i];

                    if (exist(x, y, boardSize, positions) && !sameColor(x, y, positions)) {
                        possbileCaptures++;
                    }
                }
            }
            return possbileCaptures;
        }
    }
}

class InvalidBoardSizeException extends Exception {
    public String getMessage() {
        return "Invalid board size";
    }
}

class InvalidGivenKingsException extends Exception {
    public String getMessage() {
        return "Invalid given Kings";
    }
}

class InvalidInputException extends Exception {
    public String getMessage() {
        return "Invalid input";
    }
}

class InvalidNumberOfPiecesException extends Exception {
    public String getMessage() {
        return "Invalid number of pieces";
    }
}

class InvalidPieceColorException extends Exception {
    public String getMessage() {
        return "Invalid piece color";
    }
}

class InvalidPieceNameException extends Exception {
    public String getMessage() {
        return "Invalid piece name";
    }
}

class InvalidPiecePositionException extends Exception {
    public String getMessage() {
        return "Invalid piece position";
    }
}

class King extends ChessPiece {
    public King(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
}

class Knight extends ChessPiece {
    public Knight(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
}

class Pawn extends ChessPiece {
    public Pawn(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }
}

/**
 * The available color for the pieces.
 */
enum PieceColor {
    /**
     * the color white.
     */
    WHITE,
    /**
     * the color white.
     */
    BLACK;

    public static PieceColor parse(String a) {
        if (Objects.equals(a, "White")) {
            return PieceColor.WHITE;
        } else {
            return PieceColor.BLACK;
        }
    }

}

class PiecePosition {
    /**
     * initializing the variable x.
     */
    private int x;
    /**
     * initializing the variable y.
     */
    private int y;

    public PiecePosition(int onX, int onY) {
        this.x = onX;
        this.y = onY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "PiecePosition{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    public Queen(PiecePosition piecePosition, PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public int getDiagonalMovesCount(PiecePosition position,
                                     PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        Bishop bishop = new Bishop(position, color);
        int answer = bishop.getDiagonalMovesCount(position,
                color, positions, boardSize);
        return answer;
    }

    @Override
    public int getDiagonalCapturesCount(PiecePosition position,
                                        PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        Bishop bishop = new Bishop(position, color);
        int answer = bishop.getDiagonalCapturesCount(position, color, positions, boardSize);
        return answer;
    }

    @Override
    public int getOrthogonalMovesCount(PiecePosition position,
                                       PieceColor color, Map<String,
            ChessPiece> positions, int boardSize) {
        Rook rook = new Rook(position, color);
        int answer = rook.getOrthogonalMovesCount(position, color, positions, boardSize);
        return answer;
    }

    @Override
    public int getOrthogonalCapturesCount(PiecePosition position, PieceColor color,
                                          Map<String, ChessPiece> positions, int boardSize) {
        Rook rook = new Rook(position, color);
        int answer = rook.getOrthogonalCapturesCount(position, color, positions, boardSize);
        return answer;
    }
}

class Rook extends ChessPiece implements RookMovement {
    /**
     * @param piecePosition a parameter that represent the position of the piece.
     * @param pieceColor    a parameter that represent the color of the piece.
     */
    public Rook(final PiecePosition piecePosition,
                final PieceColor pieceColor) {
        super(piecePosition, pieceColor);
    }


    /**
     * @param position  a type PiecePosition that represent the piece position.
     * @param color     a type PieceColor that represent the piece color.
     * @param positions a parameter that represent the valid positions.
     * @param boardSize a parameter that represent the board size.
     * @return the possible moves that a piece can do.
     */

    @Override
    public int getOrthogonalMovesCount(final PiecePosition position,
                                       final PieceColor color,
                                       final Map<String, ChessPiece> positions,
                                       final int boardSize) {
        int x = position.getX();
        int y = position.getY();
        int a = x + 1;
        int b = y;
        int possibleMoves = 0;
        while (exist(a, b, boardSize, positions)) {
            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a += 1;
        }
        a = x - 1;
        while (exist(a, b, boardSize, positions)) {

            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a -= 1;

        }
        a = x;
        b = y - 1;
        while (exist(a, b, boardSize, positions)) {

            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            b -= 1;

        }

        b = y + 1;
        while (exist(a, b, boardSize, positions)) {

            possibleMoves += 1;
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            b += 1;

        }
        return possibleMoves;
    }

    /**
     * @param position  a type PiecePosition that represent the piece position.
     * @param color     a type PieceColor that represent the piece color.
     * @param positions a parameter that represent the valid positions.
     * @param boardSize a parameter that represent the board size.
     * @return
     */


    @Override
    public int getOrthogonalCapturesCount(final PiecePosition position,
                                          final PieceColor color,
                                          final Map<String, ChessPiece>
                                                  positions,
                                          final int boardSize) {
        int x = position.getX();
        int y = position.getY();
        int a = x + 1;
        int b = y;
        int possibleCaptures = 0;
        while (exist(a, b, boardSize, positions)) {
            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a += 1;
        }
        a = x - 1;
        while (exist(a, b, boardSize, positions)) {

            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            a -= 1;

        }
        a = x;
        b = y - 1;
        while (exist(a, b, boardSize, positions)) {

            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            b -= 1;

        }

        b = y + 1;
        while (exist(a, b, boardSize, positions)) {

            if (!sameColor(a, b, positions)) {
                possibleCaptures += 1;
            }
            PiecePosition p = new PiecePosition(a, b);
            if (positions.containsKey(p.toString())) {
                break;
            }
            b += 1;

        }
        return possibleCaptures;
    }
}

interface RookMovement {
    public int getOrthogonalMovesCount(PiecePosition position,
                                       PieceColor color, Map<String,
            ChessPiece> positions, int boardSize);

    public int getOrthogonalCapturesCount(PiecePosition position,
                                          PieceColor color, Map<String,
            ChessPiece> positions, int boardSize);
}

 
