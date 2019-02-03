//	Gramatika pro deklaraci struktury v jazyce C
//	Struktura může obsahovat jen jednoduché deklarace proměnných 
//	typu int a; float b; char c; nebo vnořené struktury struct { int a; struct { int b; int c;} d; } e; 
//

//	Gramatika:
//	(0) S -> X
//	(1) X -> struct {V} P
//	(2) P -> a;|b;|c;|d; ... |z;
//	(3)	V -> X | M
//  (4) M -> TPF
//	(5)	T -> char | float | int
//	(6) F -> V
//	(7) F -> lambda

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParserStruct {

	private BufferedInputStream input;

	public static void main(String[] args) throws FileNotFoundException {
		ParserStruct l = new ParserStruct();
		try {
			l.X();
			System.out.println("A");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public ParserStruct() throws FileNotFoundException {

		input = new BufferedInputStream(System.in);
	}

	private int readChar() throws IOException {
		int c = input.read();
		return c;
	}

	private int peekChar() throws IOException {
		input.mark(1);
		int r = input.read();
		input.reset();
		return r;
	}

	private int readSymbol() throws IOException {
		int c = peekChar();
		while (Character.isSpaceChar(c)) {
			readChar();
			c = peekChar();
		}
		return readChar();
	}

	private int peekSymbol() throws IOException {
		int c = peekChar();
		while (Character.isSpaceChar(c)) {
			readChar();
			c = peekChar();
		}
		return c;
	}

	// X -> struct {V} P
	private void X() throws Exception {
		if (readSymbol() != 's') {
			throw new Exception("N");
		}
		String struct = "";
		for (int i = 0; i < 6; i++) {
			struct += String.valueOf((char) readSymbol());
		}
		if (!struct.equals("truct{")) {
			throw new Exception("N");
		}
		V();
		if (readSymbol() != '}') {
			throw new Exception("N");
		}
		P();
	}

	// P -> a;|b;|c;|d; ... |z;
	private void P() throws Exception {
		String character = (char) readSymbol() + "";
		if (!character.matches("[a-z]")) {
			throw new Exception("N");
		}
		if (readSymbol() != ';') {
			throw new Exception("N");
		}
	}

	// V -> X | M
	private void V() throws Exception {
		switch (peekSymbol()) {
		case 's': {
			X();
			break;
		}
		default:
			M();
			break;
		}
	}

	// M -> TPF
	private void M() throws Exception {
		T();
		P();
		F();
	}

	// F -> V | lambda
	private void F() throws Exception {
		String character2 = (char) peekSymbol() + "";
		switch (character2) {
		case "}":
			break;
		default:
			V();
		}
	}

	// T -> char | float | int
	private void T() throws Exception {
		switch (peekSymbol()) {
		case 'c': {
			readSymbol();
			String har = "";
			for (int i = 0; i < 3; i++) {
				har += String.valueOf((char) readSymbol());
			}
			if (!har.equals("har")) {
				throw new Exception("N");
			}
			break;
		}
		case 'f': {
			readSymbol();
			String loat = "";
			for (int i = 0; i < 4; i++) {
				loat += String.valueOf((char) readSymbol());
			}
			if (!loat.equals("loat")) {
				throw new Exception("N");
			}
			break;
		}
		case 'i': {
			readSymbol();
			String nt = "";
			for (int i = 0; i < 2; i++) {
				nt += String.valueOf((char) readSymbol());
			}
			if (!nt.equals("nt")) {
				throw new Exception("N");
			}
			break;
		}

		default: {
			throw new Exception("N");

		}

		}
	}

}