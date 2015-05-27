import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class NeedCrawler
{

    public static void main(String[] args) throws Exception {
          System.out.println("reading Need File");
      int numberToCrawl = 1;
      Path path = Paths.get("C:/DATA/syim/needs.txt");
      //Path file = FileSystems.getFileSystem(uri).getPath("needs" +".txt");
      //Charset charset = Charset.forName("US-ASCII");

      List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
      List<URI> needURIs = null;
      int crawled = 0;

      for(int i =0;i<lines.size();i++)
      {
        String line = lines.get(i);
        System.out.println("printing line: "+line);
        String[] uris = line.split(", ");
        for(int j= 0; j<uris.length;j++){
          if(crawled<numberToCrawl){
            sendGet(uris[j]);
            crawled++;
          }else{
            break;
          }
          Thread.sleep(10000);
        }


              //Files files = files.readAllLines("US-ASCII")
      }

    }



  private static void sendGet(String needURI) throws Exception {


    URL obj = new URL(needURI);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // optional default is GET
    con.setRequestMethod("GET");

    //add request header
    con.setRequestProperty("User-Agent", "Mozilla/5.0");

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + needURI);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(
      new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    //print result
    System.out.println(response.toString());

    Files.write(Paths.get("C:/DATA/syim/needjson.txt"), response.toString().getBytes());

  }
}
