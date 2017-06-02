package siima.models.jaxb.caex;

public class TEMP_Helpper {
	
	
	public static CAEXBasicObject insertCopyContent(CAEXBasicObject original){
		
		CAEXBasicObject newbasic = new CAEXBasicObject();
		newbasic.additionalInformation = original.getAdditionalInformation();
		
		return newbasic;
	}

}
