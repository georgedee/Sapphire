package application;

public class AltHTMLStrategy extends FormatStrategy{

	@Override
	public String Format(String Heading, String Body){
		return "<HTML>\n<HEAD>\n<TITLE>Test Title</TITLE>\n\n<BODY>\n<H1>" +
				Heading + "<H1>\n\n" + Body + "\n</BODY>\n</HTML>";
	}
}
