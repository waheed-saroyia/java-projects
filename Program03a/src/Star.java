/**
 * This Java class represents a single Star. It will consist of a set of fields that will hold information 
 * from the “mag_5_stars.csv” database. 
 */


public class Star {

	private String name;
	private String id;
	private String constellationName;
	private int rightAscensionHours;
	private int rightAscensionMinutes;
	private double rightAscensionSeconds;
	private String declination;
	private int declinationDegrees;
	private int declinationMinutes;
	private double declinationSeconds;
	private double visualMagnitude;
	private double distance;
	
	
	/**
	 * +Star(string : String)
	 * This constructor takes a String object which must be tokenized using a comma “,” delimiter. 
	 * Once the string is tokenized, the array of tokens String objects are used to initialize the instance 
	 * fields of this class.
	 */
	public Star(String string)
	{
		//Tokenize the string to extract field values
		String[] tokens = string.trim().split(",");
		//could define a local variable to hold the token
		String token;
		
		// get the name from the tokens
		if(tokens[1].trim().length()>0)
		{
			this.name=tokens[1].trim();
		
		}
		else
		{
			this.name = "unamed";
		}
		
		
		
		
		// get the id from the tokens
		if(tokens[2].trim().length()>0)
		{
			this.id=tokens[2].trim();
				
		}
		else
		{
			this.name = "unamed";
		}
		
		// get the constellationName from the tokens
		if(tokens[7].trim().length()>0)
		{
			this.constellationName=tokens[7].trim();
						
		}
		else
		{
			this.constellationName = "unamed";
		}
				
		// get the rightAscensionHours from the tokens
		token = tokens[8].trim();
		if(token.length()>0)
		{
			this.rightAscensionHours=Integer.parseInt(token);
						
		}
		else
		{
			this.rightAscensionHours = -1;
		}
				
		// get the rightAscensionMinutes from the tokens
		token = tokens[9].trim();
		if(token.length()>0)
		{
			this.rightAscensionMinutes=Integer.parseInt(token);
						
		}
		else
		{
			this.rightAscensionMinutes = -1;
		}
				
		// get the rightAscensionSeconds from the tokens
		token = tokens[10].trim();
		if(token.length()>0)
		{
			this.rightAscensionSeconds=Double.parseDouble(token);
						
		}
		else
		{
			this.rightAscensionSeconds = -1;
		}
				
		// get the declination from the tokens
		if(tokens[11].trim().length()>0)
		{
			this.declination=tokens[11].trim();
						
		}
		else
		{
			this.declination = "unamed";
		}
		
		
		// get the declinationDegrees from the tokens
		token = tokens[12].trim();
		if(token.length()>0)
		{
			this.declinationDegrees = Integer.parseInt(token);
					
		}
		else
		{
			this.declinationDegrees = -1;
					
		}
		
		// get the declinationMinutes from the tokens
		token = tokens[13].trim();
		if(token.length()>0)
		{
			this.declinationMinutes = Integer.parseInt(token);
					
		}
		else
		{
			this.declinationMinutes = -1;
					
		}
				
		// get the declinationSeconds from the tokens
		token = tokens[14].trim();
		if(token.length()>0)
		{
			this.declinationSeconds = Double.parseDouble(token);
					
		}
		else
		{
			this.declinationSeconds = -1.0;
					
		}
				
		// get the visualMagnitude from the tokens
		token = tokens[15].trim();
		if(token.length()>0)
		{
			this.visualMagnitude = Double.parseDouble(token);
					
		}
		else
		{
			this.visualMagnitude = -1.0;
					
		}
		
		
		
		
		// get the distance from the tokens
		token = tokens[21].trim();
		if(token.length()>0)
		{
			this.distance = Double.parseDouble(token);
			
		}
		else
		{
			this.distance = -1.0;
			
		}
	}
	
	/**
	 * +getName() : String
	 * returns name of the star or "unnamed" if the star does  
	 * not have official name
	 * @return the name
	 */
	
	public String getName()
	{
		return name;
	}
	
	
	
	/**
	 * +getDistance() : double
	 * returns the distance of this star or -1.0
	 * if the star doesn't have a distance 
	 * @return the distance
	 */
	public double getDistance()
	{
		return this.distance;
	}
	
	
	/**
	* +getConstellationName() : String
	* Returns the constellation name of this star or "unnamed" if the star does
	* not have an official name.
	* @return the constellationName
	*/
	public String getConstellationName()
	{
		return this.constellationName;
	}
	
	
	
	/**
	* +getVisualMagnitude() : double
	* Returns the visual magnitude of this star or -1.0 if the star does
	* not have a visual magnitude.
	* @return the visualMagnitude
	*/
	public double getVisualMagnitude()
	{
		return this.visualMagnitude;
	}
	
	
	
	
	
	
	
	
	
	
	// +toString() : String
	@Override
	public String toString()
	{
		StringBuilder report = new StringBuilder();
		
		report.append(String.format("Name:   %s\n",this.name));
		report.append(String.format("ID:         %s\n",this.id));
		report.append(String.format("RA:       %dh ",this.rightAscensionHours));
		report.append(String.format("%dm ",this.rightAscensionMinutes));
		report.append(String.format("%.1fs\n",this.rightAscensionSeconds));
		report.append(String.format("Dec:      %s ",this.declination));
		report.append(String.format("%d\u00b0 ",this.declinationDegrees));
		report.append(String.format("%d\u2032 ",this.declinationMinutes));
		report.append(String.format("%.1f\u2033\n",this.declinationSeconds));
		report.append(String.format("Mag:      %.2f\n",this.visualMagnitude));
		report.append(String.format("LY:        %.2f",this.distance));
		
		
		
		return report.toString();
	}
}
		
	
	
	






