import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class which loads the saved FSMs.
 * @author dpendergast
 *
 */
public class FileLoader {
	
	public static void loadFile(String filename, World w) throws IOException{
		w.clear();
		w.states.clear();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		//Getting state data
		String line = reader.readLine();
		
		states:
		while(line != null){
			String[] text = line.split(" ");
			if(text.length == 1)
				break states;
			if(text.length != 4){
				reader.close();
				throw new IOException("Invalid file contents: "+line);
			}
			
			String name = text[0];
			int x = Integer.parseInt(text[1]);
			int y = Integer.parseInt(text[2]);
			boolean is_success = Boolean.parseBoolean(text[3]);
			
			State new_state = new State(x,y);
			new_state.setName(name);
			new_state.setIsSuccessState(is_success);
			w.addState(new_state);
			
			line = reader.readLine();
		}
		
		line = reader.readLine();
		links:
		while(line != null){
			String[] text = line.split(" ");
			if(text.length == 1)
				break links;
			if(text.length != 5){
				reader.close();
				throw new IOException("Invalid file contents: "+line);
			}
				
			String s1 = text[0];
			String s2 = text[1];
			int cx = Integer.parseInt(text[2]);
			int cy = Integer.parseInt(text[3]);
			String con = text[4];
			
			State state_1 = w.getState(s1);
			State state_2 = w.getState(s2);
			if(state_1 == null || state_2 == null){
				reader.close();
				throw new IOException("Invalid file contents: Link's state not found:"+line);
			}
			
			Link new_link = new Link(state_1, state_2);
			new_link.setC(cx, cy);
			new_link.setConditions(con);
			w.addLink(new_link);
			line = reader.readLine();
		}
		
		w.logic_handler = new LogicHandler(w, w.states.get(0));
		
		System.out.println("completed loading");
		
	}

}
