import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Class which saves the current world to a text file.
 * @author dpendergast
 *
 */
public class FileSaver {
	
	public static void saveFile(String filename, World world) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename,"UTF-8");
		
		//naming all the states
		int i = 0;
		for(State s : world.states){
			if(s.name().equals("")){
				s.setName("S"+(i++));
			}
		}
		
		//saving state data
		for(State s : world.states){
			String line = s.name() + " "+ s.x() + " " + s.y() + " " + s.isSuccessState();
			writer.println(line);
		}
		
		//empty line
		writer.println("");
		
		//saving link data
		for(Link l : world.links){
			//skip incomplete links
			if(l.from() == null || l.to() == null)
				continue;
			String line = l.from().name() + " " + l.to().name() + " " + l.cx + " "+ l.cy + " " +l.conditions; 
			writer.println(line);
		}
		
		writer.close();
	}
}
