import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String searchList = "";
    String[] lst;
    String display = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return searchList;
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                searchList = searchList + parameters[1];
                return parameters[1] + " has been added to the search list.";
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                lst = searchList.split(" ");
                for (int i = 0; i < searchList.length(); i++) {
                    if (lst[i].contains(parameters[1])) {
                        display = display + lst[i] + " ";
                    }
                }
                return display;
            }
        }
        return "404 Not Found!";
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
