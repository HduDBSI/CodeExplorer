public class A{
    public static String obterConteudoSite(String u) {
        URL url;
        try {
            url = new URL(u);
            URLConnection conn = null;
            if (proxy != null) conn = url.openConnection(proxy.getProxy()); else conn = url.openConnection();
            conn.setDoOutput(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
            String line;
            StringBuilder resultado = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                resultado.append(line);
                resultado.append("\n");
            }
            rd.close();
            return resultado.toString();
        } catch (MalformedURLException e) {
            throw new AlfredException("Não foi possível obter contato com o site " + u, e);
        } catch (IOException e) {
            throw new AlfredException("Não foi possível obter contato com o site " + u, e);
        }
    }
}