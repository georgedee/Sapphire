package application;

public class StringStrategy extends FormatStrategy{
	
	@Override
	public String Format(String Heading, String Body){
		return Heading + "\n\n" + Body;
	}
}
