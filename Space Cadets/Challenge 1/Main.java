import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) 
    throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String emailId;

        System.out.println("Please enter an email id.");
        emailId = reader.readLine();
        
        String webAddress = "https://www.ecs.soton.ac.uk/people/" + emailId;
        URL webURL = new URL(webAddress);
        BufferedReader webReader = new BufferedReader(new InputStreamReader(webURL.openStream()));

        String content;
        while ((content = webReader.readLine()) != null) {
            if (content.contains("property=\"og:title\"")) {
                String name = content.substring(35);
                name = name.substring(0, name.indexOf("\""));
                System.out.println(name);
                break;
            }
        }   
        webReader.close();
    }
}