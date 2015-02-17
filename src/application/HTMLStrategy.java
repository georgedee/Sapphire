package application;

public class HTMLStrategy extends FormatStrategy{
	
	@Override
	public String Format(String Heading, String Body){
		return "<HTML>\n<HEAD>\n<TITLE>" + Heading + 
				"</TITLE>\n\n<BODY>\n" + Body + "\n</BODY>\n</HTML>";
	}

}
