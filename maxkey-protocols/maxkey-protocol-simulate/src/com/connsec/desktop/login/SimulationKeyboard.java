package com.connsec.desktop.login;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * @author Crystal.Sea
 *
 */
public class SimulationKeyboard {
	//用于控制鼠标和键盘
	private Robot robot;

	public SimulationKeyboard() {
		try {
			this.robot = new Robot();
			// delay 2 min
			robot.delay(2000);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public SimulationKeyboard(Robot robot) {
		this.robot = robot;
	}
	
	
	public  void pressKey(int keyvalue) {
		robot.keyPress(keyvalue); 
		robot.keyRelease(keyvalue); 
	}

	public  void pressKeyWithCapsLock(int keyvalue) {
		// CapsLock
		robot.keyPress(KeyEvent.VK_CAPS_LOCK);
		robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
		// input Char
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);

		// release CapsLock
		robot.keyPress(KeyEvent.VK_CAPS_LOCK);
		robot.keyRelease(KeyEvent.VK_CAPS_LOCK);

	}

	public  void pressKeyWithShift( int keyvalue) {
		// Shift
		robot.keyPress(KeyEvent.VK_SHIFT);
		// input Char
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);

		// release Shift
		robot.keyRelease(KeyEvent.VK_SHIFT);

	}
	
	public  void pressKeyWithAlt( int keyvalue) {
		// Alt
		robot.keyPress(KeyEvent.VK_ALT);
		// input Char
		robot.keyPress(keyvalue);
		robot.keyRelease(keyvalue);

		// release Alt
		robot.keyRelease(KeyEvent.VK_ALT);

	}
	
