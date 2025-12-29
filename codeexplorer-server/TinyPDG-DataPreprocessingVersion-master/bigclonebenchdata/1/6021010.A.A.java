public class A{
    public static Dictionary parseVertices(URL url, Graph g) throws FileNotFoundException, FlightException {
        InputStream is = null;
        try {
            is = url.openStream();
        } catch (IOException e) {
            throw new FlightException("IO Error: cannot read from URL " + url.toString());
        }
        Reader reader = new BufferedReader(new InputStreamReader(is));
        return Parser.parseVertices(reader, g);
    }
}