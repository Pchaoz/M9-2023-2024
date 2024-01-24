package Util;

public class BinaryMessage {
	
	public final byte ACK = 0b00000001;
	public final byte S_BENVINGUT = 0b00000010;
	public final byte S_NICK_EN_US = 0b00000011;
	public final byte S_ESTAS_DINS = 0b00000100;
	public final byte S_EN_CURS = 0b00000101;
	public final byte S_NO_BALA = 0b00000110;
	public final byte S_BALA = 0b00000111;
	public final byte S_FINALISTA = 0b00001000;
	public final byte S_CONTINUAR = 0b00001001;
	public final byte C_SEGUIR = 0b00001010;
	public final byte C_PLEGAR = 0b00001011;
	
	public String ByteToString (byte b) {
		
		switch(b) {
			
			case 0b00000001:
				return "ACK";
			case 0b00000010:
				return "S_BENVINGUT";
			case 0b00000011:
				return "S_NICK_EN_US";
			case 0b00000100:
				return "S_ESTAS_DINS";
			case 0b00000110:
				return "S_NO_BALA";
			case 0b00000111:
				return "S_BALA";
			case 0b00001000:
				return "S_FINALISTA";
			case 0b00001001:
				return "S_CONTINUAR";
			case 0b00001010:
				return "C_SEGUIR";
			case 0b00001011:
				return "C_PLEGAR";
			default:
				return "WRONG PROTOCOL";
		}
	}
}