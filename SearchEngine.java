import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> words;
    public String handleRequest(URI url) {
	if (words == null){
	    words = new ArrayList<String>();
	}
        if (url.getPath().equals("/")) {
	    String listString = String.join(", ", words);
            return listString;
        } else {
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String word = parameters[1];
		    words.add(word);
                    return "Added word: " + word;
                }
            }
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String word = parameters[1];
		    String returned = "";
		    for (int i = 0; i < words.size(); i++){
		        if (words.get(i).contains(word)){
			    returned += words.get(i) + " ";
			}

		    }
		    return returned;
		    
                }
	    }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