	public  void pressEnter() {
		robot.delay(2000);
		pressKey(KeyEvent.VK_ENTER);
	}
	
	
	public void pressAltKey(char character){
		switch (character) {
	        case 'a'	:	pressKeyWithAlt(KeyEvent.VK_A); break;
	        case 'b'	:	pressKeyWithAlt(KeyEvent.VK_B); break;
	        case 'c'	:	pressKeyWithAlt(KeyEvent.VK_C); break;
	        case 'd'	:	pressKeyWithAlt(KeyEvent.VK_D); break;
	        case 'e'	:	pressKeyWithAlt(KeyEvent.VK_E); break;
	        case 'f'	:	pressKeyWithAlt(KeyEvent.VK_F); break;
	        case 'g'	:	pressKeyWithAlt(KeyEvent.VK_G); break;
	        case 'h'	:	pressKeyWithAlt(KeyEvent.VK_H); break;
	        case 'i'	:	pressKeyWithAlt(KeyEvent.VK_I); break;
	        case 'j'	:	pressKeyWithAlt(KeyEvent.VK_J); break;
	        case 'k'	:	pressKeyWithAlt(KeyEvent.VK_K); break;
	        case 'l'	:	pressKeyWithAlt(KeyEvent.VK_L); break;
	        case 'm'	:	pressKeyWithAlt(KeyEvent.VK_M); break;
	        case 'n'	:	pressKeyWithAlt(KeyEvent.VK_N); break;
	        case 'o'	:	pressKeyWithAlt(KeyEvent.VK_O); break;
	        case 'p'	:	pressKeyWithAlt(KeyEvent.VK_P); break;
	        case 'q'	:	pressKeyWithAlt(KeyEvent.VK_Q); break;
	        case 'r'	:	pressKeyWithAlt(KeyEvent.VK_R); break;
	        case 's'	:	pressKeyWithAlt(KeyEvent.VK_S); break;
	        case 't'	:	pressKeyWithAlt(KeyEvent.VK_T); break;
	        case 'u'	:	pressKeyWithAlt(KeyEvent.VK_U); break;
	        case 'v'	:	pressKeyWithAlt(KeyEvent.VK_V); break;
	        case 'w'	:	pressKeyWithAlt(KeyEvent.VK_W); break;
	        case 'x'	:	pressKeyWithAlt(KeyEvent.VK_X); break;
	        case 'y'	:	pressKeyWithAlt(KeyEvent.VK_Y); break;
	        case 'z'	:	pressKeyWithAlt(KeyEvent.VK_Z); break;
	        
	        case 'A'	:	pressKeyWithAlt( KeyEvent.VK_A); break;
	        case 'B'	:	pressKeyWithAlt( KeyEvent.VK_B); break;
	        case 'C'	:	pressKeyWithAlt( KeyEvent.VK_C); break;
	        case 'D'	:	pressKeyWithAlt( KeyEvent.VK_D); break;
	        case 'E'	:	pressKeyWithAlt( KeyEvent.VK_E); break;
	        case 'F'	:	pressKeyWithAlt( KeyEvent.VK_F); break;
	        case 'G'	:	pressKeyWithAlt( KeyEvent.VK_G); break;
	        case 'H'	:	pressKeyWithAlt( KeyEvent.VK_H); break;
	        case 'I'	:	pressKeyWithAlt( KeyEvent.VK_I); break;
	        case 'J'	:	pressKeyWithAlt( KeyEvent.VK_J); break;
	        case 'K'	:	pressKeyWithAlt( KeyEvent.VK_K); break;
	        case 'L'	:	pressKeyWithAlt( KeyEvent.VK_L); break;
	        case 'M'	:	pressKeyWithAlt( KeyEvent.VK_M); break;
	        case 'N'	:	pressKeyWithAlt( KeyEvent.VK_N); break;
	        case 'O'	:	pressKeyWithAlt( KeyEvent.VK_O); break;
	        case 'P'	:	pressKeyWithAlt( KeyEvent.VK_P); break;
	        case 'Q'	:	pressKeyWithAlt( KeyEvent.VK_Q); break;
	        case 'R'	:	pressKeyWithAlt( KeyEvent.VK_R); break;
	        case 'S'	:	pressKeyWithAlt( KeyEvent.VK_S); break;
	        case 'T'	:	pressKeyWithAlt( KeyEvent.VK_T); break;
	        case 'U'	:	pressKeyWithAlt( KeyEvent.VK_U); break;
	        case 'V'	:	pressKeyWithAlt( KeyEvent.VK_V); break;
	        case 'W'	:	pressKeyWithAlt( KeyEvent.VK_W); break;
	        case 'X'	:	pressKeyWithAlt( KeyEvent.VK_X); break;
	        case 'Y'	:	pressKeyWithAlt( KeyEvent.VK_Y); break;
	        case 'Z'	:	pressKeyWithAlt( KeyEvent.VK_Z); break;
		}
	}
	
	
    public void type(char character) {
        switch (character) {
	        case 'a'	:	pressKey(KeyEvent.VK_A); break;
	        case 'b'	:	pressKey(KeyEvent.VK_B); break;
	        case 'c'	:	pressKey(KeyEvent.VK_C); break;
	        case 'd'	:	pressKey(KeyEvent.VK_D); break;
	        case 'e'	:	pressKey(KeyEvent.VK_E); break;
	        case 'f'	:	pressKey(KeyEvent.VK_F); break;
	        case 'g'	:	pressKey(KeyEvent.VK_G); break;
	        case 'h'	:	pressKey(KeyEvent.VK_H); break;
	        case 'i'	:	pressKey(KeyEvent.VK_I); break;
	        case 'j'	:	pressKey(KeyEvent.VK_J); break;
	        case 'k'	:	pressKey(KeyEvent.VK_K); break;
	        case 'l'	:	pressKey(KeyEvent.VK_L); break;
	        case 'm'	:	pressKey(KeyEvent.VK_M); break;
	        case 'n'	:	pressKey(KeyEvent.VK_N); break;
	        case 'o'	:	pressKey(KeyEvent.VK_O); break;
	        case 'p'	:	pressKey(KeyEvent.VK_P); break;
	        case 'q'	:	pressKey(KeyEvent.VK_Q); break;
	        case 'r'	:	pressKey(KeyEvent.VK_R); break;
	        case 's'	:	pressKey(KeyEvent.VK_S); break;
	        case 't'	:	pressKey(KeyEvent.VK_T); break;
	        case 'u'	:	pressKey(KeyEvent.VK_U); break;
	        case 'v'	:	pressKey(KeyEvent.VK_V); break;
	        case 'w'	:	pressKey(KeyEvent.VK_W); break;
	        case 'x'	:	pressKey(KeyEvent.VK_X); break;
	        case 'y'	:	pressKey(KeyEvent.VK_Y); break;
	        case 'z'	:	pressKey(KeyEvent.VK_Z); break;
	        
	        case 'A'	:	pressKeyWithCapsLock( KeyEvent.VK_A); break;
	        case 'B'	:	pressKeyWithCapsLock( KeyEvent.VK_B); break;
	        case 'C'	:	pressKeyWithCapsLock( KeyEvent.VK_C); break;
	        case 'D'	:	pressKeyWithCapsLock( KeyEvent.VK_D); break;
	        case 'E'	:	pressKeyWithCapsLock( KeyEvent.VK_E); break;
	        case 'F'	:	pressKeyWithCapsLock( KeyEvent.VK_F); break;
	        case 'G'	:	pressKeyWithCapsLock( KeyEvent.VK_G); break;
	        case 'H'	:	pressKeyWithCapsLock( KeyEvent.VK_H); break;
	        case 'I'	:	pressKeyWithCapsLock( KeyEvent.VK_I); break;
	        case 'J'	:	pressKeyWithCapsLock( KeyEvent.VK_J); break;
	        case 'K'	:	pressKeyWithCapsLock( KeyEvent.VK_K); break;
	        case 'L'	:	pressKeyWithCapsLock( KeyEvent.VK_L); break;
	        case 'M'	:	pressKeyWithCapsLock( KeyEvent.VK_M); break;
	        case 'N'	:	pressKeyWithCapsLock( KeyEvent.VK_N); break;
	        case 'O'	:	pressKeyWithCapsLock( KeyEvent.VK_O); break;
	        case 'P'	:	pressKeyWithCapsLock( KeyEvent.VK_P); break;
	        case 'Q'	:	pressKeyWithCapsLock( KeyEvent.VK_Q); break;
	        case 'R'	:	pressKeyWithCapsLock( KeyEvent.VK_R); break;
	        case 'S'	:	pressKeyWithCapsLock( KeyEvent.VK_S); break;
	        case 'T'	:	pressKeyWithCapsLock( KeyEvent.VK_T); break;
	        case 'U'	:	pressKeyWithCapsLock( KeyEvent.VK_U); break;
	        case 'V'	:	pressKeyWithCapsLock( KeyEvent.VK_V); break;
	        case 'W'	:	pressKeyWithCapsLock( KeyEvent.VK_W); break;
	        case 'X'	:	pressKeyWithCapsLock( KeyEvent.VK_X); break;
	        case 'Y'	:	pressKeyWithCapsLock( KeyEvent.VK_Y); break;
	        case 'Z'	:	pressKeyWithCapsLock( KeyEvent.VK_Z); break;
	        
	        case '`'	:	pressKey(KeyEvent.VK_BACK_QUOTE); break;
	        case '0'	:	pressKey(KeyEvent.VK_0); break;
	        case '1'	:	pressKey(KeyEvent.VK_1); break;
	        case '2'	:	pressKey(KeyEvent.VK_2); break;
	        case '3'	:	pressKey(KeyEvent.VK_3); break;
	        case '4'	:	pressKey(KeyEvent.VK_4); break;
	        case '5'	:	pressKey(KeyEvent.VK_5); break;
	        case '6'	:	pressKey(KeyEvent.VK_6); break;
	        case '7'	:	pressKey(KeyEvent.VK_7); break;
	        case '8'	:	pressKey(KeyEvent.VK_8); break;
	        case '9'	:	pressKey(KeyEvent.VK_9); break;
	        case '-'	:	pressKey(KeyEvent.VK_MINUS); break;
	        case '='	:	pressKey(KeyEvent.VK_EQUALS); break;
	        
	        case '~'	:	pressKeyWithShift(KeyEvent.VK_BACK_QUOTE); break;
	        case '!'	:	pressKeyWithShift(KeyEvent.VK_1); break;
	        case '@'	:	pressKeyWithShift(KeyEvent.VK_2); break;
	        case '#'	:	pressKeyWithShift(KeyEvent.VK_3); break;
	        case '$'	:	pressKeyWithShift(KeyEvent.VK_4); break;
	        case '%'	:	pressKeyWithShift(KeyEvent.VK_5); break;
	        case '^'	:	pressKeyWithShift(KeyEvent.VK_6); break;
	        case '&'	:	pressKeyWithShift(KeyEvent.VK_7); break;
	        case '*'	:	pressKeyWithShift(KeyEvent.VK_8); break;
	        case '('	:	pressKeyWithShift(KeyEvent.VK_9); break;
	        case ')'	:	pressKeyWithShift(KeyEvent.VK_0); break;
	        case '_'	:	pressKeyWithShift(KeyEvent.VK_MINUS ); break;
	        case '+'	:	pressKeyWithShift(KeyEvent.VK_EQUALS); break;
	        
	        case '['	:	pressKey(KeyEvent.VK_OPEN_BRACKET); break;
	        case ']'	:	pressKey(KeyEvent.VK_CLOSE_BRACKET); break;
	        case '\\'	:	pressKey(KeyEvent.VK_BACK_SLASH); break;
	        case '{'	:	pressKeyWithShift( KeyEvent.VK_OPEN_BRACKET); break;
	        case '}'	:	pressKeyWithShift(KeyEvent.VK_CLOSE_BRACKET); break;
	        case '|'	:	pressKeyWithShift( KeyEvent.VK_BACK_SLASH); break;
	        
	        case ';'	:	pressKey(KeyEvent.VK_SEMICOLON); break;
	        case ':'	:	pressKeyWithShift(KeyEvent.VK_SEMICOLON); break;
	        case '\''	:	pressKey(KeyEvent.VK_QUOTE); break;
	        case '"'	:	pressKeyWithShift(KeyEvent.VK_QUOTE); break;
	       
	        case ','	:	pressKey(KeyEvent.VK_COMMA); break;
	        case '<'	:	pressKeyWithShift(KeyEvent.VK_COMMA); break;
	        case '.'	:	pressKey(KeyEvent.VK_PERIOD); break;
	        case '>'	:	pressKeyWithShift(KeyEvent.VK_PERIOD); break;
	        case '/'	:	pressKey(KeyEvent.VK_SLASH); break;
	        case '?'	:	pressKeyWithShift(KeyEvent.VK_SLASH); break;
	        
	        case ' '	:	pressKey(KeyEvent.VK_SPACE); break;
	        
	        case '\t'	:	pressKey(KeyEvent.VK_TAB); break;
	        case '\n'	:	robot.delay(2000);pressKey(KeyEvent.VK_ENTER); break;
	        default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

	public Robot getRobot() {
		return robot;
	}
    
}
